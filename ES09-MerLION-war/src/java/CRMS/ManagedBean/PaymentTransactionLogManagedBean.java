/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import CRMS.Session.PaymentTransactionLogSessionBeanLocal;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "paymentTransactionLogManagedBean")
@SessionScoped
public class PaymentTransactionLogManagedBean implements Serializable {

    /**
     * Creates a new instance of PaymentTrasactionLogManagedBean
     */
    @EJB
    private PaymentTransactionLogSessionBeanLocal ptlsbl;
    public PaymentTransactionLogManagedBean() {
    }
    public List getPaymentTransactionLog(String email){
        return ptlsbl.getPaymentTransactionLog(email);
    }
}
