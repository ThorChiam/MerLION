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
import javax.faces.context.FacesContext;
import TMS.Session.DriverSession;

import TMS.Entity.Driver;


/**
 *
 * @author Zeng Xunhao
 *//**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "DriverMB")
@SessionScoped
public class DriverManagedBean implements Serializable {

    
    @EJB
    private DriverSession CSession;   
    private List<Driver> drivers=new ArrayList();
    private String userId;
    private String statusMessage="no message";
    private Driver driver=new Driver();
             
    public DriverManagedBean() {
        
    }
    
    @PostConstruct
    public void init(){      
        userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        drivers=CSession.getMyDrivers(userId);
    }  
   
    
    public void addNewDriver(){
        driver.setEmail("Available");
        Long id=CSession.addDriver(driver, userId);
        statusMessage="New driver "+id+ " added successfully!";     
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
           
    }
    public void updateDrivers(){
        for(Driver i:drivers){
            CSession.updateDriver(i);            
        }
        statusMessage="Updates saved !";     
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
    }
    
    public void deleteDriver(Driver r){
        CSession.deleteDriver(r.getId());
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

  
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}