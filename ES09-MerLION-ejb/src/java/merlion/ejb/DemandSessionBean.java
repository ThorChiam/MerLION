/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.MRP.Demand;
import merlion.entity.MRP.Item;
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
    
    public List<Demand> getItemDemand(Long itemId)throws DemandException{
        Item item = getItem(itemId);
        List result;
        Query query = entityManager.createQuery("SELECT d FROM Demand d WHERE d.item = :item");
        query.setParameter("item", item);
        result=query.getResultList();
        if(!result.isEmpty()){
            return result;}
        else{
            throw new DemandException("Unable to retrieve any demand");
        }       
    }
    
    public int[] predictItemDemand(Long itemId) throws DemandException{
        List<Demand> demand = getItemDemand(itemId);
        int[] past = new int[100];
         int[] prediction = new int[6];
        int length=demand.size();
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
