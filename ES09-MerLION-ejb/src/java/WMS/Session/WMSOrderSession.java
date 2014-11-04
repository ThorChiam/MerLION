/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Session;

import CI.Entity.Account;
import WMS.Entity.Employee;
import WMS.Entity.Inventory;
import WMS.Entity.Shipment_Notice;
import WMS.Entity.StorageArea;
import WMS.Entity.StorageArea_Inventory;
import WMS.Entity.WMSFacility;
import WMS.Entity.WMSOrder;
import WMS.Entity.WMSOrder_Inventory;
import WMS.Entity.WMSSchedule;
import WMS.Entity.Warehouse;
import WMS.Entity.Warehouse_Inventory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import javax.ejb.Remove;
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
    private WMSSchedule ws;
    private Employee employee;
    private WMSFacility facility;

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

    @Override
    public List<Inventory> getAllInventories(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account) q.getSingleResult();
        Long companyId = a.getCompany().getId();
        q = em.createQuery("SELECT whin FROM  Warehouse_Inventory whin WHERE whin.warehouse.Company.id=:companyId");
        q.setParameter("companyId", companyId);
        List<Warehouse_Inventory> whin = q.getResultList();
        List<Inventory> lin = new ArrayList<>();
        for (Warehouse_Inventory wi : whin) {
            if (!lin.contains(wi.getInventory())) {
                lin.add(wi.getInventory());
            }
        }

        return lin;
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

