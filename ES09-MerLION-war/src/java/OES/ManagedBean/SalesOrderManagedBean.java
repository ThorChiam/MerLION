/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.ManagedBean;

import CI.Entity.Account;
import javax.ejb.EJB;
import OES.Session.OESSessionLocal;
import OES.Entity.*;
import WMS.Session.WMSOrderSessionLocal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Tomato
 */
@ManagedBean(name = "sob")
@ViewScoped
public class SalesOrderManagedBean {

    @EJB
    private OESSessionLocal osbl;
    @EJB
    private WMSOrderSessionLocal wosl;

    //Quotation: view Enquire
    private String email;
    private int soSize;
    private Long soId;
    private int orderDetailsSize;
    private Date createDate;
    private String shipmentDate;
    private String salesCreationDate;
    private String salesShipDate;

    //Sales Order:Creation
    private List<Integer> quantity = new ArrayList();
    private List<PurchaseOrder> pOrders = new ArrayList();
    private List<Long> porder = new ArrayList();
    private Long selectedID;
    private String senderN;
    private String recName;
    private String senderA;
    private String recA;
    private Account sender;
    private Account reciever;
    private double tax;
    private double discount;
    private double total;
    private List<OrderList> list = new ArrayList();
    private List<OrderListSub> listsub = new ArrayList();
    private PurchaseOrder purchaseOrder;
    private int salesproductsize;
    private int checkInt;
    private long passID;
    
    //Sales Order History
    private int sSize;
    
    //Payment
    private int sizepayment;
    private int sizepaymentpaid;
    //invoice
    private int invoicesize;

    public SalesOrderManagedBean() {
    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        if (email != null) {
            this.listpurchaseOrder();
        }

    }

    //**************************************View Incoming Purchse Order******************************************
    public List<PurchaseOrder> viewIncmoingPorder(String email) {
        soSize = osbl.viewIncmoingPorder(email).size();
        return osbl.viewIncmoingPorder(email);
    }

    public List<OrderList> viewPurchaseOrderDetails(long id) {
        orderDetailsSize = osbl.viewPurchaseOrderDetails(id).size();
        List<OrderList> tmp = osbl.viewPurchaseOrderDetails(id);
        return wosl.ATPcheck(tmp);

    }

    public List<PurchaseOrder> listpurchaseOrder() {

        for (int i = 0; i < osbl.displayIncomingPorder(email).size(); i++) {
            if (osbl.displayIncomingPorder(email).get(i).getSalesorder() == null) {
                porder.add(osbl.displayIncomingPorder(email).get(i).getId());
            }
        }
        return osbl.displayIncomingPorder(email);
    }

    public List<OrderListSub> createsubforSalesOrder(Long id) {

        return osbl.createsubforSalesOrder(id);
    }

    //*******************************************Create NEW Sales Order*****************************************
    public List<PurchaseOrder> replyToPurchaseOrder() {
        return osbl.displayIncomingPorder(email);
    }

    public void displaySenderRecieverNameAddress(ActionEvent event) {
        java.util.Date date = new java.util.Date();
        createDate = new Timestamp(date.getTime());
        
        Timestamp tmp = new Timestamp(createDate.getTime());
        String creationdate = tmp.toString();
        System.out.println("displaySenderRecieverNameAddressselectedEID:********************************************************************************" + selectedID);

        senderN = osbl.displaySenderSalesOrder(selectedID).getCompany().getCompanyName();
        recName = osbl.displayRecieverSalesOrder(selectedID).getCompany().getCompanyName();
        senderA = osbl.displaySenderSalesOrder(selectedID).getCompany().getCompanyAddress();
        recA = osbl.displayRecieverSalesOrder(selectedID).getCompany().getCompanyAddress();
        listsub = osbl.createsubforSalesOrder(selectedID);
        int size = osbl.createsubforSalesOrder(selectedID).size();
        
        purchaseOrder=osbl.displayPurchaseOrder(selectedID);

        for (int i = 0; i < size; i++) {
            total += osbl.createsubforSalesOrder(selectedID).get(i).getSubtotal();
        }

        List<OrderList> listbeforeATP = new ArrayList();
        listbeforeATP = osbl.viewPurchaseOrderDetails(selectedID);

        int factor = 0;
        for (int i = 0; i < listbeforeATP.size(); i++) {
            if (listbeforeATP.get(i).getCheckResult() < 0) {

                if (Math.abs(listbeforeATP.get(i).getCheckResult()) <= 100) {

                    factor = 2;
                } else if (Math.abs(listbeforeATP.get(i).getCheckResult()) <= 200 && Math.abs(listbeforeATP.get(i).getCheckResult()) > 100) {

                    factor = 3;
                } else if (Math.abs(listbeforeATP.get(i).getCheckResult()) <= 500 && Math.abs(listbeforeATP.get(i).getCheckResult()) > 200) {

                    factor = 4;
                } else if (Math.abs(listbeforeATP.get(i).getCheckResult()) <= 1000 && Math.abs(listbeforeATP.get(i).getCheckResult()) > 500) {
                    factor = 5;

                } else {
                    factor = 8;
                }

            } else {
                factor = 1;
            }

        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, factor);
        shipmentDate=dateFormat.format(c.getTime());
        
        DecimalFormat df = new DecimalFormat("0.00");
        
        total=Double.valueOf(df.format(total));
        
        
        osbl.createSalesOrder(creationdate ,tax, discount,Double.valueOf(df.format((total+tax*total)-total*discount)), shipmentDate, purchaseOrder);
       
             
    }

