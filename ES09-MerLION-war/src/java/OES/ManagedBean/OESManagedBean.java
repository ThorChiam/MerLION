/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.ManagedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import OES.Session.OESSessionLocal;
import CI.Entity.Account;
import OES.Entity.Enquiry;
import OES.Entity.OES_Invoice;
import OES.Entity.OES_Payment;
import OES.Entity.Product;
import OES.Entity.PurchaseOrder;
import OES.Entity.Quotation;
import OES.Entity.SalesOrder;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "oESManagedBean")
@SessionScoped
public class OESManagedBean implements Serializable {

    @EJB
    private OESSessionLocal osbl;

    //Product
    private String email;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private long product_id;

    //Enquiry
    Account seller;
    Account buyer;
    Set<Product> products;
    List<Integer> quantitys = new ArrayList();
    long enquiry_id;

    //Quotation
    Enquiry enquiry;
    List<String> delivery_date;
    private long quotation_id;

    //PurchaseOrder
    private double taxrate;
    private long purchase_id;

    //SalesOrder
    PurchaseOrder purchaseorder;
    long sales_id;

    //Payment
    SalesOrder salesorder;
    String paymentdate;
    String paymenttype;
    String status;
    long payment_id;

    //Invoice
    OES_Payment payment;
   private long invoice_id;
    String notes;

    private String statusMessage;

    public OESManagedBean() {
    }

    //********************Product********************************
    public void createProduct() {
        osbl.createProduct(email, name, price, quantity, description); 
        statusMessage = "The new product is successfully added.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
    }

    public void updateProduct() {
        osbl.updateProduct(product_id, name, price, quantity, description);
    }

    public Product getProduct() {
        return osbl.getProduct(product_id);
    }

    public List<Product> getAllProduct() {
        return osbl.getAllProduct(email);
    }

    public void deleteProduct() {
        osbl.deleteProduct(product_id);
    }

    //********************Buyer-Enquiry**************************
    public void createEnquiry() {
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createEnquiry(seller, buyer, products, quantitys, createdate);
        statusMessage = "A new enquiry is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
    }

    public Enquiry getEnquiry() {
        return osbl.getEnquiry(enquiry_id);
    }

    public List<Enquiry> getAllEnquiry() {
        return osbl.getAllEnquiry(email);
    }

    public void deleteEnquiry() {
        osbl.deleteEnquiry(enquiry_id);
    }

    //********************Seller-Quotation***********************
    public List<String> ATPcheck(Enquiry enquiry) {
        return osbl.ATPcheck(enquiry);
    }

    public void createQuotation() {
        statusMessage = "A new quotation is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createQuotation(enquiry, delivery_date, createdate);
    }

    public Quotation getQuotation() {
        return osbl.getQuotation(quotation_id);
    }

    public List<Quotation> getAllQuotation(String email) {//两边都能拿？？？？
        return osbl.getAllQuotation(email);
    }

    public void deleteQuotation() {//只能buyer删?
        osbl.deleteQuotation(product_id);
    }

    //********************Buyer-Purchase Order*******************
    public void createPurchaseOrder() {
        statusMessage = "A new purchase order is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createPurchaseOrder(buyer, seller, taxrate, quantitys, products, createdate);
    }

    public PurchaseOrder getPurchaseOrder() {
        return osbl.getPurchaseOrder(purchase_id);
    }

    public List<PurchaseOrder> getAllPurchaseOrder() {//两边都能拿？？？？
        return osbl.getAllPurchaseOrder(email);
    }

    public void deletePurchaseOrder() {//只能buyer删?
        osbl.deletePurchaseOrder(purchase_id);
    }

    //********************Seller-Sales Order*********************
    public void createSalesOrder() {
        statusMessage = "A new sales order is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createSalesOrder(purchaseorder, createdate);
    }

    public SalesOrder getSalesOrder() {
        return osbl.getSalesOrder(sales_id);
    }

    public List<SalesOrder> getAllSalesOrder() {
        return osbl.getAllSalesOrder(email);
    }

    public void deleteSalesOrder() {//只能seller删，不传给buyer
        osbl.deleteSalesOrder(sales_id);
    }

    //********************Buyer-Payment**************************
    public void createPayment() {
        statusMessage = "Please finish your payment soon. Thanks!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        osbl.createPayment(paymentdate, paymenttype, status, salesorder);
    }

    public void updatePaymentStatus() {
        osbl.updatePaymentStatus(payment_id, status);
    }

    public OES_Payment getPayment() {
        return osbl.getPayment(payment_id);
    }

    public List<OES_Payment> getAllPayment() {
        return osbl.getAllPayment(email);
    }

    //********************Seller-Invoice*************************
    public void createInvoice() {
        statusMessage = "A new invoice is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String releasedate = tmp.toString();
        osbl.createInvoice(releasedate, notes, payment);
    }

    public OES_Invoice getInvoice() {
        return osbl.getInvoice(invoice_id);
    }

    public List<OES_Invoice> getAllInvoice() {
        return osbl.getAllInvoice(email);
    }

    //******************GETTER AND SETTER***************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public List<Integer> getQuantitys() {
        return quantitys;
    }

    public void setQuantitys(List<Integer> quantitys) {
        this.quantitys = quantitys;
    }

    public long getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(long enquiry_id) {
        this.enquiry_id = enquiry_id;
    }

    public List<String> getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(List<String> delivery_date) {
        this.delivery_date = delivery_date;
    }

    public long getQuotation_id() {
        return quotation_id;
    }

    public void setQuotation_id(long quotation_id) {
        this.quotation_id = quotation_id;
    }

    public double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(double taxrate) {
        this.taxrate = taxrate;
    }

    public long getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(long purchase_id) {
        this.purchase_id = purchase_id;
    }

    public PurchaseOrder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(PurchaseOrder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }

    public long getSales_id() {
        return sales_id;
    }

    public void setSales_id(long sales_id) {
        this.sales_id = sales_id;
    }

    public SalesOrder getSalesorder() {
        return salesorder;
    }

    public void setSalesorder(SalesOrder salesorder) {
        this.salesorder = salesorder;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(long payment_id) {
        this.payment_id = payment_id;
    }

    public long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(long invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
