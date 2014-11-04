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
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "AdminManagerBean")
@SessionScoped
public class AdminManagedBean implements Serializable{
    @EJB
    private AdminSessionLocal asbl;
    
    private String email;
    private String passwrod;
    private String status;
    private String accessright;
    private String security_question;
    private String security_answer;
    
    public AdminManagedBean() {
    }
    
    public void updateAccount(){
        asbl.updateUserInfo(email, passwrod, accessright, status, security_question, security_answer);
    }
    
    public Account getAccount(String email){
        return asbl.getAccount(email);
    }
    
    public List<Account> getAllAccount(){
        return asbl.getAllAccount();
    }
    
    public void activateAccount(String email){
        asbl.deactivateAccount(email);
    }
    
}
