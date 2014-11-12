/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.ManagedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javax.ejb.EJB;
import OES.Session.OESSessionLocal;
import CI.Entity.Account;
import OES.Entity.*;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;


/**
 *
 * @author sunny
 */
@ManagedBean(name = "eb")
@ViewScoped
public class Enquiry implements Serializable {

    @EJB
    private OESSessionLocal osbl;

    //Product
    private String name;
    private String description;
    private double price;
   
    private long product_id;
    private Product product;

    //Enquiry
    private Account seller;
    private Account buyer;
    private List<Product> products;
    private List<String> desc= new ArrayList();
    private List<String> selectedNames;
    private String[] strarray;
    private String arrayToString;
    private Set<Product> selectedProducts;
    private List<String> productsN;
    private List<String> quantities= new ArrayList();
    private String today;
    private String qty;
    private Date edate;
    private String quantitiesEntered;
    private  List<OrderList> orders=new ArrayList();
    private OrderList order;
    private int psize;
    private long enquiry_id;
    private List<Account> sellers;
    private List<String> sellerName;
    private String buyerName;
    private String sellerSelected;
    private List<OES.Entity.Enquiry> enquires;
    private String statusMessage;
    private List<Product> productList;

    private List<Product> allProducts;
    
    
//**************Enquiry History Attributes********************************8
    private int enSize;
    private Long enId;
    private String enBuyer;
    private String enSeller;
    private String enDate;
    private List<OrderList> enOrders;
    private List<String> enProducts=new ArrayList();
    private List<String> enQuat= new ArrayList();
    private int j=-1;
    
    

    public Enquiry() {
    }

    @PostConstruct
    public void init() {
        // enquires=osbl.getAllEnquiry(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId").toString());
        
    }
       
    //********************Buyer-Enquiry**************************
    public String getBuyName(String email) {
        buyerName = osbl.getBuyName(email);
        java.util.Date date = new java.util.Date();
        edate = new Timestamp(date.getTime());
        
        sellerName = osbl.getALLcompany(email);
        return buyerName;
    }

    public void displaySellerProducts() {
        seller = osbl.getSellerAccount(sellerSelected);
        System.out.println("sellerName:*****************************************************" + sellerSelected);

        Long id = seller.getCompany().getId();
        products = osbl.getSellerProducts(id);
        
        
        while(j>=0 && j<products.size()){
            
          desc.add(products.get(j).getDescription());
          
          j++;
        }
        j++;
        productsN = osbl.getSellerProductsName(id);
        System.out.println("Selected Products:*****************************************************" + selectedNames);
        
        psize=selectedNames.size();
        
        String[] strarray = selectedNames.toArray(new String[0]);
        
        arrayToString= Arrays.toString(strarray);
        System.out.println("Quantities Entered*****************************************************" + arrayToString);
        quantities= Arrays.asList(quantitiesEntered.split(","));
        System.out.println("Quantities Entered*****************************************************" + quantities);
    
  }

    public String createEnquiry(String email) {
        
        Timestamp tmp = new Timestamp(edate.getTime());
        
        String createdate = tmp.toString();
        
        quantities= Arrays.asList(quantitiesEntered.split(","));
        System.out.println("Quantities Entered*****************************************************" + quantities);
       
        for(int i=0;i<psize;i++){
            orders.add(osbl.createNewOrder(quantities.get(i),selectedNames.get(i)));    
            
        }
        System.out.println("OrderList:*****************************************************" + orders);
       /*
        buyerName = osbl.getBuyName(email);
        sellerName = osbl.getALLcompany(email);
        System.out.println("sellerName:*****************************************************" + sellerName);
        */
        buyer = osbl.getBuyerAccount(email);
        seller = osbl.getSellerAccount(sellerSelected);
        
        osbl.createEnquiry(seller, buyer, orders, createdate);
        statusMessage = "A new enquiry is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        
        return statusMessage;

    }
    
    //***************************Enquir History****************************************************************
    
     public List<OES.Entity.Enquiry> getAllEnquiry(String email){
        enSize=osbl.getAllEnquiry(email).size();
        return  osbl.getAllEnquiry(email);
    }
     
