/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.util.List;
import merlion.ejb.local.CompanyProfileSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.CRMS.Company;
import merlion.entity.CommonInfrastructure.Account;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class CompanyProfileSessionBean implements CompanyProfileSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    private Company companyProfile;

    @Override
    public void addCompanyProfile(String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        System.out.println("********"+email);
        List<Account> as = (List<Account>) q.getResultList();
        companyProfile = new Company();
        companyProfile.setAccount(as);
        as.get(0).setCompany(companyProfile);
        companyProfile.create(companyName, companyAddress, tel, email, website, companyHistory, service, vision);
        em.persist(companyProfile);
        em.persist(as.get(0));
        em.flush();

        //return companyProfile.getId();
    }

    @Override
    public List getAllCompanyProfile(String email) {
        Query q = em.createQuery("SELECT e FROM CompanyProfile e WHERE e.Account.email=?1");
        q.setParameter(1, email);
        return q.getResultList();
    }

    //admin can delete company profile
    @Override
    public String deleteCompanyProfile(String email, Long companyId) {
        Query query = em.createQuery("SELECT e.companyName FROM CompanyProfile e where e.id=?1 AND e.Account.email=?3");
        query.setParameter(1, companyId);
        query.setParameter(3, email);
        String companyName = (String) query.getSingleResult();

        query = em.createQuery("Delete FROM CompanyProfile e where e.id=?2");
        query.setParameter(2, companyId);
        query.executeUpdate();

        return companyName;
    }

    //update company profile
    @Override
    public void updateCompanyProfile(Long companyId, String companyName, String companyAddress, String tel, String email, String website, String companyHistory, String service, String vision) {
        Query query = em.createQuery("UPDATE CompanyProfile c SET c.companyAddress=?1, c.companyName=?2,c.tel=?3,c.email=?4,c.website=?5,c.companyHistory=?6,c.service=?7,c.vision=?8  WHERE c.id=?9 AND c.Account.email=?10");
        query.setParameter(1, companyAddress);
        query.setParameter(2, companyName);
        query.setParameter(3, tel);
        query.setParameter(4, email);
        query.setParameter(5, website);
        query.setParameter(6, companyHistory);
        query.setParameter(7, service);
        query.setParameter(8, vision);
        query.setParameter(9, companyId);
        query.setParameter(10, email);

        query.executeUpdate();

    }

}
