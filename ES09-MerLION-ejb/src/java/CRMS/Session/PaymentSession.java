/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import CRMS.Entity.Payment;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sunny
 */
@Stateless
@LocalBean
public class PaymentSession implements PaymentSessionLocal{
    @PersistenceContext
    private EntityManager em;

    
    @Override
    public void createPayment(String amount, String paymentdate, String paymentstatus, String notes, Contract contract){
        Payment tmp = new Payment();
        tmp.setAmount(amount);
        tmp.setContract(contract);
        tmp.setNotes(notes);
        tmp.setTransactionDate(paymentdate);
        tmp.setPaymentStatus(paymentstatus);
    }
    
    @Override
    public Payment getPayment(long payment_id){
        Query q = em.createQuery("SELECT f FROM Payment f WHERE f.id=:id");
        q.setParameter("id", payment_id);
        return (Payment) q.getSingleResult();
    }
    
    @Override
    public List<Payment> getAllPayment(String email){
        Query q = em.createQuery("SELECT a FROM Payment a WHERE a.Contract.serviceorder.service_provider.email=:email OR a.Contract.serviceorder.service_requester.email");
        q.setParameter("email",email);
        return q.getResultList();
    }
    
    @Override
    public void deletePayment(long payment_id){
        Query q = em.createQuery("DELETE FROM Payment f WHERE f.id=:id");
        q.setParameter("id", payment_id);
        q.executeUpdate();
    }
    
    @Override
    public Contract getTheContract(long id){
        Query q = em.createQuery("SELECT f FROM Contract f WHERE f.id=:id");
        q.setParameter("id", id);
        return (Contract) q.getSingleResult();
    }
  
}
