/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRP.Session;

import CI.Entity.Account;
import MRP.Entity.Item;
import MRP.Entity.Template;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@LocalBean
public class TemplateSessionBean {

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

    public List<Template> getItemPlan(String userId,Long itemId) {
        Item item = getItem(itemId);
        Account account=getAccount(userId);
        Query query = entityManager.createQuery("SELECT e FROM Template e WHERE e.item = :item and e.item.account=:account");
        query.setParameter("item", item);
        query.setParameter("account",account);
        return query.getResultList();
    }
    
    private Boolean checkTemplateConflict(String userId,Long itemId) {
        
        List resultList = getItemPlan(userId,itemId);
        if (resultList.size()<6) {
            return false;
        } else {
            return true;
        }
    }
    private void DeleteTemplate(String userId,Long itemId){
        Item item= getItem(itemId);
        Account account=getAccount(userId);
         Query query = entityManager.createQuery("DELETE FROM Template e WHERE e.item = :item and e.item.account=:account");
         query.setParameter("item", item);
         query.setParameter("account",account);
         query.executeUpdate();
    }

    public Long addNewTemplate(String userId,Long itemId, String name,
            int first, int second, int third, int fourth, int fifth,int sixth,int seventh){
        if (checkTemplateConflict(userId,itemId)) {
            DeleteTemplate(userId,itemId);
        }    
        
            Item item = getItem(itemId);
             Account account=getAccount(userId); 
         if(account.getItems().contains(item)){
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
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());
            template.setStartdate(date);
            
            entityManager.persist(template);
            entityManager.flush();
            item.getTemplates().add(template);
            entityManager.merge(item);
            return template.getTemplateId();}else{
             return -1L;
         }
        } 
            
        
    }
