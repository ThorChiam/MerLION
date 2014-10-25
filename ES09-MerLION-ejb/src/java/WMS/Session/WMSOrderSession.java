/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import WMS.Entity.Inventory;
import WMS.Entity.WMSOrder;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class WMSOrderSession implements WMSOrderSessionLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    //list by timeLogged in frontend
    public List<WMSOrder> getAllOrders(String email) {
        Query q = em.createQuery("SELECT o FROM WMSOrder o WHERE o.provider.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }
/**
 * check the inventory level
 * @param orderId
 * @return the condition of the stock:Adequate for the order/the number of lack
 */
    public List<Integer> checkInventoryLevel(Long orderId) {
        Query q = em.createQuery("SELECT o FROM WMSOrder o WHERE o.id=:orderId");
        q.setParameter("orderId", orderId);
        WMSOrder o = (WMSOrder) q.getSingleResult();
        List<Integer> checkI = new ArrayList();
        //ins:inventories
        List<Inventory> ins = o.getInventory();
        //ir:inventory Required
        List<Integer> ir = o.getQuantity();
        for(int j=0;j<ins.size();j++){
            checkI.add((ins.get(j).getQuantity()-ir.get(j)));
        }
        return checkI;
    }
    
    public void allocateInventory(){
        
    }
    
}
