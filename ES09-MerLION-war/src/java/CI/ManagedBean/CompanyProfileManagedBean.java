/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.ManagedBean;

import CRMS.Entity.Company;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import CRMS.Session.CompanyProfileSessionLocal;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "companyProfileManagedBean")
@ViewScoped
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
    private List<Company> cProfile;

    public CompanyProfileManagedBean() {
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        cProfile = this.getCompanyProfile();
    }

    public String addCompanyProfile(String aemail) {
        
        statusMessage = "create company profile successfully";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
        cpsbl.addCompanyProfile(companyName, companyAddress, tel, aemail, website, companyHistory, service, vision);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "signIn";
    }

    //view individual company
    public List<Company> getCompanyProfile() {
        cProfile = new ArrayList<>();
        cProfile.add(cpsbl.getCompamyProfile(email));
  
        return cProfile;
    }

    //admin can delete company profile
    public String deleteCompanyProfile(Long companyId) {
        return cpsbl.deleteCompanyProfile(companyId);
    }

    //update company profile
    public void onCellEdit(CellEditEvent event) {
        System.err.println("Selected row: " + cProfile.get(event.getRowIndex()).getCompanyName());
        System.out.println("************");
        System.out.println("");
        System.out.println("************");
        this.updateCompanyProfile(cProfile.get(event.getRowIndex()).getCompanyName(), cProfile.get(event.getRowIndex()).getCompanyAddress(), cProfile.get(event.getRowIndex()).getTel(), cProfile.get(event.getRowIndex()).getEmail(), cProfile.get(event.getRowIndex()).getWebsite(), cProfile.get(event.getRowIndex()).getCompanyHistory(), cProfile.get(event.getRowIndex()).getService(), cProfile.get(event.getRowIndex()).getVision());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"New value:" + newValue," update successfully");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //update company profile
    public void updateCompanyProfile(String companyName, String companyAddress, String tel, String aemail, String website, String companyHistory, String service, String vision) {
        cpsbl.updateCompanyProfile(email, companyName, companyAddress, tel, aemail, website, companyHistory, service, vision);
    }

    public CompanyProfileSessionLocal getCpsbl() {
        return cpsbl;
    }

    public void setCpsbl(CompanyProfileSessionLocal cpsbl) {
        this.cpsbl = cpsbl;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Company> getCProfile() {
        return cProfile;
    }

    public void setCProfile(List<Company> cProfile) {
        this.cProfile = cProfile;
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
