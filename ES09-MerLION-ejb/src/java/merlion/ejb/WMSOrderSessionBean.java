/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import merlion.ejb.local.WMSOrderSessionBeanLocal;

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
