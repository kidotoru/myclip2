package net.treewoods.myclip.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.treewoods.myclip.entity.AccessHistory;

/**
 *
 * @author kido
 */
@Stateless
public class AccessHistoryFacade extends AbstractFacade<AccessHistory> {
	@PersistenceContext(unitName = "net.treewoods_myclip2_war_1.0.0PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AccessHistoryFacade() {
		super(AccessHistory.class);
	}
	
}
