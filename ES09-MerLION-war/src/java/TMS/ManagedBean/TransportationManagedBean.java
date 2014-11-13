/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TMS.ManagedBean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import TMS.Session.TransportationSession;
import TMS.Entity.Location;
import TMS.Entity.Route;


/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "TMB")
@ViewScoped
public class TransportationManagedBean implements Serializable {
    
    @EJB
    private TransportationSession Tsession;
    
    private String s1;
    private String s2;   
    private List<Location> locations= new ArrayList<Location>();
    private List<Route> routes=new ArrayList();
    private List<List<Location>> paths= new ArrayList();
    private List<String> pts=new ArrayList<String>();
    private List<Temp> services=new ArrayList();
    private List<List<Route>> last= new ArrayList();
    
    private String userId="email";
    

    private String statusMessage;
    /**
     * Creates a new instance of BOMManagedBean
     */
    
    public TransportationManagedBean() {
       
    }
    @PostConstruct
    public void init(){
        locations=Tsession.getMyLocations(userId);
        routes=Tsession.getMyRoutes(userId);
    }
    
    public void optimize(){
        pts.clear();
        services.clear();
        paths=Tsession.getMyPaths(s1,s2 , userId);
        path2String();
        path2Routes();
        last2Temp();
        if(paths==null){
            statusMessage="no path found";
        }else{
            statusMessage= "successful, total path: "+services.size();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
    }
    public void last2Temp(){
        
        
        for(List<Route> i: last){
            double cost=0;
            double time=0;
            Temp serv=new Temp();
            serv.setPath(i);
            for(Route j: i){
                cost=cost+j.getCost();
                time=time+j.getDistance();
            }
            serv.setCost(cost);
            serv.setTime(time);
            services.add(serv);
        }
        for(int j=0;j<services.size();j++){
            services.get(j).setPts(pts.get(j));
        }
    }
    public void path2Routes(){
        //for each path, we try to find all the possible edge weights, then we aggregate all. 
        for(List<Location> i: paths){
            int length=i.size();
            Route temp=new Route();
            List<Route> total= new ArrayList();
            for(int j=0;j<length-1;j++){
               temp= Loc2route(i.get(j),i.get(j+1));
               total.add(temp);
            }
            
            last.add(total);          
        }  
    }

    private Route Loc2route(Location a, Location b){
        
        for(Route i : routes){
            if(i.getLocation1().equals(a)&& i.getLocation2().equals(b))
                return i;
        }
        return null;
    }
    public void path2String(){
        
        for(List<Location> i: paths){
            String s="";
            for(Location j: i){
            s=s+j.getName();
        }
            pts.add(s);
        }
    }

    public List<String> getPts() {
        return pts;
    }

    public void setPts(List<String> pts) {
        this.pts = pts;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Temp> getServices() {
        return services;
    }

    public void setServices(List<Temp> services) {
        this.services = services;
    }
    
    

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<List<Location>> getPaths() {
        return paths;
    }

    public void setPaths(List<List<Location>> paths) {
        this.paths = paths;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    public static class Temp {

        
        List<Route> path;
        Double Cost;
        Double Time;
        String pts;

        //getter and setter method
        public List<Route> getPath() {
            return path;
        }

        public void setPath(List<Route> path) {
            this.path = path;
        }

        public Double getCost() {
            return Cost;
        }

        public void setCost(Double Cost) {
            this.Cost = Cost;
        }

        public Double getTime() {
            return Time;
        }

        public void setTime(Double Time) {
            this.Time = Time;
        }

        public String getPts() {
            return pts;
        }

        public void setPts(String pts) {
            this.pts = pts;
        }
        

        

    }
}
