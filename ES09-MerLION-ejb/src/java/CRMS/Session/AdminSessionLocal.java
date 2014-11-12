/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Account;
import CI.Entity.Announcement;
import CI.Entity.Customer_Enquiry;
import CRMS.Entity.Company;
import CRMS.Entity.Post;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sunny
 */
@Local
public interface AdminSessionLocal {
    //About Account
    public void updateUserInfo(String email, String password, String accessright,String security_question, String security_answer);
    public Account getAccount(String email);
    public List<Account> getAllAccount();
    public void updateStatus(String email, String status);
    public void deleteAccount(String email);
    
    //About Company
    public List<Company> getAllCompany();
    public Company getCompany(long company_id);
    public void deleteCompany(long company_id);
    public void updateCompanyInfo(long id, String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision);

    //About Anouncement
    public void createAnnoun(String content, String email);
    public void deleteAnnoun(long id);
    public Announcement getAnnoun(long id);
    public List<Announcement> getAllAnnoun();
    
    //About Enquiry
    public Customer_Enquiry getEnquiry(long id);
    public List<Customer_Enquiry> getAllEnquiry();
    public void replyEnquiry(String answer,String email, long id);
    
    //About Post
    public Post getPost(long id);
    public List<Post> getAllPost();
    public void deletePost(long id);
}
