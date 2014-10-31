/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Contract;
import CRMS.Entity.Payment;
import CRMS.Session.PaymentSessionLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "omb")
@SessionScoped
@ViewScoped
public class PaymentManagedBean implements Serializable {
    @EJB
    private PaymentSessionLocal psbl;
    
    private String amount;
    private String transactionDate;
    private String paymentStatus;
    private String notes;
    private Contract Contract;
    
    private String statusMessage;
   
    public PaymentManagedBean() {
    }
    
    public void createPayment(){
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String create_date = tmp.toString();
        psbl.createPayment(amount, create_date, paymentStatus, notes, Contract);
        
        statusMessage = "A new Payment is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
    }
    
    public Payment viewPayment(long id){
        return psbl.getPayment(id);
    }
    
    public List<Payment> viewAllPayment(String email){
        return psbl.getAllPayment(email);
    }   
    
    public void deletePayment(long id){
        psbl.deletePayment(id);
    }
    
    public Contract getTheContract(long id){
        return psbl.getTheContract(id);
    }
   
    
    
    
    
    
    //******************Getter and Setter***************
    public PaymentSessionLocal getPsbl() {
        return psbl;
    }

    public void setPsbl(PaymentSessionLocal psbl) {
        this.psbl = psbl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
