/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import WMS.Entity.Inventory;
import WMS.Entity.Shipment_Notice;
import WMS.Entity.StorageArea;
import WMS.Entity.StorageArea_Inventory;
import WMS.Entity.WMSOrder;
import WMS.Entity.Warehouse;
import WMS.Entity.Warehouse_Inventory;
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

    public List<WMSOrder> getAllocatedOrders(String email);

    public List<Warehouse> getAllWarehouse(String email);

    public Warehouse getWarehouse(Long warehouseId);

    public Inventory getInventory(Long inventoryId);

    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas);

    public List<Warehouse_Inventory> getAvailableWarehouse(Long orderId);

    public void createInventory(String name, int quantity, String status/*, List<StorageArea> storageArea, List<Integer> storageQty*/);

    public void reserveInventory(Long inventoryId, List<StorageArea> storageArea, List<Integer> storageQty);

    public List<StorageArea_Inventory> getPickTable(Long warehouseId, Long inventoryId);

    public Shipment_Notice createShippingNotice(Long orderId, Long orderDate);

    public int allocateInventory(List<Integer> san, Long warehouseId, Long inventoryId);

    public List<Inventory> report(Long orderId);
}
