/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.web.managedbean;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import merlion_ejb.ProductSessionLocal;
import merlion_ejb.entity.Product;

/**
 *
 * @author Tomato
 */
public class ProductManager {
    private ProductSessionLocal pLocal;
    
    public ProductManager(){
        
    }
    public ProductManager(ProductSessionLocal pLocal){
        this.pLocal=pLocal;
    }
    
    public List<Product> getAllProduct(){
        return pLocal.getAllProduct();
    }
    
     public List getProduct(HttpServletRequest request){
       
        String productId=request.getParameter("id");
        Long   pid=productId.equals("")? 0:Long.parseLong(productId);
        System.out.println("******************************************"+pid);
         return pLocal.getProduct(pid);
     }
     
   
  
     public Long addProduct(HttpServletRequest request){
        String name=request.getParameter("name");
        String priceStr=request.getParameter("price");
        double price=priceStr.equals("")? 0: Double.parseDouble(priceStr);
        String quantityStr=request.getParameter("quantity");
        int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
        String remark=request.getParameter("remark");
        return pLocal.addProduct(name, price, quantity, remark);
    }
    /*
    public Product getProduct(HttpServletRequest request){
        String idStr=request.getParameter("id");
        Long id=idStr.equals("")? 0: Long.parseLong(idStr);
        return pLocal.getAllProduct()
    }
    /*
    public void editProduct(HttpServletRequest request){
        String name=request.getParameter("name");
        String priceStr=request.getParameter("price");
        double price=priceStr.equals("")? 0: Double.parseDouble(priceStr);
        String quantityStr=request.getParameter("quantity");
        int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
        String lastUpdateStr=request.getParameter("lastUpdate");
        Long   lastUpdate=lastUpdateStr.equals("")? 0:Long.parseLong(lastUpdateStr);
        Product p=new Product();
        p.create(name,price,quantity,lastUpdate);
        pLocal.editProduct(p);   
    }
    */
    public String deleteProduct(HttpServletRequest request){
       String idStr=request.getParameter("id");
       Long id=idStr.equals("")? 0: Long.parseLong(idStr);
       return pLocal.deleteProduct(id);
    }
    public Long updateProduct(HttpServletRequest request){
        String pId=request.getParameter("id");
        Long productId = Long.valueOf(pId);
        
        String name=request.getParameter("name");
        String priceStr=request.getParameter("price");
        double price=priceStr.equals("")? 0: Double.parseDouble(priceStr);
        String quantityStr=request.getParameter("quantity");
        int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
        String remark=request.getParameter("remark");
        
        pLocal.updateProduct(productId, name, price, quantity, remark);
        return productId;
    }
 
}

