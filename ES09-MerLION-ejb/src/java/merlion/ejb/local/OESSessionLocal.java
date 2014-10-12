/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;


import java.util.Collection;
import java.util.List;
import javax.ejb.Local;
import merlion.entity.Account;
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
@Local
public interface OESSessionLocal {
    //********************Product********************************
    public void createProduct(String email, String name, double price, int quantity, String description);
    public void updateProduct(long product_id, String name, double price, int quantity, String description);
    public OES_Product getProduct(long product_id);
    public List<OES_Product> getAllProduct(String email);
    public void deleteProduct(long product_id);
    
   
    
    //********************Buyer-Enquiry**************************
    public void createEnquiry(Account seller, Account buyer, Collection<OES_Product> products, List<Integer> quantity, String createdate);
    public OES_Enquiry getEnquiry(long enquiry_id);
    public List<OES_Enquiry> getAllEnquiry(String email);//两边都能拿？？？？？
    public void deleteEnquiry(long enquiry_id);
    
    
    
    //********************Seller-Quotation***********************
    public List<String> ATPcheck(OES_Enquiry enquiry);
    public void createQuotation(OES_Enquiry enquiry, List<String> delivery_date, String createdate);
    public OES_Quotation getQuotation(long quotation_id);
    public List<OES_Quotation> getAllQuotation(String email);//两边都能拿？？？？
    public void deleteQuotation(long quotation_id);//只能buyer删
    
    
    
    //********************Buyer-Purchase Order*******************
    public void createPurchaseOrder(Account buyer, Account seller, double taxrate, List<Integer> quantity, Collection<OES_Product> product, String createdate);
    public OES_PurchaseOrder getPurchaseOrder(long purchase_id);
    public List<OES_PurchaseOrder> getAllPurchaseOrder(String email);//两边都能拿？？？？
    public void deletePurchaseOrder(long purchase_id);//只能buyer删
    
    
    
    //********************Seller-Sales Order*********************
    public void createSalesOrder(OES_PurchaseOrder purchaseorder, String createdate);
    public OES_SalesOrder getSalesOrder(long sales_id);
    public List<OES_SalesOrder> getAllSalesOrder(String email);
    public void deleteSalesOrder(long purchase_id);//只能seller删，不传给buyer
    
    
    
    //********************Buyer-Payment**************************
    public void createPayment(String paymentdate, String paymenttype, String status,  OES_SalesOrder salesorder);
    public void updatePaymentStatus(long payment_id, String status);
    public OES_Payment getPayment(long payment_id);
    public List<OES_Payment> getAllPayment(String email);
    
    
    
    //********************Seller-Invoice*************************
    public void createInvoice(String release_date, String notes, OES_Payment payment);
    public OES_Invoice getInvoice(long invoice_id);
    public List<OES_Invoice> getAllInvoice(String email);
}
