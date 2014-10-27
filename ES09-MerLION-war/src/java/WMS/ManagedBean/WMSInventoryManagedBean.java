/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.ManagedBean;

import WMS.Session.WMSInventorySessionLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author ThorChiam
 */
@Named(value = "wimb")
@SessionScoped
public class WMSInventoryManagedBean implements Serializable {

    /**
     * Creates a new instance of WMSInventoryManagedBean
     */
    @EJB
    private WMSInventorySessionLocal wosl;
    public WMSInventoryManagedBean() {
    }
    
}
