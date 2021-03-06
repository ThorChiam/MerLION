/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.Session;

import java.util.List;
import javax.ejb.Local;
import CI.Entity.Announcement;
import CI.Entity.Notification;

/**
 *
 * @author ThorChiam
 */
@Local
public interface InternalCommunicationSessionLocal {

    public void createannou(Long id, Long announ_Id, String content);

    public void createnoti(String email, Long noti_Id, String n_title, String content, Long release_time);
//user will view all annoucements

    public List getAnnou();

    public List getNoti(String email);

    public Announcement get_the_Announ(Long releasetime);

    public Notification get_the_Noti(String email, Long sendtime);

    public void deleteannouncement(Long announ_Id);

    public void deletenotification(String email, Long noti_Id);

    public List<Notification> getServiceRequest(String email);

    public void contractCreate(Long notiId);

    public void remove();
}
