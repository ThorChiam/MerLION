/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.ProductCatalogSessionBeanLocal;
import merlion.entity.Product;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "productCatalogManagedBean")
@SessionScoped
public class ProductCatalogManagedBean implements Serializable {

    /**
     * Creates a new instance of ProductCatalogManagedBean
     */
    private ProductCatalogSessionBeanLocal pcsbl;
    private String name;
    private double price;
    private int quantity;
    private String remark;
    public ProductCatalogManagedBean() {
    }
    public List<Product> getAllProduct(String email){
        return pcsbl.getAllProduct(email);
    }

    public List<Product> getProduct(String email, Long id){
        return pcsbl.getProduct(email, id);
    }
    public Long addProduct(String email){
        return pcsbl.addProduct(email, name, price, quantity, remark);
    }
    public String deleteProduct(String email, Long id){
        return pcsbl.deleteProduct(email, id);
    }
    public void updateProduct(String email, Long id){
        pcsbl.updateProduct(email, id, name, price, quantity, remark);
    }
}
