package net.treewoods.myclip.service;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import net.treewoods.myclip.dao.ArticleFacade;
import net.treewoods.myclip.dao.CollectArticleFacade;
import net.treewoods.myclip.dao.CollectInfoFacade;
import net.treewoods.myclip.dao.CollectSiteFacade;
import net.treewoods.myclip.entity.Article;
import net.treewoods.myclip.entity.CollectArticle;
import net.treewoods.myclip.entity.CollectInfo;
import net.treewoods.myclip.entity.CollectSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSS取得 一定時間毎に、RSSを取得する。
 *
 * @author kido
 */
@Singleton
@Startup
public class GetRssService {

    // ロガーのインスタンス
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Inject
    private CollectSiteFacade collectTargetFacade;
    @Inject
    private ArticleFacade articleFacade;
    @Inject
    private CollectInfoFacade collectInfoFacade;
    @Inject
    private CollectArticleFacade collectArticleFacade;

    @PostConstruct
    @Schedule(hour = "*/1", dayOfMonth = "*")
    //@Schedule(hour="*", minute="*", second="*/30")
    public void execute() {
        try {
            log.info("GetRssService START");

            // 取得情報を生成
            CollectInfo collectInfo = new CollectInfo();
            collectInfo.setCollectArticleList(new ArrayList<CollectArticle>());
            // 取得情報を生成
	    collectInfo.setCreatedAt(new Date());
	    collectInfo.setUpdatedAt(new Date());
            this.collectInfoFacade.create(collectInfo);	    


	    // 取得対象取得
            List<CollectSite> targetList = this.collectTargetFacade.findAll();

            // 取得対象の数だけ繰り返し
            for (CollectSite target : targetList) {
		log.info("target {}",target.getSiteName());
                if (target.getFlgDelete() == '1') {
                    continue;
                }
                // RSS取得
                URL feedUrl;
                try {
                    feedUrl = new URL(target.getUrl());
                } catch (MalformedURLException ex) {
                    // 不正な形式のURL文字列
                    // 通常マスタの設定ミスでしか発生しないため、RuntimeExceptionをスローしてもよいと思われる
                    log.warn(ex.getLocalizedMessage(), ex);
                    // 次の取得対象へ
                    continue;
                }
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed;
                try {
                    feed = input.build(new XmlReader(feedUrl));
                } catch (IllegalArgumentException | FeedException | IOException ex) {
                    // 取得対象のサイトが見つからなかった、
                    // 取得対象のサイトが不正なXMLを送信してきた、
                    // 等、自アプリケーションでは防げない事象であるため、警告レベルとして処理を続行する
                    log.warn(ex.getLocalizedMessage(), ex);
                    // 次の取得対象へ
                    continue;
                }

                // 取得元情報を更新
                target.setSiteName(feed.getTitle());
		if ( feed.getPublishedDate() != null )
		{
		    target.setLastPubAt(feed.getPublishedDate());
		}
		else
		{
		    target.setLastPubAt(new Date());		    
		}
                this.collectTargetFacade.edit(target);

                // 記事の数だけ繰り返し
                for (Object obj : feed.getEntries()) {
                    SyndEntry entry = (SyndEntry) obj;
                    // 記事のURLで、保存している記事を取得
                    List<Article> resultList = this.articleFacade.findByArticleUrl(entry.getLink());
                    if (resultList.isEmpty()) {
                        // 取得できない場合、詳細を取得
                        Article article = new Article();
                        article.setArticleTitle(entry.getTitle());
                        article.setArticleUrl(entry.getLink());
                        article.setSiteId(target);
			article.setCreatedAt(new Date());
			article.setPublishAt(new Date());
			article.setUpdatedAt(new Date());

                        if (entry.getDescription() != null) {
                            String tmp = this.removeHtmlTag(entry.getDescription().getValue());
                            //65535
                            if (tmp.getBytes().length > 65535) {
                                log.warn("Data too long={}", tmp);
                                continue;
                            }

                            try {
                                article.setArticleContents(tmp.getBytes("UTF8"));
                            } catch (UnsupportedEncodingException ex) {
                                log.error(ex.getLocalizedMessage(), ex);
                                // UnsupportedEncodingExceptionが発生するのはバグ以外ない
                                // RuntimeExceptionをスローしてロールバックさせる
                                throw new RuntimeException(ex);
                            }
                        }

                        article.setCreatedAt(entry.getPublishedDate());
			
			if (entry.getUpdatedDate() != null) 
			{
			    article.setUpdatedAt(entry.getUpdatedDate());
			}
			else
			{
			    article.setUpdatedAt(new Date());
			}

                        this.articleFacade.create(article);

                        CollectArticle collectArticle = new CollectArticle();
                        collectArticle.setCollectId(collectInfo);
                        collectArticle.setArticleId(article);
			collectArticle.setCreatedAt(new Date());
			collectArticle.setUpdatedAt(new Date());			
			log.info("ArticleFacade.create {}",collectArticle.getArticleId());

                        this.collectArticleFacade.create(collectArticle);

                        collectInfo.getCollectArticleList().add(collectArticle);

                    } else {
                        // 取得できた場合、取得できたものを使用する。
                        for (Article article : resultList) {
                            CollectArticle collectArticle = new CollectArticle();
                            collectArticle.setCollectId(collectInfo);
                            collectArticle.setArticleId(article);
			    collectArticle.setCreatedAt(new Date());
			    collectArticle.setUpdatedAt(new Date());

                            this.collectArticleFacade.create(collectArticle);
                            collectInfo.getCollectArticleList().add(collectArticle);
                        }

                    }
                }
            }	  
	    
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
                log.error("size:" + cve.getConstraintViolations().size());
                ConstraintViolation v = cve.getConstraintViolations().iterator().next();
                log.error("class:" + v.getRootBeanClass().getSimpleName());
                log.error("field:" + v.getPropertyPath().toString());
                log.error("type:" + v.getConstraintDescriptor().getAnnotation().annotationType());
                log.error("message:" + v.getMessage());
            }
            this.log.error("致命的例外", e);
            throw e;
        } finally {
            log.info("GetRssService FINISH");
        }

    }

    /**
     * 文字列から特定のタグ以外の<>を実体参照へ置き換える。
     *
     * @param src
     * @return
     */
    protected String removeHtmlTag(String src) {
        char[] toCharArray = src.toCharArray();
        src = src.toUpperCase();

        // 許可するタグ
        final String[] allowTags = {"P", "IMG", "BR", "TABLE", "TBODY", "TR", "TD", "SPAN", "DIV", "A", "!", "BLOCKQUOTE"};

        // < > に囲まれた1文字以上の文字列
        Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) { // 検索(find)し、マッチする部分文字列がある限り繰り返す
            for (int j = 0; j <= matcher.groupCount(); j++) { // グループの個数繰り返す
                // group, start, end各メソッドでマッチした文字列に関する情報を得る
                //System.out.println(matcher.group(j) + "," + matcher.start(j) + "-" + matcher.end(j));
                String group = matcher.group(j);

                boolean isRemove = true;

                for (String allowTag : allowTags) {
                    if (group.contains(allowTag)) {
                        isRemove = false;
                        break;
                    }
                }

                if (isRemove) {
                    toCharArray[matcher.start(j)] = 0x20;
                    toCharArray[matcher.end(j) - 1] = 0x20;
                }
            }
        }
        src = new String(toCharArray);
        return src;
    }
}
