package CI.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import CRMS.Entity.Company;
import CRMS.Entity.Contract;
import CRMS.Entity.Forume_Replies;
import MRP.Entity.Item;
import CRMS.Entity.Post;
import OES.Entity.Enquiry;
import OES.Entity.PurchaseOrder;
import WMS.Entity.WMSOrder;

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
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Account")
    private List<Item> items = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "Account")
    private List<Post> post = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "provider")
    private List<Contract> pContracts = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "requestor")
    private List<Contract> rContracts = new ArrayList<>();
    @ManyToOne
    private Company Company;

    @OneToOne
    private MerlionAdmin admin;
    
    @OneToOne(mappedBy="account")
    private Forume_Replies reply;


    //@ManyToMany(cascade={CascadeType.PERSIST})
    //@JoinTable(name="ACCOUNT_ANNOUNCEMENT")
    //private Set<Announcement> announcements=new HashSet<>();  
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "requester")
    private List<WMSOrder> request_wmsorder = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "provider")
    private List<WMSOrder> provide_wmsorder = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "sender")
    private List<PurchaseOrder> send_purchaseorder = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "receiver")
    private List<PurchaseOrder> receive_purchaseorder = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "buyer")
    private List<Enquiry> buyer_enquiry = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "seller")
    private List<Enquiry> seller_enquiry = new ArrayList<>();

    //Added by QT  
    
    public Forume_Replies getReply() {
        return reply;
    }

    public void setReply(Forume_Replies reply) {
        this.reply = reply;
    }
    public List<Enquiry> getBuyer_enquiry() {
        return buyer_enquiry;
    }

    public void setBuyer_enquiry(List<Enquiry> buyer_enquiry) {
        this.buyer_enquiry = buyer_enquiry;
    }

    public List<Enquiry> getSeller_enquiry() {
        return seller_enquiry;
    }

    public void setSeller_enquiry(List<Enquiry> seller_enquiry) {
        this.seller_enquiry = seller_enquiry;
    }

    public List<WMSOrder> getRequest_wmsorder() {
        return request_wmsorder;
    }

    public void setRequest_wmsorder(List<WMSOrder> request_wmsorder) {
        this.request_wmsorder = request_wmsorder;
    }

    public List<WMSOrder> getProvide_wmsorder() {
        return provide_wmsorder;
    }

    public void setProvide_wmsorder(List<WMSOrder> provide_wmsorder) {
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

    public List<Contract> getpContracts() {
        return pContracts;
    }

    public void setpContracts(List<Contract> pContracts) {
        this.pContracts = pContracts;
    }

    public List<Contract> getrContracts() {
        return rContracts;
    }

    public void setrContracts(List<Contract> rContracts) {
        this.rContracts = rContracts;
    }

    public void create(String email, String password, String comp_name, String comp_address, String comp_contact_no,
            String accessright, String status, String security_question, String security_answer) {
        this.email = email;
        this.password = password;
        this.accessright = accessright;
        this.status = status;
        this.security_question = security_question;
        this.security_answer = security_answer;
    }

    /*
     public Set<Announcement> getAnnouncements() {
     return announcements;
     }
    
     public void setAnnouncements(Set<Announcement> announcements) {
     this.announcements = announcements;
     }
     */
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
