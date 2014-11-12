/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Company;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import CRMS.Session.SeviceCatalogSessionLocal;
import WMS.Entity.WMSServiceCatalog;
import java.util.ArrayList;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "scmb")
@SessionScoped
public class ServiceCatalogManagedBean implements Serializable {

    /**
     * Creates a new instance of ServiceCatalogManagedBean
     */
    @EJB
    private SeviceCatalogSessionLocal scsl;
    private List<WMSServiceCatalog> services;
    private List<WMSServiceCatalog> filteredServices;
    private List<WMSServiceCatalog> serviceDetail;
    private List<Company> companies;
    private Long serviceId = (long) 0;
    private List<String> wmsLocations;
    private int requiredCapacity;

    public ServiceCatalogManagedBean() {
    }

    @PostConstruct
    public void init() {
        services = scsl.getAllServices();
        companies = scsl.getCompanies();
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<WMSServiceCatalog> getServices() {
        return services;
    }

    public void setServices(List<WMSServiceCatalog> services) {
        this.services = services;
    }

    public List<WMSServiceCatalog> getFilteredServices() {
        return filteredServices;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<String> getWmsLocations() {
        return wmsLocations;
    }

    public void setWmsLocations(List<String> wmsLocations) {
        this.wmsLocations = wmsLocations;
    }

    public void setFilteredServices(List<WMSServiceCatalog> filteredServices) {
        this.filteredServices = filteredServices;
    }

    public boolean filterByAvailable(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

    public void initServiceId(Long sId) {
        serviceId = sId;
    }

    public int getRequiredCapacity() {
        return requiredCapacity;
    }

    public void setRequiredCapacity(int requiredCapacity) {
        this.requiredCapacity = requiredCapacity;
    }

    public List<WMSServiceCatalog> getServiceDetail() {
        serviceDetail = new ArrayList<>();
        List<WMSServiceCatalog> tmp = scsl.getAllServices();
        for (WMSServiceCatalog s : tmp) {
//            System.out.println("s.getId:" + s.getId() + ";serviceId:" + serviceId);
            if (s.getId().compareTo(serviceId) == 0) {
                serviceDetail.add(s);
            }
        }
        wmsLocations = scsl.getAllServiceLocations(serviceId);
        return serviceDetail;
    }

    public void setServiceDetail(List<WMSServiceCatalog> serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public void selectProvider(ActionEvent event) {
        WMSServiceCatalog rService = serviceDetail.get(0);
        String content = "ServiceID: " + rService.getId() + " Required: " + requiredCapacity;
        scsl.selectProvider(content, rService.getCompany());
        this.addMessage("Notification To Selected Provider:", "Sent Successfully!");
    }

    public void addMessage(String title, String content) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(title, content));
    }
}
