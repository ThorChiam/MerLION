/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Tomato
 */
@Entity
@Table(name = "LineItem")
public class LineItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantity;
    private double subtotal;
    @JoinColumn(name = "Product_id", referencedColumnName = "id", insertable = false, updatable = false)
    //@ManyToOne(optional = false)
    //private Product product;
    @OneToOne
    private Product product;
  


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   public int getQuantity(){
       return quantity;
   }
   public void setQuantity(int quantity){
       this.quantity=quantity;
   }
   public double getSubtotal(){
       return subtotal;
   }
   public void setSubtotal(double subtotal){
       this.subtotal=subtotal;
   }
   public Product getProduct(){
       return product;
   }
   
   public void setProduct(Product product){
       this.product=product;
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
        if (!(object instanceof LineItem)) {
            return false;
        }
        LineItem other = (LineItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LineItem[ id=" + id + " ]";
    }
    
}
