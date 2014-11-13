/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.ManagedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import TMS.Session.CarrierSession;
import TMS.Session.DispatchSession;
import TMS.Session.DriverSession;
import TMS.Session.TMSOrderSession;
import TMS.Session.TransportationSession;
import TMS.Entity.Carrier;
import TMS.Entity.DispatchOrder;
import TMS.Entity.Driver;
import TMS.Entity.Location;
import TMS.Entity.Route;
import TMS.Entity.TMSOrders;

/**
 *
 * @author Zeng Xunhao
 */
/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "DMB")
@ViewScoped
public class DispatchManagedBean implements Serializable {

    @EJB
    private DispatchSession DSession;
    @EJB
    private TransportationSession TSession;
    @EJB
    private CarrierSession CSession;
    @EJB
    private DriverSession HSession;
    @EJB
    private TMSOrderSession OSession;

    private List<DispatchOrder> orders;
    private DispatchOrder order = new DispatchOrder();
    
    private String userId = "email";
    private String statusMessage = "no message";

    private Long itemId;

    private Long carrierId;
    private List<Driver> drivers;
    private Carrier carrier;
    private String type;
    private List<Carrier> carriers;
    private List<String> DispatchIdlist=new ArrayList();
    private List<Long> OrderIdlist=new ArrayList();

    private String ori;
    private String des;
    private String temp;
    private List<String> path=new ArrayList();
    private Location location;
    private List<Location> locations;
     private List<List<Location>> paths= new ArrayList();

    private List<Route> routes;
    private Route route;
   
    private Timestamp orderdate;
    private List<TMSOrders> orderlist = new ArrayList();
     
            

    public DispatchManagedBean() {

    }

    @PostConstruct
    public void init() {
       
        getLocations();
        getRoutes();
                    
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void generateAllPath(){
        paths = TSession.getMyPaths(order.getOrigination(),order.getDestination() , userId);
        path = path2String(paths);
        getDrivers();  
        getCarriers();
        getTMSOrder();
        orderToId();
        
            
    }
    public void orderToId(){
        for(TMSOrders i: orderlist){           
            OrderIdlist.add(i.getId()); 
            System.out.println("i.getId");
        }
    }
    public void getTMSOrder(){
        orderlist=OSession.getAvailOrders(userId,type);
    }

    public void addNewDispatch() {
 
        java.util.Date date = new java.util.Date();      
        orderdate = new Timestamp(date.getTime());     
        order.setStartdate(orderdate);
       
        
            
        Long id = DSession.addOrder(userId, DispatchIdlist,order);
        if(id>0){
        statusMessage = "New Dispatch Order " + id + " added successfully!";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));}
        else{
            statusMessage ="Exceeds Maximum Carrier Capacity";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                statusMessage, ""));
        }
    }

    public void updateDispatch() {
        for (DispatchOrder i : orders) {
            DSession.updateOrder(i);
        }
        statusMessage = "Updates saved !";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
    }
    public List<String> path2String(List<List<Location>> locationlist){
        List<String> pts= new ArrayList();
        for(List<Location> i: locationlist){
            String s="";
            for(Location j: i){
            s=s+j.getName();
        }
            pts.add(s);
        }
        return pts;
    }
    
    public void deleteDispatch(DispatchOrder r) {
        DSession.deleteOrder(r.getId());
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<DispatchOrder> getOrders() {
        orders=DSession.getAllOrders(userId);
        return orders;
    }

    public List<String> getDispatchIdlist() {
        return DispatchIdlist;
    }

    public void setDispatchIdlist(List<String> DispatchIdlist) {
        this.DispatchIdlist = DispatchIdlist;
    }

    public void setOrders(List<DispatchOrder> orders) {
        this.orders = orders;
    }

    public String getTemp() {
        return temp;
    }

    public List<Long> getOrderIdlist() {
        return OrderIdlist;
    }

    public List<TMSOrders> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<TMSOrders> orderlist) {
        this.orderlist = orderlist;
    }

    public void setOrderIdlist(List<Long> OrderIdlist) {
        this.OrderIdlist = OrderIdlist;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public List<Route> getRoutes() {
        routes = TSession.getMyRoutes(userId);
        return routes;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Driver> getDrivers() {
        drivers=HSession.getAvailDrivers(userId,type);
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public DispatchOrder getOrder() {
        return order;
    }

    public void setOrder(DispatchOrder order) {
        this.order = order;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Location> getLocations() {
        locations = TSession.getMyLocations(userId);
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Carrier> getCarriers() {
        carriers = CSession.getAvailCarrier(userId,type);
        return carriers;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    public String getOri() {
        return ori;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
