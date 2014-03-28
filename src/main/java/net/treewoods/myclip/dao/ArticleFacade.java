package net.treewoods.myclip.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.treewoods.myclip.entity.Article;

/**
 *
 * @author kido
 */
@Stateless
public class ArticleFacade extends AbstractFacade<Article> {
	@PersistenceContext(unitName = "net.treewoods_myclip2_war_1.0.0PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ArticleFacade() {
		super(Article.class);
	}

    public List<Article> findByArticleUrl(String url){
        List<Article> resultList =
                em.createNamedQuery("Article.findByArticleUrl", Article.class)
                .setParameter("articleUrl", url)
                .getResultList();
        return resultList;
    }    
	
}
