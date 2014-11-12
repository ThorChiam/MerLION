/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Session.EnquireSessionLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@ManagedBean(name = "cem")
@SessionScoped
public class CustomerEnquireManagedBean implements Serializable{
    @EJB
    EnquireSessionLocal esl;
    private String email;
    private String qun;
    private String statusMessage;
    
    public CustomerEnquireManagedBean(){
    }
    
    public String createEnquiry(){
        esl.createEnquiry(email, qun);
       
        
       FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " ", "Enquiry send successfully!");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
         
         return "main";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQun() {
        return qun;
    }

    public void setQun(String qun) {
        this.qun = qun;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
}
