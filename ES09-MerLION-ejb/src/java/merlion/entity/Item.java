/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion_ejb.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Zeng Xunhao
 */
@Entity(name="Item")
@Table(name = "Item")
public class Item implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String supplier;
    private int scheduled_recipt;
    private int planned_order;
    private int on_hand;
    private int lead_time;
    private double cost;

    //Added by QT
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER,optional=false)
    private Account Account;


    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Item")
    private List<Demand> demand = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Item")
    private List<Template> templates = new ArrayList<>();
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Item")
    private MPS mps;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Item")
    private BOM bom;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Item")
    private List<MRPOrders> order = new ArrayList(); 

    //Added by QT
    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }





    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long systemUserId) {
        this.itemId = systemUserId;
    }

    public BOM getBom() {
        return bom;
    }

    public void setBom(BOM bom) {
        this.bom = bom;
    }

    public List<MRPOrders> getOrder() {
        return order;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setOrder(List<MRPOrders> order) {
        this.order = order;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<Demand> getDemand() {
        return demand;
    }

    public void setDemand(List<Demand> demand) {
        this.demand = demand;
    }

    public int getLead_time() {
        return lead_time;
    }

    public void setLead_time(int lead_time) {
        this.lead_time = lead_time;
    }

    public MPS getMps() {
        return mps;
    }

    public void setMps(MPS mps) {
        this.mps = mps;
    }

    
    public int getPlanned_order() {
        return planned_order;
    }

    public void setPlanned_order(int planned_order) {
        this.planned_order = planned_order;
    }

    
    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getScheduled_recipt() {
        return scheduled_recipt;
    }

    public void setScheduled_recipt(int scheduled_recipt) {
        this.scheduled_recipt = scheduled_recipt;
    }

    public int getOn_hand() {
        return on_hand;
    }

    public void setOn_hand(int on_hand) {
        this.on_hand = on_hand;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
// TODO: Warning - this method won't work in the case the systemUserId fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Item[id=" + itemId + "]";
    }
}
