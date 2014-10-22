/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.ManagedBean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "notificationManagedBean")
@SessionScoped
public class NotificationManagedBean implements Serializable {

    /**
     * Creates a new instance of NotificationManagedBean
     */
    public NotificationManagedBean() {
    }
    
}
