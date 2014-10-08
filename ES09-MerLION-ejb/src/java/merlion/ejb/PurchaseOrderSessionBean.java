/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import merlion.ejb.local.PurchaseOrderSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.LineItem;
import merlion.entity.OrderRecord;
import merlion.entity.PurchaseOrder;
import merlion.entity.Vendor;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class PurchaseOrderSessionBean implements PurchaseOrderSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PurchaseOrder> getPurchaseOrder(String email) {
        Query q = em.createQuery("SELECT p FROM PurchaseOrder p WHERE p.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    //st:is this create new or update?   

    @Override
    public Long placePurchaseOrder(String name, String email, String phone, String address, String cityRegion,
            String shippingdate, String paymentTerm, String currency, String shippingTerm, double taxRate, double discount, Long recordId) {

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        Vendor vendor = new Vendor();
        OrderRecord order = new OrderRecord();

        vendor.create(name, email, phone, address, cityRegion);

        double totalprice = 0;

        Query q = em.createQuery("SELECT r FROM OrderRecord r WHERE r.id=:id AND r.purchaseorder.Account.email=:email");
        q.setParameter("id", recordId);
        q.setParameter("email", email);
        order = (OrderRecord) q.getSingleResult();

        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());

        Iterator<LineItem> collectionline = order.getItems().iterator();

        while (collectionline.hasNext()) {

            totalprice += collectionline.next().getSubtotal();

        }

        purchaseOrder.setVendor(vendor);
        purchaseOrder.setCurrency(currency);
        purchaseOrder.setDateCreated(dateNow);
        purchaseOrder.setDiscount(discount);
        purchaseOrder.setPaymentTerm(paymentTerm);
        purchaseOrder.setShippingTerm(shippingTerm);
        purchaseOrder.setShippingdate(shippingdate);
        purchaseOrder.setTaxRate(taxRate);
        purchaseOrder.setRecord(order);

        double totalprice1 = (1 + taxRate) * totalprice;
        double totalprice2 = (totalprice1) * discount;
        double totalprice3 = Math.round((totalprice2) * 100.0) / 100.0;
        purchaseOrder.setTotal(totalprice3);
        purchaseOrder.setRecord(order);

        em.persist(purchaseOrder);
        return purchaseOrder.getId();

    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderDetails(String email, Long id) {
        Query q = em.createQuery("SELECT p FROM PurchaseOrder p WHERE p.id=:id AND p.Account.email=:email");
        q.setParameter("id", id);
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public void updatePurchaseOrder(Long porderid, String name, String email, String phone, String address, String cityRegion) {

        Query q = em.createQuery("SELECT p.vendor.id FROM PurchaseOrder p WHERE p.id=:id AND p.Account.email=:email");
        q.setParameter("id", porderid);
        q.setParameter("email", email);
        Long vendorId = (Long) q.getSingleResult();
        q = em.createQuery("UPDATE Vendor p SET p.name=?1, p.email=?2, p.phone=?3, p.address=?4, p.cityRegion=?5 WHERE p.id=?6");
        q.setParameter(1, name);
        q.setParameter(2, email);
        q.setParameter(3, phone);
        q.setParameter(4, address);
        q.setParameter(5, cityRegion);
        q.setParameter(6, vendorId);

        q.executeUpdate();

    }

    @Override
    public PurchaseOrder getPOrderDetails(String email, Long id) {
        Query q = em.createQuery("SELECT p FROM PurchaseOrder p WHERE p.id=:id AND p.Account.email=:email");
        q.setParameter("id", id);
        q.setParameter("email", email);
        PurchaseOrder p = (PurchaseOrder) q.getSingleResult();
        return p;
    }

    @Override
    public void deletePurchaseOrder(String email, Long id) {

        Query query = em.createQuery("Delete FROM PurchaseOrder e where e.id=?2 AND e.Account.email=?3");
        query.setParameter(2, id);
        query.setParameter(3, email);
        query.executeUpdate();
    }
}
