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
import merlion.entity.MRP.BOM;
import merlion.entity.MRP.Item;

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
    
    public List<BOM> getBOMs(){
       
        Query query = entityManager.createQuery("SELECT b FROM BOM b");
        
       
        return  query.getResultList();
    }
    public void deleteBOM(Long itemId) {
        Item item = getItem(itemId);
        Query query = entityManager.createQuery("Delete From BOM e where e.item= :tmp");
        query.setParameter("tmp", item);
        query.executeUpdate();
    }
    public void updateBOM(Long itemId,Long comp1,Long comp2,Long comp3,int pn1,int pn2,int pn3){
        Item item = getItem(itemId);
      Query query = entityManager.createQuery("UPDATE BOM c SET c.comp1=?2,c.comp2=?3,c.comp3=?4,c.partnumber1=?5,c.partnumber2=?6, c.partnumber3=?7 WHERE c.item=?1");
      query.setParameter(2, comp1);
      query.setParameter(3, comp2);
      query.setParameter(4, comp3);
      query.setParameter(1, item);
      query.setParameter(5, pn1);
      query.setParameter(6, pn2);
      query.setParameter(7, pn3);
      
      query.executeUpdate();
    }
    
    public Long addItemBOM(Long itemId, Long CompId1, int partnumber1, Long CompId2, int partnumber2,
            Long CompId3, int partnumber3){
        Item item = getItem(itemId);
        deleteBOM(itemId);
        BOM bom=new BOM();
        bom.setPartnumber1(partnumber1);
        bom.setPartnumber3(partnumber3);
        bom.setPartnumber2(partnumber2);
        bom.setComp1(CompId1);
        bom.setComp2(CompId2);
        bom.setComp3(CompId3);
        bom.setItem(item);
        
        entityManager.persist(bom);
        entityManager.flush();
        item.setBom(bom);
        entityManager.merge(item);
        return bom.getId();
    }
}
