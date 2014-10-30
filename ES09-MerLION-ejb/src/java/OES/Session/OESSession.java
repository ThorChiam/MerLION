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
import OES.Entity.Enquiry;
import OES.Entity.OES_Invoice;
import OES.Entity.OES_Payment;
import OES.Entity.Product;
import OES.Entity.PurchaseOrder;
import OES.Entity.Quotation;
import OES.Entity.SalesOrder;
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
//        Product product = (Product) q.getSingleResult();
//        product.setDescription(description);
//        product.setName(name);
//        product.setPrice(price);
//        product.setQuantity(quantity);
//        em.merge(product);
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

    //************************Enquiry***************************
    @Override
    public void createEnquiry(Account seller, Account buyer, Set<Product> products, List<Integer> quantity, String createdate) {
        Enquiry tmp = new Enquiry();
        tmp.setBuyer(buyer);
        tmp.setSeller(seller);
        tmp.setProduct(products);
        tmp.setQuantity(quantity);
        tmp.setCreatedate(createdate);
        em.persist(tmp);
    }

    @Override
    public Enquiry getEnquiry(long enquiry_id) {
        Query q = em.createQuery("SELECT f FROM Enquiry f WHERE f.id=:id");
        q.setParameter("id", enquiry_id);
        return (Enquiry) q.getSingleResult();
    }

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

    @Override
    public void deleteEnquiry(long enquiry_id, String email) {
        Query q = em.createQuery("SELECT f FROM Enquiry f Where f.id=:id");
        q.setParameter("id", enquiry_id);
        Enquiry tmp = (Enquiry) q.getSingleResult();

        if (tmp.getDelete_status() == 1 || tmp.getDelete_status() == 3) {
            Query p = em.createQuery("DELETE FROM Enquiry f WHERE f.id=:id");
            p.setParameter("id", enquiry_id);
            p.executeUpdate();
        }
        else if(email.equals(tmp.getBuyer().getEmail())) tmp.setDelete_status(1);
        else tmp.setDelete_status(3);    
    }

    
    
    
    //***************************Quotation*******************
    @Override
    public void createQuotation(Enquiry enquiry, List<String> delivery_date, String createdate) {
        Quotation tmp = new Quotation();
        tmp.setDeliver_date(delivery_date);
        tmp.getCreatedate();
        tmp.setEnquiry(enquiry);
        em.persist(tmp);
    }

    @Override
    public Quotation getQuotation(long quotation_id) {
        Query q = em.createQuery("SELECT f FROM Quotation f WHERE f.id=:id");
        q.setParameter("id", quotation_id);
        return (Quotation) q.getSingleResult();
    }

    @Override
    public List<Quotation> getAllQuotation(String email) {
        Query q = em.createQuery("SELECT f FROM Quotation f WHERE (f.enquiry.buyer.email=:email AND f.delete_status<>:a) OR (f.enquiry.seller.email=:emails AND f.delete_status<>:b)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        q.setParameter("a", 1);
        q.setParameter("b", 3);
        return q.getResultList();
    }

    @Override
    public void deleteQuotation(long quotation_id, String email) {
        Query q = em.createQuery("SELECT f FROM Quotation f Where f.id=:id");
        q.setParameter("id", quotation_id);
        Quotation tmp = (Quotation) q.getSingleResult();

        if (tmp.getDelete_status() == 1 || tmp.getDelete_status() == 3) {
            Query p = em.createQuery("DELETE FROM Quotation f WHERE f.id=:id");
            p.setParameter("id", quotation_id);
            p.executeUpdate();
        }
        else if(email.equals(tmp.getEnquiry().getBuyer().getEmail())) tmp.setDelete_status(1);
        else tmp.setDelete_status(3);   
    }

    
    
    
    
    
    //*********************PurchaseOrder*********************
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
        */
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

}
