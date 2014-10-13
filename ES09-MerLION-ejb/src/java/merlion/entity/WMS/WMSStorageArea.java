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

/**
 *
 * @author sunny
 */
@Entity
public class WMSStorageArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private WMSWarehouse WMSWarehouse;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSStorageArea")
    private List<WMSInventory> inventory = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WMSWarehouse getWMSWarehouse() {
        return WMSWarehouse;
    }

    public void setWMSWarehouse(WMSWarehouse WMSWarehouse) {
        this.WMSWarehouse = WMSWarehouse;
    }

    public List<WMSInventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<WMSInventory> inventory) {
        this.inventory = inventory;
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
        if (!(object instanceof WMSStorageArea)) {
            return false;
        }
        WMSStorageArea other = (WMSStorageArea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Storagebin[ id=" + id + " ]";
    }
    
}
