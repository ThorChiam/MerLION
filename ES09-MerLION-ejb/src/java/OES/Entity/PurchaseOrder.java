/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import CI.Entity.Account;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author sunny
 */
@Entity
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   
    //private double taxrate;
    
    private String createdate;
    //private String deliverydate;
    //private int delete_status;
   /*
    @OneToOne
    SalesOrder salesorder;
    
   */
    
    @OneToOne
    SalesOrder salesorder;
      
    @ManyToOne
    private Account sender;
    
    @ManyToOne
    private Account receiver;
    
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "porder")
    private List<OrderList> order=new ArrayList();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "porder")
    private List<OrderListSub> ordersub=new ArrayList();


    public PurchaseOrder(){
        setId(System.nanoTime());
    }
    
   
    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public SalesOrder getSalesorder() {
        return salesorder;
    }

    public void setSalesorder(SalesOrder salesorder) {
        this.salesorder = salesorder;
    }
*/

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }
    

    
    public List<OrderList> getOrder() {
        return order;
    }

    public void setOrder(List<OrderList> order) {
        this.order = order;
    }

    public List<OrderListSub> getOrdersub() {
        return ordersub;
    }

    public void setOrdersub(List<OrderListSub> ordersub) {
        this.ordersub = ordersub;
    }
  
  



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OES.entity.OES_PurchaseOrder[ id=" + id + " ]";
    }
    
}
