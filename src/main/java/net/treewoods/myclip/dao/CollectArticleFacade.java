package net.treewoods.myclip.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.treewoods.myclip.entity.CollectArticle;

/**
 *
 * @author kido
 */
@Stateless
public class CollectArticleFacade extends AbstractFacade<CollectArticle> {
	@PersistenceContext(unitName = "net.treewoods_myclip2_war_1.0.0PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CollectArticleFacade() {
		super(CollectArticle.class);
	}
	
}
