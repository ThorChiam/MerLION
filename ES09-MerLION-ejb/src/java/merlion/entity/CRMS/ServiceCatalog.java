/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.CRMS;

import merlion.entity.CommonInfrastructure.Account;
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
 * @author USER
 */
@Entity(name = "ServiceCatalog")
public class ServiceCatalog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceId;
    private String serviceType;
    private String carrierType;
    private String route;
    private String availableScheduleFrom;
    private String availableScheduleTo;
    private long price;
    private long maxVol;
    private long availableVol;
    
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Account Account;
    
    public ServiceCatalog(){
      
        setServiceId(System.nanoTime());
    
    }
    
    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }

    public Long serviceId() {
        return serviceId;
    }

    public void serviceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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



    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
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

    public void create(String serviceType, String carrierType, String route,String availableScheduleFrom,String availableScheduleTo ,long price,long maxVol,long availableVol){
        this.setServiceType(serviceType);
        this.setCarrierType(carrierType);
        this.setRoute(route);
        this.setAvailableScheduleFrom(availableScheduleFrom);
        this.setAvailableScheduleTo(availableScheduleTo);
        this.setPrice(price);
        this.setMaxVol(maxVol);
        this.setAvailableVol(availableVol);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceCatalog)) {
            return false;
        }
        ServiceCatalog other = (ServiceCatalog) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ServiceCatalogEntity[ id=" + serviceId + " ]";
    }

}
