package merlion.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Account")
public class Account implements Serializable { 
    private static final long serialVersionUID = 1L;
    @Id
    private String email; 
    private String password;
    private String comp_name;
    private String comp_contact_no;
    private String comp_address;
    private String accessright;
    private String status;
    private String security_question;
    private String security_answer;

    //Added by QT
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Salesquotation> salesquotations = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Item> items = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Payment> payment = new ArrayList<>();

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Favorite> favorites = new ArrayList<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Services> services = new ArrayList<>(); 
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Post> post = new ArrayList<>();
    
    @OneToOne
    private Company company;
    
    @OneToOne
    private Admin admin;

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<PurchaseOrder> purchaseorders = new ArrayList<>();
    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="ACCOUNT_ANNOUNCEMENT")
    private Set<Announcement> announcements=new HashSet<>();
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="Account")
    private List<Notification> notificationss = new ArrayList<>();
    
    
    public Account() {
    }

    
    public void create(String email, String password, String comp_name, String comp_address, String comp_contact_no, 
                       String accessright, String status,String security_question,String security_answer){
        this.email=email;
        this.password=password;
        this.comp_name=comp_name;
        this.comp_address=comp_address;
        this.comp_contact_no=comp_contact_no;
        this.accessright=accessright;
        this.status=status;
        this.security_question=security_question;
        this.security_answer=security_answer;
    }
    
    
    
    
    
  

    //Added by QT 
    
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }
    
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public List<PurchaseOrder> getPurchaseorders() {
        return purchaseorders;
    }

    public void setPurchaseorders(List<PurchaseOrder> purchaseorders) {
        this.purchaseorders = purchaseorders;
    }
    
    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
    
    public List<Salesquotation> getSalesquotations() {
        return salesquotations;
    }

    public void setSalesquotations(List<Salesquotation> salesquotations) {
        this.salesquotations = salesquotations;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
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

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_contact_no() {
        return comp_contact_no;
    }

    public void setComp_contact_no(String comp_contact_no) {
        this.comp_contact_no = comp_contact_no;
    }

    public String getComp_address() {
        return comp_address;
    }

    public void setComp_address(String comp_address) {
        this.comp_address = comp_address;
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
