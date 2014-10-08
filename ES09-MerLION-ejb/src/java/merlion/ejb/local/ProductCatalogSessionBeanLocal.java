/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;

import java.util.List;
import javax.ejb.Local;
import merlion.entity.Product;

/**
 *
 * @author ThorChiam
 */
@Local
public interface ProductCatalogSessionBeanLocal {

    public List<Product> getAllProduct(String email);

    public List<Product> getProduct(String email, Long id);

    public Long addProduct(String email, String name, double price, int quantity, String remark);

    public String deleteProduct(String email, Long id);

    public void updateProduct(String email, Long id, String name, double price, int quantity, String remark);

}
