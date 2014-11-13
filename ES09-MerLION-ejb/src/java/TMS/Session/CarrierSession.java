/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.Session;


import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import TMS.Entity.Carrier;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class CarrierSession {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }    
    public void updateCarrier(Carrier carrier){
        Query query = entityManager.createQuery("UPDATE Carrier c SET c.capacity=?2,c.type=?3 WHERE c.id=?1");
      
       query.setParameter(1, carrier.getId());
       query.setParameter(2, carrier.getCapacity());
       query.setParameter(3, carrier.getType());
             
      query.executeUpdate();
    }

    public List<Carrier> getMyCarrier(String userId) {
       
        Account account = getAccount(userId);
        List result;
        Query query = entityManager.createQuery("SELECT o FROM Carrier o WHERE o.account = :account");      
        query.setParameter("account", account);
        result = query.getResultList();
        return result;
    }
    public List<Carrier> getAvailCarrier(String userId,String type) {
       
        Account account = getAccount(userId);
        List result;
        List<Long> result1;
        Query query = entityManager.createQuery("SELECT o FROM Carrier o WHERE o.account = :account and o.type=:type and o.itemtype=:status");      
        
        query.setParameter("account", account);
         query.setParameter("type", type);
         query.setParameter("status","Available");
        
        result = query.getResultList();
        
        return result;
    }

    public Long addCarrier(Carrier carrier,String userId) {
        
        Account account = getAccount(userId);                              
            carrier.setAccount(account);
            entityManager.persist(carrier);
            entityManager.flush();
            return carrier.getId();               
    }

    public void deleteCarrier(Long Id) {
        Query query = entityManager.createQuery("Delete From Carrier r where r.id=:Id");
        query.setParameter("Id", Id);

        query.executeUpdate();
    }

}
