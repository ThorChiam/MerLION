/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.web.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import merlion.ejb.DemandSessionBean;
import merlion.ejb.MPSSessionBean;
import merlion.entity.MRP.Demand;
import merlion_exception.DemandException;
/**
 *
 * @author Zeng Xunhao
 */
@ManagedBean(name = "MPSManagerBean")
@SessionScoped
public class MPSManagedBean implements Serializable {

    /**
     * Creates a new instance of MPSManagedBean
     */
    @EJB
    private MPSSessionBean MPSSessionBean;
    @EJB
    private DemandSessionBean DemandSessionBean;
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private int sixth;
    private Long itemId;
    private Long mpsId;
    private String demandType;
    private String statusMessage;
    private List<Demand> demand;
    private int[] past=new int[6];
    private int[] prediction=new int[6];
    
    
    
    public MPSManagedBean() {
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String DemandType) {
        this.demandType = DemandType;
    }

    public List<Demand> getDemand() {
        return demand;
    }

    public void setDemand(List<Demand> demand) {
        this.demand = demand;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public int getFourth() {
        return fourth;
    }

    public void setFourth(int fourth) {
        this.fourth = fourth;
    }

    public int getFifth() {
        return fifth;
    }

    public void setFifth(int fifth) {
        this.fifth = fifth;
    }

    public int getSixth() {
        return sixth;
    }

    public void setSixth(int sixth) {
        this.sixth = sixth;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    

    public Long getMpsId() {
        return mpsId;
    }

    public void setMpsId(Long mpsId) {
        this.mpsId = mpsId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public int[] getPast() {
        return past;
    }

    public void setPast(int[] past) {
        this.past = past;
    }

    public int[] getPrediction() {
        return prediction;
    }

    public void setPrediction(int[] prediction) {
        this.prediction = prediction;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    public void addNewMPS(ActionEvent event){
        try{
        mpsId = MPSSessionBean.addNewMPS(itemId, first, second, third, fourth, fifth, sixth);
        statusMessage = "New MPS Saved Successfully";
        }
        catch(Exception ex){
            ex.printStackTrace();
            statusMessage = "?";
        }
    }
    public void showDemand()throws DemandException{
        try{
        setDemand(DemandSessionBean.getItemDemand(itemId));
        int l=demand.size();
        int j=0;
        for(int i=l-6;i<l;i++){
            past[j]=demand.get(i).getQuantity();
            j++;
        }
        }catch(DemandException e){
            e.printStackTrace();
        }
            catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void PredictDemand()throws DemandException{
        try{
            prediction=DemandSessionBean.predictItemDemand(itemId);
        }catch(DemandException e){
            e.printStackTrace();
        }
    }
}
