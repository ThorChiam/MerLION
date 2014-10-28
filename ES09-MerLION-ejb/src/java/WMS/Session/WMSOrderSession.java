/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import CI.Entity.Account;
import WMS.Entity.Inventory;
import WMS.Entity.Shipment_Notice;
import WMS.Entity.StorageArea;
import WMS.Entity.StorageArea_Inventory;
import WMS.Entity.WMSOrder;
import WMS.Entity.WMSOrder_Inventory;
import WMS.Entity.Warehouse;
import WMS.Entity.Warehouse_Inventory;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import javax.persistence.Query;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class WMSOrderSession implements WMSOrderSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private Inventory inventory;

    //list by timeLogged in frontend,pg-1
    @Override
    public List<WMSOrder> getAllOrders(String email) {
        Query q = em.createQuery("SELECT o FROM WMSOrder o WHERE o.provider.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    //for generation of shipment_notice
    @Override
    public List<WMSOrder> getAllocatedOrders(String email) {
        Query q = em.createQuery("SELECT o FROM WMSOrder o WHERE o.provider.email=:email AND o.status=:status");
        q.setParameter("email", email);
        q.setParameter("status", "allocated");
        return q.getResultList();
    }

    //inventory list and corresponding quantity can be get by order,pg-2
    @Override
    public WMSOrder getOrder(Long orderId) {
        Query q = em.createQuery("SELECT o FROM WMSOrder o WHERE o.id=:orderId");
        q.setParameter("orderId", orderId);
        return (WMSOrder) q.getSingleResult();
    }

    /**
     * check the inventory level
     *
     * @param orderId
     * @return the condition of the stock:Adequate for the order/the number of
     * lack
     */
    //pg-2
    @Override
    public List<Integer> checkInventoryLevel(Long orderId) {
        WMSOrder o = this.getOrder(orderId);
        List<Integer> checkI = new ArrayList();
        //ir:inventory Required
        List<WMSOrder_Inventory> irs = o.getWo_inven();
        for (WMSOrder_Inventory ir : irs) {
            checkI.add(ir.getInventory().getQuantity() - ir.getQty());
        }
        return checkI;
    }

    @Override
    public List<Warehouse> getAllWarehouse(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account) q.getSingleResult();
        return a.getCompany().getWarehouse();
    }

    @Override
    public Warehouse getWarehouse(Long warehouseId) {
        Query q = em.createQuery("SELECT w FROM Warehouse w WHERE w.id=:warehouseId");
        q.setParameter("warehouseId", warehouseId);
        return (Warehouse) q.getSingleResult();
    }

    @Override
    public Inventory getInventory(Long inventoryId) {
        Query q = em.createQuery("SELECT i FROM Inventory i WHERE i.id=:inventoryId");
        q.setParameter("inventoryId", inventoryId);
        return (Inventory) q.getSingleResult();
    }

    @Override
    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas) {
        List<Warehouse> lw = new ArrayList<>();
        for (StorageArea sa : sas) {
            Warehouse w = sa.getWMSWarehouse();
            if (!lw.contains(w)) {
                lw.add(w);
            }
        }
        return lw;
    }

//    public List<Integer> getWarehouseQtyBySA(Set<StorageArea> sas, List<Integer> storageQty) {
//        List<Integer> wQty = new ArrayList();
//        List<Long> wId = new ArrayList();
//        Iterator itr = sas.iterator();
//        int count = 0;
//        while (itr.hasNext()) {
//            StorageArea sa = (StorageArea) itr.next();
//            Long warehouseId = sa.getWMSWarehouse().getId();
//            int tempSQty = storageQty.get(count);
//            if (wId.contains(warehouseId)) {
//                int tempIndex = wId.indexOf(warehouseId);
//                int temp = wQty.get(tempIndex);
//                temp += tempSQty;
//                wQty.set(tempIndex, temp);
//            } else {
//                wId.add(warehouseId);
//                wQty.add(tempSQty);
//            }
//            count++;
//        }
//        return wQty;
//    }
    private int countWarehouseQtyBySA(Long warehouseId, Long inventoryId) {
        Query q = em.createQuery("SELECT si.qty FROM StorageArea_Inventory si WHERE si.inventory.id=:inventoryId AND si.sa.WMSWarehouse.id=:warehouseId");
        q.setParameter("inventoryId", inventoryId);
        q.setParameter("warehouseId", warehouseId);
        int total = 0;
        for (Object o : q.getResultList()) {
            int qty = (int) o;
            total += qty;
        }
        return total;
    }
