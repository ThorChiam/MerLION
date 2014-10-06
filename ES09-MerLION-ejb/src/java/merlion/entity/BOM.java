/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author Zeng Xunhao
 */
@Entity
public class BOM implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long comp1;
    private Long comp2;
    private Long comp3;
    private int partnumber1;
    private int partnumber2;
    private int partnumber3;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = false)
    private Item item;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComp1() {
        return comp1;
    }

    public void setComp1(Long comp1) {
        this.comp1 = comp1;
    }

    public Long getComp2() {
        return comp2;
    }

    public void setComp2(Long comp2) {
        this.comp2 = comp2;
    }

    public Long getComp3() {
        return comp3;
    }

    public void setComp3(Long comp3) {
        this.comp3 = comp3;
    }

    public int getPartnumber1() {
        return partnumber1;
    }

    public void setPartnumber1(int partnumber1) {
        this.partnumber1 = partnumber1;
    }

    public int getPartnumber2() {
        return partnumber2;
    }

    public void setPartnumber2(int partnumber2) {
        this.partnumber2 = partnumber2;
    }

    public int getPartnumber3() {
        return partnumber3;
    }

    public void setPartnumber3(int partnumber3) {
        this.partnumber3 = partnumber3;
    }

   

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
        if (!(object instanceof BOM)) {
            return false;
        }
        BOM other = (BOM) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BOM[ id=" + id + " ]";
    }
    
}
