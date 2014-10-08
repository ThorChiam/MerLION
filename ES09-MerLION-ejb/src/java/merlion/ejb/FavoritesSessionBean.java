/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.util.List;
import merlion.ejb.local.FavoritesSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.Account;
import merlion.entity.Company;
import merlion.entity.Favorite;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class FavoritesSessionBean implements FavoritesSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;
    
    private Favorite fav;
    @Override
    public List<Favorite> getFavoriteList(String email){
        Query q = em.createQuery("SELECT f FROM Favorite f WHERE f.Account.email=:email");
        q.setParameter("email", email);
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
        fav.setAccount(a);
        fav.setCompany(c);
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
        return fav.getCompany().getAccount().getComp_name();
    }
}
