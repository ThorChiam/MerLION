/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.CRMS;

import merlion.entity.CRMS.Company;
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
public class ServiceOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    //不是account么？
    @ManyToOne
    private Company service_requester;
    
    @ManyToOne
    private Company service_receiver;

    @OneToOne
    Payment Payment;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Company getService_requester() {
        return service_requester;
    }

    public void setService_requester(Company service_requester) {
        this.service_requester = service_requester;
    }

    public Company getService_receiver() {
        return service_receiver;
    }

    public void setService_receiver(Company service_receiver) {
        this.service_receiver = service_receiver;
    }

    public Payment getPayment() {
        return Payment;
    }

    public void setPayment(Payment Payment) {
        this.Payment = Payment;
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
        if (!(object instanceof ServiceOrder)) {
            return false;
        }
        ServiceOrder other = (ServiceOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.ServiceOrder[ id=" + id + " ]";
    }
    
}
