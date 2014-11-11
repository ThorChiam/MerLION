/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.Session;

import java.util.List;
import javax.ejb.Local;
import CI.Entity.Account;

/**
 *
 * @author ThorChiam
 */
@Local
public interface AccountSessionLocal {

    public Account getAccount(String email);

    public String validate(String email, String password);

    public String createaccount(String email, String password,String accessright, String status, String security_question, String security_answer);

    public void updateaccount(String email, String comp_name, String comp_address, String comp_contact_no, String status);

    public void resetpassword(String email, String newpassword);

    public List<Account> getAccounts();
    
    public void updateAccountInfo(String cemail, String qun, String ans);

    public void deleteaccount(String email);
    
     public String createMoreAccount(String aemail, String email, String password, String qun, String ans, String status);

    public void remove();
}
