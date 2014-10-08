/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.entity;

import java.io.Serializable;
//import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author Tomato
 */
@Entity
@Table(name = "OrderRecord")
public class OrderRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @OneToMany(cascade = CascadeType.PERSIST)
    private  Collection<LineItem> items;
    @OneToOne
    private PurchaseOrder purchaseorder;
    @OneToOne
    private Salesquotation salesquotation;

    public Salesquotation getSalesquotation() {
        return salesquotation;
    }

    public void setSalesquotation(Salesquotation salesquotation) {
        this.salesquotation = salesquotation;
    }

    public PurchaseOrder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(PurchaseOrder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    
    public  Collection<LineItem> getItems(){
        return items;
    }
    
    public void setItems( Collection<LineItem> items){
        this.items=items;
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
        if (!(object instanceof OrderRecord)) {
            return false;
        }
        OrderRecord other = (OrderRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderRecord[ id=" + id + " ]";
    }
    
}
