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
import merlion.entity.Product;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class ProductCatalogSessionBean implements ProductCatalogSessionBeanLocal {
    
  @PersistenceContext
  private EntityManager em;
 
  @Override
   public List<Product> getAllProduct(String email){
       Query q=em.createQuery("SELECT p FROM Product WHERE p.email=:email");
        q.setParameter("email", email);
      return q.getResultList();
   }
   
  
       
   
   @Override
   public List<Product>  getProduct(String emailm,Long id){
       Query q=em.createQuery("SELECT p FROM Product p WHERE p.id=:id");
       q.setParameter("id",id);
       
       System.out.println("SESSION BEAN***********************id is :"+id);
       System.out.println(q.getResultList());
       
       return q.getResultList();
   }

   
   @Override
   public Long addProduct(String name, double price, int quantity,String remark){
     Product p=new Product(email);
     p.create(name,price,quantity,remark);
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
   public String deleteProduct(Long id){
       
        Query query = em.createQuery("SELECT e.name FROM Product e where e.id=?1");
        query.setParameter(1, id);
        String name = (String)query.getSingleResult();
        
        query = em.createQuery("Delete FROM Product e where e.id=?2");
        query.setParameter(2, id);
        query.executeUpdate();
        
        return name; 
   
   }
   
   @Override
   public void updateProduct(Long id,String name, double price, int quantity,String remark){
   
        Query q = em.createQuery("UPDATE Product p SET p.name=?1, p.price=?2, p.quantity=?3, p.remark=?4 WHERE p.id=?5");
        q.setParameter(1, name);
        q.setParameter(2, price);
        q.setParameter(3, quantity);
        q.setParameter(4, remark);
        q.setParameter(5, id);

        q.executeUpdate();
       
   }
  
}
