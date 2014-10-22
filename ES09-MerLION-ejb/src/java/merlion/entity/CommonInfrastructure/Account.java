package merlion.entity.CommonInfrastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import merlion.entity.CRMS.Company;
import merlion.entity.MRP.Item;
import merlion.entity.CRMS.Post;
import merlion.entity.CRMS.ServiceOrder;
import merlion.entity.OES.PurchaseOrder;
import merlion.entity.WMS.Order;
import merlion.entity.WMS.Shipment_Order;

@Entity
@Table(name = "Account")
public class Account implements Serializable { 
    private static final long serialVersionUID = 1L;
    @Id
    private String email; 
    private String password;
    private String accessright;//区分用哪些系统
    private String status;
    private String security_question;
    private String security_answer;
  
     
    //Added by QT
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Item> items = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Post> post = new ArrayList<>();
    
    @ManyToOne
    private Company Company;
    
    @OneToOne
    private MerlionAdmin admin;

    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="ACCOUNT_ANNOUNCEMENT")
    private Set<Announcement> announcements=new HashSet<>();
   
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Shipment_Order> shipmentorder = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="requester")
    private List<Order> request_wmsorder = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="service_requester")
    private List<ServiceOrder> request_service = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="service_provider")
    private List<ServiceOrder> provide_service = new ArrayList<>();
     
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="provider")
    private List<Order> provide_wmsorder = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="sender")
    private List<PurchaseOrder> send_purchaseorder = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="receiver")
    private List<PurchaseOrder> receive_purchaseorder = new ArrayList<>();

    
    
  
    
    
    
    //Added by QT  
    
    public List<ServiceOrder> getRequest_service() {
        return request_service;
    }

    public void setRequest_service(List<ServiceOrder> request_service) {
        this.request_service = request_service;
    }

    public List<ServiceOrder> getProvide_service() {
        return provide_service;
    }

    public void setProvide_service(List<ServiceOrder> provide_service) {
        this.provide_service = provide_service;
    }
    
    public List<Shipment_Order> getShipmentorder() {
        return shipmentorder;
    }

    public void setShipmentorder(List<Shipment_Order> shipmentorder) {
        this.shipmentorder = shipmentorder;
    }

    public List<Order> getRequest_wmsorder() {
        return request_wmsorder;
    }

    public void setRequest_wmsorder(List<Order> request_wmsorder) {
        this.request_wmsorder = request_wmsorder;
    }

    public List<Order> getProvide_wmsorder() {
        return provide_wmsorder;
    }

    public void setProvide_wmsorder(List<Order> provide_wmsorder) {
        this.provide_wmsorder = provide_wmsorder;
    }
    
    public MerlionAdmin getMerlionAdmin() {
        return admin;
    }

    public void setMerlionAdmin(MerlionAdmin admin) {
        this.admin = admin;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }
    
    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        this.Company = company;
    }
  
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<PurchaseOrder> getSend_purchaseorder() {
        return send_purchaseorder;
    }

    public void setSend_purchaseorder(List<PurchaseOrder> send_purchaseorder) {
        this.send_purchaseorder = send_purchaseorder;
    }

    public List<PurchaseOrder> getReceive_purchaseorder() {
        return receive_purchaseorder;
    }

    public void setReceive_purchaseorder(List<PurchaseOrder> receive_purchaseorder) {
        this.receive_purchaseorder = receive_purchaseorder;
    }






    public void create(String email, String password, String comp_name, String comp_address, String comp_contact_no, 
                       String accessright, String status,String security_question,String security_answer){
        this.email=email;
        this.password=password;
        this.accessright=accessright;
        this.status=status;
        this.security_question=security_question;
        this.security_answer=security_answer;
    }  

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }
    
    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }
  
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessRight() {
        return accessright;
    }

    public void setAccessRight(String accessright) {
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
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_ejb.Account[ email=" + email + " ]";
    }
}
