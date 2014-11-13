/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Entity;

import CI.Entity.Account;
import WMS.Entity.WMSServiceCatalog;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author sunny
 */
@Entity
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sign_date;
    //pending(original status when created);
    //provider terminated
    //requestor terminated
    //destroyed
    //signed
    private String contract_status;
    private int total_price;
    @ManyToOne
    private WMSServiceCatalog wservice;
    @OneToOne
    private Account provider;
    @ManyToOne
    private Account requestor;
    @ManyToOne
    private Payment payment;

    public Contract() {
        setId(System.nanoTime());
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getContract_status() {
        return contract_status;
    }

    public void setContract_status(String contract_status) {
        this.contract_status = contract_status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public WMSServiceCatalog getWservice() {
        return wservice;
    }

    public void setWservice(WMSServiceCatalog wservice) {
        this.wservice = wservice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Account getProvider() {
        return provider;
    }

    public void setProvider(Account provider) {
        this.provider = provider;
    }

    public Account getRequestor() {
        return requestor;
    }

    public void setRequestor(Account requestor) {
        this.requestor = requestor;
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
        if (!(object instanceof Contract)) {
            return false;
        }
        Contract other = (Contract) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Contract[ id=" + id + " ]";
    }

}
