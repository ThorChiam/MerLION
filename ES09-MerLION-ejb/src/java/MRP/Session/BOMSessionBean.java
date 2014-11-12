/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRP.Session;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import MRP.Entity.BOM;
import MRP.Entity.Item;

/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class BOMSessionBean {
    @PersistenceContext
    private EntityManager entityManager;
    
    public Item getItem(Long itemId) {
        Item item = entityManager.find(Item.class, itemId);
        return item;
    }
    public Account getAccount(String userId){
        Account account=entityManager.find(Account.class,userId);
        return account;
    }
    public List<Item> getAllItems(String userId){
        Query query = entityManager.createQuery("SELECT b FROM Item b where b.account=:account");
        Account account=getAccount(userId);
        query.setParameter("account",account);
       
        return  query.getResultList();
    }
    
    public List<BOM> getBOMs( Long itemId){
       
        Query query = entityManager.createQuery("SELECT b FROM BOM b where b.item=:item");       
        Item item=getItem(itemId);
        
        query.setParameter("item",item);      
        return  query.getResultList();
    }
    public List<BOM> IsComponent(Long compId){
        Query query = entityManager.createQuery("SELECT b FROM BOM b where b.component=:comp");
        query.setParameter("comp",compId);
        return query.getResultList();
    }
      public void deleteBOM(Long BOMId) {
               
        Query query = entityManager.createQuery("DELETE From BOM e where e.id=:bomid" );
        query.setParameter("bomid", BOMId);    
        query.executeUpdate();
    }
    public int updateBOM(Long BOMid, double quantity){
        
         
      Query query = entityManager.createQuery("UPDATE BOM c SET c.quantity=?2 WHERE c.id=?1");
      query.setParameter(2, quantity);
      
      
      query.executeUpdate();
      return 1;
    }
    
    public Long addItemBOM( Long itemId, Long CompId, double quantity){
        Item item = getItem(itemId);
                  
        BOM bom=new BOM();
        bom.setComponent(CompId);
        bom.setQuantity(quantity);
        bom.setItem(item);
        
        entityManager.persist(bom);
        entityManager.flush();
        item.getBom().add(bom);
        entityManager.merge(item);
        return bom.getId();
    }
}
