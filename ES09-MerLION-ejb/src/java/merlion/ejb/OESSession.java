/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.ejb.local.OESSessionLocal;
import merlion.entity.CommonInfrastructure.Account;
import merlion.entity.CRMS.Company;
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
@Stateless

public class OESSession implements OESSessionLocal{
   
    @PersistenceContext
    private EntityManager em;
    
    
    //**********************OES_Product*************************
    @Override
    public void createProduct(String email, String name, double price, int quantity, String description) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account account = (Account)q.getSingleResult(); 
        Company company = account.getCompany();
        
        OES_Product tmp= new OES_Product();
        tmp.setCompany(company);
        tmp.setDescription(description);
        tmp.setPrice(price);
        tmp.setQuantity(quantity);
        em.persist(tmp);
    }
    
    @Override
    public void updateProduct(long product_id, String name, double price, int quantity, String description){
        Query q = em.createQuery("SELECT a FROM OES_Product a WHERE a.id=:id");
        q.setParameter("id",product_id);
        OES_Product product = (OES_Product)q.getSingleResult(); 
        product.setDescription(description);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        em.merge(product);
    }

    @Override
    public OES_Product getProduct(long product_id) {
        Query q = em.createQuery("SELECT f FROM OES_Product f WHERE f.id=:id");
        q.setParameter("id", product_id);
        return (OES_Product)q.getSingleResult();
    }

    @Override
    public List<OES_Product> getAllProduct(String email) {
        Query q = em.createQuery("SELECT f FROM OES_Product f WHERE f.company.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void deleteProduct(long product_id) {
        Query q = em.createQuery("DELETE FROM OES_Product f WHERE f.id=:id");
        q.setParameter("id", product_id);
        q.executeUpdate();
    }
    
    
    
    
    
    //************************Enquiry***************************
    @Override
    public void createEnquiry(Account seller, Account buyer, Set<OES_Product> products,  List<Integer> quantity, String createdate) {
        OES_Enquiry tmp= new OES_Enquiry();
        tmp.setBuyer(buyer);
        tmp.setSeller(seller);
        tmp.setProduct(products);
        tmp.setQuantity(quantity);
        tmp.setCreatedate(createdate);
        em.persist(tmp);
    }

    @Override
    public OES_Enquiry getEnquiry(long enquiry_id) {
        Query q = em.createQuery("SELECT f FROM OES_Enquiry f WHERE f.id=:id");
        q.setParameter("id", enquiry_id);
        return (OES_Enquiry)q.getSingleResult();
    }

    //Both seller/buyer can see enquiries
    @Override
    public List<OES_Enquiry> getAllEnquiry(String email) {
        Query q = em.createQuery("SELECT f FROM OES_Enquiry f WHERE f.seller.email=:email OR f.buyer.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails",email);
        return q.getResultList();
    }

    @Override
    public void deleteEnquiry(long enquiry_id) {
        Query q = em.createQuery("DELETE FROM OES_Enquiry f WHERE f.id=:id");
        q.setParameter("id", enquiry_id);
        q.executeUpdate();
    }
    
    
    
    
    
    
    //***************************Quotation*******************
    @Override
    public List<String> ATPcheck(OES_Enquiry enquiry){
        return null;
    }
    
    @Override
    public void createQuotation(OES_Enquiry enquiry, List<String> delivery_date, String createdate) {
        OES_Quotation tmp= new OES_Quotation();
        tmp.setDeliver_date(delivery_date);
        tmp.getCreatedate();
        tmp.setEnquiry(enquiry);
        em.persist(tmp);
    }

    @Override
    public OES_Quotation getQuotation(long quotation_id) {
        Query q = em.createQuery("SELECT f FROM OES_Quotation f WHERE f.id=:id");
        q.setParameter("id", quotation_id);
        return (OES_Quotation)q.getSingleResult();
    }

    @Override
    public List<OES_Quotation> getAllQuotation(String email) {
        Query q = em.createQuery("SELECT f FROM OES_Quotation f WHERE f.enquiry.buyer.email=:email OR f.enquiry.seller.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails",email);
        return q.getResultList();
    }

    @Override
    public void deleteQuotation(long quotation_id) {
        Query q = em.createQuery("DELETE FROM OES_Quotation f WHERE f.id=:id");
        q.setParameter("id", quotation_id);
        q.executeUpdate();
    }

    
    
    
    
    
    //*********************PurchaseOrder*********************
    @Override
    public void createPurchaseOrder(Account buyer, Account seller, double taxrate, List<Integer> quantity, Set<OES_Product> product, String createdate) {
        OES_PurchaseOrder tmp= new OES_PurchaseOrder();
        tmp.setBuyer(buyer);
        tmp.setSeller(seller);
        tmp.setProduct(product);
        tmp.setQuantity(quantity);
        tmp.setTaxrate(taxrate);
        tmp.setCreatedate(createdate);
        em.persist(tmp);
    }

    @Override
    public OES_PurchaseOrder getPurchaseOrder(long purchase_id) {
        Query q = em.createQuery("SELECT f FROM OES_PurchaseOrder f WHERE f.id=:id");
        q.setParameter("id", purchase_id);
        return (OES_PurchaseOrder)q.getSingleResult();
    }

    @Override
    public List<OES_PurchaseOrder> getAllPurchaseOrder(String email) {
        Query q = em.createQuery("SELECT f FROM OES_PurchaseOrder f WHERE f.buyer.email=:email OR f.seller.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails",email);
        return q.getResultList();
    }

    @Override
    public void deletePurchaseOrder(long purchase_id) {
        Query q = em.createQuery("DELETE FROM OES_Quotation f WHERE f.id=:id");
        q.setParameter("id", purchase_id);
        q.executeUpdate();
    }

    
    
    
    
    //****************************SalesOrder*********************
    @Override
    public void createSalesOrder(OES_PurchaseOrder purchaseorder, String createdate) {
        OES_SalesOrder tmp=new OES_SalesOrder();
        tmp.setPurchaseorder(purchaseorder);
        tmp.setCreatedate(createdate);
        em.persist(tmp);
    }

    @Override
    public OES_SalesOrder getSalesOrder(long salesOrder_id) {
        Query q = em.createQuery("SELECT f FROM OES_SalesOrder f WHERE f.id=:id");
        q.setParameter("id", salesOrder_id);
        return (OES_SalesOrder)q.getSingleResult();
    }

    @Override
    public List<OES_SalesOrder> getAllSalesOrder(String email) {
        Query q = em.createQuery("SELECT f FROM OES_SalesOrder f WHERE f.purchaseorder.buyer.email=:email OR f.purchaseorder.seller.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails",email);
        return q.getResultList();
    }

    @Override
    public void deleteSalesOrder(long sales_id) {
        Query q = em.createQuery("DELETE FROM OES_SalesOrder f WHERE f.id=:id");
        q.setParameter("id", sales_id);
        q.executeUpdate();
    }

    
    
    
    
    
    //***************************Payment************************
    @Override
    public void createPayment(String paymentdate, String paymenttype, String status,  OES_SalesOrder salesorder) {
        OES_Payment tmp=new OES_Payment();
        tmp.setPaymentDate(paymentdate);
        tmp.setPaymentType(paymenttype);
        tmp.setSalesorder(salesorder);
        tmp.setStatus(status);
        em.persist(tmp);
    }
    
    @Override
    public void updatePaymentStatus(long payment_id, String status){
        Query q = em.createQuery("SELECT a FROM OES_Payment a WHERE a.id=:id");
        q.setParameter("id",payment_id);
        OES_Payment tmp = (OES_Payment)q.getSingleResult(); 
        tmp.setStatus(status);
        em.merge(tmp);
    }

    @Override
    public OES_Payment getPayment(long payment_id) {
        Query q = em.createQuery("SELECT f FROM OES_Payment f WHERE f.id=:id");
        q.setParameter("id", payment_id);
        return (OES_Payment)q.getSingleResult();
    }

    @Override
    public List<OES_Payment> getAllPayment(String email) {
        Query q = em.createQuery("SELECT f FROM OES_SalesOrder f WHERE f.purchaseorder.seller.email=:email OR f.purchaseorder.buyer.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails",email);
        return q.getResultList();
    }

    
    
    
    
    
    //*****************************Invoice*********************************
    @Override
    public void createInvoice(String release_date, String notes, OES_Payment payment) {
        OES_Invoice tmp=new OES_Invoice();
        tmp.setNotes(notes);
        tmp.setRelease_date(release_date);
        tmp.setPayment(payment);
        em.persist(tmp);
    }

    @Override
    public OES_Invoice getInvoice(long invoice_id) {
        Query q = em.createQuery("SELECT f FROM OES_Invoice f WHERE f.id=:id");
        q.setParameter("id", invoice_id);
        return (OES_Invoice)q.getSingleResult();
    }

    @Override
    public List<OES_Invoice> getAllInvoice(String email) {
        Query q = em.createQuery("SELECT f FROM OES_Invoice f WHERE f.payment.salesorder.purchaseorder.seller.email=:email OR f.payment.salesorder.purchaseorder.buyer.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails",email);
        return q.getResultList();
    }
   
}
