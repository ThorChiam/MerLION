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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import CI.Session.AccountSessionLocal;
import CI.Entity.Account;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "accountManagedBean")
@ViewScoped
public class AccountManagedBean implements Serializable {

    /**
     * Creates a new instance of AccountManagedBean
     */
    @EJB
    private AccountSessionLocal asbl;
    private String aemail;
    private String email;
    private String password;
    private String comp_name;
    private String comp_contact_no;
    private String comp_address;
    private String accessright = "member";
    private String status = "no";
    private String security_question;
    private String security_answer;
    private String statusMessage;
    private String newpassword;
    private Boolean ans;
    private Account account;
    private Boolean login = false;
    private String response;
    private HttpServletRequest req;
    private List<Account> accountInfo;
    
    @ManagedProperty("#{accountList}")
    private List<Account> accountList;
    

    public AccountManagedBean() {
//        asbl = new AccountSessionBean();
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        
        if(email!=null){
            accountInfo=this.viewAccount();
        }
       
    }

    public void signup() {
        System.out.println("asbl:" + (asbl == null) + ";email:" + (email == null) + ";pw:" + (password == null));
        email = asbl.createaccount(email, password, accessright, status, security_question, security_answer);
        statusMessage = "sign up successful!";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accountCreated", "true");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", email);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage + " (Welcome " + comp_name + ")", ""));
    }
    
    public String createMoreAccount(){
        String tstatus="activated";
        String tempemail=asbl.createMoreAccount(email,aemail, password, security_question, security_answer,tstatus);
        
        statusMessage="create successfully!";
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage + " (Welcome " + tempemail + ")", ""));
        
        return "viewAccountInfo";
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void saveAllAccounts() {
        if (accountList == null) {
            statusMessage = "No account found";
        } else {
            for (Account a : accountList) {
                asbl.updateaccount(a.getEmail(), a.getCompany().getCompanyName(), a.getCompany().getCompanyAddress(), a.getCompany().getTel(), a.getStatus());
            }
            statusMessage = "All changes are saved.";

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
    }

    //view individual account info
    public List<Account> viewAccount() {
        accountInfo = new ArrayList();
        accountInfo.add(asbl.getAccount(email));
        
        System.out.println("accountManaged bean:viewAccount() ");

        return accountInfo;
    }

    //update account info
    public void onCellEdit(CellEditEvent event) {
        System.err.println("Selected row: " + accountInfo.get(event.getRowIndex()).getSecurity_question());
        System.out.println("************");
        System.out.println("");
        System.out.println("************");
        this.updateAccountInfo(accountInfo.get(event.getRowIndex()).getSecurity_question(), accountInfo.get(event.getRowIndex()).getSecurity_answer());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "New value:" + newValue, " update successfully");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void updateAccountInfo(String qun, String ans) {

        asbl.updateAccountInfo(email, qun, ans);
    }

    public List<Account> getAccountInfo() {

        return accountInfo;
    }

//    @PostConstruct
//    public void fetchallAccounts() {
//        accountList = asbl.getAccounts();
//    }
    public void setAccountInfo(List<Account> accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void getQuestion() {
        security_question = asbl.getAccount(email).getSecurity_question();
    }

    public void loginin(ActionEvent event) {

        response = asbl.validate(email, password);
        if (response == null) {

            statusMessage = "Invalid Login";
        } else if (response.equals("frozen")) {
            statusMessage = "Account frozen!";
        } else {
            statusMessage = "login successful! welcome " + response;
            login = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", email);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Login in status: " + statusMessage, ""));
    }

    public void resetpassword() {
        ans = security_answer.equals(asbl.getAccount(email).getSecurity_answer());

        if (ans) {
            asbl.resetpassword(email, newpassword);
            statusMessage = "password reset successful.";
        } else {
            statusMessage = "Sorry you didn't answer the question correctly.";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
    }

    public String getAemail() {
        return aemail;
    }

    public void setAemail(String aemail) {
        this.aemail = aemail;
    }
    
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public Boolean isLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_contact_no() {
        return comp_contact_no;
    }

    public void setComp_contact_no(String comp_contact_no) {
        this.comp_contact_no = comp_contact_no;
    }

    public String getComp_address() {
        return comp_address;
    }

    public void setComp_address(String comp_address) {
        this.comp_address = comp_address;
    }

    public String getAccessright() {
        return accessright;
    }

    public void setAccessright(String accessright) {
        this.accessright = accessright;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
