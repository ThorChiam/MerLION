/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.Session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CRMS.Entity.Company;
import CI.Entity.MerlionAdmin;
import CI.Entity.Announcement;
import CI.Entity.Notification;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class InternalCommunicationSession implements InternalCommunicationSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createannou(Long id, Long announ_Id, String content) {
        Query q = em.createQuery("SELECT a FROM MerlionAdmin a WHERE a.id=:id");
        q.setParameter("id", id);
        MerlionAdmin admin = (MerlionAdmin) q.getSingleResult();
        Announcement announ = new Announcement();
        announ.setMerlionAdmin(admin);
        announ.add(announ_Id, content);
        em.persist(announ);
    }

    @Override
    public void createnoti(String email, Long noti_Id, String n_title, String content, Long release_time) {
        Query q = em.createQuery("SELECT a FROM Company a WHERE a.email=:email");
        q.setParameter("email", email);
        Company a = (Company) q.getSingleResult();
        Notification noti = new Notification();
        noti.setCompany(a);
        noti.add(n_title, content, release_time);
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
        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.Company.email=:email");
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
        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.Company.email=:email AND n.release_time=:sendtime");
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
        Query query = em.createQuery("Delete From Notification e where e.Company.email=:email AND e.notiId=:noti_Id");
        query.setParameter("noti_Id", noti_Id);
        query.executeUpdate();
    }

    @Override
    public List<Notification> getServiceRequest(String email) {
        Query q = em.createQuery("SELECT a.Company FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Company c = (Company) q.getSingleResult();
        q = em.createQuery("SELECT n FROM Notification n WHERE n.company.id=:companyId AND n.n_title=:titile");
        q.setParameter("companyId", c.getId());
        q.setParameter("titile", "Service Request");
        List<Notification> notis = q.getResultList();
        return notis;
    }
    @Override
    public void contractCreate(Long notiId) {
        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.notiId=:notiId");
        q.setParameter("notiId", notiId);
        Notification noti = (Notification) q.getSingleResult();
        noti.setN_title("Contract Created");
        em.merge(noti);
    }

    @Override
    public void remove() {
        System.out.println("Intercomm:remove()");
    }
}
