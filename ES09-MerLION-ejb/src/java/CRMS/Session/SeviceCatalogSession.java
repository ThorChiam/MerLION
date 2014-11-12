/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CI.Entity.Notification;
import CRMS.Entity.Company;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import WMS.Entity.WMSServiceCatalog;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author ThorChiam
 */
@Stateless
public class SeviceCatalogSession implements SeviceCatalogSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private WMSServiceCatalog service;

    public SeviceCatalogSession() {
    }

    @Override
    public List<WMSServiceCatalog> getAllServices() {
        Query q = em.createQuery("SELECT ws FROM WMSServiceCatalog ws");
        List<WMSServiceCatalog> services = q.getResultList();
        return services;
    }

    @Override
    public List<Company> getCompanies() {
        Query q = em.createQuery("SELECT c FROM Company c");
        return q.getResultList();
    }

    @Override
    public List<String> getAllServiceTypes() {
        Query q = em.createQuery("SELECT DISTINCT ws.serviceType FROM WMSServiceCatalog ws");
        List<String> serviceTypes = q.getResultList();
        return serviceTypes;
    }

    @Override
    public List<String> getAllServiceLocations(Long serviceId) {
        Query q = em.createQuery("SELECT DISTINCT sa.WMSWarehouse.address FROM StorageArea sa WHERE sa.service.id=:serviceId");
        q.setParameter("serviceId", serviceId);
        List<String> serviceLocations = q.getResultList();
        return serviceLocations;
    }

    @Override
    public void selectProvider(String content, Company company) {
        Notification n = new Notification();
        n.setCompany(company);
        n.add("Service Request", content, Calendar.getInstance().getTimeInMillis());
        em.persist(n);
    }

}
