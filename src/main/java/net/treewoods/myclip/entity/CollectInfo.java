/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.treewoods.myclip.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kido
 */
@Entity
@Table(name = "collect_info")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "CollectInfo.findAll", query = "SELECT c FROM CollectInfo c"),
	@NamedQuery(name = "CollectInfo.findById", query = "SELECT c FROM CollectInfo c WHERE c.id = :id"),
	@NamedQuery(name = "CollectInfo.findByCreatedAt", query = "SELECT c FROM CollectInfo c WHERE c.createdAt = :createdAt"),
	@NamedQuery(name = "CollectInfo.findByUpdatedAt", query = "SELECT c FROM CollectInfo c WHERE c.updatedAt = :updatedAt")})
public class CollectInfo implements Serializable {
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "correctId")
	private List<CollectArticle> collectArticleList;

	public CollectInfo() {
	}

	public CollectInfo(Integer id) {
		this.id = id;
	}

	public CollectInfo(Integer id, Date createdAt, Date updatedAt) {
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

	@XmlTransient
	public List<CollectArticle> getCollectArticleList() {
		return collectArticleList;
	}

	public void setCollectArticleList(List<CollectArticle> collectArticleList) {
		this.collectArticleList = collectArticleList;
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
		if (!(object instanceof CollectInfo)) {
			return false;
		}
		CollectInfo other = (CollectInfo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.treewoods.myclip.entity.CollectInfo[ id=" + id + " ]";
	}
	
}
