/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRP.ManagedBean;


import MRP.Entity.BOM;
import MRP.Entity.Item;
import MRP.Entity.MPS;
import MRP.Entity.Template;
import MRP.Session.BOMSessionBean;
import MRP.Session.MPSSessionBean;
import MRP.Session.TemplateSessionBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;


@ManagedBean(name = "TemplateManagerBean")
@SessionScoped
public class TemplateManagerBean {

    @EJB
    private TemplateSessionBean TemplateSessionBean;
    @EJB
    private BOMSessionBean bs;
    @EJB
    private MPSSessionBean ms;
    private static final ArrayList<Temp> tempList
            = new ArrayList(Arrays.asList(
                            new Temp("gross requirement", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("scheduled receipt", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("planned receipt", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("planned order", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("on hand", 0, 0, 0, 0, 0, 0, 0),
                            new Temp("back log", 0, 0, 0, 0, 0, 0, 0)
                    ));
    private String statusMessage;
    private Long itemId;
    private List<Item> itemList;
    private List<Template> templates;
    private Boolean editable;
    private Item item=new Item();
    private String userId;
    

    @PostConstruct
    public void getlogin(){
        //userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        userId="email";
        getAllitems();
               
    }
    public void init(){
        getIteminfo();
        List<BOM>BOMs=getItemBOM();
        int[] gross=new int[7];
        int[] month=new int[7];
        Calendar c = Calendar.getInstance();    
        c.setTime(new Date());
        
        for(int i=0;i<7;i++){
            month[i]=c.get(Calendar.MONTH);
            c.add(Calendar.DATE, 7);
        }
        
        for(BOM i:BOMs){
            MPS mps=ms.getItemMPS(userId, i.getItem().getItemId());
            int[] mpsMonth=new int[6];
            int[] mpsQuan=new int[6];
            
            for(int j=0;j<6;j++){
            mpsMonth[j]=(mps.getStartmonth()+j)%12;
            }
            mpsQuan[0]=mps.getFifthmonth();
            mpsQuan[1]=mps.getSecondmonth();
            mpsQuan[2]=mps.getThirdmonth();
             mpsQuan[3]=mps.getFourthmonth();
              mpsQuan[4]=mps.getFifthmonth();
              mpsQuan[5]=mps.getSixthmonth();
              
             for(int j=0;j<7;j++){
                 for(int k=0;k<6;k++){
                 if(mpsMonth[k]==month[j])
                     
                 gross[j]+=(int)(mpsQuan[k]*i.getQuantity());
              }
             }
         }
        tempList.get(0).setFirst(gross[0]);
        tempList.get(0).setSecond(gross[1]);
        tempList.get(0).setThird(gross[2]);
        tempList.get(0).setFourth(gross[3]);
        tempList.get(0).setFifth(gross[4]);
        tempList.get(0).setSixth(gross[5]);
        tempList.get(0).setSeventh(gross[6]);
        
    }
    public Item getIteminfo(){
        item=bs.getItem(itemId);
        return item;
    }
    
    public List<BOM> getItemBOM(){
        return bs.IsComponent(itemId);
    }
    public List<Item> getAllitems() {
        itemList = bs.getAllItems(userId);
        return itemList;
    }
     public ArrayList<Temp> getTempList() {
        return tempList;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public String getUserId() {
        //userId=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        return userId;
    }
    
    public Boolean isEditable() {
        return editable;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
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

                 TemplateSessionBean.addNewTemplate(userId, itemId, temp.name,
                        temp.first, temp.second, temp.third, temp.fourth, temp.fifth, temp.sixth, temp.seventh);
            }
            statusMessage = "New Template Saved Successfully";
        }  catch (Exception ex) {
            ex.printStackTrace();
            statusMessage="?";
        }
    }
    
    public void getItemTemplate(){
        templates=TemplateSessionBean.getItemPlan(userId,itemId);
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
