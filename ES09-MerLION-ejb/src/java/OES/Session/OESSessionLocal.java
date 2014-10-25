/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Session;

import java.util.List;
import java.util.Set;
import javax.ejb.Local;
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
@Local
public interface OESSessionLocal {
    //********************Product********************************
    public void createProduct(String email, String name, double price, int quantity, String description);
    public void updateProduct(long product_id, String name, double price, int quantity, String description);
    public Product getProduct(long product_id);
    public List<Product> getAllProduct(String email);
    public void deleteProduct(long product_id);
    public boolean check_redundant(long company_id, String name); //check product redundancy
    
   
    
    //********************Buyer-Enquiry**************************
    public void createEnquiry(Account seller, Account buyer, Set<Product> products, List<Integer> quantity, String createdate);
    public Enquiry getEnquiry(long enquiry_id);
    public List<Enquiry> getAllEnquiry(String email);//两边都能拿？？？？？
    public void deleteEnquiry(long enquiry_id, String email);
    
    
    
    //********************Seller-Quotation***********************
    public List<String> ATPcheck(Enquiry enquiry);
    public void createQuotation(Enquiry enquiry, List<String> delivery_date, String createdate);
    public Quotation getQuotation(long quotation_id);
    public List<Quotation> getAllQuotation(String email);//两边都能拿？？？？
    public void deleteQuotation(long quotation_id, String email);//只能buyer删
    
    
    
    //********************Buyer-Purchase Order*******************
    public void createPurchaseOrder(Account buyer, Account seller, double taxrate, List<Integer> quantity, Set<Product> product, String createdate, String deliverydate);
    public PurchaseOrder getPurchaseOrder(long purchase_id);
    public List<PurchaseOrder> getAllPurchaseOrder(String email);//两边都能拿？？？？
    public void deletePurchaseOrder(long purchase_id, String email);//只能buyer删
    
    
    
    //********************Seller-Sales Order*********************
    public void createSalesOrder(PurchaseOrder purchaseorder, String createdate);
    public SalesOrder getSalesOrder(long sales_id);
    public List<SalesOrder> getAllSalesOrder(String email);
    public void deleteSalesOrder(long sales_id);//只能seller删，不传给buyer
    
    
    
    //********************Buyer-Payment**************************
    public void createPayment(String paymentdate, String paymenttype, String status,  PurchaseOrder purchaseorder);
    public void updatePaymentStatus(long payment_id, String status);
    public OES_Payment getPayment(long payment_id);
    public List<OES_Payment> getAllPayment(String email);
    public void deletePayment(long payment_id, String email);
    
    
    
    //********************Seller-Invoice*************************
    public void createInvoice(String release_date, String notes, OES_Payment payment);
    public OES_Invoice getInvoice(long invoice_id);
    public List<OES_Invoice> getAllInvoice(String email);
    public void deleteInvoice(long invoice_id, String email);
   
}