     public OES.Entity.Enquiry getSingleEnquiry(Long enId){
        
         enDate=osbl.getSingleEnquiry(enId).getCreatedate();
         enOrders=osbl.getSingleEnquiry(enId).getOrder();
        
         for(int i=0;i<enOrders.size();i++){
              enProducts.add(enOrders.get(i).getProduct().getName());
              enQuat.add(enOrders.get(i).getQuantity());
         }
         
         System.out.println("enProducts:*****************************************************" + enId);
         System.out.println("enQuat:*****************************************************" + enId);
         enBuyer=osbl.getSingleEnquiry(enId).getBuyer().getCompany().getCompanyName();
         enSeller=osbl.getSingleEnquiry(enId).getSeller().getCompany().getCompanyName();
         
        
         return osbl.getSingleEnquiry(enId);
     }     
     
     
     public void deleteEnquire(Long id){
        /*
         
        for(int i = 0; i < enquires.size(); i++)
        {
            if(enquires.get(i).getId().equals(id))
            {
                 enquires.remove(i);
                break;
            }
        }
                */
            
        osbl.deleteEnquire(id);
        System.out.println("THIS IS Enquire ID**************************************************88: " + id);
    
     }
    //******************GETTER AND SETTER***********************************************************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
    }

    public String getName() {
        //name = productList.get(0).getName();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQuantitiesEntered() {
        return quantitiesEntered;
    }

    public void setQuantitiesEntered(String quantitiesEntered) {
        this.quantitiesEntered = quantitiesEntered;
    }

   
   
    public long getProduct_id() {
        product_id = productList.get(0).getId();
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<String> quantities) {
        this.quantities = quantities;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public long getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(long enquiry_id) {
        this.enquiry_id = enquiry_id;
    }

 
 

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public List<Account> getSellers() {
        return sellers;
    }

    public void setSellers(List<Account> sellers) {
        this.sellers = sellers;
    }

   
    public List<String> getSellerName() {
        return sellerName;
    }

    public void setSellerName(List<String> sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSellerSelected() {
        return sellerSelected;
    }

    public void setSellerSelected(String sellerSelected) {
        this.sellerSelected = sellerSelected;
    }

    public List<String> getProductsN() {
        return productsN;
    }

    public void setProductsN(List<String> productsN) {
        this.productsN = productsN;
    }

    public Set<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(Set<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

   
    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public List<String> getSelectedNames() {
        return selectedNames;
    }

    public void setSelectedNames(List<String> selectedNames) {
        this.selectedNames = selectedNames;
    }

   

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public List<OrderList> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderList> orders) {
        this.orders = orders;
    }

    public int getPsize() {
        return psize;
    }

    public void setPsize(int psize) {
        this.psize = psize;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String[] getStrarray() {
        return strarray;
    }

    public void setStrarray(String[] strarray) {
        this.strarray = strarray;
    }

    public String getArrayToString() {
        return arrayToString;
    }

    public void setArrayToString(String arrayToString) {
        this.arrayToString = arrayToString;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public List<OES.Entity.Enquiry> getEnquires() {
        return enquires;
    }

    public void setEnquires(List<OES.Entity.Enquiry> enquires) {
        this.enquires = enquires;
    }

   

    public int getEnSize() {
        return enSize;
    }

    public void setEnSize(int enSize) {
        this.enSize = enSize;
    }

    public Long getEnId() {
        return enId;
    }

    public void setEnId(Long enId) {
        this.enId = enId;
    }

    public String getEnDate() {
        return enDate;
    }

    public void setEnDate(String enDate) {
        this.enDate = enDate;
    }

    public List<OrderList> getEnOrders() {
        return enOrders;
    }

    public void setEnOrders(List<OrderList> enOrders) {
        this.enOrders = enOrders;
    }

    public String getEnBuyer() {
        return enBuyer;
    }

    public void setEnBuyer(String enBuyer) {
        this.enBuyer = enBuyer;
    }

    public String getEnSeller() {
        return enSeller;
    }

    public void setEnSeller(String enSeller) {
        this.enSeller = enSeller;
    }

    public List<String> getEnProducts() {
        return enProducts;
    }

    public void setEnProducts(List<String> enProducts) {
        this.enProducts = enProducts;
    }

    public List<String> getEnQuat() {
        return enQuat;
    }

    public void setEnQuat(List<String> enQuat) {
        this.enQuat = enQuat;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

 

  

}

