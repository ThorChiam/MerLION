/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.CRMS;

import merlion.entity.CommonInfrastructure.Notification;
import merlion.entity.TMS.TMSFacility;
import merlion.entity.WMS.WMSWarehouse;
import merlion.entity.GRNS.Auction;
import merlion.entity.CommonInfrastructure.Account;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import merlion.entity.OES.OES_Product;
import merlion.entity.TMS.TMSHumanResource;
/**
 *
 * @author sunny
 */
@Entity
public class Company implements Serializable {
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
    
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<TMSFacility> tmsfacility = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Auction> auction = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Favorite> favorite = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Notification> notification = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<WMSWarehouse> warehouse = new ArrayList();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Account> Account=new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "company")
    private Collection<OES_Product> products=new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "feedback_sender")
    private List<Feedback> feedback_sender = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "feedback_receiver")
    private List<Feedback> feedback_receiver = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<ServiceCatalog> servicecatalog = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "service_requester")
    private List<ServiceOrder> service_requester = new ArrayList<>();
   
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "service_requester")
    private List<ServiceOrder> service_receiver = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<TMSFacility> TMSFacility = new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<TMSHumanResource> TMSHumanResource = new ArrayList<>();

    
  
    public List<WMSWarehouse> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<WMSWarehouse> warehouse) {
        this.warehouse = warehouse;
    }

    public List<ServiceOrder> getService_receiver() {
        return service_receiver;
    }

    public void setService_receiver(List<ServiceOrder> service_receiver) {
        this.service_receiver = service_receiver;
    }
    
    public List<TMSFacility> getTMSFacility() {
        return TMSFacility;
    }

    public void setTMSFacility(List<TMSFacility> TMSFacility) {
        this.TMSFacility = TMSFacility;
    }

    public List<TMSHumanResource> getTMSHumanResource() {
        return TMSHumanResource;
    }

    public void setTMSHumanResource(List<TMSHumanResource> TMSHumanResource) {
        this.TMSHumanResource = TMSHumanResource;
    }
    
    public List<ServiceCatalog> getServicecatalog() {
        return servicecatalog;
    }

    public void setServicecatalog(List<ServiceCatalog> servicecatalog) {
        this.servicecatalog = servicecatalog;
    }

    public List<ServiceOrder> getService_requester() {
        return service_requester;
    }

    public void setService_requester(List<ServiceOrder> service_requester) {
        this.service_requester = service_requester;
    }
    
    public List<Feedback> getFeedback_sender() {
        return feedback_sender;
    }

    public void setFeedback_sender(List<Feedback> feedback_sender) {
        this.feedback_sender = feedback_sender;
    }

    public List<Feedback> getFeedback_receiver() {
        return feedback_receiver;
    }

    public void setFeedback_receiver(List<Feedback> feedback_receiver) {
        this.feedback_receiver = feedback_receiver;
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
    
    public Collection<OES_Product> getProducts() {
        return products;
    }

    public List<Account> getAccount() {
        return Account;
    }

    public void setAccount(List<Account> Account) {
        this.Account = Account;
    }
    
    public void setProducts(Collection<OES_Product> products) {
        this.products = products;
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }
    
    public List<Favorite> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Favorite> favorite) {
        this.favorite = favorite;
    }

    public List<Auction> getAuction() {
        return auction;
    }

    public void setAuction(List<Auction> auction) {
        this.auction = auction;
    }

    public List<TMSFacility> getTmsfacility() {
        return tmsfacility;
    }

    public void setTmsfacility(List<TMSFacility> tmsfacility) {
        this.tmsfacility = tmsfacility;
    }

    public Company(){
        setId(System.nanoTime());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void create(String companyName, String companyAddress, String tel, String email,String website,String companyHistory,String service,String vision){
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
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Company[ id=" + id + " ]";
    }
    
}
