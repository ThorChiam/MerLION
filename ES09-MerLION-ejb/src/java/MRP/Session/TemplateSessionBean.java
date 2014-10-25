/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRP.Session;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MRP.Entity.Item;
import MRP.Entity.Template;


@Stateless
@LocalBean
public class TemplateSessionBean {

    @PersistenceContext
    private EntityManager entityManager;
    

    public Item getItem(Long itemId) {
        Item item = entityManager.find(Item.class, itemId);
        return item;
    }

    public List<Template> getItemPlan(Long itemId) {
        Item item = getItem(itemId);
        Query query = entityManager.createQuery("SELECT e FROM Template e WHERE e.item = :item");
        query.setParameter("item", item);
        return query.getResultList();
    }

    private Boolean checkTemplateConflict(Long itemId) {
        
        List resultList = getItemPlan(itemId);
        if (resultList.size()<5) {
            return false;
        } else {
            return true;
        }
    }
    private void DeleteTemplate(Long itemId){
        Item item= getItem(itemId);
         Query query = entityManager.createQuery("DELETE FROM Template e WHERE e.item = :item");
         query.setParameter("item", item);
         query.executeUpdate();
    }

    public Long addNewTemplate(Long itemId, String name,
            int first, int second, int third, int fourth, int fifth,int sixth,int seventh){
        if (checkTemplateConflict(itemId)) {
            DeleteTemplate(itemId);
        }         
            Item item = getItem(itemId);
            Template template = new Template();   
            template.setName(name);
            template.setFirstweek(first);
            template.setSecondweek(second);
            template.setItem(item);
            template.setThirdweek(third);
            template.setFourthweek(fourth);
            template.setFifthweek(fifth);
            template.setSixthweek(sixth);
            template.setSeventhweek(seventh);
            
            entityManager.persist(template);
            entityManager.flush();
            item.getTemplates().add(template);
            entityManager.merge(item);
            return template.getTemplateId();
        } 
            
        
    }
