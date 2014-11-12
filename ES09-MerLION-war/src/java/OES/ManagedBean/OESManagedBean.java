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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author sunny
 */

@ManagedBean(name = "omb")
@ViewScoped
public class OESManagedBean implements Serializable {

    @EJB
    private OESSessionLocal osbl;

    //Product
    private String name;
    private String description;
    private double price;
    private int quantity;
    private long product_id;
    private Product product;
    private int proSize;

    //Enquiry
    private Account seller;
    private Account buyer;
    private List<Product> products;
    private List<String> selectedNames;
    private Set<Product> selectedProducts;
    private List<String> productsN;
    private List<String> quantities = new ArrayList();
    private List<String> quantitiesEntered = new ArrayList();
    private String today;
    private String qty;
    
    private List<OrderList> orders=new ArrayList();
    private OrderList order;

    private long enquiry_id;
    private String delete_status_enquiry;
    private List<Account> sellers;
    private List<String> sellerName;
    private String buyerName;
    private String sellerSelected;
    private int n=0;

    //Quotation
    private Enquiry enquiry;
    private List<String> delivery_date;
    private long quotation_id;
    private String delete_status_quotation;
    private Quotation quotation;
    private Date edate;

    //PurchaseOrder
    private double taxrate;
    private long purchase_id;
    private String deliverydate;
    private String delete_status_purchase;

  

    private String statusMessage;
    private List<Product> productList;

    private List<Product> allProducts;

