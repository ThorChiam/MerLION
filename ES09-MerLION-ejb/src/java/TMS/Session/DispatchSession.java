/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import TMS.Entity.Carrier;
import TMS.Entity.DispatchOrder;
import TMS.Entity.Driver;
import TMS.Entity.TMSOrders;

/**
 *
 * @author Zeng Xunhao
 */
@Stateless
@LocalBean
public class DispatchSession {

    @PersistenceContext
    private EntityManager entityManager;

    public Carrier getCarrier(Long Id) {
        return entityManager.find(Carrier.class, Id);
    }

    public Driver getDriver(Long Id) {
        return entityManager.find(Driver.class, Id);
    }

    public TMSOrders getOrder(Long Id) {
        return entityManager.find(TMSOrders.class, Id);
    }

    public Account getAccount(String userId) {
        Account account = entityManager.find(Account.class, userId);
        return account;
    }

    public List<DispatchOrder> getAllOrders(String userId) {
        Account account = getAccount(userId);
        Query query = entityManager.createQuery("SELECT o FROM DispatchOrder o WHERE o.account= :account");
        query.setParameter("account", account);
        return query.getResultList();
    }

    public void updateOrder(DispatchOrder order) {
        Query query = entityManager.createQuery(
                "UPDATE DispatchOrder c SET c.status=?2 WHERE c.id=?1");

        for (TMSOrders i : order.getTMSOrders()) {
            updateTMSOrder(i, order.getStatus());
        }
        if(order.getStatus().equals("Finished")){
            updateCarrier(order.getCarrierId(),"Available");
            updateDriver(order.getDriverId(),"Available");
        }else{
            updateCarrier(order.getCarrierId(),"Unavailable");
            updateDriver(order.getDriverId(),"Unavailable");
        }
        
        query.setParameter(1, order.getId());
        query.setParameter(2, order.getStatus());
        query.executeUpdate();
    }

    public Long addOrder(String provider, List<String> idList, DispatchOrder order) {

        Account account = getAccount(provider);
       
        Carrier carrier=getCarrier(order.getCarrierId());
        
        order.setStatus("Dispatched");
        order.setAccount(account);
        List<TMSOrders> a = new ArrayList();
        order.setTMSOrders(a);
        double weight=0;
        
        for (String i : idList) {
            TMSOrders o = getOrder(Long.parseLong(i));
            weight=weight+o.getAmount();
            order.getTMSOrders().add(o);
            
        }
        if(weight<=carrier.getCapacity()){
            order.setWeight(weight);
            for(TMSOrders i: order.getTMSOrders()){
            updateTMSOrder(i, "Dispatched");
            }
            updateCarrier(order.getCarrierId(),"Unavailable");
            updateDriver(order.getDriverId(),"Unavailable");
        entityManager.persist(order);
        entityManager.flush();
        return order.getId();
        }
        else{
            
            return -1L;
        }
    }
   public void updateCarrier(Long id,String status){
       
       Query query=entityManager.createQuery("UPDATE Carrier c SET c.itemtype=?3 WHERE c.id=?1");
      query.setParameter(1, id);
      query.setParameter(3, status);
      query.executeUpdate();
   }
   public void updateDriver(Long id , String status){
       Query query=entityManager.createQuery("UPDATE Driver c SET c.email=?3 WHERE c.id=?1");
      query.setParameter(1, id);
      query.setParameter(3, status);
      query.executeUpdate();
   }
   
    public void updateTMSOrder(TMSOrders order, String status) {
        Query query = entityManager.createQuery("UPDATE TMSOrders c SET c.status=?3 WHERE c.id=?1");

        query.setParameter(1, order.getId());

        query.setParameter(3, status);
        query.executeUpdate();
    }

    public void deleteOrder(Long OrderId) {
        Query query = entityManager.createQuery("Delete From DispatchOrder r where r.id=:Id");
        query.setParameter("Id", OrderId);

        query.executeUpdate();
    }

}
