/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TMS.ManagedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import TMS.Session.ClusterSession;
import TMS.Entity.ClusterRule;


/**
 *
 * @author Zeng Xunhao
 *//**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "ClusterMB")
@ViewScoped
public class ClusterManagedBean implements Serializable {

    
    @EJB
    private ClusterSession CSession;   
    private List<ClusterRule> rules=new ArrayList();
    private String userId;
    private String statusMessage="no message";
    private ClusterRule rule=new ClusterRule();
             
    public ClusterManagedBean() {
        
    }
    
    @PostConstruct
    public void init(){      
        userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        rules=CSession.getAllRule(userId);
    }  
   
    
    public void addNewRule(ActionEvent Event){
     
        Long id=CSession.addRule(rule, userId);
        statusMessage="New Catalog "+id+ " added successfully!";     
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
           
    }
    
    
    public void deleteRule(ClusterRule r){
        CSession.deleteRule(r.getId());
    }

    public String getStatusMessage(){
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<ClusterRule> getRules() {
        return rules;
    }

    public void setRules(List<ClusterRule> rules) {
        this.rules = rules;
    }

    public ClusterRule getRule() {
        return rule;
    }

    public void setRule(ClusterRule rule) {
        this.rule = rule;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}