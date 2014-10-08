/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.util.List;
import merlion.ejb.local.InternalCommunicationSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.Account;
import merlion.entity.Admin;
import merlion.entity.Announcement;
import merlion.entity.Notification;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class InternalCommunicationSessionBean implements InternalCommunicationSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createannou(Long id, Long announ_Id, String content) {
        Query q = em.createQuery("SELECT a FROM Admin a WHERE a.id=:id");
        q.setParameter("id", id);
        Admin admin = (Admin) q.getSingleResult();
        Announcement announ = new Announcement();
        announ.setAdmin(admin);
        announ.add(announ_Id, content);
        em.persist(announ);
    }

    @Override
    public void createnoti(String email, Long noti_Id, String n_title, String content, Long release_time, String target) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account) q.getSingleResult();
        Notification noti = new Notification();
        noti.setAccount(a);
        noti.add(n_title, content, release_time, target);
        em.persist(noti);
    }
//user will view all annoucements

    @Override
    public List getAnnou() {
        Query query = em.createQuery("SELECT v FROM Announcement v");
        return query.getResultList();
    }

    @Override
    public List getNoti(String email) {
        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.Account.email=:email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public Announcement get_the_Announ(Long releasetime) {
        Announcement announcement = em.find(Announcement.class, releasetime);
        return announcement;
    }

    @Override
    public Notification get_the_Noti(String email, Long sendtime) {
        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.Account.email=:email AND n.release_time=:sendtime");
        q.setParameter("email", email);
        q.setParameter("sendtime", sendtime);
        Notification notification = (Notification) q.getSingleResult();
        return notification;
    }

    @Override
    public void deleteannouncement(Long announ_Id) {
        Query query = em.createQuery("Delete From Announcement e where e.annId=:announ_Id");
        query.setParameter("announ_Id", announ_Id);
        query.executeUpdate();
    }

    @Override
    public void deletenotification(String email, Long noti_Id) {
        Query query = em.createQuery("Delete From Notification e where e.Account.email=:email AND e.notiId=:noti_Id");
        query.setParameter("noti_Id", noti_Id);
        query.executeUpdate();
    }

    @Override
    public void remove() {
        System.out.println("Intercomm:remove()");
    }
}
