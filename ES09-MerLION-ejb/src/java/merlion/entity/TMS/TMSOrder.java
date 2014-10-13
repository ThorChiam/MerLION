/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.TMS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import merlion.entity.CRMS.Post;
import merlion.entity.CommonInfrastructure.Account;

/**
 *
 * @author sunny
 */
@Entity
public class TMSOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    Account Account;
    
    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,mappedBy="TMSOrder")
    private List<TMSDispatchOrder> dispactch = new ArrayList<>();

    
    public List<TMSDispatchOrder> getDispactch() {
        return dispactch;
    }

    public void setDispactch(List<TMSDispatchOrder> dispactch) {
        this.dispactch = dispactch;
    }

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }
    
    
    public TMSOrder(){
        setId(System.nanoTime());
    }
     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof TMSOrder)) {
            return false;
        }
        TMSOrder other = (TMSOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion.entity.TMS.TMSOrder[ id=" + id + " ]";
    }
    
}
