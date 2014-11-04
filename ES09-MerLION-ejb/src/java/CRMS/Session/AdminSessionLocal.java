/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Account;
import java.util.List;

/**
 *
 * @author sunny
 */
public interface AdminSessionLocal {
    public void updateUserInfo(String email, String password, String accessright, String status, String security_question, String security_answer);
    public Account getAccount(String email);
    public List<Account> getAllAccount();
    public void activateAccount(String email);
    public void deactivateAccount(String email);
}
