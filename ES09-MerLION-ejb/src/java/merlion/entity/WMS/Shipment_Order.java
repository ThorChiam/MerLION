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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import merlion.entity.CommonInfrastructure.Account;

/**
 *
 * @author sunny
 */
@Entity
public class Shipment_Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade={CascadeType.PERSIST})
    private List<Inventory> inventory=new ArrayList();
    
    @ManyToOne
    private Account Account;

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }
    
    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
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
        if (!(object instanceof Shipment_Order)) {
            return false;
        }
        Shipment_Order other = (Shipment_Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.WMS_Shipment_Order[ id=" + id + " ]";
    }
    
}
