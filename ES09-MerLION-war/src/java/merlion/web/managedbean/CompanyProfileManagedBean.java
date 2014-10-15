/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.CompanyProfileSessionBeanLocal;


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
    private CompanyProfileSessionBeanLocal cpsbl;
    private String companyName;
    private String companyAddress;
    private String tel;
    private String email;
    private String website;
    private String companyHistory;
    private String service;
    private String vision;

    public CompanyProfileManagedBean() {
    }
    public Long addCompanyProfile(String aemail){
        return cpsbl.addCompanyProfile(companyName, companyAddress, tel, aemail, website, companyHistory, service, vision);
    }
    public List getAllCompanyProfile(String email){
        return cpsbl.getAllCompanyProfile(email);
    }

    //admin can delete company profile
    public String deleteCompanyProfile(String email, Long companyId){
        return cpsbl.deleteCompanyProfile(email, companyId);
    }
    //update company profile

    public void updateCompanyProfile(String email,Long companyId){
        cpsbl.updateCompanyProfile(companyId, companyName, companyAddress, tel, email, website, companyHistory, service, vision);
    }

    public CompanyProfileSessionBeanLocal getCpsbl() {
        return cpsbl;
    }

    public void setCpsbl(CompanyProfileSessionBeanLocal cpsbl) {
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
