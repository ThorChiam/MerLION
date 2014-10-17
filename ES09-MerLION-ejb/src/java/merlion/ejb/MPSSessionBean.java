/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.MRP.Item;
import merlion.entity.MRP.MPS;

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
    
    public MPS getItemMPS(Long itemId) {
        Item item = getItem(itemId);
        Query query = entityManager.createQuery("SELECT m FROM MPS m WHERE m.item = :item");
        query.setParameter("item", item);
        return (MPS)query.getSingleResult();
    }
    public Long addNewMPS(Long itemId, int first, int second, int third, int fourth, int fifth, int sixth ){
        Item item=getItem(itemId);
        MPS mps= new MPS();
        mps.setFirstmonth(first);
        mps.setSecondmonth(second);
        mps.setThirdmonth(third);
        mps.setFourthmonth(fourth);
        mps.setFifthmonth(fifth);
        mps.setSixthmonth(sixth);
        mps.setItem(item);  
            
        entityManager.persist(mps);
        entityManager.flush();
        item.setMps(mps);
        entityManager.merge(item);
        return mps.getId();
    }
}
