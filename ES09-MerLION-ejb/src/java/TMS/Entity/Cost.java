/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMS.Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author sunny
 */
@Entity
public class Cost implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne(mappedBy="TMSCost")
    DispatchOrder TMSDispatchOrder;


    public Cost(){
        setId(System.nanoTime());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DispatchOrder getTMSDispatchOrder() {
        return TMSDispatchOrder;
    }

    public void setTMSDispatchOrder(DispatchOrder TMSDispatchOrder) {
        this.TMSDispatchOrder = TMSDispatchOrder;
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
        if (!(object instanceof Cost)) {
            return false;
        }
        Cost other = (Cost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion.entity.TMS.TMSCost[ id=" + id + " ]";
    }
    
}
