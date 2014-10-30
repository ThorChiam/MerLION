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

/**
 *
 * @author sunny
 */
@Stateless
@LocalBean
public class FeedbackSession implements FeedbackSessionLocal {

    @Override
    public void createFeedback(double rating, String feedback_content, String feedback_date, Company sender, Company receiver) {
        Feedback tmp = new Feedback();
        tmp.setFeedback_content(feedback_content);
        tmp.setFeedback_date(feedback_date);
        tmp.setRating(rating);
        tmp.setReciever(receiver);
        tmp.setSender(sender);
    }

    @Override
    public Feedback getFeedback(long feedback_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Feedback> getAllFeedback(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
