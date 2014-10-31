/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Contract;
import CRMS.Entity.ServiceOrder;
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
public class ContractSession implements ContractSessionLocal{
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
        Contract contract=(Contract)q.getSingleResult();
        contract.setContract_status("terminated");
        em.merge(contract);
    }

    @Override
    public void createCrontract(String sign_date, String total_price, String contract_status, ServiceOrder serviceorder) {
        Contract contract=new Contract();
        contract.setContract_status("pending");
        contract.setServiceorder(serviceorder);
        contract.setTotal_price(total_price);
        contract.setSign_date(sign_date);
    }

    @Override
    public Contract getContract(long contract_id) {
        Query q = em.createQuery("SELECT f FROM Contract f WHERE f.id=:id");
        q.setParameter("id", contract_id);
        return (Contract) q.getSingleResult();
    }
    
    @Override
    public ServiceOrder getServiceOrder(long service_id){
        Query q = em.createQuery("SELECT f FROM ServiceOrder f WHERE f.id=:id");
        q.setParameter("id", service_id);
        return (ServiceOrder) q.getSingleResult();
    }
    
}
