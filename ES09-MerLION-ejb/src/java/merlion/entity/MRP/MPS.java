/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package merlion.entity.MRP;

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
public class MPS implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int firstmonth;
    private int secondmonth;
    private int thirdmonth;
    private int fourthmonth;
    private int fifthmonth;
    private int sixthmonth;
   
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = false)
    private Item item;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFirstmonth() {
        return firstmonth;
    }

    public void setFirstmonth(int firstmonth) {
        this.firstmonth = firstmonth;
    }

    public int getSecondmonth() {
        return secondmonth;
    }

    public void setSecondmonth(int secondmonth) {
        this.secondmonth = secondmonth;
    }

    public int getThirdmonth() {
        return thirdmonth;
    }

    public void setThirdmonth(int thirdmonth) {
        this.thirdmonth = thirdmonth;
    }

    public int getFourthmonth() {
        return fourthmonth;
    }

    public void setFourthmonth(int fourthmonth) {
        this.fourthmonth = fourthmonth;
    }

    public int getFifthmonth() {
        return fifthmonth;
    }

    public void setFifthmonth(int fifthmonth) {
        this.fifthmonth = fifthmonth;
    }

    public int getSixthmonth() {
        return sixthmonth;
    }

    public void setSixthmonth(int sixthmonth) {
        this.sixthmonth = sixthmonth;
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
        if (!(object instanceof MPS)) {
            return false;
        }
        MPS other = (MPS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MPS[ id=" + id + " ]";
    }
    
}
