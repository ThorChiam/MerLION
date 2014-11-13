/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import WMS.Entity.Inventory;
import CRMS.Entity.Company;
import OES.Entity.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        System.out.println("P ID:" + tmp.getId());
        Inventory inventory = new Inventory();
        inventory.setId(tmp.getId());
        inventory.setName(tmp.getName());
        em.persist(inventory);
        System.out.println("I ID 1:" + inventory.getId());
        inventory.setId(tmp.getId());
        em.merge(inventory);
        System.out.println("I ID 2:" + inventory.getId());
    }

    @Override
    public void updateProduct(long product_id, String name, double price, int quantity, String description) {
        Query q = em.createQuery("SELECT a FROM Product a WHERE a.id=:id");
        System.out.println("****************Product Name:" + name);
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
    public List<Product> testProduct(long product_id) {
        System.out.println("************ pID" + product_id);
        Query q = em.createQuery("SELECT f FROM Product f WHERE f.id=:id");
        q.setParameter("id", product_id);
        return q.getResultList();

    }

    @Override
    public List<Product> getAllProduct(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account tmp = (Account) q.getSingleResult();
        Company comp = tmp.getCompany();

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
        long company_id = (long) p.getSingleResult();

        Query q = em.createQuery("SELECT c FROM Product c WHERE c.company.id=:id AND c.name=:name");
        q.setParameter("id", company_id);
        q.setParameter("name", name);
        List tmp = q.getResultList();

        if (tmp.isEmpty()) {
            return true;
        }

        return false;
    }

    //************************Enquiry****************************************************************************
    
    @Override
    public List<String> viewProductDesc(List<String> name){
        
        List<String> names=new ArrayList();
        
        for(int i=0;i<name.size();i++){
            String ex=name.get(i);
             Query q = em.createQuery("SELECT c.description FROM Product c WHERE c.name=:ex");
             q.setParameter("name",ex);
             
            names.add((String)q.getSingleResult());
        }
        return names;
    }
    
    
    
    
    
    @Override
    public void createEnquiry(Account seller, Account buyer, List<OrderList> orders, String createdate) {
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

        for (OrderList orderList : orders) {
            OrderList ol = em.find(OrderList.class, orderList.getId());
            tmp.getOrder().add(ol);
            ol.setEnquiry(tmp);
        }
    }

    @Override
    public OrderList createNewOrder(String qty, String name) {
        OrderList order = new OrderList();

        System.out.println("createNewOrder-Quantity:*****************************************************" + qty);
        System.out.println("createNewOrder-Name:*****************************************************" + name);

        Query p = em.createQuery("SELECT a FROM Product a WHERE a.name=:name");
        p.setParameter("name", name);
        Product pro = new Product();
        pro = (Product) p.getSingleResult();

        order.setQuantity(qty);
        order.setProduct(pro);
        em.persist(order);
        return order;
    }
    
   

    @Override
    public Account getBuyerAccount(String email) {
        Query p = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        p.setParameter("email", email);
        return (Account) p.getSingleResult();
    }

    @Override
    public String getBuyName(String email) {
        Query p = em.createQuery("SELECT m.Company.companyName FROM Account m WHERE m.email=:email");
        p.setParameter("email", email);
        return (String) p.getSingleResult();

    }

    @Override
    public List<Product> getSellerProducts(Long id) {
        Query p = em.createQuery("SELECT m FROM Product m WHERE m.company.id=:id");
        p.setParameter("id", id);
        return p.getResultList();
    }

    @Override
    public List<String> getSellerProductsName(Long id) {
        Query p = em.createQuery("SELECT m.name FROM Product m WHERE m.company.id=:id");
        p.setParameter("id", id);
        return p.getResultList();
    }
    
    @Override
    public List<Product> getSellerSelectedProductsName(List<String> names) {
        List<Product> pro=new ArrayList();
        
        for(int i=0;i<names.size();i++){
        String name=names.get(i);
        
        Query p = em.createQuery("SELECT m FROM Product m WHERE m.name=:name");
        p.setParameter("name",name);
        pro.add((Product) p.getSingleResult());
        
        }
        return pro;
    }

    @Override
    public Account getSellerAccount(String name) {
        Query p = em.createQuery("SELECT m.email FROM Company m WHERE m.companyName=:name");
        p.setParameter("name", name);
        String email = (String) p.getSingleResult();
        System.out.println("sellerEmail:*****************************************************" + email);

        Query q = em.createQuery("SELECT m FROM Account m WHERE m.email=:email");
        q.setParameter("email", email);

        return (Account) q.getSingleResult();
    }

    @Override
    public Product getProductByName(String name) {
        Query p = em.createQuery("SELECT m FROM Product m WHERE m.name=:name");
        p.setParameter("name", name);
        return (Product) p.getSingleResult();
    }

    @Override
    public List<String> getALLcompany(String email) {
        Query p = em.createQuery("SELECT m.Company.companyName FROM Account m WHERE m.email<>:email ");
        p.setParameter("email", email);
        return p.getResultList();
    }

    @Override
    public List<OrderList> getOrderList() {
        Query p = em.createQuery("SELECT m FROM OrderList m ");
        return p.getResultList();

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
    public List<Enquiry> getAllEnquiry(String email) {
        Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.buyer.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public Enquiry getSingleEnquiry(Long id) {
        System.out.println("getSingleEnquiry:*****************************************************" + id);
        Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.id=:id");
        q.setParameter("id", id);
        return (Enquiry) q.getSingleResult();

    }

    @Override
    public void deleteEnquire(Long id) {
        Query p = em.createQuery("DELETE FROM  OrderList a WHERE a.enquiry.id=:id");
        p.setParameter("id", id);
        p.executeUpdate();

        Query q = em.createQuery("DELETE FROM  Enquiry a WHERE a.id=:id");
        q.setParameter("id", id);
        q.executeUpdate();

    }

    //***************************Quotation*****************************************************************************************
    //**************************View-upcoming Enquiry***************************************************************************************
    @Override
    public List<Enquiry> viewUpcomingEnquire(String email) {

        Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.seller.email=:email AND a.quotation IS NULL");
        q.setParameter("email", email);

        return q.getResultList();

    }
    //**************************Create New Quotation***************************************************************************************

    @Override
    public OES.Entity.Enquiry viewEnquireDetails(Long id) {
        Query q = em.createQuery("SELECT a FROM  Enquiry a WHERE a.id=:id");
        q.setParameter("id", id);
        return (OES.Entity.Enquiry) q.getSingleResult();
    }

    @Override
    public List<OrderList> getOrderDetails(Long id) {
        Query q = em.createQuery("SELECT a FROM OrderList a WHERE a.enquiry.id=:id ");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<OrderListSub> createSubOrderList(Long id) {

        Enquiry en = new Enquiry();
        List<OrderListSub> olistsubs = new ArrayList();
        List<OrderList> olists = new ArrayList();

        Query q = em.createQuery("SELECT e FROM Enquiry e WHERE e.id=:id ");
        q.setParameter("id", id);
        en = (Enquiry) q.getSingleResult();

        Query p = em.createQuery("SELECT e FROM OrderList e WHERE e.enquiry.id=:id ");
        p.setParameter("id", id);
        olists = p.getResultList();

        for (int i = 0; i < olists.size(); i++) {
            OrderListSub sub = new OrderListSub();
            sub.setEnquiry(en);
            sub.setOrder(olists.get(i));
            double unitprice = olists.get(i).getProduct().getPrice();
            sub.setUnitprice(unitprice);
            double subtotal = unitprice * Integer.parseInt(olists.get(i).getQuantity());
            sub.setSubtotal(subtotal);
            em.persist(sub);

            olistsubs.add(sub);

        }
        return olistsubs;
    }

    //***********************************************Create New Quotation***********************************************8
    @Override
    public void createNewQuotation(Long id, double total, String date) {

        Enquiry en = new Enquiry();

        Query q = em.createQuery("SELECT e FROM Enquiry e WHERE e.id=:id ");
        q.setParameter("id", id);
        en = (Enquiry) q.getSingleResult();
        Quotation quo = new Quotation();
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
    public List<Quotation> viewQuotaionHistory(String email) {
        Query q = em.createQuery("SELECT f.quotation FROM Enquiry f WHERE f.seller.email=:email AND f.quotation IS NOT NULL");
        q.setParameter("email", email);
        //q.setParameter("a", null);
        System.out.println("viewQuotaionHistory:*****************************************************" + q);
        return q.getResultList();
    }

    @Override
    public List<OrderListSub> displayQuotationDetails(long id) {
        Query q = em.createQuery("SELECT f FROM Quotation f WHERE f.id=:id");
        q.setParameter("id", id);
        Quotation quo = new Quotation();
        quo = (Quotation) q.getSingleResult();
        System.out.println("dispalyQuotaionDetails-session bean:*****************************************************" + quo);
        System.out.println("dispalyOrderListSub-session bean:*****************************************************" + quo.getEnquiry().getOrdersub());
        //List<OrderListSub> sublist=new ArrayList();
        return quo.getEnquiry().getOrdersub();

    }

    @Override
    public void deleteQuotation(long id) {

        Query q = em.createQuery("DELETE FROM  OrderListSub a WHERE a.enquiry.quotation.id=:id");
        q.setParameter("id", id);
        q.executeUpdate();

        Query p = em.createQuery("DELETE FROM  OrderList b WHERE b.enquiry.quotation.id=:id");
        p.setParameter("id", id);
        p.executeUpdate();

        Query pf = em.createQuery("DELETE FROM  Enquiry c WHERE c.quotation.id=:id");
        pf.setParameter("id", id);
        pf.executeUpdate();

        Query qp = em.createQuery("DELETE FROM Quotation d WHERE d.id=:id");
        qp.setParameter("id", id);
        qp.executeUpdate();
    }

    //*********************PurchaseOrder*********************
    @Override
    public String getSenderName(String email) {
        Query q = em.createQuery("SELECT a.Company.companyName FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        return (String) q.getSingleResult();
    }

    @Override
    public void createPurchaseOrder(Account sender, Account receiver, List<OrderList> orders, String createdate) {

        System.out.println("createPurchaseOrder-Account:*****************************************************" + sender);
        System.out.println("creatPurchaseOrderCreation date:*****************************************************" + createdate);
        System.out.println("createPurchaseOrder-OrderList:*****************************************************" + orders);

        PurchaseOrder po = new PurchaseOrder();
        po.setCreatedate(createdate);
        em.persist(po);

        Account sellerAccount = em.find(Account.class, receiver.getEmail());
        Account buyerAccount = em.find(Account.class, sender.getEmail());
        po.setSender(buyerAccount);
        po.setReceiver(sellerAccount);

        for (OrderList orderList : orders) {
            OrderList ol = em.find(OrderList.class, orderList.getId());
            po.getOrder().add(ol);
            ol.setPorder(po);
        }
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrder(String email) {

        Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.sender.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void deletePurchaseOrder(long id) {

        Query s = em.createQuery("DELETE FROM  OrderListSub a WHERE a.porder.id=:id");
        s.setParameter("id", id);
        s.executeUpdate();

        Query p = em.createQuery("DELETE FROM  OrderList a WHERE a.porder.id=:id");
        p.setParameter("id", id);
        p.executeUpdate();

        Query q = em.createQuery("DELETE FROM PurchaseOrder b WHERE b.id=:id");
        q.setParameter("id", id);
        q.executeUpdate();

    }

    @Override
    public PurchaseOrder getSinglePurchaseOrder(long id) {

        System.out.println("getPurchaseOrder:*****************************************************" + id);
        Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.id=:id");
        q.setParameter("id", id);
        return (PurchaseOrder) q.getSingleResult();

    }
    
    @Override
    public List<SalesOrder> viewOrderConfirmation(String email){
        Query q = em.createQuery("SELECT a FROM SalesOrder a WHERE a.purchaseorder.sender.email=:email");
        q.setParameter("email",email);
        return q.getResultList();
     
    }
    
    @Override
    public void deleteOrderConfirmation(long id){
        Query q = em.createQuery("SELECT a FROM SalesOrder a WHERE a.id=:id");
        q.setParameter("id", id);
        q.executeUpdate();
        
    }

    //***********************************Sales Order*******************************************************************
    @Override
    public List<PurchaseOrder> viewIncmoingPorder(String email) {
        Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.receiver.email=:email");
        System.out.println("getPurchaseOrder-Email:*****************************************************" + email);
        q.setParameter("email", email);
        return q.getResultList();

    }

    @Override
    public List<OrderList> viewPurchaseOrderDetails(long id) {
        Query q = em.createQuery("SELECT a FROM OrderList a WHERE a.porder.id=:id");
        System.out.println("viewPurchaseOrderDetails:*****************************************************" + id);
        q.setParameter("id", id);
        return q.getResultList();

    }

    //*****************************************************Sales Ordder*****************************************************
    @Override
    public List<PurchaseOrder> displayIncomingPorder(String email) {

        Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.receiver.email=:email AND a.salesorder IS NULL ");
        System.out.println("viewPurchaseOrderDetails:*****************************************************" + email);
        q.setParameter("email", email);

        return q.getResultList();
    }

    @Override
    public String displaySenderName(long id) {
        Query q = em.createQuery("SELECT a.sender.Company.companyName FROM PurchaseOrder a WHERE a.id=:id");
        System.out.println("viewPurchaseOrderDetails:*****************************************************" + id);
        q.setParameter("id", id);
        return (String) q.getSingleResult();

    }

    @Override
    public Account displaySenderSalesOrder(Long id) {
        Query q = em.createQuery("SELECT a.sender FROM PurchaseOrder a WHERE a.id=:id");
        System.out.println("displaySenderSalesOrder:*****************************************************" + id);
        q.setParameter("id", id);
        return (Account) q.getSingleResult();

    }

    @Override
    public Account displayRecieverSalesOrder(Long id) {
        Query q = em.createQuery("SELECT a.receiver FROM PurchaseOrder a WHERE a.id=:id");
        System.out.println("displayRecieverSalesOrder:*****************************************************" + id);
        q.setParameter("id", id);
        return (Account) q.getSingleResult();

    }

    @Override
    public PurchaseOrder displayPurchaseOrder(long id) {
        Query q = em.createQuery("SELECT a FROM PurchaseOrder a WHERE a.id=:id");
        System.out.println("displayPurchaseOrder:*****************************************************" + id);
        q.setParameter("id", id);
        return (PurchaseOrder) q.getSingleResult();

    }

    @Override
    public String displayReciverName(Long id) {
        Query q = em.createQuery("SELECT a.receiver.Company.companyName FROM PurchaseOrder a WHERE a.id=:id");
        System.out.println("displayReciverName:*****************************************************" + id);
        q.setParameter("id", id);
        return (String) q.getSingleResult();

    }

    @Override
    public List<OrderListSub> createsubforSalesOrder(Long id) {
        PurchaseOrder porder = new PurchaseOrder();
        List<OrderListSub> olistsubs = new ArrayList();
        List<OrderList> olists = new ArrayList();

        Query q = em.createQuery("SELECT e FROM PurchaseOrder e WHERE e.id=:id");
        q.setParameter("id", id);
        porder = (PurchaseOrder) q.getSingleResult();

        Query p = em.createQuery("SELECT e FROM OrderList e WHERE e.porder.id=:id ");
        p.setParameter("id", id);
        olists = p.getResultList();

        for (int i = 0; i < olists.size(); i++) {
            OrderListSub sub = new OrderListSub();

            sub.setOrder(olists.get(i));
            double unitprice = olists.get(i).getProduct().getPrice();
            sub.setUnitprice(unitprice);
            double subtotal = unitprice * Integer.parseInt(olists.get(i).getQuantity());
            sub.setSubtotal(subtotal);

            sub.setPorder(porder);
            em.persist(sub);

            olistsubs.add(sub);

        }
        return olistsubs;
    }

    @Override
    public void createSalesOrder(String createdate, double tax, double discount, double total, String shipdate, PurchaseOrder porder) {
        SalesOrder sorder = new SalesOrder();

        Query q = em.createQuery("SELECT e FROM PurchaseOrder e WHERE e=:porder");
        q.setParameter("porder", porder);
        porder = (PurchaseOrder) q.getSingleResult();

        sorder.setPurchaseorder(porder);
        sorder.setCreatedate(createdate);
        sorder.setDiscount(discount);
        sorder.setShipdate(shipdate);
        sorder.setTax(tax);
        sorder.setTotal(total);
        em.persist(sorder);
        porder.setSalesorder(sorder);
        
        Makepayment pay=new Makepayment();
        pay.setSalesorder(sorder);
        pay.setStatus("unpaid");
        pay.setPaymentType("Check");
        em.persist(pay);
        sorder.setPayment(pay);
        

    }

    @Override
    public List<SalesOrder> viewSalesOrderHistory(String email) {
        Query q = em.createQuery("SELECT e.salesorder FROM PurchaseOrder e WHERE e.receiver.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void deleteSalesHistory(long id) {
        Query s = em.createQuery("DELETE FROM  OrderListSub a WHERE a.porder.salesorder.id=:id");
        s.setParameter("id", id);
        s.executeUpdate();

        Query a = em.createQuery("DELETE FROM  OrderList a WHERE a.porder.salesorder.id=:id");
        a.setParameter("id", id);
        a.executeUpdate();

        Query q = em.createQuery("DELETE FROM PurchaseOrder b WHERE b.salesorder.id=:id");
        q.setParameter("id", id);
        q.executeUpdate();

        Query p = em.createQuery("DELETE FROM SalesOrder b WHERE b.id=:id");
        p.setParameter("id", id);
        p.executeUpdate();

    }
    
    @Override
    public SalesOrder viewSingleSaleOrder(long id){
        Query q = em.createQuery("SELECT e FROM SalesOrder e WHERE e.id=:id");
        q.setParameter("id",id);
        return (SalesOrder)q.getSingleResult();
        
    }
 
    //*******************************************Payment**************************************************
    @Override
    public void createPayment(String email){
        Query q = em.createQuery("SELECT e FROM SalesOrder e WHERE e.purchaseorder.receiver.admin=:email AND e.payment IS NULL");
        q.setParameter("email",email);
        
        SalesOrder orders=new SalesOrder();
        Makepayment pay=new Makepayment();
        
        pay.setSalesorder(orders);
        pay.setPaymentType("Check");
        pay.setStatus("Outsanding");
        em.persist(pay);
        orders.setPayment(pay);   
    }
    
    @Override
    public List<Makepayment> viewOutstandingPayment(String email){
        Query q = em.createQuery("SELECT e FROM Makepayment e WHERE e.salesorder.purchaseorder.receiver.email=:email AND e.paymentDate IS NULL");
        q.setParameter("email",email);
        return q.getResultList();
        
    }
    
    @Override
    public void updatePaymentStatus(long id){
        Date createDate;
        //DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Query q = em.createQuery("SELECT e FROM Makepayment e WHERE e.id=:id");
        q.setParameter("id",id);
        
        Makepayment pay=(Makepayment)q.getSingleResult();
        
       
        java.util.Date date = new java.util.Date();
        createDate = new Timestamp(date.getTime());
        
        Timestamp tmp = new Timestamp(createDate.getTime());
        String creationdate = tmp.toString();
      
        pay.setStatus("paid");
        pay.setPaymentDate(creationdate);
        em.merge(pay);
        
        OES_Invoice invoice=new OES_Invoice();
        invoice.setPayment(pay);
        invoice.setRelease_date(creationdate);
        em.persist(invoice);
        
        pay.setOes_invoice(invoice);
                       
    }
   
    @Override
    public List<Makepayment> viewPaidPayment(String email){
        Query q = em.createQuery("SELECT e FROM Makepayment e WHERE e.salesorder.purchaseorder.receiver.email=:email AND e.paymentDate IS NOT NULL");
        q.setParameter("email",email);
        return q.getResultList();
        
    }
   
    @Override
    public List<OES_Invoice> viewInvoice(String email){
        Query q = em.createQuery("SELECT e FROM OES_Invoice e WHERE e.payment.salesorder.purchaseorder.receiver.email=:email");
        q.setParameter("email",email);
        return q.getResultList();
        
    }
    
    
    
    
   
    
}
