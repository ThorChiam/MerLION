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
import merlion.entity.CRMS.Contract;
import merlion.entity.CRMS.Favorite;
import merlion.entity.CRMS.Payment;
import merlion.entity.MRP.Item;
import merlion.entity.CRMS.Post;
import merlion.entity.CRMS.ServiceCatalog;
import merlion.entity.WMS.WMSOrder;
import merlion.entity.WMS.WMS_Shipment_Order;

@Entity
@Table(name = "Account")
public class Account implements Serializable { 
    private static final long serialVersionUID = 1L;
    @Id
    private String email; 
    private String password;
    private String accessright;
    private String status;
    private String security_question;
    private String security_answer;

    //Added by QT
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Item> items = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Favorite> favorites = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<ServiceCatalog> servicecatalogs = new ArrayList<>(); 
    
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
    private List<Notification> notificationss = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="requester")
    private List<Contract> Contract_requester = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="provider")
    private List<Contract> Contract_provider = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="payer")
    private List<Payment> Payment_payer = new ArrayList<>();
 
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="receiver")
    private List<Payment> Payment_receiver = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<WMS_Shipment_Order> shipmentorder = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<WMSOrder> wmsorder = new ArrayList<>();
  
    
    
    
  

    //Added by QT  
    
    public List<Contract> getContract_requester() {
        return Contract_requester;
    }

    public void setContract_requester(List<Contract> Contract_requester) {
        this.Contract_requester = Contract_requester;
    }

    public List<Contract> getContract_provider() {
        return Contract_provider;
    }

    public void setContract_provider(List<Contract> Contract_provider) {
        this.Contract_provider = Contract_provider;
    }

    public List<Payment> getPayment_payer() {
        return Payment_payer;
    }

    public void setPayment_payer(List<Payment> Payment_payer) {
        this.Payment_payer = Payment_payer;
    }

    public List<Payment> getPayment_receiver() {
        return Payment_receiver;
    }

    public void setPayment_receiver(List<Payment> Payment_receiver) {
        this.Payment_receiver = Payment_receiver;
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
   
    public List<ServiceCatalog> getServicecatalogs() {
        return servicecatalogs;
    }

    public void setServicecatalogs(List<ServiceCatalog> servicecatalogs) {
        this.servicecatalogs = servicecatalogs;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
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
    
    public List<Notification> getNotificationss() {
        return notificationss;
    }

    public void setNotificationss(List<Notification> notificationss) {
        this.notificationss = notificationss;
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
