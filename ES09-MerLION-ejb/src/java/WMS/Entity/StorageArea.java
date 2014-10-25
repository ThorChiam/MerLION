/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author sunny
 */
@Entity
public class StorageArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private int totalCapacity;

    @ManyToOne
    private Warehouse WMSWarehouse;

//    @ManyToOne
//    private Inventory Inventory;

    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="StorageArea_Inventory")
    private Set<Inventory> Inventory = new HashSet<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Warehouse getWMSWarehouse() {
        return WMSWarehouse;
    }

    public void setWMSWarehouse(Warehouse WMSWarehouse) {
        this.WMSWarehouse = WMSWarehouse;
    }

    public Set<Inventory> getInventory() {
        return Inventory;
    }

    public void setInventory(Set<Inventory> inventory) {
        this.Inventory = inventory;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
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
        if (!(object instanceof StorageArea)) {
            return false;
        }
        StorageArea other = (StorageArea) object;
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
