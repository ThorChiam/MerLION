/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.ManagedBean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import OES.Session.OESSessionLocal;
import OES.Entity.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author tomato
 */
@ManagedBean(name = "quo")
@ViewScoped
public class QuotationManagedBean implements Serializable {

    @EJB
    private OESSessionLocal osbl;

    //Quotation: view Enquire
    private int enSize;
    private OES.Entity.Enquiry enquire;
    private Long enId;
    private List<OrderList> orders = new ArrayList();
    private List<String> pnames = new ArrayList();
    private List<String> pquant = new ArrayList();
    private int orderSize;

    //Quotation: create NEW Quotation
    private List<Long> enquireID = new ArrayList();
    private Long selectedEID;
    private String sellerN;
    private String sellerA;
    private String buyerN;
    private String buyerA;
    private String email;
    
    //Quotation: calculate price
    private Product product;
    private double total;
    private List<Product> products= new ArrayList();
    private List<Double> subtotal=new ArrayList();
    private Date quodate;
    private String message;
    
    //Quotation: history
    private int quoSize;
    private long quoId;
    private int quoDetailSize;
    private double quoDetailTotal;
    private List<OrderListSub> list =new ArrayList();
    

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        if (email != null) {
            this.viewUpcomingEnquire();
        }
    }

    //*****************Quotation-view upcoming Enquire**********************************************************
    public List<Quotation> viewQuotaionHistory(){
        quoSize=osbl.viewQuotaionHistory(email).size();
        return osbl.viewQuotaionHistory(email);
    }
    
    
    
    public List<OES.Entity.Enquiry> viewUpcomingEnquire() {
        enSize = osbl.viewUpcomingEnquire(email).size();
         
        
        for (int i = 0; i < enSize; i++) {
            if(osbl.viewUpcomingEnquire(email).get(i).getQuotation()==null){
            enquireID.add(osbl.viewUpcomingEnquire(email).get(i).getId());
            }
        }
        return osbl.viewUpcomingEnquire(email);
    }

    public List<OrderList> viewEnquireDetails(Long id) {

        System.out.println("id:*****************************************************" + id);
        // enquire=osbl.viewEnquireDetails(id); 
        orderSize = osbl.getOrderDetails(id).size();

        return osbl.getOrderDetails(id);

    }
    
    public List<OrderListSub> viewQuotationDetails(Long id){
        System.out.println("viewQuotationDetails:********************************************************************************" + id);
        list=osbl.displayQuotationDetails(id);
        quoDetailSize=osbl.displayQuotationDetails(id).size();
        quoDetailTotal=osbl.getQuotation(id).getTotal();
        System.out.println("quoDetailSize:********************************************************************************" + quoDetailSize);
        return osbl.displayQuotationDetails(id);
    }

    //**********************************Create New Quotation****************************************************
    public void displaySellerBuyerNameAddress(ActionEvent event) {
        java.util.Date date = new java.util.Date();
        quodate = new Timestamp(date.getTime());
        
        
        System.out.println("selectedEID:********************************************************************************" + selectedEID);

        sellerN = osbl.viewEnquireDetails(selectedEID).getSeller().getCompany().getCompanyName();
        sellerA = osbl.viewEnquireDetails(selectedEID).getSeller().getCompany().getCompanyAddress();

        buyerN = osbl.viewEnquireDetails(selectedEID).getBuyer().getCompany().getCompanyName();
        buyerA = osbl.viewEnquireDetails(selectedEID).getBuyer().getCompany().getCompanyAddress();
    }
    
    public List<OrderListSub> displayOrderWithPrice(Long id){
        System.out.println("calculateTotalPrice-ID:********************************************************************************" + id);
        
        if(id!=null){
             total=0;
             for(int i=0;i<osbl.createSubOrderList(id).size();i++){
                 total+=osbl.createSubOrderList(id).get(i).getSubtotal();
                 System.out.println("total-sub:********************************************************************************"+i+"***"+ total);
                 
             }
        
        return osbl.createSubOrderList(id);
        }
        System.out.println("calculateTotal---total:********************************************************************************" + total);
        return null;
        
    }
    
    public void createNewQuotation(ActionEvent Event){
        Timestamp tmp = new Timestamp(quodate.getTime());
        String createdate = tmp.toString();
       osbl.createNewQuotation(selectedEID, total,createdate);
     
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful!", "A New Quotation is Created!.");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
        
    //*******************************Delete Quotation************************************************************
    
    public void deleteQuotation(long id){
        osbl.deleteQuotation(id);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful!", "Quotation has been deleted!.");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        
    }
    
    
    
    
    //******************GETTER AND SETTER***********************************************************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
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

    public List<OrderList> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderList> orders) {
        this.orders = orders;
    }

    public List<String> getPnames() {
        return pnames;
    }

    public void setPnames(List<String> pnames) {
        this.pnames = pnames;
    }

    public List<String> getPquant() {
        return pquant;
    }

    public void setPquant(List<String> pquant) {
        this.pquant = pquant;
    }

    public OES.Entity.Enquiry getEnquire() {
        return enquire;
    }

    public void setEnquire(OES.Entity.Enquiry enquire) {
        this.enquire = enquire;
    }

    public int getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(int orderSize) {
        this.orderSize = orderSize;
    }

    public List<Long> getEnquireID() {
        return enquireID;
    }

    public void setEnquireID(List<Long> enquireID) {
        this.enquireID = enquireID;
    }

    public Long getSelectedEID() {
        return selectedEID;
    }

    public void setSelectedEID(Long selectedEID) {
        this.selectedEID = selectedEID;
    }

    public String getSellerN() {
        return sellerN;
    }

    public void setSellerN(String sellerN) {
        this.sellerN = sellerN;
    }

    public String getSellerA() {
        return sellerA;
    }

    public void setSellerA(String sellerA) {
        this.sellerA = sellerA;
    }

    public String getBuyerN() {
        return buyerN;
    }

    public void setBuyerN(String buyerN) {
        this.buyerN = buyerN;
    }

    public String getBuyerA() {
        return buyerA;
    }

    public void setBuyerA(String buyerA) {
        this.buyerA = buyerA;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Double> getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(List<Double> subtotal) {
        this.subtotal = subtotal;
    }

    public Date getQuodate() {
        return quodate;
    }

    public void setQuodate(Date quodate) {
        this.quodate = quodate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getQuoSize() {
        return quoSize;
    }

    public void setQuoSize(int quoSize) {
        this.quoSize = quoSize;
    }

    public long getQuoId() {
        return quoId;
    }

    public void setQuoId(long quoId) {
        this.quoId = quoId;
    }

    public int getQuoDetailSize() {
        return quoDetailSize;
    }

    public void setQuoDetailSize(int quoDetailSize) {
        this.quoDetailSize = quoDetailSize;
    }

    public double getQuoDetailTotal() {
        return quoDetailTotal;
    }

    public void setQuoDetailTotal(double quoDetailTotal) {
        this.quoDetailTotal = quoDetailTotal;
    }

    public List<OrderListSub> getList() {
        return list;
    }

    public void setList(List<OrderListSub> list) {
        this.list = list;
    }

}
