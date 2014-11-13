/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Invoice;
import CRMS.Entity.Payment;
import CRMS.Session.PaymentSessionLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "paymentmb")
@ViewScoped
public class PaymentManagedBean implements Serializable {

    @EJB
    private PaymentSessionLocal psl;

    private String email;
    private List<Payment> allPayments;
    private List<Invoice> allInvoices;
    private List<Payment> paymentDetail;
    private Payment payment;
    private Invoice invoice;
    private Long paymentId;
    private Long invoiceId;

    public PaymentManagedBean() {
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        if (email != null) {
            allPayments = psl.getAllPayments(email);
            allInvoices = psl.getAllInvoices(email);
        }
        paymentId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("paymentId");
        if (paymentId != null) {
            this.setPayment(psl.getPayment(paymentId));
        }
        invoiceId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("invoiceId");
        if (invoiceId != null) {
            this.setInvoice(psl.getInvoice(invoiceId));
        }
    }

    public Payment viewPayment(long id) {
        return psl.getPayment(id);
    }

    //******************Getter and Setter***************
    public List<Payment> getAllPayments() {
        return allPayments;
    }

    public void setAllPayments(List<Payment> allPayments) {
        this.allPayments = allPayments;
    }

    public List<Invoice> getAllInvoices() {
        return allInvoices;
    }

    public void setAllInvoices(List<Invoice> allInvoices) {
        this.allInvoices = allInvoices;
    }

    public List<Payment> getPaymentDetail() {
        paymentDetail = new ArrayList<>();
        List<Payment> tmp = psl.getAllPayments(email);
        for (Payment s : tmp) {
//            System.out.println("s.getId:" + s.getId() + ";paymentId:" + paymentId);
            if (paymentId != null) {
                if (s.getId().compareTo(paymentId) == 0) {
                    paymentDetail.add(s);
                }
            }
        }
        return paymentDetail;
    }

    public void setPaymentDetail(List<Payment> paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public Payment getPayment() {
        return payment;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void initPaymentId(Long pId) {
        paymentId = pId;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("paymentId", pId);
    }

    public void initInvoiceId(Long iId) {
        invoiceId = iId;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("invoiceId", iId);
        this.init();
    }

    public void abort(Long paymentId) {
        psl.abort(email, paymentId);
    }

    public void delete(Long paymentId) {
        psl.delete(email, paymentId);
    }

    public void complete(Long paymentId) {
        psl.complete(email, paymentId);
    }

    public void createInvoice(ActionEvent event) {
        paymentId = paymentDetail.get(0).getId();
        psl.createInvoice(paymentId);
        this.addMessage("Invoice Created & Sent:", "Successfully!");
    }

    public void addMessage(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, content));
    }
}
