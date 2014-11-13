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
import TMS.Entity.Driver;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class DriverSession {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }    
    public void updateDriver(Driver driver){
        Query query = entityManager.createQuery("UPDATE Driver c SET c.carriertype=?2,c.email=?3,c.name=?4 WHERE c.id=?1");
      
       query.setParameter(1, driver.getId());
       query.setParameter(2, driver.getCarriertype());
       query.setParameter(3, driver.getEmail());
       query.setParameter(4, driver.getName());
      
             
      query.executeUpdate();
    }

    public List<Driver> getMyDrivers(String userId) {
       
        Account account = getAccount(userId);
        List result;
        Query query = entityManager.createQuery("SELECT o FROM Driver o WHERE o.account = :account");      
        query.setParameter("account", account);
        result = query.getResultList();
        return result;
    }
     public List<Driver> getAvailDrivers(String userId,String type) {
       
        Account account = getAccount(userId);
        List result;
        Query query = entityManager.createQuery("SELECT o FROM Driver o WHERE o.account = :account and o.carriertype=:type and o.email=:status");   
        
        query.setParameter("account", account);
        query.setParameter("type", type);
         query.setParameter("status", "Available");
        result = query.getResultList();
        return result;
    }
    

    public Long addDriver(Driver driver,String userId) {
        
        Account account = getAccount(userId);                              
            driver.setAccount(account);
            entityManager.persist(driver);
            entityManager.flush();
            return driver.getId();               
    }

    public void deleteDriver(Long Id) {
        Query query = entityManager.createQuery("Delete From Driver r where r.id=:Id");
        query.setParameter("Id", Id);

        query.executeUpdate();
    }

}
