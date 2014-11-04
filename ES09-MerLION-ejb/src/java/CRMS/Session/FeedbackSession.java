/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Company;
import CRMS.Entity.Feedback;
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
public class FeedbackSession implements FeedbackSessionLocal {
    @PersistenceContext
    private EntityManager em;
    
    
    @Override
    public void createFeedback(double rating, String feedback_content, String feedback_date, Company sender, Company receiver) {
        Feedback tmp = new Feedback();
        tmp.setFeedback_content(feedback_content);
        tmp.setFeedback_date(feedback_date);
        tmp.setRating(rating);
        tmp.setReciever(receiver);
        tmp.setSender(sender);
        em.persist(tmp);
    }

    @Override
    public Feedback getFeedback(long feedback_id) {
        Query q = em.createQuery("SELECT f FROM Product f WHERE f.id=:id");
        q.setParameter("id", feedback_id);
        return (Feedback) q.getSingleResult();
    }

    @Override
    public List<Feedback> getAllFeedback(String email) {
        Query q = em.createQuery("SELECT f FROM Feedback f WHERE f.feedback_receiver.email=:email OR f.feedback_receiver.email=:emails");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        return q.getResultList();
    }

}
