import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.EJB;
import OES.Session.OESSessionLocal;
import CI.Entity.Account;
import OES.Entity.*;
import WMS.Session.WMSOrderSessionLocal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author sunny
 */
@ManagedBean(name = "pur")
@ViewScoped
public class PurchaseOrderManagedBean implements Serializable {

    @EJB
    private OESSessionLocal osbl;
    @EJB
    private WMSOrderSessionLocal wosl;

    //purchase Order
    private String email;
    private String senderName;
    private String receiverName;
    private Account sender;
    private Account receiver;
    private List<String> receiverNames;
    private Date edate;

    //products
    private List<Product> products;
    private int j;
    private List<String> desc = new ArrayList();
    private List<String> productsN = new ArrayList();
    private List<String> selectedNames = new ArrayList();
    private List<Product> productSelected=new ArrayList();
    private int psize;
    private String quantitiesEntered;
    private List<String> quantities = new ArrayList();
    private String[] strarray;
    private String arrayToString;

    //Create New Purchase Order
    private List<OrderList> orders = new ArrayList();
    private String statusMessage;
    private int enSize;
    private long purId;

    //Purchase Order Details
    private String singleSeller;
    private String singleBuyer;
    private List<OrderList> orderl = new ArrayList();
    private String purDate;
    private List<String> detailsquantities = new ArrayList();
    private List<String> detailsproNames = new ArrayList();
    private List<OrderList> detailOrders = new ArrayList();
    private int detailSize;
    private int orderconfirmationsize;

    public PurchaseOrderManagedBean() {
    }

    @PostConstruct
    public void init() {
        // enquires=osbl.getAllEnquiry(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId").toString());   
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        /* if (email != null) {
         this.viewUpcomingEnquire();
         }
         */
        java.util.Date date = new java.util.Date();
        edate = new Timestamp(date.getTime());
    }

    //************************************************Purchase Order************************************************
    public String displaySenderName() {
        receiverNames = osbl.getALLcompany(email);

        return osbl.getSenderName(email);
    }

    public void displaySellerProducts() {
        sender = osbl.getSellerAccount(receiverName);
        System.out.println("sellerName:*****************************************************" + receiverName);

        Long id = sender.getCompany().getId();
        products = osbl.getSellerProducts(id);
        
        productSelected=osbl.getSellerSelectedProductsName(selectedNames);

        while (j >= 0 && j < products.size()) {

            desc.add(products.get(j).getDescription());

            j++;
        }
        j++;
        productsN = osbl.getSellerProductsName(id);
        System.out.println("Dispaly Products:*****************************************************" + productsN);

        psize = selectedNames.size();

        String[] strarray = selectedNames.toArray(new String[0]);

        arrayToString = Arrays.toString(strarray);
        System.out.println("Quantities Entered*****************************************************" + arrayToString);
        quantities = Arrays.asList(quantitiesEntered.split(","));
        System.out.println("Quantities Entered*****************************************************" + quantities);

    }

    public void createPurchaseOrder(String email) {

        Timestamp tmp = new Timestamp(edate.getTime());

        String createdate = tmp.toString();

        quantities = Arrays.asList(quantitiesEntered.split(","));
        System.out.println("Quantities Entered*****************************************************" + quantities);

        for (int i = 0; i < psize; i++) {
            orders.add(osbl.createNewOrder(quantities.get(i), selectedNames.get(i)));

        }
        System.out.println("OrderList:*****************************************************" + orders);
        /*
         buyerName = osbl.getBuyName(email);
         sellerName = osbl.getALLcompany(email);
         System.out.println("sellerName:*****************************************************" + sellerName);
         */
        sender = osbl.getBuyerAccount(email);
        receiver = osbl.getSellerAccount(receiverName);

        osbl.createPurchaseOrder(sender, receiver, orders, createdate);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful!", "A New Purchase Order is Created!.");

        RequestContext.getCurrentInstance().showMessageInDialog(message);

    }

    public List<PurchaseOrder> getAllPurchaseOrder(String email) {
        enSize = osbl.getAllPurchaseOrder(email).size();
        return osbl.getAllPurchaseOrder(email);
    }

    public void deletePurchaseOrder(long id) {
        osbl.deletePurchaseOrder(id);
    }

