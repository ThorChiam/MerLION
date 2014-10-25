/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CRMS.Entity.Company;
import CI.Entity.Account;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class CompanyProfileSession implements CompanyProfileSessionLocal {

    @PersistenceContext
    private EntityManager em;

    private Company company;

    @Override
    public void addCompanyProfile(String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        System.out.println("********" + email);
        List<Account> as = (List<Account>) q.getResultList();
        company = new Company();
        company.setAccount(as);
        company.create(companyName, companyAddress, tel, email, website, companyHistory, service, vision);
        em.persist(company);
        as.get(0).setCompany(company);
        em.persist(as.get(0));
        em.flush();

        //return company.getId();
    }

    @Override
    public List getAllCompanyProfile(String email) {
        Query q = em.createQuery("SELECT e FROM Company e WHERE e.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    //admin can delete company profile
    @Override
    public String deleteCompanyProfile(Long companyId) {
        Query q = em.createQuery("SELECT c.companyName FROM Company c WHERE c.id=:companyId");
        q.setParameter("companyId", companyId);
        String companyName = (String) q.getSingleResult();

        q = em.createQuery("Delete FROM Company c where c.id=:companyId");
        q.setParameter("companyId", companyId);
        q.executeUpdate();

        return companyName;
    }

    //update company profile
    @Override
    public void updateCompanyProfile(String cemail, String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision) {
        Query q = em.createQuery("SELECT c FROM Company c WHERE c.Account.email=:cemail");
        company = (Company) q.getSingleResult();
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

}
