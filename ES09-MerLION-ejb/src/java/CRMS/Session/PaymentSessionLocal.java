/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import CRMS.Entity.Invoice;
import CRMS.Entity.Payment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sunny
 */
@Local
public interface PaymentSessionLocal {

    public void createPayment(String notes, Long contractId);

    public Payment getPayment(Long payment_id);

    public Invoice getInvoice(Long invoice_id);

    public List<Payment> getAllPayments(String email);

    public List<Invoice> getAllInvoices(String email);

    public void abort(String email, Long paymentId);

    public void complete(String email, Long paymentId);

    public void delete(String email, Long paymentId);

    public void createInvoice(Long paymentId);
}
