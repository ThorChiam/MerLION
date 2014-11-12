/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CI.Entity.Account;
import CI.Entity.Announcement;
import CI.Entity.Customer_Enquiry;
import CRMS.Entity.Company;
import CRMS.Entity.Post;
import CRMS.Session.AdminSessionLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "AdminManagedBean")
@ViewScoped
public class AdminManagedBean implements Serializable {

    @EJB
    private AdminSessionLocal tmp;

    //Account
    private String email;
    private String passwrod;
    private String status;
    private String accessright;
    private String security_question;
    private String security_answer;
    private String statusMessage;
    private List<Account> allAccounts;

    //Company
    private List<Company> allCompanys;

    //Announcement
    private List<Announcement> allAnnouncements;
    private boolean checkadd;
    private String title;
    private String content;
    
    //Enquiry
    private String answer;
    private List<Customer_Enquiry> allEnquirys;
    private boolean checkreply;
    private boolean checkdetail;
    private long tmp_id;
    private long tmp_id_another;
    
    //Post
    private List<Post> allPosts;
    private boolean postdetail;
    private long post_tmp_id;




    public AdminManagedBean() {
    }

    //*********************************About Account********************************
    //activate & deactivate
    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        allAccounts = tmp.getAllAccount();
        allCompanys = tmp.getAllCompany();
        allAnnouncements = tmp.getAllAnnoun();
        allEnquirys = tmp.getAllEnquiry();
        allPosts = tmp.getAllPost();
    }

    //update User Account Info
    public void onCellEdit(CellEditEvent event) {
        System.err.println("Selected row: " + allAccounts.get(event.getRowIndex()).getEmail());
        System.out.println("************");
        System.out.println("");
        System.out.println("************");
        this.updateAccount(allAccounts.get(event.getRowIndex()).getEmail(), allAccounts.get(event.getRowIndex()).getPassword(), allAccounts.get(event.getRowIndex()).getAccessRight(), allAccounts.get(event.getRowIndex()).getSecurity_question(), allAccounts.get(event.getRowIndex()).getSecurity_answer());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New value:" + newValue, " update successfully");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public boolean statusToboolean(String msg) {
        if (msg.equals("activated")) {
            return true;
        }
        return false;
    }

    public void updateAccount(String email, String passwrod, String accessright, String security_question, String security_answer) {
        tmp.updateUserInfo(email, passwrod, accessright, security_question, security_answer);
    }

    public Account getAccount(String email) {
        return tmp.getAccount(email);
    }

    public List<Account> getAllAccount() {
        return tmp.getAllAccount();
    }

    public void updateStatus(String email, String status) {
        if (status.equals("activated")) {
            status = "deactivated";
        } else {
            status = "activated";
        }
        tmp.updateStatus(email, status);
        this.setAllAccounts(tmp.getAllAccount());
        statusMessage = "Status Updated.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
    }

    public void deleteAccount(String email) {
        for (int i = 0; i <= allAccounts.size(); i++) {
            if (allAccounts.get(i).getEmail().equals(email)) {
                allAccounts.remove(i);
                break;
            }
        }
        tmp.deleteAccount(email);
    }

    //***************************About Company*****************************   
    //update User Account Info
    public void onCellEdit_comp(CellEditEvent event) {
        System.err.println("Selected row: " + allCompanys.get(event.getRowIndex()).getId());
        System.out.println("************");
        System.out.println("");
        System.out.println("************");
        this.updateCompany(allCompanys.get(event.getRowIndex()).getId(), allCompanys.get(event.getRowIndex()).getCompanyName(), allCompanys.get(event.getRowIndex()).getCompanyAddress(), allCompanys.get(event.getRowIndex()).getTel(), allCompanys.get(event.getRowIndex()).getEmail(), allCompanys.get(event.getRowIndex()).getWebsite(), allCompanys.get(event.getRowIndex()).getCompanyHistory(), allCompanys.get(event.getRowIndex()).getService(), allCompanys.get(event.getRowIndex()).getVision());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New value:" + newValue, " update successfully");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void getAllCompany() {
        tmp.getAllCompany();
    }

    public void getCompany(long company_id) {
        tmp.getCompany(company_id);
    }

    public void updateCompany(long id, String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision) {
        tmp.updateCompanyInfo(id, companyName, companyAddress, tel, email, website, companyHistory, service, vision);
    }

    public void deleteCompany(long id) {
        for (int i = 0; i < allCompanys.size(); i++) {
            if (allCompanys.get(i).getId().equals(id)) {
                allCompanys.remove(i);
                break;
            }
        }
        tmp.deleteCompany(id);
    }

    //*************************About Announcement*************************
    public void createAnnouncement(ActionEvent event) {
        tmp.createAnnoun((title + ":" + content), email);
        allAnnouncements = tmp.getAllAnnoun();
        checkadd=false;
        title="";
        content="";
    }

    public List<Announcement> getAllAnnouncement() {
        return tmp.getAllAnnoun();
    }

    public Announcement getAnnouncement(long id) {
        return tmp.getAnnoun(id);
    }

    public void deleteAnnouncement(long id) {
        for (int i = 0; i < allAnnouncements.size(); i++) {
            if (allAnnouncements.get(i).getAnnId() == id) {
                allAnnouncements.remove(i);
                break;
            }
        }
        tmp.deleteAnnoun(id);
    }
    
    
    
    
    
    //*****************************About Enquiry************************
    public Customer_Enquiry getEnquiry(){
        return tmp.getEnquiry(tmp_id);
    }
    
    public List<Customer_Enquiry> getAllEnquiry(){
        return tmp.getAllEnquiry();
    }
    
    public void replyEnquiry(){
        tmp.replyEnquiry(answer, email, tmp_id_another);
        allEnquirys=tmp.getAllEnquiry();
        checkreply=false;
        tmp_id_another=0;
        answer="";
    }
    
    public boolean checkStatus(String status){
        if(status.equals("unsolved"))
            return false;
        return true;
    }
    
    public boolean checkStatus_reverse(String status){
        if(status.equals("solved"))
            return false;
        return true;
    }
    
    
    
    
    
    //**************************About Post*********************
    public Post getPost(){
        return tmp.getPost(post_tmp_id);
    }
    
    public List<Post> getAllPost(){
        return tmp.getAllPost();
    }
    
    public void deletePost(long id){
         for (int i = 0; i < allPosts.size(); i++) {
            if (allPosts.get(i).getId().equals(id)) {
                allPosts.remove(i);
                break;
            }
        }
        tmp.deletePost(id);
    }
    
    
    
    
    
    
    
    

    //***************************Getter and Setter**********************
    
    public boolean isPostdetail() {
        return postdetail;
    }

    public void setPostdetail(boolean postdetail,long id) {
        this.postdetail = postdetail;
        this.post_tmp_id=id;
    }

    public List<Post> getAllPosts() {
        return allPosts;
    }

    public void setAllPosts(List<Post> allPosts) {
        this.allPosts = allPosts;
    }

    public long getTmp_id_another() {
        return tmp_id_another;
    }

    public void setTmp_id_another(long tmp_id_another) {
        this.tmp_id_another = tmp_id_another;
    }

    public long getTmp_id() {
        return tmp_id;
    }

    public void setTmp_id(long tmp_id) {
        this.tmp_id = tmp_id;
    }
    
    public boolean isCheckdetail() {
        return checkdetail;
    }

    public void setCheckdetail(boolean checkdetail, long id) {
        this.checkdetail = checkdetail;
        this.setTmp_id(id);    
    }

    public boolean isCheckreply() {
        return checkreply;
    }

    public void setCheckreply(boolean checkreply, long id) {
        this.checkreply = checkreply;
        this.setTmp_id_another(id);
    }

    public List<Customer_Enquiry> getAllEnquirys() {
        return allEnquirys;
    }

    public void setAllEnquirys(List<Customer_Enquiry> allEnquirys) {
        this.allEnquirys = allEnquirys;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheckadd() {
        return checkadd;
    }

    public void setCheckadd(boolean checkadd) {
        this.checkadd = checkadd;
    }

    public List<Announcement> getAllAnnouncements() {
        return allAnnouncements;
    }

    public void setAllAnnouncements(List<Announcement> allAnnouncements) {
        this.allAnnouncements = allAnnouncements;
    }

    public List<Company> getAllCompanys() {
        return allCompanys;
    }

    public void setAllCompanys(List<Company> allCompanys) {
        this.allCompanys = allCompanys;
    }

    public List<Account> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(List<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public AdminSessionLocal getTmp() {
        return tmp;
    }

    public void setTmp(AdminSessionLocal tmp) {
        this.tmp = tmp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccessright() {
        return accessright;
    }

    public void setAccessright(String accessright) {
        this.accessright = accessright;
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

}
