/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRP.ManagedBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import MRP.Session.TemplateSessionBean;
import MRP.Entity.Item;
import MRP.Entity.Template;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean(name = "TemplateManagerBean")
@SessionScoped
public class TemplateManagerBean {

    @EJB
    private TemplateSessionBean TemplateSessionBean;

    private static final ArrayList<Temp> tempList
            = new ArrayList(Arrays.asList(
                            new Temp("gross requirement", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("scheduled receipt", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("planned order", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("on hand", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("back log", 0, 0, 0, 0, 0, 0, 0)
                    ));
    private String statusMessage;
    private Long itemId;
    private List<Template> templates;
    private Boolean editable;

    public ArrayList<Temp> getTempList() {
        return tempList;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public Boolean isEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
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

    public Item getItem(Long itemId) {
        return TemplateSessionBean.getItem(itemId);
    }

    public String editAction(Temp temp) {

        temp.setEditable(true);
        return null;
    }

    public void saveNewTemplate(ActionEvent event) {

        try {
            for (Temp temp : tempList) {

                TemplateSessionBean.addNewTemplate(itemId, temp.name,
                        temp.first, temp.second, temp.third, temp.fourth, temp.fifth, temp.sixth, temp.seventh);
            }
            statusMessage = "New Template Saved Successfully";
        } catch (Exception ex) {
            ex.printStackTrace();
            statusMessage = "?";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
    }

    public void getItemTemplate() {
        templates = TemplateSessionBean.getItemPlan(itemId);
    }

    public static class Temp {

        String name;
        int first;
        int second;
        int third;
        int fourth;
        int fifth;
        int sixth;
        int seventh;
        boolean editable;

        //getter and setter method
        public Temp(String name, int first, int second, int third, int fourth, int fifth, int sixth, int seventh) {
            this.name = name;
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
            this.fifth = fifth;
            this.sixth = sixth;
            this.seventh = seventh;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
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

        public int getSeventh() {
            return seventh;
        }

        public void setSeventh(int seventh) {
            this.seventh = seventh;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

    }
}
