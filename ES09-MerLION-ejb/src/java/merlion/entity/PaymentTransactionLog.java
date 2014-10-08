/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author USER
 */
@Entity(name = "PaymentTransactionLog")
public class PaymentTransactionLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long orderNumber;
    private long transactionId;
    private String currency;
    private long amount;
    private long transactionDate;
    private String paymentStatus;
    private String notes;
 
    //Added by QT
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Account Account;

    //private CompanyEntity company = new CompanyEntity();

    
    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PaymentTransactionLog() {
        setTransactionId(System.nanoTime());
    }

    //Added by QT
    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }




    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
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

//    @ManyToOne
//    public CompanyEntity getCompany() {
//        return company;
//    }
//
//    public void setCompany(CompanyEntity company) {
//        this.company = company;
//    }
    public void create(long orderNumber,String currency, long amount, long transactionDate,String paymentStatus,String notes) {
        this.setOrderNumber(orderNumber);
        this.setCurrency(currency);
        this.setAmount(amount);
        this.setTransactionDate(transactionDate);
        this.setPaymentStatus(paymentStatus);
        this.setNotes(notes);
    }

    @Override
    public String toString() {
        return "entity.PaymentTransactionLogEntity[ id=" + orderNumber + " ]";
    }

}
