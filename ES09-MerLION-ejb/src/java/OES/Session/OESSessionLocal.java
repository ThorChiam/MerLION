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
import CRMS.Entity.Company;
import OES.Entity.Enquiry;
import OES.Entity.Makepayment;
import OES.Entity.OES_Invoice;
//import OES.Entity.OES_Invoice;
//import OES.Entity.OES_Payment;
import OES.Entity.OrderList;
import OES.Entity.OrderListSub;
import OES.Entity.Product;
import OES.Entity.PurchaseOrder;
import OES.Entity.Quotation;
import OES.Entity.SalesOrder;
//import OES.Entity.SalesOrder;

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
    public boolean check_redundant(String email, String name); //check product redundancy
    public List<Product> testProduct(long product_id);//test
    
   
    
    //********************Buyer-Enquiry**************************
    public void createEnquiry(Account seller, Account buyer,List<OrderList> orders,String createdate);
   
    //public void deleteEnquiry(long enquiry_id, String email);
    public String getBuyName(String email);
    public List<String> getALLcompany(String email);
    public Account getBuyerAccount(String email);
    public Account getSellerAccount(String name);
    public List<Product> getSellerProducts(Long id);
    public List<String> getSellerProductsName(Long id);
    public Product getProductByName(String name);
    public OrderList createNewOrder(String qty,String name);
    public List<OrderList> getOrderList();
    
     public List<String> viewProductDesc(List<String> name);
     public List<Product> getSellerSelectedProductsName(List<String> names);
     
     
     
    //***************Enquiry History******************************************************88
    public List<Enquiry> getAllEnquiry(String email);//两边都能拿？？？？？
    public Enquiry getSingleEnquiry(Long id);
    public void deleteEnquire(Long id);
  
    
    
    
    //********************Seller-Quotation***********************
    //public void createQuotation(Enquiry enquiry, List<String> delivery_date, String createdate);
    public Quotation getQuotation(long quotation_id);
    
    //public void deleteQuotation(long quotation_id, String email);//只能buyer删
    
    public List<Enquiry> viewUpcomingEnquire(String email);
    public OES.Entity.Enquiry viewEnquireDetails(Long id);
    public List<OrderList> getOrderDetails(Long id);
   
     public List<OrderListSub> createSubOrderList(Long id);
     public void createNewQuotation(Long id,double total,String date);
     public List<Quotation> viewQuotaionHistory(String email);
    public List<OrderListSub> displayQuotationDetails(long id);
    public void deleteQuotation(long id);
     
     
     
     

    //********************Buyer-Purchase Order*******************
    //public void createPurchaseOrder(Account buyer, Account seller, double taxrate, List<Integer> quantity, Set<Product> product, String createdate, String deliverydate);
   // public PurchaseOrder getPurchaseOrder(long purchase_id);
   //public List<PurchaseOrder> getAllPurchaseOrder(String email);//两边都能拿？？？？
    //public void deletePurchaseOrder(long purchase_id, String email);//只能buyer删
     
    //*************************************New-Purchase Order******************************
    public String getSenderName(String email);
    public void createPurchaseOrder(Account sender,Account receiver,List<OrderList> orders,String createdate);
     public List<PurchaseOrder> getAllPurchaseOrder(String email);
     public void deletePurchaseOrder(long id);
     public PurchaseOrder getSinglePurchaseOrder(long id);
     
     public List<SalesOrder> viewOrderConfirmation(String email);
     public void deleteOrderConfirmation(long id);
    
   
    //****************************************Sales Order*************************************
     
     public List<PurchaseOrder> viewIncmoingPorder(String email);
     public List<OrderList> viewPurchaseOrderDetails(long id);
     public List<PurchaseOrder> displayIncomingPorder(String email);
      public String displaySenderName(long id);
       public String displayReciverName(Long id);
       public Account displaySenderSalesOrder(Long id);
        public Account displayRecieverSalesOrder(Long id);
        public PurchaseOrder displayPurchaseOrder(long id);
         public List<OrderListSub> createsubforSalesOrder(Long id);
      public void createSalesOrder(String createdate, double tax, double discount, double total, String shipdate, PurchaseOrder porder);
      public List<SalesOrder> viewSalesOrderHistory(String email);
       public void deleteSalesHistory(long id);
       public SalesOrder viewSingleSaleOrder(long id);
     
   
    
    
    
    //********************Buyer-Payment**************************
    public void createPayment(String email);
    
    public List<Makepayment> viewOutstandingPayment(String email);
    public void updatePaymentStatus(long id);
     public List<Makepayment> viewPaidPayment(String email);
     
     //************************Invoice*********************************8
     
     public List<OES_Invoice> viewInvoice(String email);
    
  
}