    public OESManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        allProducts = osbl.getAllProduct(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId").toString());
        proSize=allProducts.size();
    }
      
  
    
    
    //********************Product********************************
    public void createProduct(String email) {

        boolean tmp = osbl.check_redundant(email, name);
        if (tmp) {
            statusMessage = "The new product is successfully added.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Status: " + statusMessage, ""));
            osbl.createProduct(email, name, price, quantity, description);
        } else {
            statusMessage = "This product has already be added.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Status: " + statusMessage, ""));
        }
    }

    public void updateProduct(Long product_id, String name, double price, int quantity, String description) {

        osbl.updateProduct(product_id, name, price, quantity, description);
    }

    public void onCellEdit(CellEditEvent event) {
        System.err.println("Selected row: " + allProducts.get(event.getRowIndex()).getId());
        updateProduct(allProducts.get(event.getRowIndex()).getId(), allProducts.get(event.getRowIndex()).getName(), allProducts.get(event.getRowIndex()).getPrice(), allProducts.get(event.getRowIndex()).getQuantity(), allProducts.get(event.getRowIndex()).getDescription());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    
    }
    


    public Product getProduct() {
        product = osbl.getProduct(product_id);
        return product;
    }

    public void passValue(Long value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pid", value);
    }

    public List<Product> getAllProduct(String email) {
        return osbl.getAllProduct(email);
    }

    public void deleteProduct(Long id) {
        
        for(int i = 0; i <= allProducts.size(); i++)
        {
            if(allProducts.get(i).getId().equals(id))
            {
                allProducts.remove(i);
                break;
            }
        }
            
        osbl.deleteProduct(id);
        System.out.println("THIS IS PRODUCT ID: " + id);
    }

    //********************Buyer-Enquiry**************************
    public String getBuyName(String email) {
        buyerName = osbl.getBuyName(email);

        java.util.Date date = new java.util.Date();
        edate = new Timestamp(date.getTime());
        sellerName = osbl.getALLcompany(email);

        /* String name=getSellerSelected();
         seller = osbl.getSellerAccount(sellerSelected);
         products = osbl.getSellerProducts(seller);
         */
        return buyerName;
    }

    public void displaySellerProducts() {
        seller = osbl.getSellerAccount(sellerSelected);
        System.out.println("sellerName:*****************************************************" + sellerSelected);

        Long id = seller.getCompany().getId();
        products = osbl.getSellerProducts(id);
        productsN = osbl.getSellerProductsName(id);
        System.out.println("Selected Products:*****************************************************" + selectedNames);
        
        int size=selectedNames.size();
        //quantities.add(qty);
        for(int i=0;i<size;i++){
            orders.add(osbl.createNewOrder("0",selectedNames.get(i)));
        }
        
        
    }

    public void addToQuantities(ActionEvent event) {
       
        System.out.println("Selected Products Quantities 1:*****************************************************" + qty);
        OrderList ol=new OrderList();
        Product p=new Product();
        //p=osbl.getProductByName(selectedNames.get(n));
        System.out.println("Selected Products Names*****************************************************" + selectedNames.get(n));
        
       
       
        System.out.println("Selected Products Quantities 2:*****************************************************" + qty);
        
    }

    public void createEnquiry(String email) {
        //edate = new java.util.Date();
        Timestamp tmp = new Timestamp(edate.getTime());
        buyerName = osbl.getBuyName(email);
        sellerName = osbl.getALLcompany(email);
        System.out.println("sellerName:*****************************************************" + sellerName);
        String createdate = tmp.toString();
        buyer = osbl.getBuyerAccount(email);
        seller = osbl.getSellerAccount(sellerSelected);
        
        products = osbl.getSellerProducts(seller.getCompany().getId());
        
        
        
        //osbl.createEnquiry(seller, buyer, selectedProducts, quantities, createdate);
        statusMessage = "A new enquiry is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));

    }
  /*
    public Enquiry getEnquiry() {
        enquiry = osbl.getEnquiry(enquiry_id);
        return enquiry;
    }

    public List<Enquiry> getAllEnquiry(String email) {
        return osbl.getAllEnquiry(email);
    }
   

    public void deleteEnquiry(String email) {
        osbl.deleteEnquiry(enquiry_id, email);
    }
*/
    //********************Seller-Quotation***********************
    /*public List<String> ATPcheck(Enquiry enquiry) {
     return osbl.ATPcheck(enquiry);
     }
     
    public void createQuotation() {
        statusMessage = "A new quotation is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createQuotation(enquiry, delivery_date, createdate);
    }
    

    public Quotation getQuotation() {
        quotation = osbl.getQuotation(quotation_id);
        return quotation;
    }


    //********************Buyer-Purchase Order*******************
   
     public void createPurchaseOrder() {
     statusMessage = "A new purchase order is successfully created.";
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
     "Status: " + statusMessage, ""));
     Date date = new java.util.Date();
     Timestamp tmp = new Timestamp(date.getTime());
     String createdate = tmp.toString();
     osbl.createPurchaseOrder(buyer, seller, taxrate, quantitys, products, createdate, deliverydate);
     }
     
    public PurchaseOrder getPurchaseOrder() {
        purchaseorder = osbl.getPurchaseOrder(purchase_id);
        return purchaseorder;
    }

    public List<PurchaseOrder> getAllPurchaseOrder(String email) {//两边都能拿？？？？
        return osbl.getAllPurchaseOrder(email);
    }

    public void deletePurchaseOrder(String email) {
        osbl.deletePurchaseOrder(purchase_id, email);
    }

    //********************Seller-Sales Order*********************
    public void createSalesOrder() {
        statusMessage = "A new sales order is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        osbl.createSalesOrder(purchaseorder, createdate);
    }

    public SalesOrder getSalesOrder() {
        salesorder = osbl.getSalesOrder(sales_id);
        return salesorder;
    }

    public List<SalesOrder> getAllSalesOrder(String email) {
        return osbl.getAllSalesOrder(email);
    }

    public void deleteSalesOrder() {//只能seller删，不传给buyer
        osbl.deleteSalesOrder(sales_id);
    }

    //********************Buyer-Payment**************************
    public void createPayment() {
     statusMessage = "Please finish your payment soon. Thanks!";
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
     "Status: " + statusMessage, ""));
     osbl.createPayment(paymentdate, paymenttype, status, purchaseorder);
     }
     
    public void updatePaymentStatus() {
        osbl.updatePaymentStatus(payment_id, status);
    }

    public OES_Payment getPayment() {
        payment = osbl.getPayment(payment_id);
        return payment;
    }

    public List<OES_Payment> getAllPayment(String email) {
        return osbl.getAllPayment(email);
    }

    public void deletePayment(String email) {
        osbl.deletePayment(payment_id, email);
    }

    //********************Seller-Invoice*************************
    public void createInvoice() {
        statusMessage = "A new invoice is successfully created.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String releasedate = tmp.toString();
        osbl.createInvoice(releasedate, notes, payment);
    }

    public OES_Invoice getInvoice() {
        invoice = osbl.getInvoice(invoice_id);
        return invoice;
    }

    public List<OES_Invoice> getAllInvoice(String email) {
        return osbl.getAllInvoice(email);
    }

    public void deleteInvoice(String email) {
        osbl.deleteInvoice(invoice_id, email);
    }
*/
    //******************GETTER AND SETTER***************************
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        //quantityList.add();
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

    public List<String> getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(List<String> delivery_date) {
        this.delivery_date = delivery_date;
    }

    public long getQuotation_id() {
        return quotation_id;
    }

    public void setQuotation_id(long quotation_id) {
        this.quotation_id = quotation_id;
    }

    public double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(double taxrate) {
        this.taxrate = taxrate;
    }

    public long getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(long purchase_id) {
        this.purchase_id = purchase_id;
    }


    

    public String getDelete_status_enquiry() {
        return delete_status_enquiry;
    }

    public void setDelete_status_enquiry(String delete_status_enquiry) {
        this.delete_status_enquiry = delete_status_enquiry;
    }

    public String getDelete_status_quotation() {
        return delete_status_quotation;
    }

    public void setDelete_status_quotation(String delete_status_quotation) {
        this.delete_status_quotation = delete_status_quotation;
    }

    public String getDelete_status_purchase() {
        return delete_status_purchase;
    }

    public void setDelete_status_purchase(String delete_status_purchase) {
        this.delete_status_purchase = delete_status_purchase;
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

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
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

    public static class prod {

        String name;
        Integer quantity;

        public prod(String name, Integer quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

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

    public List<String> getQuantitiesEntered() {
        return quantitiesEntered;
    }

    public void setQuantitiesEntered(List<String> quantitiesEntered) {
        this.quantitiesEntered = quantitiesEntered;
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

    public int getProSize() {
        return proSize;
    }

    public void setProSize(int proSize) {
        this.proSize = proSize;
    }

    public OrderList getOrder() {
        return order;
    }

    public void setOrder(OrderList order) {
        this.order = order;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public OES.ManagedBean.Enquiry getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(OES.ManagedBean.Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
