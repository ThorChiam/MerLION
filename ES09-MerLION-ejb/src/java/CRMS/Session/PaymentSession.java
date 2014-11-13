/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import CRMS.Entity.Invoice;
import CRMS.Entity.Payment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sunny
 */
@Stateless
public class PaymentSession implements PaymentSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createPayment(String notes, Long contractId) {
        Payment tmp = new Payment();
        Query q = em.createQuery("SELECT c FROM Contract c WHERE c.id=:contractId");
        q.setParameter("contractId", contractId);
        Contract c = (Contract) q.getSingleResult();
        tmp.setContract(c);
        tmp.setNotes(notes);
        tmp.setPaymentStatus("pending");
        tmp.setDeletestatus("neither");
        c.setPayment(tmp);
        em.persist(tmp);
        em.merge(c);
    }

    @Override
    public void createInvoice(Long paymentId) {
        Invoice tmp = new Invoice();
        Query q = em.createQuery("SELECT p FROM Payment p WHERE p.id=:paymentId");
        q.setParameter("paymentId", paymentId);
        Payment c = (Payment) q.getSingleResult();
        tmp.setPayment(c);
        tmp.setGenerate_date(Calendar.getInstance().getTime().toString());
        c.setInvoice(tmp);
        em.persist(tmp);
        em.merge(c);
    }

    @Override
    public Payment getPayment(Long payment_id) {
        Query q = em.createQuery("SELECT f FROM Payment f WHERE f.id=:id");
        q.setParameter("id", payment_id);
        return (Payment) q.getSingleResult();
    }

    @Override
    public Invoice getInvoice(Long invoice_id) {
        Query q = em.createQuery("SELECT i FROM Invoice i WHERE i.id=:id");
        q.setParameter("id", invoice_id);
        return (Invoice) q.getSingleResult();
    }

    @Override
    public List<Payment> getAllPayments(String email) {
        Query q = em.createQuery("SELECT p FROM Payment p WHERE (p.contract.requestor.email=:email) OR (p.contract.provider.email=:emails)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        List<Payment> results = q.getResultList();
        List<Payment> payments = new ArrayList<>();
        for (Payment c : results) {
            if (c.getPaymentStatus().equals("pending") || c.getPaymentStatus().equals("completed")) {
                payments.add(c);
            } else {
                if (c.getContract().getProvider().getEmail().equals(email)) {
                    if (c.getDeletestatus().equals("requestor")) {
                        payments.add(c);
                    }
                } else {
                    if (c.getDeletestatus().equals("provider")) {
                        payments.add(c);
                    }
                }
            }
        }
        return payments;
    }

    @Override
    public List<Invoice> getAllInvoices(String email) {
        Query q = em.createQuery("SELECT i FROM Invoice i WHERE (i.payment.contract.requestor.email=:email) OR (i.payment.contract.provider.email=:emails)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        List<Invoice> results = q.getResultList();
        return results;
    }

    @Override
    public void abort(String email, Long paymentId) {
        Query q = em.createQuery("SELECT p FROM Payment p WHERE p.id=:paymentId");
        q.setParameter("paymentId", paymentId);
        Payment c = (Payment) q.getSingleResult();
        c.setPaymentStatus("aborted");
        em.merge(c);
    }

    @Override
    public void complete(String email, Long paymentId) {
        Query q = em.createQuery("SELECT p FROM Payment p WHERE p.id=:paymentId");
        q.setParameter("paymentId", paymentId);
        Payment c = (Payment) q.getSingleResult();
        c.setPaymentStatus("completed");
        String paidDate = Calendar.getInstance().getTime().toString();
        c.setTransactionDate(paidDate);
        em.merge(c);
    }

    @Override
    public void delete(String email, Long paymentId) {
        Query q = em.createQuery("SELECT p FROM Payment p WHERE p.id=:paymentId");
        q.setParameter("paymentId", paymentId);
        Payment c = (Payment) q.getSingleResult();
        String deleteStatus = c.getDeletestatus();
        if (!c.getPaymentStatus().equals("completed")) {
            c.setPaymentStatus("aborted");
        }
        if (deleteStatus.equals("provider") || deleteStatus.equals("requestor")) {
            c.setDeletestatus("both");
        } else {
            if (c.getContract().getProvider().getEmail().equals(email)) {
                c.setDeletestatus("provider");
            } else {
                c.setDeletestatus("requestor");
            }
        }
    }
}
