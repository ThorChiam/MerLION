 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRP.Session;

import CI.Entity.Account;
import MRP.Entity.Item;
import MRP.Entity.MRPOrders;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class OrderSessionBean {

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
    
    public List<MRPOrders> getItemOrder(String userId, Long itemId){
        Item item = getItem(itemId);
        List result;
        Query query = entityManager.createQuery("SELECT o FROM MRPOrders o WHERE o.item = :item");
        query.setParameter("item", item);
        result=query.getResultList();
        return result;
    }
    public Long addItemOder(String userId, Long itemId,  double amount, Timestamp orderdate){
        Item item = getItem(itemId);
          Account account=getAccount(userId); 
         if(account.getItems().contains(item)){ 
        MRPOrders order=new MRPOrders();
        order.setAmount(amount);
        order.setOrderdate(orderdate);              
        order.setItem(item);
        
        entityManager.persist(order);
        entityManager.flush();
        item.getOrder().add(order);
        entityManager.merge(item);
        return order.getId();}else{
             return -1L;
         }
    }
  
}
