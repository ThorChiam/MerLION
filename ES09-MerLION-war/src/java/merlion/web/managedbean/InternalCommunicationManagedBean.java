/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.InternalCommunicationSessionBeanLocal;
import merlion.entity.CommonInfrastructure.Announcement;
import merlion.entity.CommonInfrastructure.Notification;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "internalCommunicationManagedBean")
@SessionScoped
public class InternalCommunicationManagedBean implements Serializable {

    /**
     * Creates a new instance of InternalCommunicationManagedBean
     */
    private InternalCommunicationSessionBeanLocal icsbl;
    private Long noti_Id;
    private String n_title;
    private String content;
    private Long release_time;
    private String target;
    public InternalCommunicationManagedBean() {
    }
    public void createannou(Long id){
        icsbl.createannou(id, noti_Id, content);
    }

    public void createnoti(String email){
        icsbl.createnoti(email, noti_Id, n_title, content, release_time, target);
    }
//user will view all annoucements

    public List getAnnou(String email){
        return icsbl.getAnnou();
    }

    public List getNoti(String email){
        return icsbl.getNoti(email);
    }

    public Announcement get_the_Announ(Long releasetime){
        return icsbl.get_the_Announ(releasetime);
    }

    public Notification get_the_Noti(String email, Long sendtime){
        return icsbl.get_the_Noti(email, sendtime);
    }
    public void deleteannouncement(Long announ_Id){
        icsbl.deleteannouncement(announ_Id);
    }
    public void deletenotification(String email, Long noti_Id){
        icsbl.deletenotification(email, noti_Id);
    }
    public void remove(){
        icsbl.remove();
    }
}
