/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface EnquireSessionLocal {
    public void createEnquiry(String email, String qun);
}
