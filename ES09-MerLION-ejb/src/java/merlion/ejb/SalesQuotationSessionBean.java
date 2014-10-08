/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import merlion.ejb.local.SalesQuotationSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.Account;
import merlion.entity.Customer;
import merlion.entity.LineItem;
import merlion.entity.OrderRecord;
import merlion.entity.Product;
import merlion.entity.Salesquotation;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class SalesQuotationSessionBean implements SalesQuotationSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Salesquotation> getSalesquotation(String email, Long id) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        List<Salesquotation> lsq = null;
        for (int i = 0; i < q.getResultList().size(); i++) {
            Salesquotation sq = (Salesquotation) q.getResultList().get(i);
            if (sq.getId().equals(id)) {
                lsq.add(sq);
            }
        }
        return lsq;
    }

    @Override
    public Salesquotation getCustomerQuotation(String email, Long id) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        for (int i = 0; i < q.getResultList().size(); i++) {
            Salesquotation sq = (Salesquotation) q.getResultList().get(i);
            if (sq.getId().equals(id)) {
                return sq;
            }
        }
        return null;
    }

    @Override
    public List<OrderRecord> getAllrecord(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        List<OrderRecord> lor = null;
        for (int i = 0; i < q.getResultList().size(); i++) {
            OrderRecord or = (OrderRecord) q.getResultList().get(i);
            lor.add(or);
        }
        return lor;
    }

    public Customer getCustomer(String email, Long id) {
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.quotation.Account.email=:email AND c.id=:id");
        q.setParameter("id", id);
        q.setParameter("email", email);
        Customer c = new Customer();
        c = (Customer) q.getSingleResult();
        return c;
    }

    @Override
    public Long addMoreToRecord(String email, Long oid, Long pid, int quantity) {
        LineItem item = new LineItem();
        Product product = new Product();
        OrderRecord order = new OrderRecord();
        Query q1 = em.createQuery("SELECT r FROM OrderRecord r WHERE r.id=:id AND r.purchaseorder.Account.email=:email");
        q1.setParameter("id", oid);
        q1.setParameter("email", email);

        order = (OrderRecord) q1.getSingleResult();
        //st:product should be link to account?    
        Query q2 = em.createQuery("SELECT p FROM Product p WHERE p.id=:pid");
        q2.setParameter("pid", pid);
        product = (Product) q2.getSingleResult();

        double quantityd = quantity;
        double amount = Math.round((product.getPrice() * quantityd) * 100.0) / 100.0;
        item.setProduct(product);
        item.setSubtotal(amount);
        item.setProduct(product);
        item.setQuantity(quantity);
        em.persist(item);

        order.getItems().add(item);
        return order.getId();

    }

    @Override
    public Long createRecord(String title, Long id, int quantity) {
        Collection<LineItem> items = new ArrayList<LineItem>();
        LineItem item = new LineItem();
        OrderRecord order = new OrderRecord();
        Product product = new Product();
//st:product foreign key issue
        Query q = em.createQuery("SELECT p FROM Product p WHERE p.id=:id");
        q.setParameter("id", id);
        product = (Product) q.getSingleResult();

        double quantityd = quantity;
        double amount = Math.round((product.getPrice() * quantityd) * 100.0) / 100.0;

        item.setSubtotal(amount);
        item.setProduct(product);
        item.setQuantity(quantity);
        em.persist(item);

        items.add(item);

        order.setTitle(title);
        order.setItems(items);

        em.persist(order);
        return order.getId();
    }

    @Override
    public void updateQuotation(Long quotationid, String name, String email, String phone, String address, String cityRegion) {
        Query q = em.createQuery("SELECT s.customer.id FROM Salesquotation s WHERE s.id=:id AND s.Account.email=:email");
        q.setParameter("id", quotationid);
        q.setParameter("email", email);

        Long customerId = (Long) q.getSingleResult();

        q = em.createQuery("UPDATE Customer p SET p.name=?1, p.email=?2, p.phone=?3, p.address=?4, p.cityRegion=?5 WHERE p.id=?6 AND p.quotation.Account.email=?7");
        q.setParameter(1, name);
        q.setParameter(2, email);
        q.setParameter(3, phone);
        q.setParameter(4, address);
        q.setParameter(5, cityRegion);
        q.setParameter(6, customerId);
        q.setParameter(7, email);

        q.executeUpdate();

    }

    @Override
    public Long placeQuotation(String name, String email, String phone, String address, String cityRegion, Long recordId) {
        Salesquotation quotation = new Salesquotation();
        Customer customer = new Customer();
        OrderRecord order = new OrderRecord();
        customer.create(name, email, phone, address, cityRegion);
        double totalprice = 0;

        Query q = em.createQuery("SELECT r FROM OrderRecord r WHERE r.id=:id AND r.salesquotation.Account.email=:email");
        q.setParameter("id", recordId);
        q.setParameter("email", email);
        order = (OrderRecord) q.getSingleResult();
        //System.out.println("Order:"+order.getItems().size());
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());

        Iterator<LineItem> collectionline = order.getItems().iterator();

        while (collectionline.hasNext()) {

            totalprice += collectionline.next().getSubtotal();
        }

        totalprice = Math.round((totalprice) * 100.0) / 100.0;
        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);

        quotation.setConfirmationNumber(i);
        quotation.setTotal(totalprice);
        quotation.setCustomer(customer);
        quotation.setDateCreated(dateNow);
        quotation.setRecord(order);

        em.persist(quotation);
        return quotation.getId();

    }

    @Override
    public Long deleteQuotation(String email, Long id) {

        Query query = em.createQuery("SELECT s.id FROM Salesquotation s where s.id=?1 AND s.Account.email=?3");
        query.setParameter(1, id);
        query.setParameter(3, email);
        Long quotationid = (Long) query.getSingleResult();

        query = em.createQuery("Delete FROM Salesquotation s where s.id=?2 AND s.Account.email=?4");
        query.setParameter(2, id);
        query.setParameter(4, email);
        query.executeUpdate();

        return quotationid;
    }

    @Override
    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account) q.getSingleResult();
        //st:should the customer directly be linked with account?
        Salesquotation sq = new Salesquotation();
        sq.setAccount(a);
        Customer customer = new Customer();
        customer.create(name, email, phone, address, cityRegion);
        Collection<Salesquotation> sqc = null;
        sqc.add(sq);
        customer.setSalesquotation(sqc);
        em.persist(customer);
        return customer;
    }

    @Override
    public List<Salesquotation> getAllquotation(String email) {
        Query q = em.createQuery("SELECT s FROM Salesquotation s WHERE s.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public Salesquotation getQuotationDetails(String email, Long id) {
        Query q = em.createQuery("SELECT s FROM Salesquotation s WHERE s.id=:id AND s.Account.email=:email");
        q.setParameter("id", id);
        q.setParameter("email", email);
        Salesquotation s = (Salesquotation) q.getSingleResult();
        return s;
    }

    @Override
    public List<Customer> getAllcustomer(String email) {
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.quotation.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }
}
