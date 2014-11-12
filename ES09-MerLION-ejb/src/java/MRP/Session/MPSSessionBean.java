/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRP.Session;

import CI.Entity.Account;
import MRP.Entity.Item;
import MRP.Entity.MPS;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class MPSSessionBean {

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
    
    public MPS getItemMPS(String userId, Long itemId) {
        Item item = getItem(itemId);
        Account account=getAccount(userId);
        Query query = entityManager.createQuery("SELECT m FROM MPS m WHERE m.item = :item and m.item.account=:account");
        query.setParameter("item", item);
        query.setParameter("account", account);
        return (MPS)query.getSingleResult();
    }
    public Long addNewMPS(String userId,Long itemId, int first, int second, int third, int fourth, int fifth, int sixth ){
        Item item=getItem(itemId);
         Account account=getAccount(userId); 
         if(account.getItems().contains(item)){ 
        MPS mps= new MPS();
        mps.setFirstmonth(first);
        mps.setSecondmonth(second);
        mps.setThirdmonth(third);
        mps.setFourthmonth(fourth);
        mps.setFifthmonth(fifth);
        mps.setSixthmonth(sixth);
        
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();   
          cal.setTime(stamp);  
      
        int sm = cal.get(Calendar.MONTH);  
        int sy = cal.get(Calendar.YEAR);  
        mps.setStartmonth(sm+1);
        mps.setStartyear(sy);
        mps.setItem(item);  
            
        entityManager.persist(mps);
        entityManager.flush();
        item.setMps(mps);
        entityManager.merge(item);
        return mps.getId();}else{
             return -1L;
         }
    }
}
