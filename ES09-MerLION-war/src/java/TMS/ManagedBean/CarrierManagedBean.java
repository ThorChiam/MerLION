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
import TMS.Session.CarrierSession;
import TMS.Entity.Carrier;


/**
 *
 * @author Zeng Xunhao
 *//**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "CarrierMB")
@SessionScoped
public class CarrierManagedBean implements Serializable {

    
    @EJB
    private CarrierSession CSession;   
    private List<Carrier> carriers=new ArrayList();
    private String userId;
    private String statusMessage="no message";
    private Carrier carrier=new Carrier();
             
    public CarrierManagedBean() {
        
    }
    
    @PostConstruct
    public void init(){     
        userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        carriers=CSession.getMyCarrier(userId);
        
    }  
   
    
    public void addNewCarrier(){
        carrier.setItemtype("Available");
        Long id=CSession.addCarrier(carrier, userId);
        statusMessage="New Catalog "+id+ " added successfully!";     
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
           
    }
    public void updateCarriers(){
        for(Carrier i:carriers){
            CSession.updateCarrier(i);            
        }
        statusMessage="Updates saved !";     
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
    }
    
    public void deleteCarrier(Carrier r){
        CSession.deleteCarrier(r.getId());
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Carrier> getCarriers() {
        
        return carriers;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

  
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}