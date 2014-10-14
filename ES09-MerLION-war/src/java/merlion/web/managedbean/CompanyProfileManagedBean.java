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
    public Long addCompanyProfile(){
        return cpsbl.addCompanyProfile(companyName, companyAddress, tel, email, website, companyHistory, service, vision);
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
}
