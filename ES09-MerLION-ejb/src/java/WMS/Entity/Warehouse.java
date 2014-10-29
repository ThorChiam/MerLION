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
import CRMS.Entity.Company;

/**
 *
 * @author sunny
 */
@Entity
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String capacity;
    private List<Integer> available;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
    private List<WMSFacility> facility = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
    private List<Employee> employee = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
    private List<StorageArea> storageArea = new ArrayList<>();

//    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "WMSWarehouse")
//    private List<Inventory> inventory = new ArrayList<>();
//    @ManyToMany(cascade={CascadeType.PERSIST})
//    @JoinTable(name="Warehouse_Inventory")
//    @JoinColumn
//    private Set<Inventory> inventory = new HashSet<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "warehouse")
    private List<Warehouse_Inventory> ws_inven = new ArrayList<>();
    @ManyToOne
    private Company Company;

    public List<Integer> getAvailable() {
        return available;
    }

    public void setAvailable(List<Integer> available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public List<StorageArea> getStorageArea() {
        return storageArea;
    }

    public void setStorageArea(List<StorageArea> storageArea) {
        this.storageArea = storageArea;
    }

    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        this.Company = company;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public List<WMSFacility> getFacility() {
        return facility;
    }

    public List<Warehouse_Inventory> getWs_inven() {
        return ws_inven;
    }

    public void setWs_inven(List<Warehouse_Inventory> ws_inven) {
        this.ws_inven = ws_inven;
    }

//    public Set<Inventory> getInventory() {
//        return inventory;
//    }
//
//    public void setInventory(Set<Inventory> inventory) {
//        this.inventory = inventory;
//    }
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
        if (!(object instanceof Warehouse)) {
            return false;
        }
        Warehouse other = (Warehouse) object;
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
