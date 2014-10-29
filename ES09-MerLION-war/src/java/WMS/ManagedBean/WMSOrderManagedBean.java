/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.ManagedBean;

import WMS.Entity.Inventory;
import WMS.Entity.Shipment_Notice;
import WMS.Entity.StorageArea;
import WMS.Entity.StorageArea_Inventory;
import WMS.Entity.WMSOrder;
import WMS.Entity.Warehouse;
import WMS.Entity.Warehouse_Inventory;
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
    private Long warehouseId;
    private Long inventoryId;
    private List<Warehouse> lw;
    
    private String inventoryName;
    private int inventoryQty;
    private String inventoryStatus;
    private List<StorageArea> storageArea;
    private List<Integer> storageQty;
    private Long noticeDate;
    private List<Integer> san;//storageArea qty
            
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
    public List<WMSOrder> getAllOrders(String email){
        return wosl.getAllOrders(email);
    }

    public WMSOrder getOrder(){
        return wosl.getOrder(orderId);
    }

    public List<WMSOrder> getAllocatedOrders(String email){
        return wosl.getAllocatedOrders(email);
    }

    public List<Warehouse> getAllWarehouse(String email){
        return wosl.getAllWarehouse(email);
    }

    public Warehouse getWarehouse(){
        return wosl.getWarehouse(warehouseId);
    }

    public Inventory getInventory(){
       return wosl.getInventory(inventoryId);
    }

//    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas);

    public List<Warehouse_Inventory> getAvailableWarehouse(){
        return wosl.getAvailableWarehouse(orderId);
    }

    public void createInventory(){
        wosl.createInventory(inventoryName, inventoryQty, inventoryStatus/*, storageArea, storageQty*/);
    }

    public List<StorageArea_Inventory> getPickTable(){
        return wosl.getPickTable(warehouseId, inventoryId);
    }

    public Shipment_Notice createShippingNotice(){
        return wosl.createShippingNotice(orderId, noticeDate);
    }

    public int allocateInventory(){
        return wosl.allocateInventory(san, warehouseId, inventoryId);
    }

    public List<Inventory> report(){
        return wosl.report(orderId);
    }
    //***************************InventoryManagement***********
    public void reserveStorage(){
        wosl.reserveInventory(inventoryId, storageArea, storageQty);
    }
}
