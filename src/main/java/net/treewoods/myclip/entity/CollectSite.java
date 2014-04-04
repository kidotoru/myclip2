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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "collect_site")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CollectSite.findAll", query = "SELECT c FROM CollectSite c"),
    @NamedQuery(name = "CollectSite.findById", query = "SELECT c FROM CollectSite c WHERE c.id = :id"),
    @NamedQuery(name = "CollectSite.findByUrl", query = "SELECT c FROM CollectSite c WHERE c.url = :url"),
    @NamedQuery(name = "CollectSite.findBySiteName", query = "SELECT c FROM CollectSite c WHERE c.siteName = :siteName"),
    @NamedQuery(name = "CollectSite.findByFlgDelete", query = "SELECT c FROM CollectSite c WHERE c.flgDelete = :flgDelete"),
    @NamedQuery(name = "CollectSite.findByLastPubAt", query = "SELECT c FROM CollectSite c WHERE c.lastPubAt = :lastPubAt"),
    @NamedQuery(name = "CollectSite.findByCreatedAt", query = "SELECT c FROM CollectSite c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "CollectSite.findByUpdatedAt", query = "SELECT c FROM CollectSite c WHERE c.updatedAt = :updatedAt")})
public class CollectSite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 512)
    private String url;
    @Size(max = 512)
    @Column(name = "site_name")
    private String siteName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flg_delete")
    private Character flgDelete;
    @Basic(optional = false)
    @Column(name = "last_pub_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPubAt;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "siteId")
    private List<Article> articleList;

    public CollectSite() {
    }

    public CollectSite(Integer id) {
        this.id = id;
    }

    public CollectSite(Integer id, Character flgDelete, Date lastPubAt, Date createdAt, Date updatedAt) {
        this.id = id;
        this.flgDelete = flgDelete;
        this.lastPubAt = lastPubAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Character getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(Character flgDelete) {
        this.flgDelete = flgDelete;
    }

    public Date getLastPubAt() {
        return lastPubAt;
    }

    public void setLastPubAt(Date lastPubAt) {
        this.lastPubAt = lastPubAt;
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
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
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
        if (!(object instanceof CollectSite)) {
            return false;
        }
        CollectSite other = (CollectSite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.treewoods.myclip.entity.CollectSite[ id=" + id + " ]";
    }
    
}
