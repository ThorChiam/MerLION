/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import WMS.Entity.WMSOrder;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ThorChiam
 */
@Local
public interface WMSOrderSessionLocal {

    public List<WMSOrder> getAllOrders(String email);

    public WMSOrder getOrder(Long orderId);

    public List<Integer> checkInventoryLevel(Long orderId);
}
