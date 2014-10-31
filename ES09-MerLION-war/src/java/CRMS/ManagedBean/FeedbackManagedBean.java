/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Company;
import CRMS.Entity.Feedback;
import CRMS.Session.FeedbackSessionLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author sunny
 */

@ManagedBean(name = "fmb")
@SessionScoped
@ViewScoped

public class FeedbackManagedBean implements Serializable{
    @EJB
    private FeedbackSessionLocal fsbl;
    
    private double rating;
    private String feedback_content;
    private Company sender;
    private Company receiver;
    private String statusMessage;

    
    public FeedbackManagedBean() {    
    }  
    
    public void createFeedback(String email){
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String feedback_date = tmp.toString();
        fsbl.createFeedback(rating,feedback_content,feedback_date,sender,receiver);
        statusMessage = "Your feedback has been sent successfully.";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Status: " + statusMessage, ""));
    }
    
    public Feedback getFeedback(long feedback_id){
        return fsbl.getFeedback(feedback_id);
    }
    
    public List<Feedback> getAllFeedback(String email){
        return fsbl.getAllFeedback(email);
    }
    
    
    
    //****************Getter and Setter********************
    
    public FeedbackSessionLocal getFsbl() {
        return fsbl;
    }

    public void setFsbl(FeedbackSessionLocal fsbl) {
        this.fsbl = fsbl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public Company getSender() {
        return sender;
    }

    public void setSender(Company sender) {
        this.sender = sender;
    }

    public Company getReceiver() {
        return receiver;
    }

    public void setReceiver(Company receiver) {
        this.receiver = receiver;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
