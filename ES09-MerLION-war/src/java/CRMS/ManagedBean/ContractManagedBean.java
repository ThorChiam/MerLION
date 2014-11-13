/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CI.Session.InternalCommunicationSessionLocal;
import CRMS.Entity.Contract;
import CRMS.Session.ContractSessionLocal;
import CRMS.Session.PaymentSessionLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "contractmb")
@ViewScoped
public class ContractManagedBean implements Serializable {

    @EJB
    private ContractSessionLocal csl;
    @EJB
    private InternalCommunicationSessionLocal icsl;
    @EJB
    private PaymentSessionLocal psl;
    private String email;
    private List<Contract> allContracts;
    private List<Contract> contractDetail;
    private Contract contract;
    private Long contractId;
    private String paymentNotes;

    public ContractManagedBean() {
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        if (email != null) {
            allContracts = csl.getAllContracts(email);
        }
        contractId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("contractId");
        if (contractId != null) {
            this.setContract(csl.getContract(contractId));
        }
    }

    public void createCrontract(Long notiId, String content) {
        String[] contentInfo = content.split(" ");
        Long contractId = Long.valueOf(contentInfo[1]);
        int requiredCapacity = Integer.valueOf(contentInfo[3]);
        String requestorId = contentInfo[5];
        csl.createCrontract(requiredCapacity, contractId, email, requestorId);
        icsl.contractCreate(notiId);
    }

    public List<Contract> getAllContracts() {
        return allContracts;
    }

    public void setAllContracts(List<Contract> allContracts) {
        this.allContracts = allContracts;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public void terminate(Long contractId) {
        csl.terminate(email, contractId);
    }

    public void signed(Long contractId) {
        csl.signed(email, contractId);
    }

    public void initContract(ActionEvent event) {
        contractId = (Long) event.getComponent().getAttributes().get("cId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("contractId", contractId);
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void initContractId(Long cId) {
        contractId = cId;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("contractId", contractId);
    }

    public List<Contract> getContractDetail() {
        contractDetail = new ArrayList<>();
        List<Contract> tmp = csl.getAllContracts(email);
        for (Contract s : tmp) {
//            System.out.println("s.getId:" + s.getId() + ";contractId:" + contractId);
            if (contractId != null) {
                if (s.getId().compareTo(contractId) == 0) {
                    contractDetail.add(s);
                }
            }
        }
        return contractDetail;
    }

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public void setContractDetail(List<Contract> contractDetail) {
        this.contractDetail = contractDetail;
    }

    public void createPayment(ActionEvent event) {
        contractId = contractDetail.get(0).getId();
        psl.createPayment(paymentNotes, contractId);
        this.addMessage("Payment Created:", "Successfully!");
    }

    public void addMessage(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, content));
    }
}
