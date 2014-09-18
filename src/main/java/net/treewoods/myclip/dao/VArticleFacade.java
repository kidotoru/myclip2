package net.treewoods.myclip.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.treewoods.myclip.entity.VArticle;

/**
 *
 * @author kido
 */
@Stateless
public class VArticleFacade extends AbstractFacade<VArticle>{
	@PersistenceContext(unitName = "net.treewoods_myclip2_war_1.0.0PU")
	private EntityManager em;

    public VArticleFacade() {
        super(VArticle.class);
    }

	public VArticleFacade(Class<VArticle> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	final private static String FIND_BY_COLLECT_ID_SQL;

	static {
		StringBuilder sb = new StringBuilder();

		sb.append("select ");
		sb.append("  t2.article_id ");
		sb.append("  , ( ");
		sb.append("    select ");
		sb.append("      ct.site_name ");
		sb.append("    from ");
		sb.append("      collect_site ct ");
		sb.append("    where ");
		sb.append("      ct.id = t3.site_id ");
		sb.append("  ) as site_name ");
		sb.append("  , t3.created_at as collect_date ");
		sb.append("  , t3.article_url ");
		sb.append("  , t3.article_title ");
		sb.append("  , ( ");
		sb.append("    select ");
		sb.append("      count(distinct t.from_ip) ");
		sb.append("    from ");
		sb.append("      access_history t ");
		sb.append("    where ");
		sb.append("      t.to_article_id = t3.id ");
		sb.append("  ) as cnt1 ");
		sb.append("  , ( ");
		sb.append("    select ");
		sb.append("      count(t.from_ip) ");
		sb.append("    from ");
		sb.append("      access_history t ");
		sb.append("    where ");
		sb.append("      t.to_article_id = t3.id ");
		sb.append("  ) as cnt2 ");
		sb.append("  , t3.article_contents ");
		sb.append("from ");
		sb.append("  collect_info t1 ");
		sb.append("  , collect_article t2 ");
		sb.append("  , article t3 ");
		sb.append("where ");
		sb.append("  t1.id = :ID ");
		sb.append("  and t1.id = t2.collect_id ");
		sb.append("  and t2.article_id = t3.id ");
		sb.append("order by ");
		sb.append("  t3.created_at desc ");
		sb.append("  , t3.id ");
		sb.append("limit :START_POS ,:END_POS ");

		FIND_BY_COLLECT_ID_SQL = sb.toString();
	}

    public List<VArticle> findByCollectId(int collectId){
        List<VArticle> resultList =
                em.createNativeQuery(FIND_BY_COLLECT_ID_SQL, VArticle.class)
                .setParameter("ID", collectId)
                .setParameter("START_POS", 0)
                .setParameter("END_POS", 99)
                .getResultList();
        return resultList;
    }

}
