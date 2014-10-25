/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import CRMS.Session.SeviceCatalogSessionLocal;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "serviceCatalogManagedBean")
@SessionScoped
public class ServiceCatalogManagedBean implements Serializable {

    /**
     * Creates a new instance of ServiceCatalogManagedBean
     */
    @EJB
    private SeviceCatalogSessionLocal scsbl;
    private String serviceType;
    private String carrierType;
    private String route;
    private long scheduleFrom;
    private long scheduleTo;
    private String availableScheduleFrom;
    private String availableScheduleTo;
    private long price;
    private long maxVol;
    private long avaVol;

    public ServiceCatalogManagedBean() {
    }

    public void deleteServiceCatalog(String email, Long serviceId) {
        scsbl.deleteServiceCatalog(email, serviceId);
    }

    public void updateServiceCatalog(String email, Long serviceId) {
        scsbl.updateServiceCatalog(email, serviceId, serviceType, carrierType, route, scheduleFrom, scheduleTo, price, maxVol, avaVol);
    }

    public List getServiceCatalog(String email, Long serviceId) {
        return scsbl.getServiceCatalog(email, serviceId);
    }

    public List getAllServiceCatalog(String email) {
        return scsbl.getAllServiceCatalog(email);
    }

    public Long addServiceCatalog(String email) {
        return scsbl.addServiceCatalog(email, serviceType, carrierType, route, availableScheduleFrom, availableScheduleTo, price, maxVol, avaVol);
    }
}
