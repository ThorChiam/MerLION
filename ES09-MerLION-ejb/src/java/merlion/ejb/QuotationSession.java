/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion_ejb.entity.Customer;
import merlion_ejb.entity.LineItem;
import merlion_ejb.entity.OrderRecord;
import merlion_ejb.entity.Product;
import merlion_ejb.entity.PurchaseOrder;
import merlion_ejb.entity.Salesquotation;
import merlion_ejb.entity.Vendor;



/**
 *
 * @author Tomato
 */

@Stateless

public class QuotationSession implements QuotationSessionLocal {
    @PersistenceContext
    private EntityManager em;
    
    //@Override
    /*public void addItemToQuotation(Long productId,Long quotationId,int quantity){
        OrderedProductPK pk=new OrderedProductPK();
        pk.setProductId(productId);
        pk.setQuotationId(quotationId);
        OrderedProduct op=new OrderedProduct();
        op.setOrderedProductPK(pk);
        op.setQuantity(quantity);
  
        em.persist(op);
        em.flush();
        
    }
    */
     @Override
   public List<Salesquotation>  getSalesquotation(Long id){
       Query q=em.createQuery("SELECT p FROM Salesquotation p WHERE p.id=:id");
       q.setParameter("id",id);
       
       System.out.println("SESSION BEAN***********************id is :"+id);
       System.out.println(q.getResultList());
       
       return q.getResultList();
   }
   
    
    @Override
    public Salesquotation getCustomerQuotation(Long id){
       Salesquotation quotation=new Salesquotation();
       Query q=em.createQuery("SELECT s FROM Salesquotation s WHERE s.id=:id");
       q.setParameter("id",id);
       quotation=(Salesquotation) q.getSingleResult();
       return quotation;
    }
    
    private Product getProduct(Long id){
        Product p=new Product();
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.id=:id");
        q.setParameter("id",id);
        p=(Product)q.getSingleResult();
        return p;
        
    }
    @Override
    public List<Product> getAllProduct(){
        Query q=em.createQuery("SELECT p FROM Product p");
        return q.getResultList();
    }
    @Override
    public List<OrderRecord> getAllrecord(){
        Query q=em.createQuery("SELECT o FROM OrderRecord o");
        System.out.println(q.getResultList());
        return q.getResultList();
    }
    
    
    @Override
    public List<PurchaseOrder> getPurchaseOrder(){
         Query q=em.createQuery("SELECT p FROM PurchaseOrder p");
         
         System.out.println("*********************"+q);
         return q.getResultList();
     }
    /*
    @Override
    
    public Long addNewQuotation(int quantity,Long productId,Long quotationId){
        Salesquotation quotation=getQuotation(quotationId);
        Product product=getProduct(productId);
        
        LineItem item=new LineItem();
        item.setProduct(product);
        double amount;
        double quantityd=quantity;
        amount=Math.round((product.getPrice()*quantityd)*100.0)/100.0;
        item.setQuantity(quantity);
        item.setSubtotal(amount);
        item.setQuotation(quotation);
        em.persist(item);
        em.flush();  
        quotation.getLineItem().add(item);
        product.getLineItem().add(item);
        return item.getId();
    }
     */
    private Customer getCustomer(Long id){
        Query q=em.createQuery("SELECT c FROM Customer c WHERE c.id=:id");
        q.setParameter("id",id);
        Customer c=new Customer();
        c=(Customer)q.getSingleResult();
        return c;
    } 
   
