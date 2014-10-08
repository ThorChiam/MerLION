/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;

import java.util.List;
import javax.ejb.Local;
import merlion.entity.PurchaseOrder;

/**
 *
 * @author ThorChiam
 */
@Local
public interface PurchaseOrderSessionBeanLocal {

    public Long placePurchaseOrder(String name, String email, String phone, String address, String cityRegion,
            String shippingdate, String paymentTerm, String currency, String shippingTerm, double taxRate, double discount, Long recordId);

    public List<PurchaseOrder> getPurchaseOrder(String email);

    public List<PurchaseOrder> getPurchaseOrderDetails(String email, Long id);

    public void updatePurchaseOrder(Long porderid, String name, String email, String phone, String address, String cityRegion);

    public PurchaseOrder getPOrderDetails(String email, Long id);

    public void deletePurchaseOrder(String email, Long id);

}
