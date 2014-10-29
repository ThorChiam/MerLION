/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.ManagedBean;

import javax.inject.Named;
import java.util.List;

import OES.Entity.Product;
import javax.enterprise.context.RequestScoped;
import OES.Session.OESSession;
import javax.ejb.EJB;


/**
 *
 * @author sunny
 */
@Named(value = "productBean")
@RequestScoped
public class ProductBean {

    @EJB
    private OESSession oes;
    
    public ProductBean() {
    }
    
    public List<Product> getProducts(String email){
        return oes.getAllProduct(email);
    }
    
}
