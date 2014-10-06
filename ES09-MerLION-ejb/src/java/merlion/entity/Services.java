/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author sunny
 */
@Entity
public class Services implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serviceType;
    private String carrierType;
    private String route;
    private String availableScheduleFrom;
    private String availableScheduleTo;
    private long price;
    private long maxVol;
    private long availableVol;

    
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER,optional=false)
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAvailableScheduleFrom() {
        return availableScheduleFrom;
    }

    public void setAvailableScheduleFrom(String availableScheduleFrom) {
        this.availableScheduleFrom = availableScheduleFrom;
    }

    public String getAvailableScheduleTo() {
        return availableScheduleTo;
    }

    public void setAvailableScheduleTo(String availableScheduleTo) {
        this.availableScheduleTo = availableScheduleTo;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getMaxVol() {
        return maxVol;
    }

    public void setMaxVol(long maxVol) {
        this.maxVol = maxVol;
    }

    public long getAvailableVol() {
        return availableVol;
    }

    public void setAvailableVol(long availableVol) {
        this.availableVol = availableVol;
    }
    
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        if (!(object instanceof Services)) {
            return false;
        }
        Services other = (Services) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Services[ id=" + id + " ]";
    }
    
}
