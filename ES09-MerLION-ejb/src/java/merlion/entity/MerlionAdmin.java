/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author sunny
 */
@Entity
public class MerlionAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="admin")
    private List<AggregateDemand> aggregatedemand = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<ActionRecord> actionrecord = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Announcement> announcement = new ArrayList<>();
    @OneToOne(mappedBy="admin")
    private Account Account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AggregateDemand> getAggregatedemand() {
        return aggregatedemand;
    }

    public void setAggregatedemand(List<AggregateDemand> aggregatedemand) {
        this.aggregatedemand = aggregatedemand;
    }

    public List<ActionRecord> getActionrecord() {
        return actionrecord;
    }

    public void setActionrecord(List<ActionRecord> actionrecord) {
        this.actionrecord = actionrecord;
    }

    public List<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<Announcement> announcement) {
        this.announcement = announcement;
    }

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
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
        if (!(object instanceof MerlionAdmin)) {
            return false;
        }
        MerlionAdmin other = (MerlionAdmin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion.netity.MerlionAdmin[ id=" + id + " ]";
    }
    
}