    public List<OrderList> getSinglePurchaseOrder(long id) {

        singleSeller = osbl.getSinglePurchaseOrder(id).getReceiver().getCompany().getCompanyName();
        singleBuyer = osbl.getSinglePurchaseOrder(id).getSender().getCompany().getCompanyName();

        purDate = osbl.getSinglePurchaseOrder(id).getCreatedate();
        detailOrders = osbl.getSinglePurchaseOrder(id).getOrder();
        detailSize = osbl.getSinglePurchaseOrder(id).getOrder().size();
        List<OrderList> tmp = osbl.getSinglePurchaseOrder(id).getOrder();
        return wosl.ATPcheck(tmp);
    }
    
    
    //***************************************Order Confirmation********************************************
    public List<SalesOrder> viewOrderConfirmation(){
        orderconfirmationsize=osbl.viewOrderConfirmation(email).size();
        return osbl.viewOrderConfirmation(email);
    }
    
    public void deleteOrderConfirmation(long id){
        osbl.deleteOrderConfirmation(id);
    }
    

//******************GETTER AND SETTER***********************************************************************
    public OESSessionLocal getOsbl() {
        return osbl;
    }

    public void setOsbl(OESSessionLocal osbl) {
        this.osbl = osbl;
    }

    public long getPurId() {
        return purId;
    }

    public void setPurId(long purId) {
        this.purId = purId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Account getSender() {
        return sender;
    }

    public int getOrderconfirmationsize() {
        return orderconfirmationsize;
    }

    public void setOrderconfirmationsize(int orderconfirmationsize) {
        this.orderconfirmationsize = orderconfirmationsize;
    }

    
    
    public void setSender(Account sender) {
        this.sender = sender;
    }

    public WMSOrderSessionLocal getWosl() {
        return wosl;
    }

    public void setWosl(WMSOrderSessionLocal wosl) {
        this.wosl = wosl;
    }

    public List<Product> getProductSelected() {
        return productSelected;
    }

    public void setProductSelected(List<Product> productSelected) {
        this.productSelected = productSelected;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public List<String> getReceiverNames() {
        return receiverNames;
    }

    public void setReceiverNames(List<String> receiverNames) {
        this.receiverNames = receiverNames;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public List<String> getProductsN() {
        return productsN;
    }

    public void setProductsN(List<String> productsN) {
        this.productsN = productsN;
    }

    public List<String> getSelectedNames() {
        return selectedNames;
    }

    public void setSelectedNames(List<String> selectedNames) {
        this.selectedNames = selectedNames;
    }

    public int getPsize() {
        return psize;
    }

    public void setPsize(int psize) {
        this.psize = psize;
    }

    public String getQuantitiesEntered() {
        return quantitiesEntered;
    }

    public void setQuantitiesEntered(String quantitiesEntered) {
        this.quantitiesEntered = quantitiesEntered;
    }

    public List<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<String> quantities) {
        this.quantities = quantities;
    }

    public String[] getStrarray() {
        return strarray;
    }

    public void setStrarray(String[] strarray) {
        this.strarray = strarray;
    }

    public String getArrayToString() {
        return arrayToString;
    }

    public void setArrayToString(String arrayToString) {
        this.arrayToString = arrayToString;
    }

    public List<OrderList> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderList> orders) {
        this.orders = orders;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getEnSize() {
        return enSize;
    }

    public void setEnSize(int enSize) {
        this.enSize = enSize;
    }

    public String getSingleSeller() {
        return singleSeller;
    }

    public void setSingleSeller(String singleSeller) {
        this.singleSeller = singleSeller;
    }

    public String getSingleBuyer() {
        return singleBuyer;
    }

    public void setSingleBuyer(String singleBuyer) {
        this.singleBuyer = singleBuyer;
    }

    public List<OrderList> getOrderl() {
        return orderl;
    }

    public void setOrderl(List<OrderList> orderl) {
        this.orderl = orderl;
    }

    public String getPurDate() {
        return purDate;
    }

    public void setPurDate(String purDate) {
        this.purDate = purDate;
    }

    public List<String> getDetailsquantities() {
        return detailsquantities;
    }

    public void setDetailsquantities(List<String> detailsquantities) {
        this.detailsquantities = detailsquantities;
    }

    public List<String> getDetailsproNames() {
        return detailsproNames;
    }

    public void setDetailsproNames(List<String> detailsproNames) {
        this.detailsproNames = detailsproNames;
    }

    public List<OrderList> getDetailOrders() {
        return detailOrders;
    }

    public void setDetailOrders(List<OrderList> detailOrders) {
        this.detailOrders = detailOrders;
    }

    public int getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(int detailSize) {
        this.detailSize = detailSize;
    }

}
