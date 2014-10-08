/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.AdministratorSessionBeanLocal;
import merlion.entity.Admin;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "administratorManagedBean")
@SessionScoped
public class AdministratorManagedBean implements Serializable {

    /**
     * Creates a new instance of AdministratorManagedBean
     */
    private AdministratorSessionBeanLocal adsbl;
    private String email;
    private String password;
    private String accessright;
    private String status;
    private String security_question;
    private String security_answer;

    public AdministratorManagedBean() {
    }

    public List<Admin> getAdmins() {
        return adsbl.getAdmins();
    }

    public Admin getAdmin(String email) {
        return adsbl.getAdmin(email);
    }

    public void deleteAdmin() {
        adsbl.deleteAdmin(email);
    }

    public String createAdmin() {

        return adsbl.createAdmin(email, password,accessright, status, security_question, security_answer);
    }

    public void resetPassword() {
        adsbl.resetpassword(email, password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessright() {
        return accessright;
    }

    public void setAccessright(String accessright) {
        this.accessright = accessright;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
