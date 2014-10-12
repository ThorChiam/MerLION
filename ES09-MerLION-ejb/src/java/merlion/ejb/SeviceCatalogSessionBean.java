/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb;

import java.util.List;
import merlion.ejb.local.SeviceCatalogSessionBeanLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import merlion.entity.CommonInfrastructure.Account;
import merlion.entity.CRMS.ServiceCatalog;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class SeviceCatalogSessionBean implements SeviceCatalogSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    private ServiceCatalog serviceCatalog;

    @Override
    public Long addServiceCatalog(String email, String serviceType, String carrierType, String route, String availableScheduleFrom, String availableScheduleTo, long price, long maxVol, long availableVol) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        Account a = (Account) q.getSingleResult();
        serviceCatalog = new ServiceCatalog();
        serviceCatalog.setAccount(a);
        serviceCatalog.create(serviceType, carrierType, route, availableScheduleFrom, availableScheduleTo, price, maxVol, availableVol);
        em.persist(serviceCatalog);
        em.flush();

        return serviceCatalog.getServiceId();
    }

    @Override
    public List getAllServiceCatalog(String email) {
        Query q = em.createQuery("SELECT e FROM ServiceCatalog e WHERE e.Account.email");
        q.setParameter("email", email);
        return q.getResultList();
    }

    //individual user can delete their own service catalog info

    @Override
    public void deleteServiceCatalog(String email, Long serviceId) {

        Query query = em.createQuery("Delete FROM ServiceCatalog e where e.serviceId=?1 AND e.Account.email=?2");
        query.setParameter(1, serviceId);
        query.setParameter(2, email);
        query.executeUpdate();
    }

    @Override
    public void updateServiceCatalog(String email, Long serviceId, String serviceType, String carrierType, String route, long scheduleFrom, long scheduleTo, long price, long maxVol, long avaVol) {
        Query query = em.createQuery("UPDATE ServiceCatalog s SET s.serviceType=?1,s.carrierType=?2, s.route=?3, s.availableScheduleFrom=?4,s.availableScheduleTo=?5,s.price=?6, s.maxVol=?7,s.availableVol=?8 WHERE s.serviceId=?9 AND s.Account.email=?10");
        query.setParameter(1, serviceType);
        query.setParameter(2, carrierType);
        query.setParameter(3, route);
        query.setParameter(4, scheduleFrom);
        query.setParameter(5, scheduleTo);
        query.setParameter(6, price);
        query.setParameter(7, maxVol);
        query.setParameter(8, avaVol);
        query.setParameter(9, serviceId);
        query.setParameter(10, email);

        query.executeUpdate();
        //return serviceId;
    }

    @Override
    public List getServiceCatalog(String email, Long serviceId) {
        Query query = em.createQuery("SELECT e FROM ServiceCatalog e WHERE e.serviceId=?1 AND e.Account=?2");
        query.setParameter(1, serviceId);
        query.setParameter(2, email);
        return query.getResultList();
    }
}