    public List<OrderListSub> displayOrderWithPrice(Long id) {

        return osbl.createsubforSalesOrder(id);
    }
    
    public List<SalesOrder> viewSalesOrderHistory(){
        soSize=osbl.viewSalesOrderHistory(email).size();
        return osbl.viewSalesOrderHistory(email);
    }
    
    public SalesOrder viewSingleSalesOrder(long id){
        System.out.println("**************************************Single Sales Order**************"+id);
        System.out.println("**************************************Single Sales Order**************"+osbl.viewSingleSaleOrder(id));
        return osbl.viewSingleSaleOrder(id);
    }
    
    public void deleteSalesHistory(long id){
        osbl.deleteSalesHistory(id);
    }

    //******************************************Payment********************************************************
    public List<Makepayment> viewOutStandingPayment(){
      sizepayment=osbl.viewOutstandingPayment(email).size();
      return osbl.viewOutstandingPayment(email);
        
    }
    
     public List<Makepayment> viewPaidPayment(){
      sizepaymentpaid=osbl.viewPaidPayment(email).size();
      return osbl.viewPaidPayment(email);
        
    }
    
    public void updatePaymentStatus(long id){
         System.out.println("displaySenderRecieverNameAddressselectedEID:********************************************************************************" + id);
        osbl.updatePaymentStatus(id);
    }
    
    public List<OES_Invoice> viewInvoice(){
        invoicesize=osbl.viewInvoice(email).size();
        
        return osbl.viewInvoice(email);
    }
    
    
    
    
    
    
    
    
    
    //******************GETTER AND SETTER***********************************************************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
    }

    public int getInvoicesize() {
        return invoicesize;
    }

    public void setInvoicesize(int invoicesize) {
        this.invoicesize = invoicesize;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSizepaymentpaid() {
        return sizepaymentpaid;
    }

    public void setSizepaymentpaid(int sizepaymentpaid) {
        this.sizepaymentpaid = sizepaymentpaid;
    }

  

    public int getOrderDetailsSize() {
        return orderDetailsSize;
    }

    public void setOrderDetailsSize(int orderDetailsSize) {
        this.orderDetailsSize = orderDetailsSize;
    }

    public int getsSize() {
        return sSize;
    }

    public void setsSize(int sSize) {
        this.sSize = sSize;
    }

    public int getSoSize() {
        return soSize;
    }

    public void setSoSize(int soSize) {
        this.soSize = soSize;
    }

    
    public WMSOrderSessionLocal getWosl() {
        return wosl;
    }

    public String getSalesCreationDate() {
        return salesCreationDate;
    }

    public int getSizepayment() {
        return sizepayment;
    }

    public void setSizepayment(int sizepayment) {
        this.sizepayment = sizepayment;
    }

    public int getSalesproductsize() {
        return salesproductsize;
    }

    public void setSalesproductsize(int salesproductsize) {
        this.salesproductsize = salesproductsize;
    }

    public int getCheckInt() {
        return checkInt;
    }

    public void setCheckInt(int checkInt) {
        this.checkInt = checkInt;
    }

    public void setSalesCreationDate(String salesCreationDate) {
        this.salesCreationDate = salesCreationDate;
    }

    public String getSalesShipDate() {
        return salesShipDate;
    }

    public void setSalesShipDate(String salesShipDate) {
        this.salesShipDate = salesShipDate;
    }

    public void setWosl(WMSOrderSessionLocal wosl) {
        this.wosl = wosl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }

    public List<PurchaseOrder> getpOrders() {
        return pOrders;
    }

    public void setpOrders(List<PurchaseOrder> pOrders) {
        this.pOrders = pOrders;
    }

    public Long getSelectedID() {
        return selectedID;
    }

    public void setSelectedID(Long selectedID) {
        this.selectedID = selectedID;
    }

    public String getSenderN() {
        return senderN;
    }

    public void setSenderN(String senderN) {
        this.senderN = senderN;
    }

    public String getRecName() {
        return recName;
    }

    public List<OrderListSub> getListsub() {
        return listsub;
    }

    public void setListsub(List<OrderListSub> listsub) {
        this.listsub = listsub;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReciever() {
        return reciever;
    }

    public void setReciever(Account reciever) {
        this.reciever = reciever;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Long> getPorder() {
        return porder;
    }

    public void setPorder(List<Long> porder) {
        this.porder = porder;
    }

    public List<OrderList> getList() {
        return list;
    }

    public void setList(List<OrderList> list) {
        this.list = list;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getSenderA() {
        return senderA;
    }

    public void setSenderA(String senderA) {
        this.senderA = senderA;
    }

    public String getRecA() {
        return recA;
    }

    public void setRecA(String recA) {
        this.recA = recA;
    }

    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    public long getPassID() {
        return passID;
    }

    public void setPassID(long passID) {
        this.passID = passID;
    }

    
    
}
