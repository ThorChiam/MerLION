/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRP.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import CRMS.Entity.Contract;

@Entity
public class Template implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;
    @ManyToMany(cascade={CascadeType.ALL},mappedBy="Template")
    private Set<Contract> contract=new HashSet<>();

    private String name;
    private int firstweek;
    private int secondweek;
    private int thirdweek;
    private int fourthweek;
    private int fifthweek;
    private int sixthweek;
    private int seventhweek;
    
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = false)
    private Item item;
    
    
    public Set<Contract> getContract() {
        return contract;
    }

    public void setContract(Set<Contract> contract) {
        this.contract = contract;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirstweek() {
        return firstweek;
    }

    public void setFirstweek(int firstweek) {
        this.firstweek = firstweek;
    }

    public int getSecondweek() {
        return secondweek;
    }

    public void setSecondweek(int secondweek) {
        this.secondweek = secondweek;
    }

    public int getThirdweek() {
        return thirdweek;
    }

    public void setThirdweek(int thirdweek) {
        this.thirdweek = thirdweek;
    }

    public int getFourthweek() {
        return fourthweek;
    }

    public void setFourthweek(int fourthweek) {
        this.fourthweek = fourthweek;
    }

    public int getFifthweek() {
        return fifthweek;
    }

    public void setFifthweek(int fifthweek) {
        this.fifthweek = fifthweek;
    }

    public int getSixthweek() {
        return sixthweek;
    }

    public void setSixthweek(int sixthweek) {
        this.sixthweek = sixthweek;
    }

    public int getSeventhweek() {
        return seventhweek;
    }

    public void setSeventhweek(int seventhweek) {
        this.seventhweek = seventhweek;
    }

    



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateId != null ? templateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
// TODO: Warning - this method won't work in the case the eventId fields are not set
        if (!(object instanceof Template)) {
            return false;
        }
        Template other = (Template) object;
        if ((this.templateId == null && other.templateId != null) || (this.templateId != null && !this.templateId.equals(other.templateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Event[id=" + templateId + "]";
    }
}
