/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import CI.Entity.Account;

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
    private String create_date;
    private String notes;
    
    
    //不是account么？
    @ManyToOne
    private Account service_requester;
    
    @ManyToOne
    private Account service_provider;

    @OneToOne
    private Contract Contract;

 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Contract getContract() {
        return Contract;
    }

    public void setContract(Contract Contract) {
        this.Contract = Contract;
    }
    
    public Account getService_requester() {
        return service_requester;
    }

    public void setService_requester(Account service_requester) {
        this.service_requester = service_requester;
    }

    public Account getService_provider() {
        return service_provider;
    }

    public void setService_provider(Account service_provider) {
        this.service_provider = service_provider;
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
