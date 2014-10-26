/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.ManagedBean;

import WMS.Session.WMSOrderSessionLocal;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "womb")
@SessionScoped
public class WMSOrderManagedBean implements Serializable {

    /**
     * Creates a new instance of WMSOrderManagedBean
     */
    @EJB
    private WMSOrderSessionLocal wosl;
    private Long orderId;
    
    public WMSOrderManagedBean() {
    }
    /**
     * check the inventory level
     * @param orderId
     * @return the last element of the list indicate the stock condition,1 for adequate,0 for any lack
     */
    public List<Integer> checkInventoryLevel(Long orderId){
        List<Integer> checkResult = wosl.checkInventoryLevel(orderId);
        for(int i=0;i<checkResult.size();i++){
            if(checkResult.get(i)<0){
                checkResult.add(0);
                return checkResult;
            }
        }
        checkResult.add(1);
        return checkResult;
    }
}
