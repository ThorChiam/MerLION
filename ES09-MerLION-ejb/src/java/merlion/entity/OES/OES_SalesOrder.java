/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.OES;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author sunny
 */
@Entity
public class OES_SalesOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String createdate;

    @OneToOne(mappedBy="salesorder")
    OES_PurchaseOrder purchaseorder;
    
    @OneToOne
    OES_Payment payment;

    public OES_SalesOrder(){
        setId(System.nanoTime());
    }
    
     public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
    
    public OES_Payment getPayment() {
        return payment;
    }

    public void setPayment(OES_Payment payment) {
        this.payment = payment;
    }
    
    public OES_PurchaseOrder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(OES_PurchaseOrder purchaseorder) {
        this.purchaseorder = purchaseorder;
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
        if (!(object instanceof OES_SalesOrder)) {
            return false;
        }
        OES_SalesOrder other = (OES_SalesOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OES.entity.OES_SalesOrder[ id=" + id + " ]";
    }
    
}
