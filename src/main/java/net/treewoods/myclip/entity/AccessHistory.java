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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kido
 */
@Entity
@Table(name = "access_history", catalog = "myclip2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessHistory.findAll", query = "SELECT a FROM AccessHistory a"),
    @NamedQuery(name = "AccessHistory.findById", query = "SELECT a FROM AccessHistory a WHERE a.id = :id"),
    @NamedQuery(name = "AccessHistory.findByFromIp", query = "SELECT a FROM AccessHistory a WHERE a.fromIp = :fromIp"),
    @NamedQuery(name = "AccessHistory.findByCreatedAt", query = "SELECT a FROM AccessHistory a WHERE a.createdAt = :createdAt"),
    @NamedQuery(name = "AccessHistory.findByUpdatedAt", query = "SELECT a FROM AccessHistory a WHERE a.updatedAt = :updatedAt")})
public class AccessHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 32)
    @Column(name = "from_ip")
    private String fromIp;
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
    @JoinColumn(name = "to_article_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Article toArticleId;

    public AccessHistory() {
    }

    public AccessHistory(Integer id) {
	this.id = id;
    }

    public AccessHistory(Integer id, Date createdAt, Date updatedAt) {
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

    public String getFromIp() {
	return fromIp;
    }

    public void setFromIp(String fromIp) {
	this.fromIp = fromIp;
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

    public Article getToArticleId() {
	return toArticleId;
    }

    public void setToArticleId(Article toArticleId) {
	this.toArticleId = toArticleId;
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
	if (!(object instanceof AccessHistory)) {
	    return false;
	}
	AccessHistory other = (AccessHistory) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "net.treewoods.myclip.entity.AccessHistory[ id=" + id + " ]";
    }
    
}
