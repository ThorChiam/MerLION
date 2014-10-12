/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.security.MessageDigest;
import java.util.List;
import merlion.ejb.local.AccountSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.CommonInfrastructure.Account;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class AccountSessionBean implements AccountSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    /**
     *
     * @param email String: the email of the user
     * @return - Account: the account entity of this user
     */
    @Override
    public Account getAccount(String email) {
        Query q = em.createQuery("SELECT s FROM Account s WHERE s.email=:email");
        q.setParameter("email", email);
        Account systemUser = null;
        try {
            systemUser = (Account) q.getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return systemUser;
    }

    @Override
    public String validate(String email, String password) {
        //To change body of generated methods, choose Tools | Templates.
        Account systemUser = getAccount(email);
        if (systemUser == null) {
            return null;
        } else {
            if (systemUser.getPassword().equals(doMD5Hashing(password))) {
                if (systemUser.getStatus().equals("activated")) {
                    return systemUser.getComp_name();
                } else {
                    return "frozen";
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public String createaccount(String email, String password, String comp_name, String comp_address, String comp_contact_no,
            String accessright, String status, String security_question, String security_answer) {
        Account f = new Account();
        f.setEmail(email);
        f.setAccessRight(accessright);
        f.setComp_address(comp_address);
        f.setComp_contact_no(comp_contact_no);
        f.setPassword(doMD5Hashing(password));
        f.setSecurity_question(security_question);
        f.setSecurity_answer(security_answer);
        f.setStatus(status);
        f.setComp_name(comp_name);

        em.persist(f);
        em.flush();
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
