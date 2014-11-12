/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Company;
import CRMS.Entity.Feedback;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sunny
 */
@Local
public interface FeedbackSessionLocal {
    public void createFeedback(double rating, String feedback_content, String feedback_date, Company sender, Company receiver);
    public Feedback getFeedback(long feedback_id);
    public List<Feedback> getAllFeedback(String email);
}
