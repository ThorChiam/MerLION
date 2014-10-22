/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRP.ManagedBean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import MRP.Session.BOMSessionBean;
import MRP.Entity.BOM;

/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "BOMManagerBean")
@SessionScoped
public class BOMManagedBean implements Serializable {
     @EJB
    private BOMSessionBean BOMsession;
    private Long compId1;
    private Long compId2;
    private Long compId3;
    private int quantity1;
    private int quantity2;
    private int quantity3;
    private Long ItemId;
    @ManagedProperty("#{BOMList}")
    private List<BOM> BOMList;
   
    private Long BOMId;
    private String statusMessage;
    /**
     * Creates a new instance of BOMManagedBean
     */
    public BOMManagedBean() {
    }
    public void addNewBOM(ActionEvent event){
       statusMessage = "New Bill of Material Saved Successfully";
       BOMId = BOMsession.addItemBOM(ItemId, compId1, quantity1, compId2, quantity2, compId3, quantity3);
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        "Add New Order Result: " + statusMessage + " (New BOM ID is " + BOMId + ")", ""));
    }
   public void deleteBOM(ActionEvent event){
       try{
       BOMsession.deleteBOM(ItemId);
       statusMessage = "Bill of Material for item"+ItemId+"delete Successfully";
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));}
       catch(Exception ex){
           ex.printStackTrace();
           statusMessage = "something wrong";
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
       }      
   }
    public void saveAllBOMs(){
        if(BOMList==null){
         statusMessage="No BOM";}
        else{
        for (BOM a : BOMList) {
        BOMsession.updateBOM(a.getItem().getItemId(), a.getComp1(), a.getComp2(),a.getComp3(),
                a.getPartnumber1(),a.getPartnumber2(),a.getPartnumber3());
        
        }
        statusMessage="All changes are saved.";
      
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
        statusMessage, ""));
}  

    public Long getCompId1() {
        return compId1;
    }

    public void setCompId1(Long compId1) {
        this.compId1 = compId1;
    }

    public Long getCompId2() {
        return compId2;
    }
    @PostConstruct
    public void getallboms(){
        BOMList=BOMsession.getBOMs();
    }
    public List<BOM> getBOMList() {
        
        return BOMList;
    }

    public void setBOMList(List<BOM> BOMList) {
        
        this.BOMList = BOMList;
    }

    public void setCompId2(Long compId2) {
        this.compId2 = compId2;
    }

    public Long getCompId3() {
        return compId3;
    }

    public void setCompId3(Long compId3) {
        this.compId3 = compId3;
    }

    public int getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(int quantity1) {
        this.quantity1 = quantity1;
    }

    public int getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(int quantity2) {
        this.quantity2 = quantity2;
    }

    public int getQuantity3() {
        return quantity3;
    }

    public void setQuantity3(int quantity3) {
        this.quantity3 = quantity3;
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
