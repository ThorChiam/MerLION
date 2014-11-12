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
 * @author Tomato
 */
@ManagedBean(name = "sob")
@ViewScoped
public class SalesOrderManagedBean {
    
     @EJB
    private OESSessionLocal osbl;

    //Quotation: view Enquire
    private String email;
    private int soSize;
    private long soId;
    private int orderDetailsSize;
    
   
    
    public SalesOrderManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        /*if (email != null) {
            this.viewIncmoingPorder();
        }
     */
    }
    
    //**************************************View Incoming Purchse Order******************************************
    public List<PurchaseOrder> viewIncmoingPorder(String email){
       soSize=osbl.viewIncmoingPorder(email).size();
       return osbl.viewIncmoingPorder(email);
    }
    
    public List<OrderList> viewPurchaseOrderDetails(long id){
        orderDetailsSize=osbl.viewPurchaseOrderDetails(id).size();
        return osbl.viewPurchaseOrderDetails(id);
        
        
    }
    
    
    
    
    
    
    
    
     //******************GETTER AND SETTER***********************************************************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSoSize() {
        return soSize;
    }

    public void setSoSize(int soSize) {
        this.soSize = soSize;
    }

    public long getSoId() {
        return soId;
    }

    public void setSoId(long soId) {
        this.soId = soId;
    }

    public int getOrderDetailsSize() {
        return orderDetailsSize;
    }

    public void setOrderDetailsSize(int orderDetailsSize) {
        this.orderDetailsSize = orderDetailsSize;
    }

    
    
    
    
    
    
    
    
}
