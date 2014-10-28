/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import CI.Entity.Account;
import WMS.Entity.Inventory;
import WMS.Entity.StorageArea;
import WMS.Entity.StorageArea_Inventory;
import WMS.Entity.WMSFacility;
import WMS.Entity.WMSOrder;
import WMS.Entity.Warehouse;
import WMS.Entity.Warehouse_Inventory;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
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
        //ins:inventories
        List<Inventory> ins = o.getInventory();
        //ir:inventory Required
        List<Integer> ir = o.getQuantity();
        for (int j = 0; j < ins.size(); j++) {
            checkI.add((ins.get(j).getQuantity() - ir.get(j)));
        }
        return checkI;
    }

    public List<Warehouse> getAllWarehouse(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account) q.getSingleResult();
        return a.getCompany().getWarehouse();
    }

    public Warehouse getWarehouse(Long warehouseId) {
        Query q = em.createQuery("SELECT w FROM Warehouse w WHERE w.id=:warehouseId");
        q.setParameter("warehouseId", warehouseId);
        return (Warehouse) q.getSingleResult();
    }

    public Inventory getInventory(Long inventoryId) {
        Query q = em.createQuery("SELECT i FROM Inventory i WHERE i.id=:inventoryId");
        q.setParameter("inventoryId", inventoryId);
        return (Inventory) q.getSingleResult();
    }

    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas) {
        List<Warehouse> lw = new ArrayList<>();
        for (int i = 0; i < sas.size(); i++) {
            Warehouse w = sas.get(i).getWMSWarehouse();
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
    public int countWarehouseQtyBySA(Long warehouseId, Long inventoryId) {
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
    public List<Warehouse_Inventory> getAvailableWarehouse(Long orderId) {
        WMSOrder order = this.getOrder(orderId);
        List<Inventory> lin = order.getInventory();
            Query q = em.createQuery("SELECT wi FROM Warehouse_Inventory wi");
            List<Warehouse_Inventory> wis = (List<Warehouse_Inventory>) q.getResultList();
            List<Warehouse_Inventory> awis = new ArrayList<>();
            for(int i=0;i<wis.size();i++){
                if(lin.contains(wis.get(i).getInventory())){
                    awis.add(wis.get(i));
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

    public void createInventory(String name, int quantity, String status, List<StorageArea> storageArea, List<Integer> storageQty) {
        inventory = new Inventory();
        inventory.setName(name);
        inventory.setStatus(status);
        inventory.setQuantity(quantity);
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
        for (int i = 0; i < lw.size(); i++) {
            Warehouse_Inventory wi = new Warehouse_Inventory();
            wi.setWarehouse(lw.get(i));
            wi.setInventory(inventory);
            wi.setQty(this.countWarehouseQtyBySA(lw.get(i).getId(), inventory.getId()));
            em.persist(wi);
            wis.add(wi);
        }
        inventory.setWs_inven(wis);
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
    public List<StorageArea_Inventory> getPickTable(Long warehouseId, Long inventoryId, int iQty) {
        Query q = em.createQuery("SELECT si FROM StorageArea_Inventory si WHERE si.inventory.id=:inventoryId AND si.sa.WMSWarehouse.id=:warehouseId");
        q.setParameter("inventoryId", inventoryId);
        q.setParameter("warehouseId", warehouseId);
        List<StorageArea_Inventory> sal = q.getResultList();
        return sal;
    }
//pg-4
    public List<WMSFacility> getPack(Long warehouseId) {
        Query q = em.createQuery("SELECT wf FROM WMSFacility wf WHERE wf.type=:type");
        q.setParameter("type", "pack");
        return (List<WMSFacility>)q.getResultList();
    }

    public void report() {

    }

    public void generateShippingNotice() {

    }
}
