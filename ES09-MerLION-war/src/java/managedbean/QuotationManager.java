
package managedbean;

import merlion_ejb.entity.PurchaseOrder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import merlion_ejb.QuotationSessionLocal;
import merlion_ejb.entity.OrderRecord;
import merlion_ejb.entity.Salesquotation;


/**
 *
 * @author Tomato
 */
public class QuotationManager {
    public QuotationSessionLocal qLocal;
    
    
    public QuotationManager(){
        
    }
    public QuotationManager(QuotationSessionLocal qLocal){
        this.qLocal=qLocal;
    }
    /*
    public Long placeQuotation(HttpServletRequest request){
                
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String cityRegion=request.getParameter("cityRegion");
        String address=request.getParameter("address");
      
        String id=request.getParameter("product");
        Long product=id.equals("")? 0: Long.parseLong(id);      
        String quantityStr=request.getParameter("quantity");
        int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
        return qLocal.placeQuotation(name, email, phone, address, cityRegion, product, quantity);
    }
    */
     public Long createRecord(HttpServletRequest request){
        String title=request.getParameter("title");
        String id=request.getParameter("product");
        Long product=id.equals("")? 0: Long.parseLong(id);      
        String quantityStr=request.getParameter("quantity");
        int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
        return qLocal.createRecord(title, product, quantity);
     }
     
     public Long addMoreToRecord(HttpServletRequest request){
        
        String rid=request.getParameter("record");
        Long recordId=rid.equals("")? 0: Long.parseLong(rid); 
        String id=request.getParameter("product");
        Long product=id.equals("")? 0: Long.parseLong(id);      
        String quantityStr=request.getParameter("quantity");
        int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
        return qLocal.addMoreToRecord(recordId, product, quantity);
     }
     
     public List<Salesquotation> getAllquotation(){
         return qLocal.getAllquotation();
     }
    
     public Salesquotation getCustomerQuotation(Long id){
         return qLocal.getCustomerQuotation(id);
     }
     
         
    public Long placeQuotation(HttpServletRequest request){
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String cityRegion=request.getParameter("cityRegion");
        String address=request.getParameter("address");
      
        String id=request.getParameter("record");
        Long record=id.equals("")? 0: Long.parseLong(id);      
        
        return qLocal.placeQuotation(name, email, phone, address, cityRegion, record);
        
    }
     public Long deleteQuotation(HttpServletRequest request){
         
       
       String idStr=request.getParameter("id");
       Long id=idStr.equals("")? 0: Long.parseLong(idStr);
       
       return qLocal.deleteQuotation(id);
     }
     
     public Salesquotation getQuotationDetails(Long id){
           
           return qLocal.getQuotationDetails(id);
      }
     
    
     public List<Salesquotation>  getSalesquotation(HttpServletRequest request){
   
        String quotationId=request.getParameter("id");
        Long   qid=quotationId.equals("")? 0:Long.parseLong(quotationId);
        System.out.println("******************************************"+qid);
         return qLocal.getSalesquotation(qid);
     }
     
     
     public List<PurchaseOrder> getPurchaseOrderDetails(HttpServletRequest request){
         
        String orderId=request.getParameter("id");
        Long   pid=orderId.equals("")? 0:Long.parseLong(orderId);
        System.out.println("quotation manager : purchcase order******************************************"+pid);
         
        return qLocal.getPurchaseOrderDetails(pid);
     }
 
     public List<OrderRecord> getAllrecord(){
          return qLocal.getAllrecord();
      }
     
     public PurchaseOrder getPOrderDetails(Long id){
         return qLocal.getPOrderDetails(id);
     }
      
      public List<PurchaseOrder> getPurchaseOrder(){
         return qLocal.getPurchaseOrder();
     }
     
     public Long updatePurchaseOrder(HttpServletRequest request){
        
        String pId=request.getParameter("id");
        Long porderId = Long.valueOf(pId);
        System.out.println("******************************************update"+porderId );
        
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        String cityRegion=request.getParameter("cityRegion");
        
        qLocal.updatePurchaseOrder(porderId, name, email, phone, address, cityRegion);
        return porderId;
         
     }
      
      
      
    
     public Long updateQuotation(HttpServletRequest request){
      
        String pId=request.getParameter("id");
        Long quotationId = Long.valueOf(pId);
         System.out.println("******************************************update"+quotationId );
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        String cityRegion=request.getParameter("cityRegion");
        
        
        qLocal.updateQuotation(quotationId, name, email, phone, address, cityRegion);
        return quotationId;
    }
     
     public Long placePurchaseOrder(HttpServletRequest request){
           
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String cityRegion=request.getParameter("cityRegion");
        String address=request.getParameter("address");
        
        String shippingdate=request.getParameter("shippingdate");
        String paymentTerm=request.getParameter("paymentTerm");
        String currency=request.getParameter("currency");
        String shippingTerm=request.getParameter("shippingTerm");
        
        double taxRate=Double.valueOf(request.getParameter("taxRate"));
        double discount=Double.valueOf(request.getParameter("discount"));
        

        String id=request.getParameter("record");
        Long record=id.equals("")? 0: Long.parseLong(id);      
           
        return qLocal.placePurchaseOrder(name, email, phone, address, cityRegion, shippingdate, paymentTerm, currency, shippingTerm, taxRate, discount, record);
       }
    /*

    public Long addItemToQuotation(HttpServletRequest request){
            String quantityStr=request.getParameter("quantity");
            int quantity=quantityStr.equals("")? 0: Integer.parseInt(quantityStr);
            String pId=request.getParameter("product");
            Long productId=pId.equals("")? 0: Long.parseLong(pId);
            String qId=request.getParameter("quotationId");
            Long quotationId=qId.equals("")? 0: Long.parseLong(qId);   
            return qLocal.addNewQuotation(quantity, productId, quotationId);
          
      }
      
    public List<Product> getAllProduct(){
        return qLocal.getAllProduct();
    }
    */
     public void deletePurchaseOrder(HttpServletRequest request){
         
       String idStr=request.getParameter("id");
       Long id=idStr.equals("")? 0: Long.parseLong(idStr);
       qLocal.deletePurchaseOrder(id);
       
    }
     
     
}
          



          


