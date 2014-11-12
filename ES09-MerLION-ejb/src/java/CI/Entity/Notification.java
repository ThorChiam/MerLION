/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.Entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import CRMS.Entity.Company;

@Entity
@Table(name = "Notification")

public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long notiId;
    private String n_title;//Service Request;Contract;Payment;Invoice;MemberType
    private String content;
    private Long release_time;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Company company;

    public Notification() {
        this.setNotiId(System.nanoTime());
    }

    public void add(String n_title, String content, Long release_time) {
        this.setN_title(n_title);
        this.setContent(content);
        this.setRelease_time(release_time);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getNotiId() {
        return notiId;
    }

    public void setNotiId(Long notiId) {
        this.notiId = notiId;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public Long getRelease_time() {
        return release_time;
    }

    public void setRelease_time(Long release_time) {
        this.release_time = release_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notiId != null ? notiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        return (this.notiId != null || other.notiId == null) && (this.notiId == null || this.notiId.equals(other.notiId));
    }

    @Override
    public String toString() {
        return "merlion_ejb.Notification[ notiId=" + notiId + " ]";
    }

}
