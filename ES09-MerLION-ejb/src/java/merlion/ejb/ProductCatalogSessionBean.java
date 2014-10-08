/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.util.List;
import merlion.ejb.local.ProductCatalogSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.LineItem;
import merlion.entity.Product;
import merlion.entity.Salesquotation;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class ProductCatalogSessionBean implements ProductCatalogSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    //st:is this "all products" refer to all products the customer has?
    @Override
    public List<Product> getAllProduct(String email) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        List<Product> lp = null;
        for (int i = 0; i < q.getResultList().size(); i++) {
            Salesquotation sq = (Salesquotation) q.getResultList().get(i);
            for (LineItem li : sq.getRecord().getItems()) {
                Product p = li.getProduct();
                lp.add(p);
            }
        }
        return lp;
    }

    @Override
    public List<Product> getProduct(String email, Long id) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        List<Product> lp = null;
        for (int i = 0; i < q.getResultList().size(); i++) {
            Salesquotation sq = (Salesquotation) q.getResultList().get(i);
            for (LineItem li : sq.getRecord().getItems()) {
                Product p = li.getProduct();
                if (p.getId().equals(id)) {
                    lp.add(p);
                }
            }
        }
        return lp;
    }
    @Override
    public Long addProduct(String email, String name, double price, int quantity, String remark
    ) {
        //st:should product directly be linked to account?
        Product p = new Product();
        p.create(name, price, quantity, remark);
        em.persist(p);
        em.flush();
        return p.getId();
    }
    /*
     @Override
     public void editProduct(Product product){
     em.merge(product);
       
     }
     */

    @Override
    public String deleteProduct(String email, Long id) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Product p = null;
        for (int i = 0; i < q.getResultList().size(); i++) {
            Salesquotation sq = (Salesquotation) q.getResultList().get(i);
            for (LineItem li : sq.getRecord().getItems()) {
                if (li.getProduct().getId().equals(id)) {
                    p = li.getProduct();
                }
            }
        }
        q = em.createQuery("Delete FROM Product e where e.id=?2");
        q.setParameter(2, id);
        q.executeUpdate();
        return p.getName();
    }

    @Override
    public void updateProduct(String email,Long id, String name, double price, int quantity, String remark) {

        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Product p = null;
        for (int i = 0; i < q.getResultList().size(); i++) {
            Salesquotation sq = (Salesquotation) q.getResultList().get(i);
            for (LineItem li : sq.getRecord().getItems()) {
                if (li.getProduct().getId().equals(id)) {
                    p = li.getProduct();
                }
            }
        }
        p.setName(name);
        p.setPrice(price);
        p.setQuantity(quantity);
        p.setRemark(remark);
        em.merge(p);
    }

}
