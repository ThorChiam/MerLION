
package merlion_ejb.entity;

import java.io.Serializable;
//import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Salesquotation")
public class Salesquotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "date_created")
    private String dateCreated;
    private double total;
    @Basic(optional = false)
    @Column(name = "confirmation_number")
    private int confirmationNumber;
    @OneToOne(mappedBy="salesquotation")
    private OrderRecord record;

    //Added by QT
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Account Account;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    public Salesquotation() {
        setId(System.nanoTime());
        
    }

    //Added by QT
    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    public double getTotal(){
        return total;
    }
    public void setTotal(double total){
        this.total=total;
    }
    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public OrderRecord getRecord() {
        return record;
    }

    public void setRecord(OrderRecord record) {
        this.record=record;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        if (!(object instanceof Salesquotation)) {
            return false;
        }
        Salesquotation other = (Salesquotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Salesquotation[id=" + id + "]";
    }

}
    
    
    