//    @Override
//    public List<Warehouse> getWarehouseByStorageArea(List<StorageArea> sas) {
//        List<Warehouse> lw = new ArrayList<>();
//        for (StorageArea sa : sas) {
//            Warehouse w = sa.getWMSWarehouse();
//            if (!lw.contains(w)) {
//                lw.add(w);
//            }
//        }
//        return lw;
//    }
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
        Query q = em.createQuery("SELECT si.qty FROM StorageArea_Inventory si WHERE si.inventory.id=:inventoryId AND si.sa.WMSWarehouse.id=:warehouseId AND si.status=:status");
        q.setParameter("inventoryId", inventoryId);
        q.setParameter("warehouseId", warehouseId);
        q.setParameter("status", "used");
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

    @Override
    public List<StorageArea_Inventory> getAllSis() {
        Query q = em.createQuery("SELECT si FROM StorageArea_Inventory si");
        return (List<StorageArea_Inventory>) q.getResultList();
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
    public List<Inventory> getInventories(Long orderId) {
        Query q = em.createQuery("SELECT wo FROM WMSOrder wo WHERE wo.id=:orderId");
        q.setParameter("orderId", orderId);
        WMSOrder wo = (WMSOrder) q.getSingleResult();
        List<Inventory> li = this.convertInventory(wo.getWo_inven());
        return li;
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

    //*****************************
    @Override
    public void replenish(List<Inventory> lis) {
        for (Inventory i : lis) {
            em.merge(i);
        }
    }

    @Override
    public List<StorageArea_Inventory> reserveStorage(Long inventoryId, List<StorageArea> storageArea, List<Integer> storageQty) {
        Query q = em.createQuery("SELECT iv FROM Inventory iv WHERE iv.id=:inventoryId");
        q.setParameter("inventoryId", inventoryId);
        inventory = (Inventory) q.getSingleResult();
        List<StorageArea_Inventory> sais = new ArrayList<>();
        List<Warehouse_Inventory> wis = new ArrayList<>();

        for (int i = 0; i < storageArea.size(); i++) {
            StorageArea_Inventory sai = new StorageArea_Inventory();
            sai.setSa(storageArea.get(i));
            sai.setInventory(inventory);
            sai.setQty(storageQty.get(i));
            sai.setStatus("reserved");
            if (i == 0) {
                Warehouse_Inventory wi = new Warehouse_Inventory();
                List<StorageArea_Inventory> wsais = new ArrayList<>();
                wsais.add(sai);
                wi.setWarehouse(storageArea.get(i).getWMSWarehouse());
                wi.setInventory(inventory);
                wi.setQty(sai.getQty());
                wi.setStatus("reserved");
                wi.setStorageArea_Inventory(wsais);
                wis.add(wi);
                sai.setWi(wi);
            } else {
                for (Warehouse_Inventory wi2 : wis) {
                    if (wi2.getWarehouse().getId().equals(storageArea.get(i).getWMSWarehouse().getId())) {
                        wi2.getStorageArea_Inventory().add(sai);
                        int tmp = sai.getQty() + wi2.getQty();
                        wi2.setQty(tmp);
                        sai.setWi(wi2);
                    } else {
                        Warehouse_Inventory wi = new Warehouse_Inventory();
                        List<StorageArea_Inventory> wsais = new ArrayList<>();
                        wsais.add(sai);
                        wi.setWarehouse(storageArea.get(i).getWMSWarehouse());
                        wi.setInventory(inventory);
                        wi.setQty(sai.getQty());
                        wi.setStatus("reserved");
                        wi.setStorageArea_Inventory(wsais);
                        wis.add(wi);
                        sai.setWi(wi);
                    }
                }
            }
            em.persist(sai);
            sais.add(sai);
        }
        inventory.setSa_inven(sais);
//        List<Warehouse> lw = this.getWarehouseByStorageArea(storageArea);
//        for (Warehouse lw1 : lw) {
//            Warehouse_Inventory wi = new Warehouse_Inventory();
//            wi.setWarehouse(lw1);
//            wi.setInventory(inventory);
//            wi.setQty(this.countWarehouseQtyBySA(lw1.getId(), inventory.getId()));
//            wi.setStatus("reserved");
//            em.persist(wi);
//            wis.add(wi);
//        }
        for (Warehouse_Inventory wi : wis) {
            em.merge(wi);
        }
        inventory.setWs_inven(wis);
        em.merge(inventory);
        return sais;
    }

//    private void updateStorageCapacity(List<StorageArea_Inventory> sais) {
//        List<StorageArea> sas = new ArrayList<>();
//        for (StorageArea_Inventory sai : sais) {
//            if (!sas.contains(sai.getSa())) {
//                sas.add(sai.getSa());
//            }
//        }
//        for (StorageArea sa : sas) {
//            Query q = em.createQuery("SELECT ")
//        }
//    }
    private void updateWarehouseCapacity(List<Warehouse_Inventory> wis) {

    }
//currently not method for confirm failed case

    @Override
    public void putAway(List<StorageArea_Inventory> sais) {
        for (StorageArea_Inventory sai : sais) {
            sai.setStatus("used");
            Warehouse_Inventory wi = sai.getWi();
            Inventory i = sai.getInventory();
            int tmp = i.getQuantity() + sai.getQty();
            i.setQuantity(tmp);
            int tmp2 = wi.getQty() + sai.getQty();
            wi.setQty(tmp2);
            em.merge(i);
            em.persist(wi);
            em.merge(sai);
        }

    }

    @Override
    public List<Inventory> reportInventories(String email) {
        return this.getAllInventories(email);
    }

    @Override
    public WMSOrder_Inventory gotRI() {
        Query q = em.createQuery("SELECT ri FROM WMSOrder_Inventory ri");
        List<WMSOrder_Inventory> ris = q.getResultList();
        return ris.get(0);
    }

    //*********************************************************HR*******************************************
    @Override
    public void generateSchedule(Long employeeId, Long facilityId, String scheduleContent, long scheduleStart, long scheduleEnd) {

        ws = new WMSSchedule();
        ws.setScheduleContent(scheduleContent);
        ws.setScheduleStart(scheduleStart);
        ws.setScheduleEnd(scheduleEnd);

        Date date = new Date(scheduleStart);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String start = df.format(date);
        date = new Date(scheduleEnd);
        String end = df.format(date);
        String schedulePeriod = start + " - " + end;

        ws.setSchedulePeriod(schedulePeriod);

        Query q = em
                .createQuery("SELECT e FROM Employee e WHERE e.id=:employeeId");
        q.setParameter("employeeId", employeeId);
        employee = (Employee) q.getSingleResult();
        q = em.createQuery("SELECT f FROM WMSFacility f WHERE f.id=:facilityId");
        q.setParameter("facilityId", facilityId);
        facility = (WMSFacility) q.getSingleResult();
        employee.setStatus("busy");
        facility.setStatus("used");
        ws.setEmployee(employee);
        ws.setFacility(facility);

        em.persist(ws);
        em.merge(employee);
        em.merge(facility);
    }

    @Override
    public List<Employee> getEmployees(Long warehouseId) {
        Query q = em.createQuery("SELECT e FROM Employee e WHERE e.WMSWarehouse.id=:warehouseId");
        q.setParameter("warehouseId", warehouseId);
        return (List<Employee>) q.getResultList();
    }

    @Override
    public List<WMSFacility> getFacilities(Long warehouseId) {
        Query q = em.createQuery("SELECT f FROM WMSFacility f WHERE f.WMSWarehouse.id=:warehouseId");
        q.setParameter("warehouseId", warehouseId);
        return (List<WMSFacility>) q.getResultList();
    }

    @Override
    @Remove
    public void remove() {
        System.out.println("wmsOrderSessionBean:remove()");
    }
}