//pg-3

    @Override
    public List<Warehouse_Inventory> getAvailableWarehouse(Long orderId) {
        WMSOrder order = this.getOrder(orderId);
        List<WMSOrder_Inventory> lin = order.getWo_inven();
        Query q = em.createQuery("SELECT wi FROM Warehouse_Inventory wi");
        List<Warehouse_Inventory> wis = (List<Warehouse_Inventory>) q.getResultList();
        List<Warehouse_Inventory> awis = new ArrayList<>();
        for (Warehouse_Inventory wi : wis) {
            Long w_inventoryId = wi.getInventory().getId();
            for (WMSOrder_Inventory lin1 : lin) {
                Long o_inventoryId = lin1.getInventory().getId();
                if (Objects.equals(w_inventoryId, o_inventoryId)) {
                    awis.add(wi);
                }
            }
        }
        return awis;
    }
//    public List<Warehouse> getAllocateWarehouse(List<Warehouse> lw, List<Integer> allocateQty) {
//
//        List<Warehouse> lwo = new ArrayList();
//
//        for (int i = 0; i < allocateQty.size(); i++) {
//            if (allocateQty.get(i) != 0) {
//                lwo.add(lw.get(i));
//            }
//        }
//        return lwo;
//    }

    @Override
    public void reserveInventory(Long inventoryId, List<StorageArea> storageArea, List<Integer> storageQty){
        Query q = em.createQuery("SELECT iv FROM Inventory iv WHERE iv.id=:inventoryId");
        q.setParameter("inventoryId", inventoryId);
        inventory = (Inventory)q.getSingleResult();
       List<StorageArea_Inventory> sais = new ArrayList<>();
        for (int i = 0; i < storageArea.size(); i++) {
            StorageArea_Inventory sai = new StorageArea_Inventory();
            sai.setSa(storageArea.get(i));
            sai.setInventory(inventory);
            sai.setQty(storageQty.get(i));
            em.persist(sai);
            sais.add(sai);
        }
        inventory.setSa_inven(sais);
        List<Warehouse> lw = this.getWarehouseByStorageArea(storageArea);
        List<Warehouse_Inventory> wis = new ArrayList<>();
        for (Warehouse lw1 : lw) {
            Warehouse_Inventory wi = new Warehouse_Inventory();
            wi.setWarehouse(lw1);
            wi.setInventory(inventory);
            wi.setQty(this.countWarehouseQtyBySA(lw1.getId(), inventory.getId()));
            em.persist(wi);
            wis.add(wi);
        }
        inventory.setWs_inven(wis);
        em.merge(inventory);
    }
    @Override
    public void createInventory(String name, int quantity, String status/*, List<StorageArea> storageArea, List<Integer> storageQty*/) {
        inventory = new Inventory();
        inventory.setName(name);
        inventory.setStatus(status);
        inventory.setQuantity(quantity);
//        List<StorageArea_Inventory> sais = new ArrayList<>();
//        for (int i = 0; i < storageArea.size(); i++) {
//            StorageArea_Inventory sai = new StorageArea_Inventory();
//            sai.setSa(storageArea.get(i));
//            sai.setInventory(inventory);
//            sai.setQty(storageQty.get(i));
//            em.persist(sai);
//            sais.add(sai);
//        }
//        inventory.setSa_inven(sais);
//        List<Warehouse> lw = this.getWarehouseByStorageArea(storageArea);
//        List<Warehouse_Inventory> wis = new ArrayList<>();
//        for (Warehouse lw1 : lw) {
//            Warehouse_Inventory wi = new Warehouse_Inventory();
//            wi.setWarehouse(lw1);
//            wi.setInventory(inventory);
//            wi.setQty(this.countWarehouseQtyBySA(lw1.getId(), inventory.getId()));
//            em.persist(wi);
//            wis.add(wi);
//        }
//        inventory.setWs_inven(wis);
        em.persist(inventory);
    }

