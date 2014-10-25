/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GRNS.Entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import CRMS.Entity.Company;

/**
 *
 * @author sunny
 */
@Entity
public class Auction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Auction_date;
    private double Auction_price;
    private String Auction_status;

    @OneToOne(mappedBy="auction")
    private AggregateDemand aggregatedemand;
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER,optional=false)
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AggregateDemand getAggregatedemand() {
        return aggregatedemand;
    }

    public void setAggregatedemand(AggregateDemand aggregatedemand) {
        this.aggregatedemand = aggregatedemand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuction_date() {
        return Auction_date;
    }

    public void setAuction_date(String Auction_date) {
        this.Auction_date = Auction_date;
    }

    public double getAuction_price() {
        return Auction_price;
    }

    public void setAuction_price(double Auction_price) {
        this.Auction_price = Auction_price;
    }

    public String getAuction_status() {
        return Auction_status;
    }

    public void setAuction_status(String Auction_status) {
        this.Auction_status = Auction_status;
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
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Auction[ id=" + id + " ]";
    }
    
}
