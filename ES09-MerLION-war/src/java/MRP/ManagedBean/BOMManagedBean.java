/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRP.ManagedBean;

import MRP.Entity.BOM;
import MRP.Entity.Item;
import MRP.Session.BOMSessionBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;



/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "BOMManagerBean")
@ViewScoped
public class BOMManagedBean implements Serializable {

    @EJB
    private BOMSessionBean BOMsession;
    private Long compId;

    private double quantity;

    private Long ItemId;

    private String userId;

    private List<BOM> BOMList;
    private List<Item> itemList;

    private Long BOMId;
    private String statusMessage;

    /**
     * Creates a new instance of BOMManagedBean
     */
    public BOMManagedBean() {
    }

    @PostConstruct
    public void init() {
        userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        
        getAllitems();
    }

    public void addNewBOM() {
        if (ItemId != null) {
            statusMessage = "New Bill of Material Saved Successfully";
            BOMId = BOMsession.addItemBOM(ItemId, compId, quantity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Add New Order Result: " + statusMessage + " (New BOM ID is " + BOMId + ")", ""));
        } else {
            statusMessage = "null itemID";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    statusMessage, ""));
        }
    }

    public void deleteBom(BOM b) {
//        BOM b = (BOM) event.getComponent().getAttributes().get("bom");
        System.out.println("B:" + (b == null));
        if (b == null) {
            statusMessage = "null pointer?";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    statusMessage, ""));
        } else {
            BOMsession.deleteBOM(b.getId());
            statusMessage = "component for item " + ItemId + " delete Successfully";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    statusMessage, ""));
        }

    }

    public void saveAllBOMs() {
        if (BOMList == null) {
            statusMessage = "No BOM";
        } else {
            for (BOM a : BOMList) {
                BOMsession.updateBOM(a.getId(), a.getQuantity());
            }
            statusMessage = "All changes are saved.";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
    }

    public List<Item> getAllitems() {
        itemList = BOMsession.getAllItems(userId);
        return itemList;
    }

    public void getItemBOM() {
        BOMList = BOMsession.getBOMs(ItemId);
    }

    public List<BOM> getBOMList() {

        return BOMList;
    }

    public String getUserId() {
        userId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        return userId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setBOMList(List<BOM> BOMList) {

        this.BOMList = BOMList;
    }

    public Long getItemId() {
        return ItemId;
    }

    public void setItemId(Long ItemId) {
        this.ItemId = ItemId;
    }

    public Long getBOMId() {
        return BOMId;
    }

    public void setBOMId(Long BOMId) {
        this.BOMId = BOMId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
