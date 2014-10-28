/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Entity;
//warehouse order management

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import CI.Entity.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author sunny
 */
@Entity
public class WMSOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderdate;
    private String status;//2 types:new;allocated
//    private List<Integer> quantity = new ArrayList();

    @ManyToOne
    private Account requester;

    @ManyToOne
    private Account provider;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<Inventory> inventory = new ArrayList();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "order")
    private List<WMSOrder_Inventory> wo_inven = new ArrayList();

    public WMSOrder() {
        this.status = "new";
    }

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

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public Account getRequester() {
        return requester;
    }

    public void setRequester(Account requester) {
        this.requester = requester;
    }

    public Account getProvider() {
        return provider;
    }

    public void setProvider(Account provider) {
        this.provider = provider;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

//    public List<Integer> getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(List<Integer> quantity) {
//        this.quantity = quantity;
//    }

    public List<WMSOrder_Inventory> getWo_inven() {
        return wo_inven;
    }

    public void setWo_inven(List<WMSOrder_Inventory> wo_inven) {
        this.wo_inven = wo_inven;
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
        if (!(object instanceof WMSOrder)) {
            return false;
        }
        WMSOrder other = (WMSOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_ejb.entity.New_Order[ id=" + id + " ]";
    }

}
