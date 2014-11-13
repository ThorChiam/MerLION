/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TMS.ManagedBean;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import TMS.Session.TMSOrderSession;
import TMS.Entity.TMSOrders;


/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "OMB")
@SessionScoped
public class TMSOrderManagedBean implements Serializable {

    /**
     * Creates a new instance of OrderManagedBean
     */
    @EJB
    private TMSOrderSession ordersession;
    private Long itemId;
    private double amount;
    private Timestamp orderdate;
    private Long orderId;
    private String statusMessage;
    
    private double cost;
    private TMSOrders order;
    private String time;
    private String ori;
    private String des;
    private List<TMSOrders> orders;
    private String provider="email";
   private List<TMSOrders> track;
 
    
    public TMSOrderManagedBean() {
        
    }
    @PostConstruct
    public void init(){
        getOrders();   
        getTracking();
    }
    public void getTracking(){
        track=ordersession.track(provider);
    }
    public void SaveNewOrder(){
       
       for(TMSOrders i:orders){
           ordersession.updateOrder(i);
       }
        
       statusMessage = "Orders Saved Successfully";
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
         statusMessage, ""));
    }
    
    public void DeleteOrder(TMSOrders order){
        ordersession.deleteOrder(order.getId());
        statusMessage = "deletion completed";
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
         statusMessage + " (order ID is " + order.getId() + ")", ""));
    }
    
    
   
    public Long getItemId() {
        return itemId;
    }

  

    public String getOri() {
        return ori;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }

    public List<TMSOrders> getTrack() {
        return track;
    }

    public void setTrack(List<TMSOrders> track) {
        this.track = track;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    
    public List<TMSOrders> getOrders() {
        orders=ordersession.getAllOrders(provider);
        return orders;
    }

    public void setOrders(List<TMSOrders> orders) {
        this.orders = orders;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TMSOrders getOrder() {
        return order;
    }

    public void setOrder(TMSOrders order) {
        this.order = order;
    }

   
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Timestamp orderdate) {
        this.orderdate = orderdate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

   
    
}