    @Override
    public Long addMoreToRecord(Long oid,Long pid,int quantity){
        LineItem item=new LineItem();
        Product product=new Product();
        OrderRecord order=new OrderRecord();
        Query q1=em.createQuery("SELECT r FROM OrderRecord r WHERE r.id=?1");
        q1.setParameter("1",oid);
       
        order=(OrderRecord)q1.getSingleResult();
        
        
        Query q2=em.createQuery("SELECT p FROM Product p WHERE p.id=?2");
        q2.setParameter("2",pid);
        product=(Product)q2.getSingleResult();
   
        double quantityd=quantity;
        double amount=Math.round((product.getPrice()*quantityd)*100.0)/100.0;
        
        item.setSubtotal(amount);
        item.setProduct(product);
        item.setQuantity(quantity);
        em.persist(item);
        
        order.getItems().add(item);
        return order.getId();
                
    }
 
   
    @Override
    public Long createRecord(String title,Long id,int quantity){
        Collection<LineItem> items=new ArrayList<LineItem>();
        LineItem item=new LineItem();
        OrderRecord order=new OrderRecord(); 
        Product product=new Product();
      
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.id=:id");
        q.setParameter("id",id);
        product=(Product)q.getSingleResult();
        
        double quantityd=quantity;
        double amount=Math.round((product.getPrice()*quantityd)*100.0)/100.0;
        
        item.setSubtotal(amount);
        item.setProduct(product);
        item.setQuantity(quantity);
        em.persist(item);
        
        items.add(item);
        
        order.setTitle(title);
        order.setItems(items);
  
        em.persist(order);  
        return order.getId();
    }
    
    @Override
     public Long placePurchaseOrder(String name, String email, String phone, String address, String cityRegion,
             String shippingdate,String paymentTerm,String currency,String shippingTerm,double taxRate, double discount,Long recordId) {
         
            PurchaseOrder purchaseOrder=new PurchaseOrder();
            Vendor vendor=new Vendor();
            OrderRecord order=new OrderRecord();
            
            vendor.create(name, email, phone, address, cityRegion);
            
            double totalprice=0;
            
            Query q=em.createQuery("SELECT r FROM OrderRecord r WHERE r.id=:id");
            q.setParameter("id", recordId);
            order=(OrderRecord)q.getSingleResult();
           
            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNow = formatter.format(currentDate.getTime());

            Iterator<LineItem> collectionline=order.getItems().iterator();
            
            while(collectionline.hasNext()){
               
                totalprice+=collectionline.next().getSubtotal();
               
            }
          
            purchaseOrder.setVendor(vendor);
            purchaseOrder.setCurrency(currency);
            purchaseOrder.setDateCreated(dateNow);
            purchaseOrder.setDiscount(discount);
            purchaseOrder.setPaymentTerm(paymentTerm);
            purchaseOrder.setShippingTerm(shippingTerm);
            purchaseOrder.setShippingdate(shippingdate);
            purchaseOrder.setTaxRate(taxRate);
            purchaseOrder.setRecord(order);
            
            double totalprice1=(1+taxRate)*totalprice;
            double totalprice2= (totalprice1)*discount;
            double totalprice3=Math.round((totalprice2)*100.0)/100.0;
            purchaseOrder.setTotal(totalprice3);
            purchaseOrder.setRecord(order);
            
            
            em.persist(purchaseOrder);
            return purchaseOrder.getId();
            
    }   
    @Override   
    public void updateQuotation(Long quotationid, String name, String email, String phone, String address, String cityRegion){
        Query q=em.createQuery("SELECT s.customer.id FROM Salesquotation s WHERE s.id=:id");
        q.setParameter("id", quotationid);
        
        Long customerId=(Long)q.getSingleResult();
        
        
//        Customer c=new Customer();
//        c=(Customer)q.getSingleResult();
//        Query q=em.createQuery("SELECT c FROM Cusomer ")
                
       System.out.println("*******************************************Customer ID"+customerId);
       q = em.createQuery("UPDATE Customer p SET p.name=?1, p.email=?2, p.phone=?3, p.address=?4, p.cityRegion=?5 WHERE p.id=?6");
        q.setParameter(1, name);
        q.setParameter(2, email);
        q.setParameter(3, phone);
        q.setParameter(4, address);
        q.setParameter(5, cityRegion);
        q.setParameter(6, customerId);
       

        q.executeUpdate();
       
   }
    
    
    
    
  

