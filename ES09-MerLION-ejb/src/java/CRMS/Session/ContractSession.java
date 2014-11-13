/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Account;
import CRMS.Entity.Contract;
import WMS.Entity.WMSServiceCatalog;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sunny
 */
@Stateless
public class ContractSession implements ContractSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contract> getAllContracts(String email) {
        Query q = em.createQuery("SELECT c FROM Contract c WHERE (c.requestor.email=:email) OR (c.provider.email=:emails)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        List<Contract> results = q.getResultList();
        List<Contract> contracts = new ArrayList<>();
        for (Contract c : results) {
            if (c.getContract_status().equals("pending") || c.getContract_status().equals("signed")) {
                contracts.add(c);
            } else {
                if (c.getProvider().getEmail().equals(email)) {
                    if (c.getContract_status().equals("requestor terminated")) {
                        contracts.add(c);
                    }
                } else {
                    if (c.getContract_status().equals("provider terminated")) {
                        contracts.add(c);
                    }
                }
            }
        }
        return contracts;
    }

    @Override
    public void terminate(String email, Long contractId) {
        Query q = em.createQuery("SELECT c FROM Contract c WHERE c.id=:contractId");
        q.setParameter("contractId", contractId);
        Contract c = (Contract) q.getSingleResult();
        String status = c.getContract_status();
        if (status.equals("provider terminated") || status.equals("requestor terminated")) {
            c.setContract_status("destroyed");
        } else {
            if (c.getProvider().getEmail().equals(email)) {
                c.setContract_status("provider terminated");
            } else {
                c.setContract_status("requestor terminated");
            }
        }
        em.merge(c);
    }

    @Override
    public void signed(String email, Long contractId) {
        Query q = em.createQuery("SELECT c FROM Contract c WHERE c.id=:contractId");
        q.setParameter("contractId", contractId);
        Contract c = (Contract) q.getSingleResult();
        c.setContract_status("signed");
        em.merge(c);
    }

    @Override
    public void createCrontract(int requiredCapacity, Long serviceId, String email, String requestorId) {
        Contract contract = new Contract();
        Query q = em.createQuery("SELECT s FROM WMSServiceCatalog s WHERE s.id=:serviceId");
        q.setParameter("serviceId", serviceId);
        WMSServiceCatalog wService = (WMSServiceCatalog) q.getSingleResult();
        contract.setWservice(wService);
        int total_price = wService.getServicePrice() * requiredCapacity;
        contract.setTotal_price(total_price);
        q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account provider = (Account) q.getSingleResult();
        contract.setProvider(provider);
        q = em.createQuery("SELECT a FROM Account a WHERE a.email=:requestorId");
        q.setParameter("requestorId", requestorId);
        Account requestor = (Account) q.getSingleResult();
        contract.setRequestor(requestor);
        contract.setContract_status("pending");
        em.persist(contract);
    }

    @Override
    public Contract getContract(Long contractId) {
        Query q = em.createQuery("SELECT c FROM Contract c WHERE c.id=:id");
        q.setParameter("id", contractId);
        return (Contract) q.getSingleResult();
    }

}
