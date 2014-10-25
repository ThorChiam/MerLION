/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Entity;

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
public class OES_Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String PaymentDate;
    private String PaymentType;
    private String status;
    private int delete_status;

    
    @OneToOne
    OES_Invoice invoice;
    
    @OneToOne(mappedBy="payment")
    PurchaseOrder purchase;

    
    public OES_Payment(){
        setId(System.nanoTime());
    }   
    
    
    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }
    
    public PurchaseOrder getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseOrder purchase) {
        this.purchase = purchase;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public OES_Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(OES_Invoice invoice) {
        this.invoice = invoice;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String PaymentDate) {
        this.PaymentDate = PaymentDate;
    }
    
    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymenttype) {
        this.PaymentType = paymenttype;
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
        if (!(object instanceof OES_Payment)) {
            return false;
        }
        OES_Payment other = (OES_Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OES.entity.OES_Payment[ id=" + id + " ]";
    }
    
}
