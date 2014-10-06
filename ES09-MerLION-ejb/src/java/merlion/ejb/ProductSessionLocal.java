/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import java.util.List;
import javax.ejb.Local;
import merlion_ejb.entity.Product;

/**
 *
 * @author Tomato
 */
@Local
public interface ProductSessionLocal {
   
   public List<Product> getAllProduct();
   public List<Product>  getProduct(Long id);
  
  
   public Long addProduct(String name, double price, int quantity, String remark);
   public String deleteProduct(Long id);
   public void updateProduct(Long id, String name, double price, int quantity,String remark);
}

