/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.treewoods.myclip.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kido
 */
@Entity
@Table(name = "collect_article")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "CollectArticle.findAll", query = "SELECT c FROM CollectArticle c"),
	@NamedQuery(name = "CollectArticle.findById", query = "SELECT c FROM CollectArticle c WHERE c.id = :id"),
	@NamedQuery(name = "CollectArticle.findByCreatedAt", query = "SELECT c FROM CollectArticle c WHERE c.createdAt = :createdAt"),
	@NamedQuery(name = "CollectArticle.findByUpdatedAt", query = "SELECT c FROM CollectArticle c WHERE c.updatedAt = :updatedAt")})
public class CollectArticle implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Integer id;
	@Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	@JoinColumn(name = "ariticle_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Article ariticleId;
	@JoinColumn(name = "correct_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private CollectInfo correctId;

	public CollectArticle() {
	}

	public CollectArticle(Integer id) {
		this.id = id;
	}

	public CollectArticle(Integer id, Date createdAt, Date updatedAt) {
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Article getAriticleId() {
		return ariticleId;
	}

	public void setAriticleId(Article ariticleId) {
		this.ariticleId = ariticleId;
	}

	public CollectInfo getCorrectId() {
		return correctId;
	}

	public void setCorrectId(CollectInfo correctId) {
		this.correctId = correctId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CollectArticle)) {
			return false;
		}
		CollectArticle other = (CollectArticle) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.treewoods.myclip.entity.CollectArticle[ id=" + id + " ]";
	}
	
}
