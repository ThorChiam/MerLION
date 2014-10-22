/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import WMS.Session.WMSOrderSessionBeanLocal;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class WMSOrderSessionBean implements WMSOrderSessionBeanLocal{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
    
    public void allocateInventory(){
        
    }
}
