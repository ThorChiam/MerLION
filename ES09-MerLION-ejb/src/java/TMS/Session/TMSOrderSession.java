/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.Session;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import TMS.Entity.TMSOrders;

/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class TMSOrderSession {

    @PersistenceContext
    private EntityManager entityManager;

    

    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }
    public List<TMSOrders> getAllOrders(String userId){
        Account account = getAccount(userId);
         Query query = entityManager.createQuery("SELECT o FROM TMSOrders o WHERE o.provider=:account");        
        query.setParameter("account", account);
        return query.getResultList();
    }
    public List<TMSOrders> getAvailOrders(String userId,String type){
        Account account = getAccount(userId);
         Query query = entityManager.createQuery("SELECT o FROM TMSOrders o WHERE o.provider=:account and o.status=:status and o.carriertype=:type " );
         query.setParameter("status","New");
        query.setParameter("account", account);
        
        query.setParameter("type",type);
        return query.getResultList();
    }
    public List<TMSOrders> track(String userId){
        Account account = getAccount(userId);
         Query query = entityManager.createQuery("SELECT o FROM TMSOrders o WHERE o.requestor=:account");        
        query.setParameter("account", account);
        return query.getResultList();
    }
    public void updateOrder(TMSOrders order){
        Query query = entityManager.createQuery("UPDATE TMSOrders c SET c.amount=?2,c.status=?3,c.origination=?4,c.destination=?5 WHERE c.id=?1");
      
       query.setParameter(1, order.getId());
       query.setParameter(2, order.getAmount());
      
      query.setParameter(4, order.getOrigination());      
      query.setParameter(5, order.getDestination());       
      query.setParameter(3,order.getStatus());
      query.executeUpdate();
    }
    

    

    public Long addOrder(String provider,String requestor, String itemname, double amount, int time ,String origination, String destination,String type) {
        
        Account account1 = getAccount(provider);
        Account account2= getAccount(requestor);
        
            TMSOrders order = new TMSOrders();
            order.setAmount(amount);
            java.util.Date date = new java.util.Date();
        
            Timestamp orderdate = new Timestamp(date.getTime());
            order.setOrderdate(orderdate);
            order.setItemName(itemname);
            order.setOrigination(origination);
            order.setDestination(destination);       
            order.setCarriertype(type);
            order.setDuration(time);
            order.setProvider(account1);
            order.setRequestor(account2);
            order.setStatus("New");

            entityManager.persist(order);
            entityManager.flush();

            return order.getId();
        
        
    }

    public void deleteOrder(Long OrderId) {
        Query query = entityManager.createQuery("Delete From TMSOrders r where r.id=:Id");
        query.setParameter("Id", OrderId);

        query.executeUpdate();
    }

}
