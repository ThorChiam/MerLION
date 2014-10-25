/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.ManagedBean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import CRMS.Session.CompanyProfileSessionLocal;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "companyProfileManagedBean")
@SessionScoped
public class CompanyProfileManagedBean implements Serializable {

    /**
     * Creates a new instance of CompanyProfileManagedBean
     */
    @EJB
    private CompanyProfileSessionLocal cpsbl;
    private String companyName;
    private String companyAddress;
    private String tel;
    private String email;
    private String website;
    private String companyHistory;
    private String service;
    private String vision;
    private String statusMessage;

    public CompanyProfileManagedBean() {
    }

    public String addCompanyProfile(String aemail) {
        statusMessage = "create company profile successfully";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        cpsbl.addCompanyProfile(companyName, companyAddress, tel, aemail, website, companyHistory, service, vision);
        return "main";
    }

    public List getAllCompanyProfile(String email) {
        return cpsbl.getAllCompanyProfile(email);
    }

    //admin can delete company profile
    public String deleteCompanyProfile(Long companyId) {
        return cpsbl.deleteCompanyProfile(companyId);
    }
    //update company profile

    public void updateCompanyProfile(String cemail) {
        cpsbl.updateCompanyProfile(cemail,companyName, companyAddress, tel, email,website, companyHistory, service, vision);
    }

    public CompanyProfileSessionLocal getCpsbl() {
        return cpsbl;
    }

    public void setCpsbl(CompanyProfileSessionLocal cpsbl) {
        this.cpsbl = cpsbl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyHistory() {
        return companyHistory;
    }

    public void setCompanyHistory(String companyHistory) {
        this.companyHistory = companyHistory;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

}
