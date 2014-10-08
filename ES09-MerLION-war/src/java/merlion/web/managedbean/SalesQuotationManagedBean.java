/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.SalesQuotationSessionBeanLocal;
import merlion.entity.Customer;
import merlion.entity.OrderRecord;
import merlion.entity.Salesquotation;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "salesQuotationManagedBean")
@SessionScoped
public class SalesQuotationManagedBean implements Serializable {

    /**
     * Creates a new instance of SalesQuotationManagedBean
     */
    private SalesQuotationSessionBeanLocal sqsbl;
    private String name;
    private String phone;
    private String address;
    private String cityRegion;
    private Long recordId;
    private int quantity;
    private String title;
    public SalesQuotationManagedBean() {
    }
    public List<Salesquotation> getSalesquotation(String email, Long id){
        return sqsbl.getSalesquotation(email, id);
    }

    public Salesquotation getCustomerQuotation(String email, Long id){
        return sqsbl.getCustomerQuotation(email, id);
    }
    public List<OrderRecord> getAllrecord(String email){
        return sqsbl.getAllrecord(email);
    }
    public Customer getCustomer(String email, Long id){
        return sqsbl.getCustomer(email, id);
    }
    public Long addMoreToRecord(String email,Long oid, Long pid){
        return sqsbl.addMoreToRecord(email, oid, pid, quantity);
    }
    public Long createRecord(Long id){
        return sqsbl.createRecord(title, id, quantity);
    }

    public void updateQuotation(Long quotationid,String email){
       sqsbl.updateQuotation(quotationid, name, email, phone, address, cityRegion);
    }
    public Long placeQuotation(String email){
        return sqsbl.placeQuotation(name, email, phone, address, cityRegion, recordId);
    }

    public Long deleteQuotation(String email, Long id){
        return sqsbl.deleteQuotation(email, id);
    }
    public List<Salesquotation> getAllquotation(String email){
        return sqsbl.getAllquotation(email);
    }
    public Salesquotation getQuotationDetails(String email, Long id){
        return sqsbl.getQuotationDetails(email, id);
    }
    public List<Customer> getAllcustomer(String email){
        return sqsbl.getAllcustomer(email);
    }
    public Customer addCustomer(String email){
        return sqsbl.getCustomer(email, recordId);
    }
}
