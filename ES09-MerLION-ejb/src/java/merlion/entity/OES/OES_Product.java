package merlion.entity.OES;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import merlion.entity.CRMS.Company;


@Entity
@Table(name = "Product")
public class OES_Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String description;

    @ManyToOne
    private OES_Enquiry enquiry;
    
    @ManyToOne
    private Company company;
    
    @ManyToOne
    private OES_PurchaseOrder purchaseorder;

    
    public OES_Product() {
        setId(System.nanoTime());
    }
    
    public OES_PurchaseOrder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(OES_PurchaseOrder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }
    
    public OES_Enquiry getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(OES_Enquiry enquiry) {
        this.enquiry = enquiry;
    }
    
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
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
        if (!(object instanceof OES_Product)) {
            return false;
        }
        OES_Product other = (OES_Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Product[id=" + id + "]";
    }

}