    @Override
    public Long placeQuotation(String name, String email, String phone, String address, String cityRegion, Long recordId) {
            Salesquotation quotation=new Salesquotation();
            Customer customer = new Customer();
            OrderRecord order=new OrderRecord();
            customer.create(name, email, phone, address, cityRegion);
            double totalprice=0;
            
            Query q=em.createQuery("SELECT r FROM OrderRecord r WHERE r.id=:id");
            q.setParameter("id", recordId);
            order=(OrderRecord)q.getSingleResult();
            //System.out.println("Order:"+order.getItems().size());
            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNow = formatter.format(currentDate.getTime());

            Iterator<LineItem> collectionline=order.getItems().iterator();
            
            while(collectionline.hasNext()){
               
                totalprice+=collectionline.next().getSubtotal(); 
            }
            
            totalprice=Math.round((totalprice)*100.0)/100.0;
            // create confirmation number
            Random random = new Random();
            int i = random.nextInt(999999999);
            
            quotation.setConfirmationNumber(i);
            quotation.setTotal(totalprice);
            quotation.setCustomer(customer);
            quotation.setDateCreated(dateNow);
            quotation.setRecord(order);
            
            em.persist(quotation);
            return quotation.getId();
            
    }
    @Override
    public Long deleteQuotation(Long id){
        
        
        Query query = em.createQuery("SELECT s.id FROM Salesquotation s where s.id=?1");
        query.setParameter(1, id);
        Long quotationid = (Long)query.getSingleResult();
        
        query = em.createQuery("Delete FROM Salesquotation s where s.id=?2");
        query.setParameter(2, id);
        query.executeUpdate();
        
        return quotationid; 
    }
    
    private Customer addCustomer(String name, String email, String phone, String address, String cityRegion) {
        Customer customer = new Customer();
        customer.create(name, email, phone, address, cityRegion);
        em.persist(customer);
        return customer;
    }
     
    @Override
    public List<Salesquotation> getAllquotation(){
        Query q=em.createQuery("SELECT s FROM Salesquotation s");
        return q.getResultList();
    }
    
  
    @Override
    public Salesquotation getQuotationDetails(Long id){
        Query q=em.createQuery("SELECT s FROM Salesquotation s WHERE s.id=:id");
        q.setParameter("id",id);
        Salesquotation s=(Salesquotation)q.getSingleResult();
        
      
        return s;
    }
    
    
    @Override
    public List<PurchaseOrder> getPurchaseOrderDetails(Long id){
        Query q=em.createQuery("SELECT p FROM PurchaseOrder p WHERE p.id=:id");
        q.setParameter("id",id);
        System.out.println("SESSION BEAN purchase order ID***********************id is :"+id);
       
        return  q.getResultList();
    }
    
   @Override
   public void updatePurchaseOrder(Long porderid, String name, String email, String phone, String address, String cityRegion){
     
        Query q=em.createQuery("SELECT p.vendor.id FROM PurchaseOrder p WHERE p.id=:id");
        q.setParameter("id", porderid);
        
        Long vendorId=(Long)q.getSingleResult();
    
                
       System.out.println("*******************************************Vendor ID"+vendorId);
       q = em.createQuery("UPDATE Vendor p SET p.name=?1, p.email=?2, p.phone=?3, p.address=?4, p.cityRegion=?5 WHERE p.id=?6");
        q.setParameter(1, name);
        q.setParameter(2, email);
        q.setParameter(3, phone);
        q.setParameter(4, address);
        q.setParameter(5, cityRegion);
        q.setParameter(6, vendorId);
    
        q.executeUpdate();
       
   }
    
    @Override
    public PurchaseOrder getPOrderDetails(Long id){
        Query q=em.createQuery("SELECT p FROM PurchaseOrder p WHERE p.id=:id");
        q.setParameter("id",id);
        PurchaseOrder  p=(PurchaseOrder)q.getSingleResult();
        
      
        return p;
    }
    
    
    
    
    @Override
    public void deletePurchaseOrder(Long id){
       
        //Query query = em.createQuery("SELECT e.id FROM PurchaseOrder where e.id=?1");
        //query.setParameter(1, id);
        //Long porderId = (Long)query.getSingleResult();
        
        Query query = em.createQuery("Delete FROM PurchaseOrder e where e.id=?2");
        query.setParameter(2, id);
        query.executeUpdate();
        //return porderId;
   
   }
 
     
}
