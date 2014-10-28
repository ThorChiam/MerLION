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
    private List<Inventory> lin;

    public WMSOrderManagedBean() {
    }

    /**
     * check the inventory level
     *
     * @param orderId
     * @return the last element of the list indicate the stock condition,1 for
     * adequate,0 for any lack
     */
    public List<Integer> checkInventoryLevel() {
        orderId = Long.valueOf(1);//testing use
        List<Integer> checkResult = wosl.checkInventoryLevel(orderId);
        for (int i = 0; i < checkResult.size(); i++) {
            if (checkResult.get(i) < 0) {
                checkResult.add(0);
                return checkResult;
            }
        }
        checkResult.add(1);
        return checkResult;
    }

    public String check() {
        int size = this.checkInventoryLevel().size();
        int flag = this.checkInventoryLevel().get(size - 1);
        if (flag == 1) {
            return "Allocate";
        } else {
            return "Replenish";
        }
    }

    public List<WMSOrder> getAllOrders(String email) {
        return wosl.getAllOrders(email);
    }

    public WMSOrder getOrder() {
        orderId = Long.valueOf(1);//testing use
        return wosl.getOrder(orderId);
    }

    public List<WMSOrder> getAllocatedOrders(String email) {
        return wosl.getAllocatedOrders(email);
    }

    public List<Warehouse> getAllWarehouse(String email) {
        return wosl.getAllWarehouse(email);
    }

    public Warehouse getWarehouse() {
        return wosl.getWarehouse(warehouseId);
    }

    public Inventory getInventory() {
        return wosl.getInventory(inventoryId);
    }

//    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas);
    public List<Warehouse_Inventory> getAvailableWarehouse() {
        orderId = Long.valueOf(1);//testing use
        return wosl.getAvailableWarehouse(orderId);
    }

    public void createInventory() {
        wosl.createInventory(inventoryName, inventoryQty, inventoryStatus, storageArea, storageQty);
    }

    public List<StorageArea_Inventory> getPickTable() {
        return wosl.getPickTable(warehouseId, inventoryId);
    }

    public Shipment_Notice createShippingNotice() {
        return wosl.createShippingNotice(orderId, noticeDate);
    }

    public int allocateInventory() {
        return wosl.allocateInventory(san, warehouseId, inventoryId);
    }

    public List<Inventory> report() {
        return wosl.report(orderId);
    }

    public WMSOrderSessionLocal getWosl() {
        return wosl;
    }

    public void setWosl(WMSOrderSessionLocal wosl) {
        this.wosl = wosl;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public List<Warehouse> getLw() {
        return lw;
    }

    public void setLw(List<Warehouse> lw) {
        this.lw = lw;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public int getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(int inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public List<StorageArea> getStorageArea() {
        return storageArea;
    }

    public void setStorageArea(List<StorageArea> storageArea) {
        this.storageArea = storageArea;
    }

    public List<Integer> getStorageQty() {
        return storageQty;
    }

    public void setStorageQty(List<Integer> storageQty) {
        this.storageQty = storageQty;
    }

    public Long getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Long noticeDate) {
        this.noticeDate = noticeDate;
    }

    public List<Integer> getSan() {
        return san;
    }

    public void setSan(List<Integer> san) {
        this.san = san;
    }

    public List<Inventory> getInventories() {
        orderId = Long.valueOf(1);//testing use
        
        return wosl.getInventories(orderId);
    }

    public List<Inventory> getLin() {
        lin = this.getInventories();
        return lin;
    }

    public void setLin(List<Inventory> lin) {
        this.lin = lin;
    }

    public void replenish() {
        System.out.println("**************test replenish");
        orderId = Long.valueOf(1);//testing use
        List<Inventory> afterRepl = wosl.getInventories(orderId);
        for(int j=0;j<lin.size();j++){
            int tmp = afterRepl.get(j).getQuantity()+ lin.get(j).getQuantity();
            afterRepl.get(j).setQuantity(tmp);
            System.out.println("*****af***:"+afterRepl.get(j).getQuantity()+"****lin***:"+lin.get(j).getQuantity());
        }
        wosl.replenish(afterRepl);
    }

}