//    public List<Warehouse> allocateInventory(Long inventoryId, List<Integer> allocateQty) {
//        Inventory inventories = this.getInventory(inventoryId);
//        Set<Warehouse> sw = inventories.getWarehouse();
//        List<Integer> wQty = inventories.getWarehouseQty();
//        List<Warehouse> lw = new ArrayList();
//        Iterator itr = sw.iterator();
//        int count = 0;
//        while (itr.hasNext()) {
//            Warehouse tempW = (Warehouse)itr.next();
//            lw.add(tempW);
//            int tmp = wQty.get(count);
//            tmp -= allocateQty.get(count);
//            wQty.set(count, tmp);
//            count++;
//        }
//        inventories.setWarehouseQty(wQty);
//        em.persist(inventories);
//        List<Warehouse> lwo = this.getAllocateWarehouse(lw, allocateQty);
//        return lwo;
//    }
    //pg-4,bottom dynamic table
    @Override
    public List<StorageArea_Inventory> getPickTable(Long warehouseId, Long inventoryId) {
        Query q = em.createQuery("SELECT si FROM StorageArea_Inventory si WHERE si.inventory.id=:inventoryId AND si.sa.WMSWarehouse.id=:warehouseId");
        q.setParameter("inventoryId", inventoryId);
        q.setParameter("warehouseId", warehouseId);
        List<StorageArea_Inventory> sal = q.getResultList();
        return sal;
    }
//pg-extra
//    public List<WMSFacility> getPack(Long warehouseId) {
//        Query q = em.createQuery("SELECT wf FROM WMSFacility wf WHERE wf.type=:type");
//        q.setParameter("type", "pack");
//        return (List<WMSFacility>)q.getResultList();
//    }

    @Override
    public Shipment_Notice createShippingNotice(Long orderId, Long orderDate) {
        Shipment_Notice sn = new Shipment_Notice();
        Query q = em.createQuery("SELECT wo FROM WMSOrder wo WHERE wo.id=:orderId");
        q.setParameter("orderId", orderId);
        WMSOrder wo = (WMSOrder) q.getSingleResult();
        sn.setOrder(wo);
        sn.setOrderdate(orderDate);
        em.persist(sn);
        return sn;
    }
//pg-4

    @Override
    public int allocateInventory(List<Integer> san, Long warehouseId, Long inventoryId) {
        List<StorageArea_Inventory> sal = this.getPickTable(warehouseId, inventoryId);
        for (int i = 0; i < san.size(); i++) {
            StorageArea_Inventory si = sal.get(i);
            int tmpQty = si.getQty();
            tmpQty -= san.get(i);
            si.setQty(tmpQty);
            em.merge(si);
        }

        Query q = em.createQuery("SELECT wi FROM Warehouse_Inventory wi WHERE wi.id=:warehouseId");
        q.setParameter("warehouseId", warehouseId);
        Warehouse_Inventory wi = (Warehouse_Inventory) q.getSingleResult();
        int warehouseNewStock = this.countWarehouseQtyBySA(warehouseId, inventoryId);
        wi.setQty(warehouseNewStock);
        em.merge(wi);

        return warehouseNewStock;
    }

    private int countInventoryQty(Inventory inven) {
        List<Warehouse_Inventory> wis = inven.getWs_inven();
        int totalQty = 0;
        for (Warehouse_Inventory wi : wis) {
            totalQty += wi.getQty();
        }
        return totalQty;
    }

    private void updateInventory(List<Inventory> li) {
        for (Inventory inven : li) {
            int qty = this.countInventoryQty(inven);
            inven.setQuantity(qty);
            em.merge(inven);
        }
    }

    private List<Inventory> convertInventory(List<WMSOrder_Inventory> lwi) {
        List<Inventory> lin = new ArrayList();
        for (WMSOrder_Inventory lwi1 : lwi) {
            lin.add(lwi1.getInventory());
        }
        return lin;
    }

    @Override
    public List<Inventory> report(Long orderId) {
        Query q = em.createQuery("SELECT wo FROM WMSOrder wo WHERE wo.id=:orderId");
        q.setParameter("orderId", orderId);
        WMSOrder wo = (WMSOrder) q.getSingleResult();
        List<Inventory> li = this.convertInventory(wo.getWo_inven());
        this.updateInventory(li);
        wo.setStatus("allocated");
        em.merge(wo);
        return li;
    }
}
