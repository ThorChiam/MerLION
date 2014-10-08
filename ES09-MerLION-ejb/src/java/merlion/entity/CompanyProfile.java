/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion_ejb.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author USER
 */
@Entity(name = "CompanyProfile")
public class CompanyProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyName;
    private String companyAddress;
    private String tel;
    private String email;
    private String website;
    private String companyHistory;
    private String service;
    private String vision;
    
    @OneToOne(mappedBy="companyprofile")
    private Account Account;


    public CompanyProfile() {
        setId(System.nanoTime());
    }
    
  
    
    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public void create(String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision){
        this.setCompanyName(companyName);
        this.setCompanyAddress(companyAddress);
        this.setTel(tel);
        this.setEmail(email);
        this.setWebsite(website);
        this.setCompanyHistory(companyHistory);
        this.setService(service);
        this.setVision(vision);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyProfile)) {
            return false;
        }
        CompanyProfile other = (CompanyProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompanyProfileEntity[ id=" + id + " ]";
    }

}
