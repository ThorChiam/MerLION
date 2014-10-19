/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.TMS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author sunny
 */
@Entity
public class DispatchOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    TMSOrder TMSOrder;
    
    @OneToOne
    Cost TMSCost;
    
    @OneToMany(cascade={CascadeType.PERSIST})
    public List<TMSFacility> TMSFacility=new ArrayList<>(); 
    
    @OneToMany(cascade={CascadeType.PERSIST})
    public List<TMSHumanResource> TMSHumanResource=new ArrayList<>();
    
    

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TMSOrder getTMSOrder() {
        return TMSOrder;
    }

    public void setTMSOrder(TMSOrder TMSOrder) {
        this.TMSOrder = TMSOrder;
    }

    public Cost getTMSCost() {
        return TMSCost;
    }

    public void setTMSCost(Cost TMSCost) {
        this.TMSCost = TMSCost;
    }

    public List<TMSFacility> getTMSFacility() {
        return TMSFacility;
    }

    public void setTMSFacility(List<TMSFacility> TMSFacility) {
        this.TMSFacility = TMSFacility;
    }

    public List<TMSHumanResource> getTMSHumanResource() {
        return TMSHumanResource;
    }

    public void setTMSHumanResource(List<TMSHumanResource> TMSHumanResource) {
        this.TMSHumanResource = TMSHumanResource;
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
        if (!(object instanceof DispatchOrder)) {
            return false;
        }
        DispatchOrder other = (DispatchOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.DispatchOrder[ id=" + id + " ]";
    }
    
}
