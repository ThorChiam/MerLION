/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.OESSessionLocal;
import merlion.entity.CommonInfrastructure.Account;
import merlion.entity.OES.OES_Enquiry;
import merlion.entity.OES.OES_Invoice;
import merlion.entity.OES.OES_Payment;
import merlion.entity.OES.OES_Product;
import merlion.entity.OES.OES_PurchaseOrder;
import merlion.entity.OES.OES_Quotation;
import merlion.entity.OES.OES_SalesOrder;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "oESManagedBean")
@SessionScoped
public class OESManagedBean implements Serializable{

    @EJB
    private OESSessionLocal osbl;
    
    //Product
    String email;
    String name; 
    String description;
    double price;
    int quantity;
    long product_id;
    
    //Enquiry
    Account seller;
    Account buyer;
    Set<OES_Product> products;
    List<Integer> quantitys=new ArrayList();
    long enquiry_id;
    
    //Quotation
    OES_Enquiry enquiry;
    List<String> delivery_date;
    
    
    
    
    public OESManagedBean(){
    }
    
    //********************Product********************************
    public void createProduct(){
        osbl.createProduct(email,name,price,quantity,description);
    }
    
    public void updateProduct(){
        osbl.updateProduct(product_id,name,price,quantity,description);
    }
    
    public OES_Product getProduct(){
        return osbl.getProduct(product_id);
    }
    
    public List<OES_Product> getAllProduct(){
        return osbl.getAllProduct(email);
    }
    
    public void deleteProduct(){
        osbl.deleteProduct(product_id);
    }
    
    
    
    
    //********************Buyer-Enquiry**************************
    public void createEnquiry(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate=tmp.toString();
        osbl.createEnquiry(seller,buyer,products,quantitys,createdate);
    }
    
    public OES_Enquiry getEnquiry(){
        return osbl.getEnquiry(enquiry_id);
    }
    
    public List<OES_Enquiry> getAllEnquiry(){
        return osbl.getAllEnquiry(email);
    }
    
    public void deleteEnquiry(){
        osbl.deleteEnquiry(enquiry_id);
    }
    
    //********************Seller-Quotation***********************
    public List<String> ATPcheck(OES_Enquiry enquiry){
        return osbl.ATPcheck(enquiry);
    }
    
    public void createQuotation(){
        Date date= new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate=tmp.toString();
        osbl.createQuotation(enquiry, delivery_date, createdate);
    }
    
    public OES_Quotation getQuotation(long quotation_id){
        return osbl.getQuotation(quotation_id);
    }
     
    public List<OES_Quotation> getAllQuotation(String email){//两边都能拿？？？？
        return osbl.getAllQuotation(email);
    }
    
    public void deleteQuotation(){//只能buyer删
        osbl.deleteQuotation(product_id);
    }
    
    
    //********************Buyer-Purchase Order*******************
    //public void createPurchaseOrder(Account buyer, Account seller, double taxrate, List<Integer> quantity, Set<OES_Product> product, String createdate);
    //public OES_PurchaseOrder getPurchaseOrder(long purchase_id);
    //public List<OES_PurchaseOrder> getAllPurchaseOrder(String email);//两边都能拿？？？？
    //public void deletePurchaseOrder(long purchase_id);//只能buyer删
}  
