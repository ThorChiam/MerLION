/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "accountManagedBean")
@SessionScoped
public class AccountManagedBean implements Serializable {

    /**
     * Creates a new instance of AccountManagedBean
     */
    public AccountManagedBean() {
    }
    
}
