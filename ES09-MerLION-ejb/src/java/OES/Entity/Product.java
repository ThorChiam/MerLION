package OES.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import CRMS.Entity.Company;


@Entity
@Table(name = "Product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    

    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="OES_PRODUCT_OES_ENQUIRY")
    private Set<Enquiry> enquiry=new HashSet<>();
    
    @ManyToOne
    private Company company;
    
    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="OES_PRODUCT_OES_PURCHASEORDER")
    private Set<PurchaseOrder> purchaseorder=new HashSet<>();

    public Product() {
        setId(System.nanoTime());
    }
    
    public Set<Enquiry> getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(Set<Enquiry> enquiry) {
        this.enquiry = enquiry;
    }

    public Set<PurchaseOrder> getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(Set<PurchaseOrder> purchaseorder) {
        this.purchaseorder = purchaseorder;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
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