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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

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
    private List<StorageArea_Inventory> imanage;
    private List<Integer> checkResult;
//    private List CCC;
//    private ResultOfCheck roc;

    private String statusMessage;

    public WMSOrderManagedBean() {
    }

    public WMSOrderSessionLocal getWosl() {
        return wosl;
    }

    public void setWosl(WMSOrderSessionLocal wosl) {
        this.wosl = wosl;
    }

//    public ResultOfCheck getResultOfCheck() {
//        return roc;
//    }

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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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

    public List<Inventory> getLin() {
        lin = wosl.getInventories(orderId);
        return lin;
    }

    public void setLin(List<Inventory> lin) {
        this.lin = lin;
    }

    /**
     * check the inventory level
     *
     * @param orderId
     * @return the last element of the list indicate the stock condition,1 for
     * adequate,0 for any lack
     */
//    public List getCCC() {
//        CCC = new ArrayList<>();
//        CCC.add(this.checkResult);
//        return CCC;
//    }

    public List<Integer> getCheckResult() {
        orderId = Long.valueOf(1);//testing use
        checkResult = wosl.checkInventoryLevel(orderId);
        return checkResult;
    }

    public List<Integer> checkInventoryLevel() {
        orderId = Long.valueOf(1);//testing use
        checkResult = wosl.checkInventoryLevel(orderId);
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
    public List<Warehouse> getAvailableWarehouse() {
        orderId = Long.valueOf(1);//testing use
        List<Warehouse> lw = new ArrayList<>();
        List<Warehouse_Inventory> olwi = wosl.getAvailableWarehouse(orderId);
        List<Integer> avai = new ArrayList<>();
        for(Warehouse_Inventory wi:olwi){
            avai.add(wi.getQty());
        }
        for(Warehouse_Inventory wi:olwi){
            Warehouse w = wi.getWarehouse();
            w.setAvailable(avai);
            lw.add(w);
        }
        return lw;
    }

    public void createInventory() {
        wosl.createInventory(inventoryName, inventoryQty, inventoryStatus/*, storageArea, storageQty*/);
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

    public List<StorageArea_Inventory> getImanage() {
        return imanage;
    }

    //***************************InventoryManagement***********
    public void setImanage(List<StorageArea_Inventory> imanage) {
        this.imanage = imanage;
    }

    public void reserveStorage() {
        this.setImanage(wosl.reserveStorage(inventoryId, storageArea, storageQty));
    }

    public void putAway() {
        wosl.putAway(imanage);
    }

    public void replenish() {
        List<Inventory> ins = wosl.getInventories(orderId);
        for (int i = 0; i < ins.size(); i++) {
            Inventory in = ins.get(i);
            int tmp = in.getQuantity();
            tmp += lin.get(i).getQuantity();
            in.setQuantity(tmp);
        }
        wosl.replenish(ins);

    }

    public List<Inventory> reportInventories(String email) {
        return wosl.reportInventories(email);
    }

//    static class ResultOfCheck implements Serializable {
//
//        private List<Integer> checkResult;
//
//        public List<Integer> getCheckResult() {
//            return checkResult;
//        }
//    }
}
