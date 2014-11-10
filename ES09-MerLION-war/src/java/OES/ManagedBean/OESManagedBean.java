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
import java.util.Set;
import javax.ejb.EJB;
import OES.Session.OESSessionLocal;
import CI.Entity.Account;
import OES.Entity.Enquiry;
import OES.Entity.OES_Invoice;
import OES.Entity.OES_Payment;
import OES.Entity.Product;
import OES.Entity.PurchaseOrder;
import OES.Entity.Quotation;
import OES.Entity.SalesOrder;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "omb")
@ViewScoped
public class OESManagedBean implements Serializable {

    @EJB
    private OESSessionLocal osbl;

    private String email;
    //Product
    private String name;
    private String description;
    private double price;
    private int quantity;
    private long product_id;
    private Product product;

    //Enquiry
    private Account seller;
    private Account buyer;
    private Set<Product> products;
    private List<Integer> quantitys = new ArrayList();
    private long enquiry_id;
    private String delete_status_enquiry;

    //Quotation
    private Enquiry enquiry;
    private List<String> delivery_date;
    private long quotation_id;
    private String delete_status_quotation;
    private Quotation quotation;

    //PurchaseOrder
    private double taxrate;
    private long purchase_id;
    private String deliverydate;
    private String delete_status_purchase;

    //SalesOrder
    private PurchaseOrder purchaseorder;
    private long sales_id;
    private SalesOrder salesorder;

    //Payment
    private String paymentdate;
    private String paymenttype;
    private String status;
    private long payment_id;
    private String delete_status_payment;

    //Invoice
    private OES_Payment payment;
    private long invoice_id;
    private String notes;
    private String delete_status_invoice;
    private OES_Invoice invoice;

    private String statusMessage;
    private List<Product> productList;

    private List<Product> allProducts;

    public OESManagedBean() {
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        if (email != null) {
            allProducts = osbl.getAllProduct(email);
        }
    }

    //********************Product********************************
    public void createProduct(String email) {

        boolean tmp = osbl.check_redundant(email, name);
        if (tmp) {
            statusMessage = "The new product is successfully added.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Status: " + statusMessage, ""));
            osbl.createProduct(email, name, price, quantity, description);
        } else {
            statusMessage = "This product has already be added.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Status: " + statusMessage, ""));
        }
    }

    public void updateProduct(Long product_id, String name, double price, int quantity, String description) {

        osbl.updateProduct(product_id, name, price, quantity, description);
    }

    public void onCellEdit(CellEditEvent event) {
        System.err.println("Selected row: " + allProducts.get(event.getRowIndex()).getId());
        updateProduct(allProducts.get(event.getRowIndex()).getId(), allProducts.get(event.getRowIndex()).getName(), allProducts.get(event.getRowIndex()).getPrice(), allProducts.get(event.getRowIndex()).getQuantity(), allProducts.get(event.getRowIndex()).getDescription());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Product getProduct() {
        product = osbl.getProduct(product_id);
        return product;
    }

    public List<Product> getProductList(Long id) {
        System.out.println("********test product id:" + id);

        productList = osbl.testProduct(id);
        return productList;
    }

    public void passValue(Long value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pid", value);
    }

    public List<Product> getAllProduct(String email) {
        return osbl.getAllProduct(email);
    }

    public void deleteProduct(Long id) {
        osbl.deleteProduct(id);
        System.out.println("THIS IS PRODUCT ID: " + id);
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

//    public String getBuyName(String email){
//        return osbl.getBuyName(email);
//    }
    public Enquiry getEnquiry() {
        enquiry = osbl.getEnquiry(enquiry_id);
        return enquiry;
    }

    public List<Enquiry> getAllEnquiry(String email) {
        return osbl.getAllEnquiry(email);
    }

    public void deleteEnquiry(String email) {
        osbl.deleteEnquiry(enquiry_id, email);
    }

    //********************Seller-Quotation***********************
    /*public List<String> ATPcheck(Enquiry enquiry) {
     return osbl.ATPcheck(enquiry);
     }
     */
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
        quotation = osbl.getQuotation(quotation_id);
        return quotation;
    }

    public List<Quotation> getAllQuotation(String email) {//两边都能拿？？？？
        return osbl.getAllQuotation(email);
    }

    public void deleteQuotation(String email) {//只能buyer删?
        osbl.deleteQuotation(product_id, email);
    }

    //********************Buyer-Purchase Order*******************
    public void createPurchaseOrder() {
        statusMessage = "A new purchase order is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createPurchaseOrder(buyer, seller, taxrate, quantitys, products, createdate, deliverydate);
    }

    public PurchaseOrder getPurchaseOrder() {
        purchaseorder = osbl.getPurchaseOrder(purchase_id);
        return purchaseorder;
    }

    public List<PurchaseOrder> getAllPurchaseOrder(String email) {//两边都能拿？？？？
        return osbl.getAllPurchaseOrder(email);
    }

    public void deletePurchaseOrder(String email) {
        osbl.deletePurchaseOrder(purchase_id, email);
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
        salesorder = osbl.getSalesOrder(sales_id);
        return salesorder;
    }

    public List<SalesOrder> getAllSalesOrder(String email) {
        return osbl.getAllSalesOrder(email);
    }

    public void deleteSalesOrder() {//只能seller删，不传给buyer
        osbl.deleteSalesOrder(sales_id);
    }

    //********************Buyer-Payment**************************
   /* public void createPayment() {
     statusMessage = "Please finish your payment soon. Thanks!";
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
     "Status: " + statusMessage, ""));
     osbl.createPayment(paymentdate, paymenttype, status, purchaseorder);
     }
     */
    public void updatePaymentStatus() {
        osbl.updatePaymentStatus(payment_id, status);
    }

    public OES_Payment getPayment() {
        payment = osbl.getPayment(payment_id);
        return payment;
    }

    public List<OES_Payment> getAllPayment(String email) {
        return osbl.getAllPayment(email);
    }

    public void deletePayment(String email) {
        osbl.deletePayment(payment_id, email);
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
        invoice = osbl.getInvoice(invoice_id);
        return invoice;
    }

    public List<OES_Invoice> getAllInvoice(String email) {
        return osbl.getAllInvoice(email);
    }

    public void deleteInvoice(String email) {
        osbl.deleteInvoice(invoice_id, email);
    }

    //******************GETTER AND SETTER***************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
    }

    public String getName() {
        //name = productList.get(0).getName();
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
        product_id = productList.get(0).getId();
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

    public String getDelete_status_enquiry() {
        return delete_status_enquiry;
    }

    public void setDelete_status_enquiry(String delete_status_enquiry) {
        this.delete_status_enquiry = delete_status_enquiry;
    }

    public String getDelete_status_quotation() {
        return delete_status_quotation;
    }

    public void setDelete_status_quotation(String delete_status_quotation) {
        this.delete_status_quotation = delete_status_quotation;
    }

    public String getDelete_status_purchase() {
        return delete_status_purchase;
    }

    public void setDelete_status_purchase(String delete_status_purchase) {
        this.delete_status_purchase = delete_status_purchase;
    }

    public String getDelete_status_payment() {
        return delete_status_payment;
    }

    public void setDelete_status_payment(String delete_status_payment) {
        this.delete_status_payment = delete_status_payment;
    }

    public String getDelete_status_invoice() {
        return delete_status_invoice;
    }

    public void setDelete_status_invoice(String delete_status_invoice) {
        this.delete_status_invoice = delete_status_invoice;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

}
