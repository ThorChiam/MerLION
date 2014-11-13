/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.ManagedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import TMS.Session.CatalogSession;
import TMS.Session.TMSOrderSession;
import TMS.Session.TransportationSession;
import TMS.Entity.Location;
import TMS.Entity.TMSCatalog;

/**
 *
 * @author Zeng Xunhao
 */
/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "CMB")
@ViewScoped
public class CatalogManagedBean implements Serializable {

    /**
     * Creates a new instance of MPSManagedBean
     */
    @EJB
    private TransportationSession TSession;
    @EJB
    private CatalogSession CSession;
    @EJB
    private TMSOrderSession TOS;

    private List<TMSCatalog> catalog = new ArrayList();
    private List<Location> locations;
    private String userId = "email";
    private String statusMessage = "no message";
    private String itemname;
    
    private String carrierType;
    private Long itemId;
    private double amount;
    private int capacity;
    private TMSCatalog cat = new TMSCatalog();

    @ManagedProperty("#{allcatalogs}")
    private List<TMSCatalog> allcatalogs=new ArrayList();
    private List<TMSCatalog> filtered=new ArrayList();

    public CatalogManagedBean() {

    }

    @PostConstruct
    public void init() {
        locations = TSession.getAllLocations();
        getCatalog();        
        allcatalogs = CSession.getAllCatalog();
    }
    public boolean filterByMin(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
         
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

    public void addNewCatalog() {

        CSession.addCatalog(cat, userId);
        statusMessage = "New Catalog " + " added successfully!";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));

    }
    public void updateCatalog(){
        for(TMSCatalog i: catalog){
            CSession.updateCatalog(i);
        }
         statusMessage = "Changes saved";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
    }
    public void createOrder(TMSCatalog o)
    {   
        if(o.getCapacity()>=amount){
        TOS.addOrder(userId,o.getAccount().getEmail(), itemname,amount, o.getTimetaken(),o.getOri(),o.getDes(), o.getCarrierType());
        statusMessage = "New Order" + " added successfully!";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
        }else{
            statusMessage = "Exceeds maximum weight!";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                statusMessage, ""));
        }
    }
    public Location stringToLocation(String s) {
        for (Location i : locations) {
            if (i.getName().equals(s)) {
                return i;
            }
        }
        return null;
    }

    public void deleteCatalog(TMSCatalog r) {
        CSession.deleteCatalog(r.getId());
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TMSCatalog getCat() {
        return cat;
    }

    public List<TMSCatalog> getAllcatalogs() {

        return allcatalogs;
    }

    public void setAllcatalogs(List<TMSCatalog> allcatalogs) {
        this.allcatalogs = allcatalogs;
    }

    public void setCat(TMSCatalog cat) {
        this.cat = cat;
    }

    

    public List<TMSCatalog> getCatalog() {
        catalog = CSession.getMyCatalog(userId);
        return catalog;
    }

   

    public List<TMSCatalog> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<TMSCatalog> filtered) {
        this.filtered = filtered;
    }

   

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void setCatalog(List<TMSCatalog> catalog) {
        this.catalog = catalog;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
