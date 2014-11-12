/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Account;
import CI.Entity.Announcement;
import CI.Entity.Customer_Enquiry;
import CI.Entity.MerlionAdmin;
import CRMS.Entity.Company;
import CRMS.Entity.Post;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sunny
 */
@Stateless
@LocalBean
public class AdminSession implements AdminSessionLocal{
    @PersistenceContext
    private EntityManager em;

    
    //***********************About Account*************************
    @Override
    public void updateUserInfo(String email, String password, String accessright, String security_question, String security_answer) {
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        Account tmp=(Account)q.getSingleResult();
        tmp.setPassword(password);
        tmp.setAccessRight(accessright);
        tmp.setSecurity_question(security_question);
        tmp.setSecurity_answer(security_answer);
        em.persist(tmp);
    }

    @Override
    public Account getAccount(String email) {
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        Account tmp=(Account)q.getSingleResult();
        return tmp;
    }

    @Override
    public List<Account> getAllAccount() {
        Query query = em.createQuery("SELECT v FROM Account v WHERE v.accessright<>:type");
        query.setParameter("type", "Admin");
        return query.getResultList();
    }

    @Override
    public void updateStatus(String email, String status) {
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        Account tmp=(Account)q.getSingleResult();
        tmp.setStatus(status);
        em.persist(tmp);
    }
    
    @Override
    public void deleteAccount(String email){
        Query q = em.createQuery("DELETE FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        q.executeUpdate();
    }
    
    
    
    
    
    //*************************About Company*************************
    @Override
    public List<Company> getAllCompany(){
        Query q = em.createQuery("SELECT e FROM Company e WHERE e.companyName<>:merlion");
        q.setParameter("merlion", "Merlion Logistic");
        return q.getResultList();
    }
    
    @Override
    public Company getCompany(long company_id){
        Query q = em.createQuery("SELECT e FROM Company e WHERE e.id=:id");
        q.setParameter("id", company_id);
        return (Company)q.getSingleResult();
    }
    
    @Override
    public void deleteCompany(long company_id){     
        Query p = em.createQuery("Delete FROM Account a where a.Company.id=:company_id");
        p.setParameter("company_id", company_id);
        p.executeUpdate();
        
        
        Query q = em.createQuery("Delete FROM Company c where c.id=:companyId");
        q.setParameter("companyId", company_id);
        q.executeUpdate();
        
      
    }
    
    @Override
    public void updateCompanyInfo(long company_id, String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision){
        Query q = em.createQuery("SELECT c FROM Company c WHERE c.id=:id AND c.companyName<>:merlion");
        q.setParameter("merlion", "Merlion Logistic");
        q.setParameter("id", company_id);
        Company company = (Company) q.getSingleResult();
        company.setCompanyName(companyName);
        company.setCompanyAddress(companyAddress);
        company.setTel(tel);
        company.setEmail(email);
        company.setWebsite(website);
        company.setCompanyHistory(companyHistory);
        company.setService(service);
        company.setVision(vision);
        em.merge(company);
    }
    
    
    
    //**************************About Announcement*****************************
    @Override
    public void createAnnoun(String content, String email){
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String createdate = tmp.toString();
        System.out.println("Admin:"+email);
        Query q = em.createQuery("SELECT a FROM MerlionAdmin a WHERE a.Account.email=:email");
        q.setParameter("email", email);
        MerlionAdmin admin = (MerlionAdmin)q.getSingleResult();
        
        Announcement announ=new Announcement();
        announ.setContent(content);
        announ.setMerlionAdmin(admin);
        announ.setReleasedate(createdate);
        em.persist(announ);
    }
    
    @Override
    public void deleteAnnoun(long id){
        Query p = em.createQuery("Delete FROM Announcement a where a.annId=:id");
        p.setParameter("id", id);
        p.executeUpdate();       
    }
    
    @Override
    public Announcement getAnnoun(long id){
        Query p = em.createQuery("SELECT a FROM Announcement a where a.annId=:id");
        p.setParameter("id", id);
        return (Announcement)p.getSingleResult();
    }
    
    @Override
    public List<Announcement> getAllAnnoun(){
        Query q = em.createQuery("SELECT a FROM Announcement a");
        return q.getResultList();
    }

    
    //********************************About Enquiry************************
    @Override
    public Customer_Enquiry getEnquiry(long id){
        Query p = em.createQuery("SELECT a FROM Customer_Enquiry a where a.id=:id");
        p.setParameter("id", id);
        return (Customer_Enquiry)p.getSingleResult();
    }
    
    @Override
    public List<Customer_Enquiry> getAllEnquiry(){
        Query q = em.createQuery("SELECT a FROM Customer_Enquiry a");
        return q.getResultList();
    }
    
    @Override
    public void replyEnquiry(String answer, String email, long id){
        Query p = em.createQuery("SELECT a FROM Customer_Enquiry a WHERE a.id=:id");
        p.setParameter("id", id);
        Customer_Enquiry tmp = (Customer_Enquiry)p.getSingleResult();
        
        Query q = em.createQuery("SELECT b FROM MerlionAdmin b WHERE b.Account.email=:theemail");
        q.setParameter("theemail",email);
        MerlionAdmin admin = (MerlionAdmin)q.getSingleResult();
        
        
        Date date = new java.util.Date();
        Timestamp time = new Timestamp(date.getTime());
        String createdate = time.toString();
        
        tmp.setStatus("solved");
        tmp.setAnswer(answer);
        tmp.setMerlionAdmin(admin);
        tmp.setAnswertime(createdate);
        em.persist(tmp);
    }
    
    
    
    
    
    //****************************About Post**************************
    @Override
    public Post getPost(long id){
        Query p = em.createQuery("SELECT a FROM Post a WHERE a.id=:id");
        p.setParameter("id", id);
        return (Post)p.getSingleResult();
    }
    
    @Override
    public List<Post> getAllPost(){
        Query p = em.createQuery("SELECT a FROM Post a");
        return p.getResultList();
    }
    
    @Override 
    public void deletePost(long id){
        Query p = em.createQuery("Delete FROM Post a WHERE a.id=:id");
        p.setParameter("id", id);
        p.executeUpdate();
    }
}
