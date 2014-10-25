/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OES.Entity;

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
public class OES_Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Release_date;
    private String notes;
    private int delete_status;

    
    @OneToOne(mappedBy="invoice") 
    OES_Payment payment;

    public OES_Invoice(){
        setId(System.nanoTime());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }
    
    public OES_Payment getPayment() {
        return payment;
    }

    public void setPayment(OES_Payment payment) {
        this.payment = payment;
    }

    public String getRelease_date() {
        return Release_date;
    }

    public void setRelease_date(String release_date) {
        this.Release_date = release_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(object instanceof OES_Invoice)) {
            return false;
        }
        OES_Invoice other = (OES_Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OES.entity.OES_Invoice[ id=" + id + " ]";
    }
    
}
