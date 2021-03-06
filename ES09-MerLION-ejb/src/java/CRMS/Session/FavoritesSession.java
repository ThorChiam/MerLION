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
import CI.Entity.Account;
import CRMS.Entity.Company;
import CRMS.Entity.Favorite;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class FavoritesSession implements FavoritesSessionLocal {

    @PersistenceContext
    private EntityManager em;
    
    private Favorite fav;
    @Override
    public List<Favorite> getFavoriteList(String email){
        Query p = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        p.setParameter("email",email);
        Account account=(Account)p.getSingleResult();
        long tmp=account.getCompany().getId();        
        
        Query q = em.createQuery("SELECT f FROM Favorite f WHERE f.favoritor.id=:id");
        q.setParameter("id", tmp);
        return q.getResultList();
    }
    @Override
    public Favorite getFavorite(String email,Long companyId){
        Query q = em.createQuery("SELECT f FROM Favorite f WHERE f.Account.email=:email AND f.company.id=:id");
        q.setParameter("email", email);
        q.setParameter("id", companyId);
        return (Favorite)q.getSingleResult();
    }
    @Override
    public void addFavorite(String email,Long companyId,String remark){
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account)q.getSingleResult();
        q = em.createQuery("SELECT c FROM Company c WHERE c.id=:companyId");
        q.setParameter("id", companyId);
        Company c = (Company)q.getSingleResult();
        fav = new Favorite();
        fav.setFavoritee(c);
        fav.setRemarks(remark);
        em.persist(fav);
    }
    @Override
    public void deleteFavorite(String email,Long companyId){
        Query q = em.createQuery("DELETE FROM Favorite f WHERE f.Account.email=:email AND f.company.id=:id");
        q.setParameter("email", email);
        q.setParameter("id", companyId);
        q.executeUpdate();
    }
    @Override
    public String updateFavorite(String email,Long companyId,String remark){
        Query q = em.createQuery("SELECT f FROM Favorite f WHERE f.Account.email=:email AND f.company.id=:id");
        q.setParameter("email", email);
        q.setParameter("id", companyId);
        fav = (Favorite)q.getSingleResult();
        fav.setRemarks(remark);
        em.merge(fav);
        return fav.getFavoritee().getCompanyName();
    }
}
