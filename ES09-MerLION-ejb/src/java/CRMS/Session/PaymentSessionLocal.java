/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import CRMS.Entity.Payment;
import java.util.List;

/**
 *
 * @author sunny
 */
public interface PaymentSessionLocal {
    public void createPayment(String amount, String paymentdate, String paymentstatus, String notes, Contract contract);
    public Payment getPayment(long payment_id);
    public List<Payment> getAllPayment(String email);
    public void deletePayment(long payment_id);
    public Contract getTheContract(long id);
  
}
