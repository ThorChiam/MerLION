/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import CI.Entity.Account;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
/**
 *
 * @author Tomato
 */
@Entity
public class Makepayment implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   
    private Long id;
    private String paymentDate;
    private String paymentType;
    private String status;
    
    @OneToOne(mappedBy="payment")
    SalesOrder salesorder;
    
    @OneToOne
    OES_Invoice oes_invoice;


    public Long getId() {
        return id;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public OES_Invoice getOes_invoice() {
        return oes_invoice;
    }

    public void setOes_invoice(OES_Invoice oes_invoice) {
        this.oes_invoice = oes_invoice;
    }

   
    public String getStatus() {
        return status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SalesOrder getSalesorder() {
        return salesorder;
    }

    public void setSalesorder(SalesOrder salesorder) {
        this.salesorder = salesorder;
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
        if (!(object instanceof Makepayment)) {
            return false;
        }
        Makepayment other = (Makepayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OES.Entity.Makepayment[ id=" + id + " ]";
    }
    
}
