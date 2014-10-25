/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import java.util.List;
import CRMS.Session.PaymentTransactionLogSessionLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CRMS.Entity.Payment;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class PaymentTransactionLogSession implements PaymentTransactionLogSessionLocal {

    @PersistenceContext
    private EntityManager em;
    
    private Payment payment;
    private List<Payment> payments;

    @Override
    public List getPaymentTransactionLog(String email) {

        Query q = em.createQuery("SELECT e FROM Payment e WHERE e.company.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();

    }
}
