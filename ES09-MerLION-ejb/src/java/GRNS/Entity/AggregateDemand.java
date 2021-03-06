/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GRNS.Entity;

import CI.Entity.MerlionAdmin;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author sunny
 */
@Entity
public class AggregateDemand implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String aggregate_date;
    private String aggregate_status;

    

    
    @OneToOne
    private Auction auction;
//  @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="aggregatedemand")
//  private List<Order> orders = new ArrayList<>();
    
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER,optional=false)
    private MerlionAdmin admin;

    @OneToOne(mappedBy="AggregateDemand")
    private DemandRequest DemandRequest;
    
    public String getAggregate_status() {
        return aggregate_status;
    }

    public void setAggregate_status(String aggregate_status) {
        this.aggregate_status = aggregate_status;
    }
    
    public DemandRequest getDemandRequest() {
        return DemandRequest;
    }

    public String getAggregate_date() {
        return aggregate_date;
    }

    public void setAggregate_date(String aggregate_date) {
        this.aggregate_date = aggregate_date;
    }
    
    public void setDemandRequest(DemandRequest DemandRequest) {
        this.DemandRequest = DemandRequest;
    }
    
    public MerlionAdmin getMerlionAdmin() {
        return admin;
    }

    public void setMerlionAdmin(MerlionAdmin admin) {
        this.admin = admin;
    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof AggregateDemand)) {
            return false;
        }
        AggregateDemand other = (AggregateDemand) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_ejb.entity.AggregateDemand[ id=" + id + " ]";
    }
    
}
