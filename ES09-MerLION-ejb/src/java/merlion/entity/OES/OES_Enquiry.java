/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.OES;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import merlion.entity.Account;

/**
 *
 * @author sunny
 */

@Entity
public class OES_Enquiry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Account seller;
    private Account buyer;
    
    @OneToOne 
    private OES_Quotation quotation;
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="oes_equiry")
    private Collection<OES_Product> product = new ArrayList<>();

    
    private List<Integer> quantity = new ArrayList();

    public OES_Enquiry(){
        setId(System.nanoTime());
    }
    
    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }
    
    public Collection<OES_Product> getProduct() {
        return product;
    }

    public void setProduct(Collection<OES_Product> product) {
        this.product = product;
    }
    
    public OES_Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(OES_Quotation quotation) {
        this.quotation = quotation;
    }
    
    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
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
        if (!(object instanceof OES_Enquiry)) {
            return false;
        }
        OES_Enquiry other = (OES_Enquiry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OES.entity.OES_Enquiry[ id=" + id + " ]";
    }
    
}
