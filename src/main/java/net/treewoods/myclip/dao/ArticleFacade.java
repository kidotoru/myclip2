/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

	public List<Article> findByArticleUrl(String link) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
