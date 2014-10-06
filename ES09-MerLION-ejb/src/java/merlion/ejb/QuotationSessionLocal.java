/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.ejb;

import javax.ejb.Local;
import java.util.List;
import merlion.entity.OrderRecord;
import merlion.entity.Product;
import merlion.entity.PurchaseOrder;
import merlion.entity.Salesquotation;


/**
 *
 * @author Tomato
 */
@Local
public interface QuotationSessionLocal {
     
    //******************************Product****************************************************
     public List<OrderRecord> getAllrecord();
     public List<Product> getAllProduct();
   
     //*****************************Quotation***************************************************
     public Long createRecord(String title,Long id,int quantity);
     public Long addMoreToRecord(Long id,Long productId,int quantity);
     public Long placeQuotation(String name, String email, String phone, String address, String cityRegion, Long recordId);
     public List<Salesquotation> getAllquotation();
     public Salesquotation getCustomerQuotation(Long id);
     public Long deleteQuotation(Long id);
     public Salesquotation getQuotationDetails(Long id);
     public List<Salesquotation>  getSalesquotation(Long id);
     public void updateQuotation(Long quotationid, String name, String email, String phone, String address, String cityRegion);
     
     //**************************Purchase Order*************************************************
     public List<PurchaseOrder> getPurchaseOrder();
     public Long placePurchaseOrder(String name, String email, String phone, String address, String cityRegion,
     String shippingdate,String paymentTerm,String currency,String shippingTerm,double taxRate, double discount,Long recordId);
     public void deletePurchaseOrder(Long id);
     public List<PurchaseOrder> getPurchaseOrderDetails(Long id);
     public void updatePurchaseOrder(Long porderid, String name, String email, String phone, String address, String cityRegion);
     public PurchaseOrder getPOrderDetails(Long id);
}
