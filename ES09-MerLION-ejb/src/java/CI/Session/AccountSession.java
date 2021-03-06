/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CI.Session;

import java.security.MessageDigest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import CI.Entity.Account;
import CRMS.Entity.Company;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class AccountSession implements AccountSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private Account account;

    public AccountSession() {
    }

    //get individual account info
    @Override
    public Account getAccount(String email) {
        Query q = em.createQuery("SELECT s FROM Account s WHERE s.email=:email");
        q.setParameter("email", email);
        System.out.println("$$$$$$ Account session: getAccount: " + email);

//        Account systemUser = null;
//        try {
//            systemUser = (Account) q.getSingleResult();
//        } catch (NoResultException ex) {
//            ex.printStackTrace();
//        }
        return (Account) q.getSingleResult();
    }

    @Override
    public void updateAccountInfo(String cemail, String qun, String ans) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:cemail");
        q.setParameter("cemail", cemail);
        account = (Account) q.getSingleResult();
        account.setSecurity_question(qun);
        account.setSecurity_answer(ans);

        em.merge(account);
    }

    @Override
    public String validate(String email, String password) {
        boolean check=false;
        Query q = em.createQuery("SELECT a FROM Account a");
        List<Account> tmp= (List<Account>)q.getResultList();
        for(int i=0;i<tmp.size();i++){
            if(tmp.get(i).getEmail().equals(email)){
                check=true;
                break;
            }
        }

      //  System.out.println("*****test accountSession: email" + systemUser.getEmail());     
        if (!check) {
            return null;
        } else {
            Account systemUser=getAccount(email);
            if (systemUser.getPassword().equals(doMD5Hashing(password))) {
                if (systemUser.getStatus().equals("activated")) {
                    return systemUser.getCompany().getCompanyName();

                } else {
                    return "frozen";
                }
            } else {
                return "wrong password";
            }
        }

    }

    @Override
    public String createMoreAccount(String aemail, String email, String password, String qun, String ans, String status) {
        Account a = new Account();
        a.setEmail(email);
        a.setPassword(password);
        a.setSecurity_question(qun);
        a.setSecurity_answer(ans);
        a.setStatus(status);

        Query q = em.createQuery("SELECT a.Company FROM Account a WHERE a.email=:aemail");
        q.setParameter("aemail", aemail);
        a.setCompany((Company) q.getSingleResult());
      

        em.persist(a);
        return a.getEmail();
    }

    @Override
    public String createaccount(String email, String password, String accessright, String status, String security_question, String security_answer) {
        Account f = new Account();
        f.setEmail(email);
        f.setAccessRight(accessright);
        f.setPassword(doMD5Hashing(password));
        f.setSecurity_question(security_question);
        f.setSecurity_answer(security_answer);
        f.setStatus(status);
        System.out.println(f.getEmail() + ";" + f.getPassword());
//        try {
//            ic = new InitialContext();
//            utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
//            try {
//                utx.begin();
//            } catch (NotSupportedException ex) {
//                Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SystemException ex) {
//                Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (NamingException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        }

        em.persist(f);
//        try {
//            utx.commit();
//        } catch (RollbackException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (HeuristicMixedException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (HeuristicRollbackException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SecurityException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalStateException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SystemException ex) {
//            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        em.flush();
        return f.getEmail();
    }

    @Override
    public void updateaccount(String email, String comp_name, String comp_address, String comp_contact_no, String status) {
        Query query = em.createQuery("UPDATE Account c SET c.comp_name=?2,c.comp_address=?3,c.comp_contact_no=?4,c.status=?5 WHERE c.email=?1");
        query.setParameter(2, comp_name);
        query.setParameter(3, comp_address);
        query.setParameter(4, comp_contact_no);
        query.setParameter(1, email);
        query.setParameter(5, status);
        query.executeUpdate();
    }

    @Override
    public void resetpassword(String email, String newpassword) {
        Query query = em.createQuery("UPDATE Account c SET c.password=?1 WHERE c.email=?2");
        query.setParameter(1, doMD5Hashing(newpassword));
        query.setParameter(2, email);
        query.executeUpdate();
    }

    @Override
    public List<Account> getAccounts() {
        Query query = em.createQuery("SELECT v FROM Account v");
        return query.getResultList();
    }

    @Override
    public void deleteaccount(String email) {
        Query query = em.createQuery("Delete From Account e where e.email=:email");
        query.setParameter("email", email);
        query.executeUpdate();
    }

    @Override
    public void remove() {
        System.out.println("Accountmanage:remove()");
    }

    private String doMD5Hashing(String stringToHash) {
        MessageDigest md = null;
        byte[] hashSum = null;

        try {
            md = MessageDigest.getInstance("MD5");
            hashSum = md.digest(stringToHash.getBytes());
        } catch (Exception ex) {
            System.err.println("********** Exception: " + ex);
        }

        if (hashSum != null) {
            return byteArrayToHexString(hashSum);
        } else {
            return null;
        }
    }

    private String byteArrayToHexString(byte[] bytes) {
        int lo = 0;
        int hi = 0;
        String hexString = "";

        for (int i = 0; i < bytes.length; i++) {
            lo = bytes[i];
            lo = lo & 0xff;
            hi = lo >> 4;
            lo = lo & 0xf;
            if (hi == 0) {
                hexString += "0";
            } else if (hi == 1) {
                hexString += "1";
            } else if (hi == 2) {
                hexString += "2";
            } else if (hi == 3) {
                hexString += "3";
            } else if (hi == 4) {
                hexString += "4";
            } else if (hi == 5) {
                hexString += "5";
            } else if (hi == 6) {
                hexString += "6";
            } else if (hi == 7) {
                hexString += "7";
            } else if (hi == 8) {
                hexString += "8";
            } else if (hi == 9) {
                hexString += "9";
            } else if (hi == 10) {
                hexString += "a";
            } else if (hi == 11) {
                hexString += "b";
            } else if (hi == 12) {
                hexString += "c";
            } else if (hi == 13) {
                hexString += "d";
            } else if (hi == 14) {
                hexString += "e";
            } else if (hi == 15) {
                hexString += "f";
            }

            if (lo == 0) {
                hexString += "0";
            } else if (lo == 1) {
                hexString += "1";
            } else if (lo == 2) {
                hexString += "2";
            } else if (lo == 3) {
                hexString += "3";
            } else if (lo == 4) {
                hexString += "4";
            } else if (lo == 5) {
                hexString += "5";
            } else if (lo == 6) {
                hexString += "6";
            } else if (lo == 7) {
                hexString += "7";
            } else if (lo == 8) {
                hexString += "8";
            } else if (lo == 9) {
                hexString += "9";
            } else if (lo == 10) {
                hexString += "a";
            } else if (lo == 11) {
                hexString += "b";
            } else if (lo == 12) {
                hexString += "c";
            } else if (lo == 13) {
                hexString += "d";
            } else if (lo == 14) {
                hexString += "e";
            } else if (lo == 15) {
                hexString += "f";
            }
        }

        return hexString;
    }
}
