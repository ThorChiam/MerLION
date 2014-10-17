/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.web.managedbean;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import merlion.ejb.OrderSessionBean;
import merlion.entity.MRP.Item;
import merlion.entity.MRP.MRPOrders;

/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "OrderManagerBean", eager=true)
@SessionScoped
public class OrderManagedBean implements Serializable {

    /**
     * Creates a new instance of OrderManagedBean
     */
    @EJB
    private OrderSessionBean ordersession;
    private Long itemId;
    private double amount;
    private Timestamp orderdate;
    private Long orderId;
    private String statusMessage;
    private Item item;
    private double cost;
    private MRPOrders order;
    private String time;
 
    
    public OrderManagedBean() {
        
    }
    public void SaveNewOrder(ActionEvent event){
       java.util.Date date= new java.util.Date();
       orderdate = new Timestamp(date.getTime());
       orderId = ordersession.addItemOder(itemId,amount,orderdate);
       statusMessage = "New Order Saved Successfully";
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        "Add New Order Result: " + statusMessage + " (New order ID is " + orderId + ")", ""));
    }
    public void report(ActionEvent event){
        
        item=ordersession.getItem(itemId);
        cost=amount*item.getCost();
        time=orderdate.toString();
        if(orderdate == null){
        statusMessage="new bean";}
    }

    public Long getItemId() {
        return itemId;
    }

    public Item getItem() {
        return item;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MRPOrders getOrder() {
        return order;
    }

    public void setOrder(MRPOrders order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
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
