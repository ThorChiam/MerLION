/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Customer_Enquiry;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Stateless
public class EnquireSession implements EnquireSessionLocal {
@PersistenceContext
    private EntityManager em;
    private Customer_Enquiry customer_Enquiry;
    
    @Override
    public void createEnquiry(String email, String qun){
        Customer_Enquiry c=new Customer_Enquiry();
        c.setContact_email(email);
        c.setQuestion(qun);
        
        em.persist(c);
    }
}
