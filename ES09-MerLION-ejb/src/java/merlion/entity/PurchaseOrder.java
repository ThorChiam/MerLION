
package merlion.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PurchaseOrder")
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dateCreated;
    private String shippingdate;
    private String paymentTerm;
    private String currency;
    private String shippingTerm;
    private double total;
    private double taxRate;
    private double discount;
    
    @OneToOne(mappedBy="purchaseorder")
    private OrderRecord record;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Vendor vendor;
    
    //Added by QT
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Account Account;
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Company company;


    
    public PurchaseOrder() {
        setId(System.nanoTime());  
    }
    public void create(String dateCreated,String shippingdate,String paymentTerm,String currency,String shippingTerm, double total,double taxRate, double discount){
        this.setDateCreated(dateCreated);
        this.setShippingdate(shippingdate);
        this.setPaymentTerm(paymentTerm);
        this.setCurrency(currency);
        this.setShippingTerm(shippingTerm);
        this.setTaxRate(taxRate);
        this.setDiscount(discount);
    }
    
    //Added by QT
    
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    public double getTotal(){
        return total;
    }
    public void setTotal(double total){
        this.total=total;
    }
    public String getShippingdate() {
        return shippingdate;
    }

    public void setShippingdate(String shippingdate) {
        this.shippingdate = shippingdate;
    }
    
    public String getPaymentTerm(){
        return paymentTerm;
    }
    
    public void setPaymentTerm(String paymentTerm){
        this.paymentTerm=paymentTerm;
    }
    
    public String getCurrency(){
        return currency;
    }
    public void setCurrency(String currency){
        this.currency=currency;
    }
    
    public String getShippingTerm(){
        return shippingTerm;
    }
    
    public void setShippingTerm(String shippingTerm){
        this.shippingTerm=shippingTerm;
    }
    
    public double getTaxRate(){
        return taxRate;
    }
    public void setTaxRate(double taxRate){
        this.taxRate=taxRate;
    }
    
    public double getDiscount(){
        return discount;
    }
    
    public void setDiscount(double discount){
        this.discount=discount;
    }
    
    public OrderRecord getRecord() {
        return record;
    }

    public void setRecord(OrderRecord record) {
        this.record=record;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor( Vendor vendor) {
        this.vendor = vendor;
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
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PurchaseOrder[ id=" + id + " ]";
    }
    
}
