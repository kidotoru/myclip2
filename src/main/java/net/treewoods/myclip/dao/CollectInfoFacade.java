package net.treewoods.myclip.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.treewoods.myclip.entity.CollectInfo;

/**
 *
 * @author kido
 */
@Stateless
public class CollectInfoFacade extends AbstractFacade<CollectInfo> {
	@PersistenceContext(unitName = "net.treewoods_myclip2_war_1.0.0PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public CollectInfoFacade() {
		super(CollectInfo.class);
	}

    static final String FIND_MAX_COLLECT_ID_SQL = "select max(t.id) as current_id from collect_info t";
    public int findMaxCollectId(){
        Object obj = this.em.createNativeQuery(FIND_MAX_COLLECT_ID_SQL).getSingleResult();
        int result = 0;
        if ( obj != null) {
            result = (int)obj;
        }
        return result;
    }    
}
