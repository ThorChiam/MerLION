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
    private String serviceType;
    private int capacityRequired;
    private int priceMin;
    private int priceMax;
    private List<WMSServiceCatalog> services;
    private List<WMSServiceCatalog> filteredServices;
    private List<WMSServiceCatalog> serviceDetail;
    private List<Company> companies;
    private Long serviceId;

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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getCapacityRequired() {
        return capacityRequired;
    }

    public void setCapacityRequired(int capacityRequired) {
        this.capacityRequired = capacityRequired;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
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

    public void initServiceId(ActionEvent event) {
        serviceId = (Long) event.getComponent().getAttributes().get("sId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("serviceId", serviceId);
    }

    public List<WMSServiceCatalog> getServiceDetail() {
        serviceDetail = new ArrayList<>();
        List<WMSServiceCatalog> tmp = scsl.getAllServices();
        for (WMSServiceCatalog s : tmp) {
            if (s.getId().compareTo(serviceId) == 0) {
                serviceDetail.add(s);
            }
        }
        return serviceDetail;
    }

    public void setServiceDetail(List<WMSServiceCatalog> serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public void selectServices(ActionEvent event) {
        services = scsl.selectServices(serviceType, capacityRequired, priceMin, priceMax);
    }
}
