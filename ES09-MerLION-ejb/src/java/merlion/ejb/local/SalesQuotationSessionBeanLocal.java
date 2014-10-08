/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.ejb.local;

import java.util.List;
import javax.ejb.Local;
import merlion.entity.Customer;
import merlion.entity.OrderRecord;
import merlion.entity.Salesquotation;

/**
 *
 * @author ThorChiam
 */
@Local
public interface SalesQuotationSessionBeanLocal {

    public List<Salesquotation> getSalesquotation(String email, Long id);

    public Salesquotation getCustomerQuotation(String email, Long id);

    public List<OrderRecord> getAllrecord(String email);

    public Customer getCustomer(String email, Long id);

    public Long addMoreToRecord(String email,Long oid, Long pid, int quantity);

    public Long createRecord(String title, Long id, int quantity);

    public void updateQuotation(Long quotationid, String name, String email, String phone, String address, String cityRegion);

    public Long placeQuotation(String name, String email, String phone, String address, String cityRegion, Long recordId);

    public Long deleteQuotation(String email, Long id);

    public List<Salesquotation> getAllquotation(String email);

    public Salesquotation getQuotationDetails(String email, Long id);

    public List<Customer> getAllcustomer(String email);

    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion);
}
