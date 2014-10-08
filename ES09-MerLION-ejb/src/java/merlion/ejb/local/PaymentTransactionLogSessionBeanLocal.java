/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ThorChiam
 */
@Local
public interface PaymentTransactionLogSessionBeanLocal {
    public List getPaymentTransactionLog(String email);
}
