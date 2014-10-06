/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;
import merlion.entity.Customer;


/**
 *
 * @author Tomato
 */
@Stateless
public class CustomerSession implements CustomerSessionLocal {
    @PersistenceContext
    private EntityManager em;
    
    @Override
     public List<Customer> getAllcustomer(){
        Query q=em.createQuery("SELECT c FROM Customer c");
        return q.getResultList();
    }
    @Override
   public Customer getCustomer(Long id){
       Query q=em.createQuery("SELECT c FROM Customer c WHERE c.id=:id");
       q.setParameter("id",id);
       Customer c=null;
       try{
           c=(Customer)q.getSingleResult();
       }catch(NoResultException ex){
           ex.printStackTrace();
       }
       return c;  
    }
   
   /*
    private CustomerOrder getCustomerOrder(Long id){
       Query q=em.createQuery("SELECT c FROM CustomerOrder c WHERE c.id=:id");
       q.setParameter("id",id);
       CustomerOrder c=null;
       try{
           c=(CustomerOrder)q.getSingleResult();
       }catch(NoResultException ex){
           ex.printStackTrace();
       }
       return c;  
        
    }
   */
    @Override
    public String addCustomer(String name, String email, String phone, String address, String cityRegion){
       //CustomerOrder co=getCustomerOrder(customerOrderId);
        Customer c=new Customer();
        c.create(name, email, phone, address, cityRegion);
        em.persist(c);
        em.flush();
        return c.getName();
    }
}

