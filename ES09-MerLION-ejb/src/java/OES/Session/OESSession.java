/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Session;

import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import CRMS.Entity.Company;
import OES.Entity.*;
import java.util.ArrayList;

/**
 *
 * @author sunny
 */
@Stateless
public class OESSession implements OESSessionLocal {

    @PersistenceContext
    private EntityManager em;

    //**********************OES_Product*************************
    @Override
    public void createProduct(String email, String name, double price, int quantity, String description) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account account = (Account) q.getSingleResult();
        Company company = account.getCompany();

        Product tmp = new Product();
        tmp.setName(name);
        tmp.setCompany(company);
        tmp.setDescription(description);
        tmp.setPrice(price);
        tmp.setQuantity(quantity);
        em.persist(tmp);
    }

    @Override
    public void updateProduct(long product_id, String name, double price, int quantity, String description) {
        Query q = em.createQuery("SELECT a FROM Product a WHERE a.id=:id");
        System.out.println("****************Product Name:"+name);
        q.setParameter("id", product_id);
       Product product = (Product) q.getSingleResult();
       product.setDescription(description);
       product.setName(name);
       product.setPrice(price);
       product.setQuantity(quantity);
       em.merge(product);
    }
   
    @Override
    public Product getProduct(long product_id) {
        Query q = em.createQuery("SELECT f FROM Product f WHERE f.id=:id");
        q.setParameter("id", product_id);
        return (Product) q.getSingleResult();
    }

    @Override
    public List<Product> testProduct(long product_id){
        System.out.println("************ pID"+product_id);
        Query q = em.createQuery("SELECT f FROM Product f WHERE f.id=:id");
        q.setParameter("id", product_id);
        return q.getResultList();
       
    }
    
    @Override
    public List<Product> getAllProduct(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email",email);
        Account tmp=(Account)q.getSingleResult();
        Company comp=tmp.getCompany();
        
        Query p = em.createQuery("SELECT f FROM Product f WHERE f.company=:comp");
        p.setParameter("comp", comp);
        return p.getResultList();
    }

    @Override
    public void deleteProduct(long product_id) {
        Query q = em.createQuery("DELETE FROM Product f WHERE f.id=:id");
        q.setParameter("id", product_id);
        q.executeUpdate();
    }

    @Override
    public boolean check_redundant(String email, String name) {
        //check product redundancy
        Query p = em.createQuery("SELECT m.Company.id FROM Account m WHERE m.email=:email");
        p.setParameter("email", email);
        long company_id = (long)p.getSingleResult();
        
        Query q = em.createQuery("SELECT c FROM Product c WHERE c.company.id=:id AND c.name=:name");
        q.setParameter("id", company_id);
        q.setParameter("name", name);  
        List tmp=q.getResultList();
        
        if(tmp.isEmpty())
            return true;
       
        return false;
    }

    //************************Enquiry****************************************************************************
    
    @Override
    public void createEnquiry(Account seller, Account buyer,List<OrderList> orders,String createdate) {
        System.out.println("createNew Enquire-Account:*****************************************************" + buyer);
        System.out.println("createNew Enquire-Creation date:*****************************************************" + createdate);
        System.out.println("createNew Enquire-OrderList:*****************************************************" + orders);
        
        Enquiry tmp = new Enquiry();        
        tmp.setCreatedate(createdate);
        em.persist(tmp);
        
        Account sellerAccount = em.find(Account.class, seller.getEmail());
        Account buyerAccount = em.find(Account.class, buyer.getEmail());
        tmp.setBuyer(buyerAccount);
        tmp.setSeller(sellerAccount);
        
        for(OrderList orderList:orders)
        {
            OrderList ol = em.find(OrderList.class, orderList.getId());
            tmp.getOrder().add(ol);
            ol.setEnquiry(tmp);
        }
    }
    
    @Override
    public OrderList createNewOrder(String qty,String name){
        OrderList order=new OrderList();
        
        System.out.println("createNewOrder-Quantity:*****************************************************" + qty);
        System.out.println("createNewOrder-Name:*****************************************************" + name);
        
        
        Query p = em.createQuery("SELECT a FROM Product a WHERE a.name=:name");
        p.setParameter("name",name);
        Product pro=new Product();
        pro=(Product)p.getSingleResult();
        
        order.setQuantity(qty);
        order.setProduct(pro);
        em.persist(order);
        return order;
    }
  
    
    @Override
    public Account getBuyerAccount(String email){
       Query p = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
       p.setParameter("email", email);
           return (Account) p.getSingleResult();
    }
    
    @Override
    public String getBuyName(String email){
       Query p = em.createQuery("SELECT m.Company.companyName FROM Account m WHERE m.email=:email");
       p.setParameter("email", email);
       return (String) p.getSingleResult();
       
    }
    
    @Override
    public List<Product> getSellerProducts(Long id){
        Query p=em.createQuery("SELECT m FROM Product m WHERE m.company.id=:id");
        p.setParameter("id",id);
       return p.getResultList();
    }
    
    @Override
    public List<String> getSellerProductsName(Long id){
        Query p=em.createQuery("SELECT m.name FROM Product m WHERE m.company.id=:id");
        p.setParameter("id",id);
       return p.getResultList();
    }
    @Override
    public Account getSellerAccount(String name){
        Query p = em.createQuery("SELECT m.email FROM Company m WHERE m.companyName=:name");
        p.setParameter("name", name);
        String email=(String)p.getSingleResult();
         System.out.println("sellerEmail:*****************************************************" + email);
       
        Query q = em.createQuery("SELECT m FROM Account m WHERE m.email=:email");
        q.setParameter("email",email);
        
        return (Account) q.getSingleResult();
    }
    
    @Override
    public Product getProductByName(String name){
        Query p=em.createQuery("SELECT m FROM Product m WHERE m.name=:name");
        p.setParameter("name",name);
        return (Product) p.getSingleResult();
    }
    
    @Override
    public List<String> getALLcompany(String email){
       Query p = em.createQuery("SELECT m.Company.companyName FROM Account m WHERE m.email<>:email ");
       p.setParameter("email", email);
       return p.getResultList();
    }
    
   @Override
   public List<OrderList> getOrderList(){
       Query p=em.createQuery("SELECT m FROM OrderList m ");
       return  p.getResultList();
       
   }

    /*
    //Both seller/buyer can see enquiries
    @Override
    public List<Enquiry> getAllEnquiry(String email) {
        Query q = em.createQuery("SELECT f FROM Enquiry f WHERE (f.buyer.email=:email AND f.delete_status<>:a) OR (f.seller.email=:emails AND f.delete_status<>:b)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        q.setParameter("a", 1);
        q.setParameter("b", 3);
        return q.getResultList();
    }
//*************************************************Enquiry History******************************************************
   */
    @Override
    public List<Enquiry> getAllEnquiry(String email){
        Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.buyer.email=:email");
        q.setParameter("email",email); 
        return  q.getResultList();
    }
    
    @Override
     public Enquiry getSingleEnquiry(Long id){
         System.out.println("getSingleEnquiry:*****************************************************" + id);
         Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.id=:id");
         q.setParameter("id",id); 
        return  (Enquiry)q.getSingleResult();
        
     }
     @Override
     public void deleteEnquire(Long id){
          Query p = em.createQuery("DELETE FROM  OrderList a WHERE a.enquiry.id=:id");
          p.setParameter("id",id);
          p.executeUpdate();
         
         Query q = em.createQuery("DELETE FROM  Enquiry a WHERE a.id=:id");
          q.setParameter("id",id); 
          q.executeUpdate();
         
     }
    
    //***************************Quotation*****************************************************************************************
     //**************************View-upcoming Enquiry***************************************************************************************
   @Override
   public List<Enquiry> viewUpcomingEnquire(String email){
       
         Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.seller.email=:email AND a.quotation IS NULL");
         q.setParameter("email",email); 
         
         return  q.getResultList();
        
   }
    //**************************Create New Quotation***************************************************************************************
   
   @Override
   public OES.Entity.Enquiry viewEnquireDetails(Long id){
       Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.id=:id");
       q.setParameter("id",id); 
       return (OES.Entity.Enquiry)q.getSingleResult();
   }
   
   
   @Override
   public List<OrderList> getOrderDetails(Long id){
       Query q = em.createQuery("SELECT a FROM OrderList a WHERE a.enquiry.id=:id ");
       q.setParameter("id",id); 
       return q.getResultList();
   }
   
   @Override
   public List<OrderListSub> createSubOrderList(Long id){
     
       Enquiry en=new Enquiry();
       List<OrderListSub> olistsubs=new ArrayList();
       List<OrderList> olists=new ArrayList();
       
       Query q = em.createQuery("SELECT e FROM Enquiry e WHERE e.id=:id ");
       q.setParameter("id",id); 
       en=(Enquiry) q.getSingleResult();
       
       Query p = em.createQuery("SELECT e FROM OrderList e WHERE e.enquiry.id=:id ");
       p.setParameter("id",id); 
       olists=p.getResultList();
       
       for(int i=0;i<olists.size();i++){
           OrderListSub sub=new OrderListSub();
           sub.setEnquiry(en);
           sub.setOrder(olists.get(i));
           double unitprice=olists.get(i).getProduct().getPrice();
           sub.setUnitprice(unitprice);
           double subtotal=unitprice*Integer.parseInt(olists.get(i).getQuantity());
           sub.setSubtotal(subtotal);
           em.persist(sub);
           
           olistsubs.add(sub);
           
       }
       return olistsubs;
   }
       
     //***********************************************Create New Quotation***********************************************8
   @Override
    public void createNewQuotation(Long id,double total,String date){
       
       Enquiry en=new Enquiry();
      
       Query q = em.createQuery("SELECT e FROM Enquiry e WHERE e.id=:id ");
       q.setParameter("id",id); 
       en=(Enquiry) q.getSingleResult();
       Quotation quo=new Quotation();
       quo.setEnquiry(en);
       quo.setQdate(date);
       quo.setTotal(total);
       
       em.persist(quo);
       en.setQuotation(quo);
    
    }
   

    @Override
    public Quotation getQuotation(long quotation_id) {
        Query q = em.createQuery("SELECT f FROM Quotation f WHERE f.id=:id");
        q.setParameter("id", quotation_id);
        return (Quotation) q.getSingleResult();
    }
    
    @Override
    public List<Quotation> viewQuotaionHistory(String email){
        Query q = em.createQuery("SELECT f.quotation FROM Enquiry f WHERE f.seller.email=:email AND f.quotation IS NOT NULL");
        q.setParameter("email",email);
        //q.setParameter("a", null);
        System.out.println("viewQuotaionHistory:*****************************************************" + q);
        return q.getResultList();  
    }
    
    @Override
    public List<OrderListSub> displayQuotationDetails(long id){
        Query q = em.createQuery("SELECT f FROM Quotation f WHERE f.id=:id");
        q.setParameter("id",id);
        Quotation quo=new Quotation();
        quo=(Quotation)q.getSingleResult();
        System.out.println("dispalyQuotaionDetails-session bean:*****************************************************" + quo);
        System.out.println("dispalyOrderListSub-session bean:*****************************************************" + quo.getEnquiry().getOrdersub());
        //List<OrderListSub> sublist=new ArrayList();
       return quo.getEnquiry().getOrdersub();
            
        }
    
   @Override
   public void deleteQuotation(long id){
       
          Query q = em.createQuery("DELETE FROM  OrderListSub a WHERE a.enquiry.quotation.id=:id");
          q.setParameter("id",id); 
          q.executeUpdate();
         
        
          Query p = em.createQuery("DELETE FROM  OrderList b WHERE b.enquiry.quotation.id=:id");
          p.setParameter("id",id);
          p.executeUpdate();
          
         
          
          Query pf = em.createQuery("DELETE FROM  Enquiry c WHERE c.quotation.id=:id");
          pf.setParameter("id",id);
          pf.executeUpdate();
         
         
          Query qp = em.createQuery("DELETE FROM Quotation d WHERE d.id=:id");
          qp.setParameter("id",id); 
          qp.executeUpdate();
   }
    
    
    //*********************PurchaseOrder*********************
   
   @Override
   public String getSenderName(String email){
       Query q=em.createQuery("SELECT a.Company.companyName FROM Account a WHERE a.email=:email");
       q.setParameter("email",email);
       return (String)q.getSingleResult();
   }
   
   @Override 
   public void createPurchaseOrder(Account sender,Account receiver,List<OrderList> orders,String createdate){
       
        System.out.println("createPurchaseOrder-Account:*****************************************************" + sender);
        System.out.println("creatPurchaseOrderCreation date:*****************************************************" + createdate);
        System.out.println("createPurchaseOrder-OrderList:*****************************************************" + orders);
        
        PurchaseOrder po=new PurchaseOrder();
        po.setCreatedate(createdate);
        em.persist(po);
        
        Account sellerAccount = em.find(Account.class,receiver.getEmail());
        Account buyerAccount = em.find(Account.class, sender.getEmail());
        po.setSender(buyerAccount);
        po.setReceiver(sellerAccount);
        
      
        for(OrderList orderList:orders)
        {
            OrderList ol = em.find(OrderList.class, orderList.getId());
            po.getOrder().add(ol);
            ol.setPorder(po);
        }
    }
   
   @Override
   public List<PurchaseOrder> getAllPurchaseOrder(String email){
       
        Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.sender.email=:email");
        q.setParameter("email",email); 
        return  q.getResultList();
   }
   @Override
   public void deletePurchaseOrder(long id){
       
          Query p = em.createQuery("DELETE FROM  OrderList a WHERE a.porder.id=:id");
          p.setParameter("id",id);
          p.executeUpdate();
         
         Query q = em.createQuery("DELETE FROM PurchaseOrder b WHERE b.id=:id");
          q.setParameter("id",id); 
          q.executeUpdate();
         
     }
      
   @Override
   public PurchaseOrder getSinglePurchaseOrder(long id){
       
        System.out.println("getPurchaseOrder:*****************************************************" + id);
         Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.id=:id");
         q.setParameter("id",id); 
        return  (PurchaseOrder )q.getSingleResult();
       
       
   }
   
   //***********************************Sales Order*******************************************************************
   @Override
   public List<PurchaseOrder> viewIncmoingPorder(String email){
         Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.receiver.email=:email");
         System.out.println("getPurchaseOrder-Email:*****************************************************" + email);
         q.setParameter("email",email); 
         return q.getResultList();
       
   }
   
   @Override
   public List<OrderList> viewPurchaseOrderDetails(long id){
        Query q = em.createQuery("SELECT a FROM OrderList a WHERE a.porder.id=:id");
        System.out.println("viewPurchaseOrderDetails:*****************************************************" + id);
        q.setParameter("id",id); 
        return q.getResultList();
       
   }
   
   
   
   
   
   
   
   
   
   
   
       
   }
   
   
   
   
   
   /*
    @Override
    public void createPurchaseOrder(Account buyer, Account seller, double taxrate, List<Integer> quantity, Set<Product> product, String createdate, String deliverydate) {
        PurchaseOrder tmp = new PurchaseOrder();
        tmp.setSender(buyer);
        tmp.setReceiver(seller);
        tmp.setProduct(product);
        tmp.setQuantity(quantity);
        tmp.setTaxrate(taxrate);
        tmp.setCreatedate(createdate);
        tmp.setDeliverydate(deliverydate);
        em.persist(tmp);
    }

    @Override
    public PurchaseOrder getPurchaseOrder(long purchase_id) {
        Query q = em.createQuery("SELECT f FROM PurchaseOrder f WHERE f.id=:id");
        q.setParameter("id", purchase_id);
        return (PurchaseOrder) q.getSingleResult();
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrder(String email) {
        Query q = em.createQuery("SELECT f FROM PurchaseOrder f WHERE (f.sender.email=:email AND f.delete_status<>:a) OR (f.receiver.email=:emails AND f.delete_status<>:b)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        q.setParameter("a", 1);
        q.setParameter("b", 3);
        return q.getResultList();
    }

    @Override
    public void deletePurchaseOrder(long purchase_id, String email) {
        Query q = em.createQuery("SELECT f FROM PurchaseOrder f Where f.id=:id");
        q.setParameter("id", purchase_id);
        PurchaseOrder tmp = (PurchaseOrder) q.getSingleResult();

        if (tmp.getDelete_status() == 1 || tmp.getDelete_status() == 3) {
            Query p = em.createQuery("DELETE FROM PurchaseOrder f WHERE f.id=:id");
            p.setParameter("id", purchase_id);
            p.executeUpdate();
        }
        else if(email.equals(tmp.getSender().getEmail())) tmp.setDelete_status(1);
        else tmp.setDelete_status(3);   
    }

    
    
    
    
    
    
    
    //****************************SalesOrder*********************
    @Override
    public void createSalesOrder(PurchaseOrder purchaseorder, String createdate) {
        SalesOrder tmp = new SalesOrder();
        tmp.setPurchaseorder(purchaseorder);
        tmp.setCreatedate(createdate);
        em.persist(tmp);
    }

    @Override
    public SalesOrder getSalesOrder(long salesOrder_id) {
        Query q = em.createQuery("SELECT f FROM SalesOrder f WHERE f.id=:id");
        q.setParameter("id", salesOrder_id);
        return (SalesOrder) q.getSingleResult();
    }

    @Override
    public List<SalesOrder> getAllSalesOrder(String email) {
        Query q = em.createQuery("SELECT f FROM SalesOrder f WHERE f.purchaseorder.receiver=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void deleteSalesOrder(long sales_id) {
        Query q = em.createQuery("DELETE FROM SalesOrder f WHERE f.id=:id");
        q.setParameter("id", sales_id);
        q.executeUpdate();
    }

   
    
    //***************************Payment************************
    @Override
    public void createPayment(String paymentdate, String paymenttype, String status, PurchaseOrder purchaseorder, String total_price) {
        OES_Payment tmp = new OES_Payment();
        tmp.setPaymentDate(paymentdate);
        tmp.setPaymentType(paymenttype);
        tmp.setPurchase(purchaseorder);
        tmp.setStatus(status);
        tmp.setTotal_price(total_price);
        em.persist(tmp);
    }

    @Override
    public void updatePaymentStatus(long payment_id, String status) {
        Query q = em.createQuery("SELECT a FROM OES_Payment a WHERE a.id=:id");
        q.setParameter("id", payment_id);
        OES_Payment tmp = (OES_Payment) q.getSingleResult();
        tmp.setStatus(status);
        em.merge(tmp);
    }

    @Override
    public OES_Payment getPayment(long payment_id) {
        Query q = em.createQuery("SELECT f FROM OES_Payment f WHERE f.id=:id");
        q.setParameter("id", payment_id);
        return (OES_Payment) q.getSingleResult();
    }

    
    @Override
    public List<OES_Payment> getAllPayment(String email) {
        Query q = em.createQuery("SELECT f FROM OES_Payment f WHERE (f.purchase.sender.email=:email AND f.delete_status<>:a) OR (f.purchase.receiver.email=:emails AND f.delete_status<>:b)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        q.setParameter("a", 1);
        q.setParameter("b", 3);
        return q.getResultList();
    }
    
    @Override
    public void deletePayment(long payment_id, String email) {
        Query q = em.createQuery("SELECT f FROM OES_Payment f Where f.id=:id");
        q.setParameter("id", payment_id);
        OES_Payment tmp = (OES_Payment) q.getSingleResult();

        if (tmp.getDelete_status() == 1 || tmp.getDelete_status() == 3) {
            Query p = em.createQuery("DELETE FROM OES_Payment f WHERE f.id=:id");
            p.setParameter("id", payment_id);
            p.executeUpdate();
        }
        else if(email.equals(tmp.getPurchase().getSender().getEmail())) tmp.setDelete_status(1);
        else tmp.setDelete_status(3);   
    }

    
    
    
    
    //*****************************Invoice*********************************
    @Override
    public void createInvoice(String release_date, String notes, OES_Payment payment) {
        OES_Invoice tmp = new OES_Invoice();
        tmp.setNotes(notes);
        tmp.setRelease_date(release_date);
        tmp.setPayment(payment);
        em.persist(tmp);
    }

    @Override
    public OES_Invoice getInvoice(long invoice_id) {
        Query q = em.createQuery("SELECT f FROM OES_Invoice f WHERE f.id=:id");
        q.setParameter("id", invoice_id);
        return (OES_Invoice) q.getSingleResult();
    }

    @Override
    public List<OES_Invoice> getAllInvoice(String email) {
        Query q = em.createQuery("SELECT f FROM OES_Invoice f WHERE (f.payment.purchase.sender.email=:email AND f.delete_status<>:a) OR (f.payment.purchase.receiver.email=:emails AND f.delete_status<>:b)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        q.setParameter("a", 1);
        q.setParameter("b", 3);
        return q.getResultList();
    }

    @Override
    public void deleteInvoice(long invoice_id, String email) {
        Query q = em.createQuery("SELECT f FROM OES_Invoice f Where f.id=:id");
        q.setParameter("id", invoice_id);
        OES_Invoice tmp = (OES_Invoice) q.getSingleResult();

        if (tmp.getDelete_status() == 1 || tmp.getDelete_status() == 3) {
            Query p = em.createQuery("DELETE FROM OES_Invoice f WHERE f.id=:id");
            p.setParameter("id", invoice_id);
            p.executeUpdate();
        }
        else if(email.equals(tmp.getPayment().getPurchase().getSender().getEmail())) tmp.setDelete_status(1);
        else tmp.setDelete_status(3);   
    }
    
    
    
    
    
    
    //**************************Others****************************
    @Override
    public List<Integer> ATPcheck(List<Long> product_id) {
        /*
        int n=0;
        List<Integer> tmp = new ArrayList();
        tmp = checkInventoryLevel(product_id);
        while(n<product_id.size()){
          Query q = em.createQuery("SELECT a FROM Product a WHERE a=:id");
          q.setParameter("id", product_id.get(n));
          Product product = (Product) q.getSingleResult();
          product.setQuantity(tmp.get(n));
          em.merge(tmp);   
        }
        
        return tmp;
       
        return null;
    }
    
    @Override
    public Account getTheAccount(String email){
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        return (Account) q.getSingleResult();
    }
    
    @Override
    public List<Company> getTheCompanies(){
        Query q = em.createQuery("SELECT DISTINCT p.company FROM Product p"); 
        return q.getResultList();
    }
    
    @Override
    public Company getTheCompany(long company_id){
        Query q = em.createQuery("SELECT f FROM Company f WHERE f.id=:id");
        q.setParameter("id", company_id);
        return (Company) q.getSingleResult();
    }
    
    @Override
    public List<Product> getTheProducts(long company_id){
        Query q = em.createQuery("SELECT f FROM Company f WHERE f.id=:id");
        q.setParameter("id", company_id);
        Company tmp = (Company) q.getSingleResult();
        List<Product> result = tmp.getProducts();  
        return result;
    }
*/



