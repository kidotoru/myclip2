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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kido
 */
@Entity
@Table(name = "article")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
	@NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id"),
	@NamedQuery(name = "Article.findByArticleUrl", query = "SELECT a FROM Article a WHERE a.articleUrl = :articleUrl"),
	@NamedQuery(name = "Article.findByArticleTitle", query = "SELECT a FROM Article a WHERE a.articleTitle = :articleTitle"),
	@NamedQuery(name = "Article.findByPublishAt", query = "SELECT a FROM Article a WHERE a.publishAt = :publishAt"),
	@NamedQuery(name = "Article.findByCreatedAt", query = "SELECT a FROM Article a WHERE a.createdAt = :createdAt"),
	@NamedQuery(name = "Article.findByUpdatedAt", query = "SELECT a FROM Article a WHERE a.updatedAt = :updatedAt")})
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Integer id;
	@Size(max = 512)
    @Column(name = "article_url")
	private String articleUrl;
	@Size(max = 512)
    @Column(name = "article_title")
	private String articleTitle;
	@Lob
    @Column(name = "article_contents")
	private byte[] articleContents;
	@Basic(optional = false)
    @NotNull
    @Column(name = "publish_at")
    @Temporal(TemporalType.TIMESTAMP)
	private Date publishAt;
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
	@JoinColumn(name = "site_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private CollectSite siteId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "toArticleId")
	private List<AccessHistory> accessHistoryList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId")
	private List<CollectArticle> collectArticleList;

	public Article() {
	}

	public Article(Integer id) {
		this.id = id;
	}

	public Article(Integer id, Date publishAt, Date createdAt, Date updatedAt) {
		this.id = id;
		this.publishAt = publishAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public byte[] getArticleContents() {
		return articleContents;
	}

	public void setArticleContents(byte[] articleContents) {
		this.articleContents = articleContents;
	}

	public Date getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(Date publishAt) {
		this.publishAt = publishAt;
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

	public CollectSite getSiteId() {
		return siteId;
	}

	public void setSiteId(CollectSite siteId) {
		this.siteId = siteId;
	}

	@XmlTransient
	public List<AccessHistory> getAccessHistoryList() {
		return accessHistoryList;
	}

	public void setAccessHistoryList(List<AccessHistory> accessHistoryList) {
		this.accessHistoryList = accessHistoryList;
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
		if (!(object instanceof Article)) {
			return false;
		}
		Article other = (Article) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "net.treewoods.myclip.entity.Article[ id=" + id + " ]";
	}
	
}
