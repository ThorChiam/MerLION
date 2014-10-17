/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.OESSessionLocal;
import merlion.entity.CommonInfrastructure.Account;
import merlion.entity.OES.OES_Enquiry;
import merlion.entity.OES.OES_Invoice;
import merlion.entity.OES.OES_Payment;
import merlion.entity.OES.OES_Product;
import merlion.entity.OES.OES_PurchaseOrder;
import merlion.entity.OES.OES_Quotation;
import merlion.entity.OES.OES_SalesOrder;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "oESManagedBean")
@SessionScoped
public class OESManagedBean implements Serializable{

    @EJB
    private OESSessionLocal osbl;
    
    //Product
    String email;
    String name; 
    String description;
    double price;
    int quantity;
    long product_id;
    
    //Enquiry
    Account seller;
    Account buyer;
    Set<OES_Product> products;
    List<Integer> quantitys=new ArrayList();
    long enquiry_id;
    
    //Quotation
    OES_Enquiry enquiry;
    List<String> delivery_date;
    long quotation_id;
    
    //PurchaseOrder
    double taxrate;
    long purchase_id;
    
    //SalesOrder
    OES_PurchaseOrder purchaseorder;
    long sales_id;
  
    //Payment
    OES_SalesOrder salesorder;
    String paymentdate;
    String paymenttype;
    String status;
    long payment_id;  
    
    //Invoice
    OES_Payment payment;
    long invoice_id;
    String notes;
    
    
    
    public OESManagedBean(){
    }
    
    //********************Product********************************
    public void createProduct(){
        osbl.createProduct(email,name,price,quantity,description);
    }
    
    public void updateProduct(){
        osbl.updateProduct(product_id,name,price,quantity,description);
    }
    
    public OES_Product getProduct(){
        return osbl.getProduct(product_id);
    }
    
    public List<OES_Product> getAllProduct(){
        return osbl.getAllProduct(email);
    }
    
    public void deleteProduct(){
        osbl.deleteProduct(product_id);
    }
    
    
    
    
    //********************Buyer-Enquiry**************************
    public void createEnquiry(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate=tmp.toString();
        osbl.createEnquiry(seller,buyer,products,quantitys,createdate);
    }
    
    public OES_Enquiry getEnquiry(){
        return osbl.getEnquiry(enquiry_id);
    }
    
    public List<OES_Enquiry> getAllEnquiry(){
        return osbl.getAllEnquiry(email);
    }
    
    public void deleteEnquiry(){
        osbl.deleteEnquiry(enquiry_id);
    }
    
    
    
    //********************Seller-Quotation***********************
    public List<String> ATPcheck(OES_Enquiry enquiry){
        return osbl.ATPcheck(enquiry);
    }
    
    public void createQuotation(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate=tmp.toString();
        osbl.createQuotation(enquiry, delivery_date, createdate);
    }
    
    public OES_Quotation getQuotation(){
        return osbl.getQuotation(quotation_id);
    }
     
    public List<OES_Quotation> getAllQuotation(String email){//两边都能拿？？？？
        return osbl.getAllQuotation(email);
    }
    
    public void deleteQuotation(){//只能buyer删?
        osbl.deleteQuotation(product_id);
    }
    
    
    //********************Buyer-Purchase Order*******************
    public void createPurchaseOrder(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate=tmp.toString();
        osbl.createPurchaseOrder(buyer,seller,taxrate,quantitys,products,createdate);
    }
    public OES_PurchaseOrder getPurchaseOrder(){
        return osbl.getPurchaseOrder(purchase_id);
    }
    
    public List<OES_PurchaseOrder> getAllPurchaseOrder(){//两边都能拿？？？？
        return osbl.getAllPurchaseOrder(email);
    }
    public void deletePurchaseOrder(){//只能buyer删?
        osbl.deletePurchaseOrder(purchase_id);
    }
    
    
    //********************Seller-Sales Order*********************
    public void createSalesOrder(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate=tmp.toString();
        osbl.createSalesOrder(purchaseorder, createdate);
    }
    
    public OES_SalesOrder getSalesOrder(){
        return osbl.getSalesOrder(sales_id);
    }
    
    public List<OES_SalesOrder> getAllSalesOrder(){
        return osbl.getAllSalesOrder(email);
    }
    
    public void deleteSalesOrder(){//只能seller删，不传给buyer
        osbl.deleteSalesOrder(sales_id);
    }
    
    
    //********************Buyer-Payment**************************
    public void createPayment(){
        osbl.createPayment(paymentdate, paymenttype, status, salesorder);
    }
    
    public void updatePaymentStatus(){
        osbl.updatePaymentStatus(payment_id, status);
    }
    
    public OES_Payment getPayment(){
        return osbl.getPayment(payment_id);
    }
    
    public List<OES_Payment> getAllPayment(){
        return osbl.getAllPayment(email);
    }
    
    
    //********************Seller-Invoice*************************
    public void createInvoice(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String releasedate=tmp.toString();
        osbl.createInvoice(releasedate, notes, payment);
    }
    
    public OES_Invoice getInvoice(){
        return osbl.getInvoice(invoice_id);
    }
    
    public List<OES_Invoice> getAllInvoice(){
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

    public Set<OES_Product> getProducts() {
        return products;
    }

    public void setProducts(Set<OES_Product> products) {
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

    public OES_PurchaseOrder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(OES_PurchaseOrder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }

    public long getSales_id() {
        return sales_id;
    }

    public void setSales_id(long sales_id) {
        this.sales_id = sales_id;
    }

    public OES_SalesOrder getSalesorder() {
        return salesorder;
    }

    public void setSalesorder(OES_SalesOrder salesorder) {
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
