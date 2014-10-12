/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.WMS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import merlion.entity.CRMS.Company;

/**
 *
 * @author sunny
 */
@Entity
public class WMSWarehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
    private List<WMSFacility> facility = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
    private List<WMSEmployee> employee = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
    private List<WMSStorageArea> storagearea = new ArrayList<>();

    @ManyToOne
    private Company Company;
    
    
    public List<WMSStorageArea> getStoragearea() {
        return storagearea;
    }

    public void setStoragearea(List<WMSStorageArea> storagearea) {
        this.storagearea = storagearea;
    }
            
    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        this.Company = company;
    }

    public List<WMSEmployee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<WMSEmployee> employee) {
        this.employee = employee;
    }

    public List<WMSFacility> getFacility() {
        return facility;
    }

    public void setFacility(List<WMSFacility> facility) {
        this.facility = facility;
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
        if (!(object instanceof WMSWarehouse)) {
            return false;
        }
        WMSWarehouse other = (WMSWarehouse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Warehouse[ id=" + id + " ]";
    }
    
}
