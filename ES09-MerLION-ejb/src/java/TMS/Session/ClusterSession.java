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
import TMS.Entity.ClusterRule;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class ClusterSession {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }    
    public List<ClusterRule> getAllRule(String userId){
        Account account = getAccount(userId);
        List result;
        Query query = entityManager.createQuery("SELECT o FROM ClusterRule o WHERE o.account = :account");      
        query.setParameter("account", account);
        result = query.getResultList();
        return result;
    }
    public void updateRule(ClusterRule rule){
        Query query = entityManager.createQuery("UPDATE ClusterRule c SET c.type=?2,c.name=?3 WHERE c.id=?1");
      
       query.setParameter(1, rule.getId());
       query.setParameter(2, rule.getType());
       query.setParameter(3, rule.getName());
      query.executeUpdate();
    }


    public Long addRule(ClusterRule rule,String userId) {
        
        Account account = getAccount(userId);                              
            rule.setAccount(account);
            entityManager.persist(rule);
            entityManager.flush();
            return rule.getId();               
    }

    public void deleteRule(Long Id) {
        Query query = entityManager.createQuery("Delete From ClusterRule r where r.id=:Id");
        query.setParameter("Id", Id);

        query.executeUpdate();
    }

}
