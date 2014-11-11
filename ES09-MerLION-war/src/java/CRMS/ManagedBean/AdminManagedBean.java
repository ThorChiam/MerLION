/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CI.Entity.Account;
import CRMS.Session.AdminSessionLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "AdminManagedBean")
@SessionScoped
public class AdminManagedBean implements Serializable{
    @EJB
    private AdminSessionLocal tmp;
    
    private String email;
    private String passwrod;
    private String status;
    private String accessright;
    private String security_question;
    private String security_answer;
    private String statusMessage;
    private List<Account> allAccounts;

 


    //activate & deactivate

    @PostConstruct
    public void init(){
        email = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        allAccounts = tmp.getAllAccount();
    }
    public boolean statusToboolean(String msg){
        if(msg.equals("activated"))
            return true;
        return false;
    }
    
    public AdminManagedBean() {
    }
  
    public void updateAccount(){
        tmp.updateUserInfo(email, passwrod, accessright, status, security_question, security_answer);
    }
    
    public Account getAccount(String email){
        return tmp.getAccount(email);
    }
    
    public List<Account> getAllAccount(){
        return tmp.getAllAccount();
    }

    public List<Account> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(List<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }
   
    public void updateStatus(String email, String status){
        if(status.equals("activated"))
            status="deactivated";
        else
            status="activated";
        tmp.updateStatus(email, status);
        this.setAllAccounts(tmp.getAllAccount());
        statusMessage = "Status Updated.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
    }
    
    
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    public AdminSessionLocal getTmp() {
        return tmp;
    }

    public void setTmp(AdminSessionLocal tmp) {
        this.tmp = tmp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccessright() {
        return accessright;
    }

    public void setAccessright(String accessright) {
        this.accessright = accessright;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }
    
}
