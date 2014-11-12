/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Account;
import CRMS.Entity.Contract;
import WMS.Entity.WMSServiceCatalog;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sunny
 */
@Stateless
@LocalBean
public class ContractSession implements ContractSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contract> getAllContract(String email) {
        Query q = em.createQuery("SELECT f FROM Contract f WHERE (f.serviceorder.service_provider.email=:email) OR (f.serviceorder.service_requester.email=:emails)");
        q.setParameter("email", email);
        q.setParameter("emails", email);
        return q.getResultList();
    }

    @Override
    public void deleteContract(long contract_id) {
        Query q = em.createQuery("DELETE FROM Contract f WHERE f.id=:id");
        q.setParameter("id", contract_id);
        q.executeUpdate();
    }

    @Override
    public void terminateContract(long contract_id) {
        Query q = em.createQuery("SELECT a FROM Contract a WHERE a.id=:id");
        q.setParameter("id", contract_id);
        Contract contract = (Contract) q.getSingleResult();
        contract.setContract_status("terminated");
        em.merge(contract);
    }

    @Override
    public void createCrontract(String sign_date, int total_price, Long serviceId, Long email, Long requestorId) {
        Contract contract = new Contract();
        contract.setSign_date(sign_date);
        contract.setContract_status("pending");
        contract.setTotal_price(total_price);
        Query q = em.createQuery("SELECT s FROM WMSServiceCatalog s WHERE s.id=:serviceId");
        q.setParameter("serviceId", serviceId);
        WMSServiceCatalog wService = (WMSServiceCatalog) q.getSingleResult();
        contract.setwService(wService);
        q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account provider = (Account) q.getSingleResult();
        contract.setProvider(provider);
        q = em.createQuery("SELECT a FROM Account a WHERE a.email=:requestorId");
        q.setParameter("requestorId", requestorId);
        Account requestor = (Account) q.getSingleResult();
        contract.setRequestor(requestor);
        contract.setContract_status("pending");
        contract.setTotal_price(total_price);
        contract.setSign_date(sign_date);
        em.persist(contract);
    }

    @Override
    public Contract getContract(long contract_id) {
        Query q = em.createQuery("SELECT f FROM Contract f WHERE f.id=:id");
        q.setParameter("id", contract_id);
        return (Contract) q.getSingleResult();
    }

}
