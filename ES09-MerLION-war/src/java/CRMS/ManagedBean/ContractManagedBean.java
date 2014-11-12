/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Contract;
import CRMS.Session.ContractSessionLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "contractmb")
@ViewScoped
public class ContractManagedBean implements Serializable {

    @EJB
    private ContractSessionLocal csbl;
    private String sign_date;
    private String contract_status;
    private String total_price;

    public ContractManagedBean() {
    }

    public void createCrontract(String content) {
//        "ServiceID: " + rService.getId() + " Required: " + requiredCapacity + " From: " + email
        Date date = new java.util.Date();
        Timestamp tmp = new Timestamp(date.getTime());
        String sign_date = tmp.toString();
//        csbl.createCrontract(sign_date, total_price, contract_status, serviceorder);

    }

    public Contract getContract(long contract_id) {
        return csbl.getContract(contract_id);
    }

    public List<Contract> getAllContract(String email) {
        return csbl.getAllContract(email);
    }

    public void deleteContract(long contract_id) {
        csbl.deleteContract(contract_id);
    }

    public void terminateContract(long contract_id) {
        csbl.terminateContract(contract_id);
    }

//    public ServiceOrder getServiceOrder(long id) {
//        return csbl.getServiceOrder(id);
//    }

}
