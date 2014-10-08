/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.PurchaseOrderSessionBeanLocal;
import merlion.entity.PurchaseOrder;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "purchaseOrderManagedBean")
@SessionScoped
public class PurchaseOrderManagedBean implements Serializable {

    /**
     * Creates a new instance of PurchaseOrderManagedBean
     */
    private PurchaseOrderSessionBeanLocal posbl;
    private String name;
    private String phone;
    private String address;
    private String cityRegion;
    private String shippingdate;
    private String paymentTerm;
    private String currency;
    private String shippingTerm;
    private double taxRate;
    private double discount;
    private Long recordId;

    public PurchaseOrderManagedBean() {
    }
    public Long placePurchaseOrder(String email){
        return posbl.placePurchaseOrder(name, email, phone, address, cityRegion, shippingdate, paymentTerm, currency, shippingTerm, taxRate, discount, recordId);
    }
    public List<PurchaseOrder> getPurchaseOrder(String email){
        return posbl.getPurchaseOrder(email);
    }
    public List<PurchaseOrder> getPurchaseOrderDetails(String email, Long id){
        return posbl.getPurchaseOrderDetails(email, id);
    }
    public void updatePurchaseOrder(String email){
       posbl.updatePurchaseOrder(recordId, name, email, phone, address, cityRegion);
    }
    public PurchaseOrder getPOrderDetails(String email, Long id){
        return posbl.getPOrderDetails(email, id);
    }
    public void deletePurchaseOrder(String email, Long id){
        posbl.deletePurchaseOrder(email, id);
    }
}
