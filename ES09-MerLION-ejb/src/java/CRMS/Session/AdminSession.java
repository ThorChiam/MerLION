/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Account;
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

    @Override
    public void updateUserInfo(String email, String password, String accessright, String status, String security_question, String security_answer) {
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        Account tmp=(Account)q.getSingleResult();
        tmp.setPassword(password);
        tmp.setAccessRight(accessright);
        tmp.setStatus(status);
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
        Query query = em.createQuery("SELECT v FROM Account v");
        return query.getResultList();
    }

    @Override
    public void activateAccount(String email) {
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        Account tmp=(Account)q.getSingleResult();
        tmp.setStatus("activated");
        em.persist(tmp);
    }

    @Override
    public void deactivateAccount(String email) {
        Query q = em.createQuery("SELECT f FROM Account f WHERE f.email=:email");
        q.setParameter("email", email);
        Account tmp=(Account)q.getSingleResult();
        tmp.setStatus("deactivated");
        em.persist(tmp);
    }
}
