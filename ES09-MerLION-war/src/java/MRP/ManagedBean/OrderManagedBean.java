/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRP.ManagedBean;


import MRP.Entity.Item;
import MRP.Entity.MRPOrders;
import MRP.Entity.Template;
import MRP.Session.BOMSessionBean;
import MRP.Session.OrderSessionBean;
import MRP.Session.TemplateSessionBean;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "OrderManagerBean")
@SessionScoped
public class OrderManagedBean implements Serializable {

    /**
     * Creates a new instance of OrderManagedBean
     */
    @EJB
    private OrderSessionBean ordersession;
    @EJB
    private BOMSessionBean bs; 
    @EJB
    private TemplateSessionBean ts;
    private Long itemId;
    private double amount;
    private Timestamp orderdate;
    private Long orderId;
    private String statusMessage;
    private Item item;
    private double cost;
    private MRPOrders order;
    private String time;
   private List<Item> itemList;
    private String userId;
    private List<Template> template;
 
    
    public OrderManagedBean() {
        
    }
    @PostConstruct
    public void getlogin(){
       // userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        userId="email";
        getAllitems();
    }
    public List<Item> getAllitems() {
        itemList = bs.getAllItems(userId);
        return itemList;
    }
    public void SaveNewOrder(ActionEvent event){
       java.util.Date date= new java.util.Date();
       orderdate = new Timestamp(date.getTime());
       template=getItemTemplate();
       orderId = ordersession.addItemOder(userId, itemId,template.get(3).getFirstweek(),orderdate);
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
    public List<Template> getItemTemplate(){
       return ts.getItemPlan(userId, itemId);
    }
    
    public String getUserId() {
        //userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        return userId;
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

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

   
    
}
