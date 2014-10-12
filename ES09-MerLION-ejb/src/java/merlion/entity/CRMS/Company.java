/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.CRMS;

import merlion.entity.CommonInfrastructure.Notification;
import merlion.entity.WMS.HumanResource;
import merlion.entity.TMS.TMSFacility;
import merlion.entity.TMS.DispatchOrder;
import merlion.entity.WMS.Warehouse;
import merlion.entity.GRNS.Auction;
import merlion.entity.CommonInfrastructure.Account;
import merlion.entity.CommonInfrastructure.Chat;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import merlion.entity.OES.OES_Product;
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
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<ServiceOrder> serviceorder = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<TMSFacility> tmsfacility = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<HumanResource> humanresource = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<DispatchOrder> dispatchorder = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Auction> auction = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Feedback> feedback = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Favorite> favorite = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Notification> notification = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Payment> payment = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Contract> contract = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Company")
    private List<Chat> chats = new ArrayList<>();

    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER,optional=false)
    private Warehouse warehouse;
    
    @OneToOne(mappedBy="company")
    private Account Account;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "company")
    private Collection<OES_Product> products=new ArrayList<>();

    
    public Company(){
        setId(System.nanoTime());
    }
    
    public Collection<OES_Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<OES_Product> products) {
        this.products = products;
    }

    public List<Contract> getContract() {
        return contract;
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
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

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }
    
    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<Auction> getAuction() {
        return auction;
    }

    public void setAuction(List<Auction> auction) {
        this.auction = auction;
    }

    public List<DispatchOrder> getDispatchorder() {
        return dispatchorder;
    }

    public void setDispatchorder(List<DispatchOrder> dispatchorder) {
        this.dispatchorder = dispatchorder;
    }

    public List<HumanResource> getHumanresource() {
        return humanresource;
    }

    public void setHumanresource(List<HumanResource> humanresource) {
        this.humanresource = humanresource;
    }

    public List<TMSFacility> getTmsfacility() {
        return tmsfacility;
    }

    public void setTmsfacility(List<TMSFacility> tmsfacility) {
        this.tmsfacility = tmsfacility;
    }

    public List<ServiceOrder> getServiceorder() {
        return serviceorder;
    }

    public void setServiceorder(List<ServiceOrder> serviceorder) {
        this.serviceorder = serviceorder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
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
