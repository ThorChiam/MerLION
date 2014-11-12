/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.ManagedBean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import CI.Session.InternalCommunicationSessionLocal;
import CI.Entity.Announcement;
import CI.Entity.Notification;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

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
    @EJB
    private InternalCommunicationSessionLocal icsbl;
    private Long noti_Id;
    private String n_title;
    private String content;
    private Long release_time;
    private String email;
    private List<Notification> serviceRequest;

    public InternalCommunicationManagedBean() {
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        if (email != null) {
            this.serviceRequest = icsbl.getServiceRequest(email);
        }
    }

    public List<Notification> getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(List<Notification> serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public void createannou(Long id) {
        icsbl.createannou(id, noti_Id, content);
    }

    public void createnoti(String email) {
        icsbl.createnoti(email, noti_Id, n_title, content, release_time);
    }
//user will view all annoucements

    public List getAnnou(String email) {
        return icsbl.getAnnou();
    }

    public List getNoti(String email) {
        return icsbl.getNoti(email);
    }

    public Announcement get_the_Announ(Long releasetime) {
        return icsbl.get_the_Announ(releasetime);
    }

    public Notification get_the_Noti(String email, Long sendtime) {
        return icsbl.get_the_Noti(email, sendtime);
    }

    public void deleteannouncement(Long announ_Id) {
        icsbl.deleteannouncement(announ_Id);
    }

    public void deletenotification(String email, Long noti_Id) {
        icsbl.deletenotification(email, noti_Id);
    }

    public void remove() {
        icsbl.remove();
    }
}
