/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Entity;

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
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int quantity;
    private String status;
//    private List<Integer> storageQty;//the storage quantity for each storage area
//    private List<Integer> warehouseQty;//the storage quantity for each warehouse

//    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "Inventory")
//    private Set<Warehouse> warehouses;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "inventory")
    private List<StorageArea_Inventory> sa_inven = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "inventory")
    private List<Warehouse_Inventory> ws_inven = new ArrayList<>();
    @ManyToOne
    private WMSOrder order;

//    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Inventory")
//    private List<StorageArea> storagebin = new ArrayList<>();
//    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "Inventory")
//    private Set<StorageArea> storageArea = new HashSet<>();
    public WMSOrder getOrder() {
        return order;
    }

    public void setOrder(WMSOrder order) {
        this.order = order;
    }

//    public Set<Warehouse> getWarehouse() {
//        return warehouses;
//    }
//
//    public void setWarehouses(Set<Warehouse> warehouses) {
//        this.warehouses = warehouses;
//    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public List<Integer> getStorageQty() {
//        return storageQty;
//    }
//
//    public void setStorageQty(List<Integer> storageQty) {
//        this.storageQty = storageQty;
//    }
//
//    public List<Integer> getWarehouseQty() {
//        return warehouseQty;
//    }
//
//    public void setWarehouseQty(List<Integer> warehouseQty) {
//        this.warehouseQty = warehouseQty;
//    }

    public List<StorageArea_Inventory> getSa_inven() {
        return sa_inven;
    }

    public void setSa_inven(List<StorageArea_Inventory> sa_inven) {
        this.sa_inven = sa_inven;
    }

    public List<Warehouse_Inventory> getWs_inven() {
        return ws_inven;
    }

    public void setWs_inven(List<Warehouse_Inventory> ws_inven) {
        this.ws_inven = ws_inven;
    }

//    public Set<StorageArea> getStorageArea() {
//        return storageArea;
//    }
//
//    public void setStorageArea(Set<StorageArea> storageArea) {
//        this.storageArea = storageArea;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Inventory[ id=" + id + " ]";
    }

}
