/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;

import java.util.List;
import javax.ejb.Local;
import merlion.entity.MerlionAdmin;

/**
 *
 * @author ThorChiam
 */
@Local
public interface AdministratorSessionBeanLocal {

    public MerlionAdmin getMerlionAdmin(String email);

    public String validate(String email, String password);

    public String createMerlionAdmin(String email, String password, String accessright, String status, String security_question, String security_answer);

    public void resetpassword(String email, String newpassword);

    public List<MerlionAdmin> getMerlionAdmins();

    public void deleteMerlionAdmin(String email);

    public void remove();

    public void storeAction(String email, long accessTime, String accessed);
}
