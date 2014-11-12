/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRP.Session;

import CI.Entity.Account;
import MRP.Entity.Demand;
import com.sun.mail.imap.protocol.Item;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion_exception.DemandException;


/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class DemandSessionBean {

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
    
    public List<Demand> getItemDemand(String userId, Long itemId)throws DemandException{
        Item item = getItem(itemId);
        List result;
        Account account=getAccount(userId);
        Query query = entityManager.createQuery("SELECT d FROM Demand d WHERE d.item = :item and d.item.account=:account");
        query.setParameter("item", item);
        query.setParameter("account",account);
      
        result=query.getResultList();
        if(!result.isEmpty()){
            return result;}
        else{
            throw new DemandException("Unable to retrieve any demand");
        }       
    }
    
    public int[] predictItemDemand(String userId, Long itemId) throws DemandException{
        List<Demand> demand = getItemDemand(userId, itemId);
        int[] past = new int[100];
         int[] prediction = new int[6];
        int length=demand.size();
        if(length<4){
            return null;
        }
        for(int i=0;i<length;i++){
            past[i] =demand.get(i).getQuantity();
        }
        
        int[]pastfour = new int[11];
        int j=0;
        for(int i=length-4;i<length;i++){
            pastfour[j]= past[i];
            j++;
        }
        for(int i=0;i<6;i++){
            pastfour[4+i]=(pastfour[i]+pastfour[i+1]+pastfour[i+2]+pastfour[i+3])/4;
        }
        for(int i=0;i<6;i++){
            prediction[i]=pastfour[4+i];
        }
        return prediction;
    }
    
}
