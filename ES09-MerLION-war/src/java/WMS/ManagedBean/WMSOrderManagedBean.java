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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
    
    private String whName;
    private String inName;
    private Long whId;
    private Long inId;
    private Long selectedStainId;
    private List<StorageArea_Inventory> stain;
    private String statusMessage;
//    private List<AllocateWarehouse> aws;
//    private List<allocateWarehouse> aws;
//private List<String[]> wss; 

    public WMSOrderManagedBean() {
    }
//    public ResultOfCheck getResultOfCheck() {
//        return roc;
//    }

//    public List<String[]> getWss(){
//        this.initWss();
//        return wss;
//    }
//    private void initWss(){
//        orderId = Long.valueOf(2);//testing use
//        List<WMSOrder_Inventory> woil = wosl.getOrder(orderId).getWo_inven();
//        List<Warehouse_Inventory> olwi = wosl.getAvailableWarehouse(orderId);
//        int columnNo = olwi.size()+2;
//        
//        String[] header;
//        header = new String[columnNo];
//           header[0] = "Inventory Name";
//           header[1] = "Qty";
//           for(int j=2;j<olwi.size();j++){
//               header[j] = olwi.get(j).getWarehouse().getName();
//           }
//           wss.add(header);
//        for(int i=0;i<woil.size();i++){
//           String[] wsi;
//            wsi = new String[columnNo];
//           wsi[0] = woil.get(i).getInventory().getName();
//           wsi[1] = String.valueOf(woil.get(i).getQty());
//           for(int j=2;j<olwi.size();j++){
//               wsi[j] = String.valueOf(olwi.get(i).getQty());
//           }
//           wss.add(wsi);
//        }
//        
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
        orderId = Long.valueOf(2);
        lin = wosl.getInventories(orderId);
        return lin;
    }

    public void setLin(List<Inventory> lin) {
        this.lin = lin;
    }

    public List<Integer> getCheckResult() {
        orderId = Long.valueOf(2);//testing use
        checkResult = wosl.checkInventoryLevel(orderId);
        return checkResult;
    }

    public List<Integer> checkInventoryLevel() {
        orderId = Long.valueOf(2);//testing use
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
        orderId = Long.valueOf(2);//testing use
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

    public List<Warehouse_Inventory> showWarhouseInventory() {
        orderId = Long.valueOf(2);
        return wosl.getAvailableWarehouse(orderId);
    }
//    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas);

    public List<Warehouse> getAvailableWarehouse() {
        orderId = Long.valueOf(2);//testing use
        List<Warehouse> lww = new ArrayList<>();
        List<Warehouse_Inventory> olwi = wosl.getAvailableWarehouse(orderId);
        List<Integer> avai = new ArrayList<>();
        for (Warehouse_Inventory wi : olwi) {
            avai.add(wi.getQty());
        }
        for (Warehouse_Inventory wi : olwi) {
            Warehouse w = wi.getWarehouse();
            w.setAvailable(avai);
            lww.add(w);
        }
        return lww;
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
    public String whName(){
       Long tmpId = (Long)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("whId");
       return wosl.getWarehouse(tmpId).getName();
    }
    public String inName(){
        Long tmpId = (Long)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("inId");
       return wosl.getInventory(tmpId).getName();
    }
    public List<StorageArea_Inventory> getStain() {
        stain = wosl.getPickTable(whId, inId);
        return stain;
    }

    public void setStain(List<StorageArea_Inventory> stain) {
        this.stain = stain;
    }

    public void putStain(ActionEvent event) {
        whId = (Long) event.getComponent().getAttributes().get("whId");
        inId = (Long) event.getComponent().getAttributes().get("inId");
        whName = (String) event.getComponent().getAttributes().get("whName");
        inName = (String) event.getComponent().getAttributes().get("inName");
        System.out.println("*****  w:"+whName+";**********   i:"+inName);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("whId", whId);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("inId", inId);
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public void setSelectedStainId(ActionEvent event) {
        selectedStainId = (Long) event.getComponent().getAttributes().get("stain");
    }

    public Long getSelectedStainId() {
        return selectedStainId;
    }

    public void test(ActionEvent event) {
        selectedStainId = (Long) event.getComponent().getAttributes().get("inventoryId");

        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("name", 1);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("name");

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("name", 1);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("name", 1);
    }

//    private List<Warehouse_Inventory> sortWi(List<Warehouse_Inventory> olwi, List<Long> wiList) {
//        List<Warehouse_Inventory> tmp = new ArrayList<>();
//        for (Long w : wiList) {
//            for (Warehouse_Inventory wi : olwi) {
//                if (wi.getWarehouse().getId().equals(w)) {
//                    tmp.add(wi);
//                }
//            }
//        }
//        return tmp;
//    }
//    public List<AllocateWarehouse> getAws() {
//        return aws;
//    }
//
//    public void setAws(List<AllocateWarehouse> aws) {
//        this.aws = aws;
//    }
//    public List<allocateWarehouse> getAws() {
//        orderId = Long.valueOf(2);//testing use
//        aws = new ArrayList<>();
//        List<Warehouse_Inventory> olwi = wosl.getAvailableWarehouse(orderId);
//        List<WMSOrder_Inventory> woil = wosl.getOrder(orderId).getWo_inven();
//        List<Long> wiList = new ArrayList();
//        for (Warehouse_Inventory kw : olwi) {
//            wiList.add(kw.getWarehouse().getId());
//        }
//        Collections.sort(wiList);
//        List<Warehouse_Inventory> sorted = this.sortWi(olwi, wiList);
//
//        for (WMSOrder_Inventory woil1 : woil) {
//            String iName = woil1.getInventory().getName();
//            int iQty = woil1.getQty();
//            List<Integer> tmp = new ArrayList<>();
////            System.out.println("****Qty:"+tmp.size()+",Sort:"+sorted.size());
//            for (Warehouse_Inventory wii : sorted) {
//                tmp.add(wii.getQty());
//                System.out.println("****Qty:" + tmp.size() + "," + wii.getQty());
//            }
//            allocateWarehouse aw = new allocateWarehouse(iName, iQty, tmp);
//            aws.add(aw);
//        }
//        return aws;
//    }
//
//    public void setAws(List<allocateWarehouse> aws) {
//        this.aws = aws;
//    }
//
//    public List<ColumnModel> getColumns() {
//        return columns;
//    }
//
//    public void setColumns(List<ColumnModel> columns) {
//        this.columns = columns;
//    }
//
//    static public class allocateWarehouse implements Serializable {
//
//        private String inventoryName;
//        private int inventoryQty;
//        private List<Integer> wil;//{w1-i1,w2-i1,w3-i1}
//
//        public allocateWarehouse(String inventoryName, int inventoryQty, List<Integer> wil) {
//            this.inventoryName = inventoryName;
//            this.inventoryQty = inventoryQty;
//            this.wil = wil;
//        }
//
//        public String getInventoryName() {
//            return inventoryName;
//        }
//
//        public void setInventoryName(String inventoryName) {
//            this.inventoryName = inventoryName;
//        }
//
//        public int getInventoryQty() {
//            return inventoryQty;
//        }
//
//        public void setInventoryQty(int inventoryQty) {
//            this.inventoryQty = inventoryQty;
//        }
//
//        public List<Integer> getWil() {
//            return wil;
//        }
//
//        public void setWil(List<Integer> wil) {
//            this.wil = wil;
//        }
//
//    }
//    static public class ColumnModel implements Serializable {
//
//        private String header;
//        private String property;
//
//        public ColumnModel(String header, String property) {
//            this.header = header;
//            this.property = property;
//        }
//
//        public String getHeader() {
//            return header;
//        }
//
//        public String getProperty() {
//            return property;
//        }
//    }
//    static public class AllocateWarehouse implements Serializable {
//
//        private String wName;
//        private int wQty;
//
//        public AllocateWarehouse(String wName, int wQty) {
//            this.wName = wName;
//            this.wQty = wQty;
//        }
//
//        public String getWName() {
//            return wName;
//        }
//
//        public int getWQty() {
//            return wQty;
//        }
//    }
}
