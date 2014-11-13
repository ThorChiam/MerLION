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
import TMS.Entity.TMSCatalog;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class CatalogSession {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }    
    public List<TMSCatalog> getMyCatalog(String userId){
        Account account = getAccount(userId);
        List result;
        Query query = entityManager.createQuery("SELECT o FROM TMSCatalog o WHERE o.account = :account");      
        query.setParameter("account", account);
        result = query.getResultList();
        return result;
    }
    public List<TMSCatalog> getAllCatalog(){
        Query query = entityManager.createQuery("SELECT o FROM TMSCatalog o");
        return query.getResultList();
    }
    public void updateCatalog(TMSCatalog cat){
        Query query = entityManager.createQuery("UPDATE TMSCatalog c SET c.CarrierType=?2,c.ori=?3,c.des=?4,c.price=?5, c.timetaken=?6,c.capacity=?7 where c.id=?1");
      
       query.setParameter(1, cat.getId());
       query.setParameter(2, cat.getCarrierType());
       query.setParameter(3, cat.getOri());
       query.setParameter(4, cat.getDes());
       query.setParameter(5, cat.getPrice());
       query.setParameter(6, cat.getTimetaken());
       query.setParameter(7,cat.getCapacity());
       
      query.executeUpdate();
    }


    public Long addCatalog(TMSCatalog rule,String userId) {
        
        Account account = getAccount(userId);                              
            rule.setAccount(account);
            entityManager.persist(rule);
            entityManager.flush();
            return rule.getId();               
    }

    public void deleteCatalog(Long Id) {
        Query query = entityManager.createQuery("Delete From TMSCatalog r where r.id=:Id");
        query.setParameter("Id", Id);

        query.executeUpdate();
    }

}
