package net.treewoods.myclip.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import net.treewoods.myclip.dao.AccessHistoryFacade;
import net.treewoods.myclip.dao.ArticleFacade;
import net.treewoods.myclip.dao.CollectInfoFacade;
import net.treewoods.myclip.dao.VArticleFacade;
import net.treewoods.myclip.entity.AccessHistory;
import net.treewoods.myclip.entity.Article;
import net.treewoods.myclip.entity.VArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 記事表示
 * 記事の一覧表示と、記事クリック時のリダイレクトを行う。
 * @author kido
 */
@Named
@RequestScoped
public class ArticleAction {

    // ロガーのインスタンス
    private final Logger log = LoggerFactory.getLogger(this.getClass());      
    
    @Inject
    private VArticleFacade vArticleFacade;

    @Inject
    private CollectInfoFacade collectInfoFacade;

    @Inject
    private ArticleFacade articleFacade;

    @Inject
    private AccessHistoryFacade accessHistoryFacade;
    
    private List<VArticle> articleList = null;
    
    private String displayType;
    
    private Integer startPos;

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Integer getStartPos() {
	return startPos;
    }

    public void setStartPos(Integer startPos) {
	this.startPos = startPos;
    }

    
    public int getPervPos(){
	return (this.startPos != null && this.startPos != 0) ? this.startPos - 100 : 0;
    }
    public int getNextPos(){
	return (this.startPos != null && this.startPos != 0) ? this.startPos + 100 : 100;
    }
	    
	    
    
    public List<VArticle> getArticleList(){
        log.info("getArticleList start  pos={}",this.startPos);
        if ( this.articleList == null ) {
            int currentId = this.collectInfoFacade.findMaxCollectId();
	    int start = this.startPos != null ? this.startPos : 0;
            this.articleList = this.vArticleFacade.findByCollectId(currentId, start);
        
            log.info("currentId={}",currentId);

            if (this.articleList == null) {
                this.articleList = new ArrayList<>();
            }
        }
        log.info("getArticleList finish");
        return this.articleList;
    }

    /**
     * リダイレクト。
     * 引数の記事IDから、リダイレクト先のURLを取得し、リダイレクトを行う。
     * リダイレクト先のURLが取得できない場合、そのまま一覧表示を行う。
     * @param strArticleId 記事ID
     */
    public void redirect(String strArticleId) {
        try {
            log.info("redirect start id={}", strArticleId);

            if (strArticleId != null && !strArticleId.isEmpty()) {
                int articleId = Integer.parseInt(strArticleId);
                Article article = this.articleFacade.find(articleId);

                if (article != null) {
                    this.log.info(article.getArticleUrl());
                    // アクセス履歴追加
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();

                    // IPアドレスを、Webサーバーのものからリモートアドレスのものへ変更
                    String remoteAddr = request.getRemoteAddr();
                    String http_x_forward_for = request.getHeader("x-forwarded-for");
                    if (http_x_forward_for != null && !http_x_forward_for.isEmpty()) {
                        remoteAddr = http_x_forward_for;
                    }
                    
                    log.info("remoteAddr={}", remoteAddr);
                    
                    AccessHistory accessHistory = new AccessHistory();
                    accessHistory.setFromIp(remoteAddr);
                    accessHistory.setToArticleId(article);
		    accessHistory.setCreatedAt(new Date());
		    accessHistory.setUpdatedAt(new Date());
                    
                    this.accessHistoryFacade.create(accessHistory);
                    
                    // リダイレクト
                    FacesContext.getCurrentInstance().getExternalContext().redirect(article.getArticleUrl());
                }
                else {
                    this.log.warn("NOT FOUND ARTICLE.ID={}", strArticleId);
                }
            }
        } catch (IOException ex) {
            log.warn("IOException", ex);
        } catch (NumberFormatException ex) {
            log.warn(ex.getLocalizedMessage());
        } finally {
            log.info("redirect finish.");
        }
    }
}
