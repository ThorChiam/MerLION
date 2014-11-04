/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WMS.Entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author ThorChiam
 */
@Entity
public class WMSOperation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String operationContent;
    private long operationStart;
    private long operationEnd;
    private String operationPeriod;
    @OneToOne(cascade = {CascadeType.ALL})
    private Employee employee;
    @OneToOne(cascade = {CascadeType.ALL})
    private WMSFacility facility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public long getOperationStart() {
        return operationStart;
    }

    public void setOperationStart(long operationStart) {
        this.operationStart = operationStart;
    }

    public long getOperationEnd() {
        return operationEnd;
    }

    public void setOperationEnd(long operationEnd) {
        this.operationEnd = operationEnd;
    }

    public String getOperationPeriod() {
        return operationPeriod;
    }

    public void setOperationPeriod(String operationPeriod) {
        this.operationPeriod = operationPeriod;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public WMSFacility getFacility() {
        return facility;
    }

    public void setFacility(WMSFacility facility) {
        this.facility = facility;
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
        if (!(object instanceof WMSOperation)) {
            return false;
        }
        WMSOperation other = (WMSOperation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WMS.Entity.WMSOperation[ id=" + id + " ]";
    }

}
