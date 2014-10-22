/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.CRMS;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author ThorChiam
 */
@Entity
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long favorite_id;
    private String remarks;

    //Added by QT
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Company favoritor;
    
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Company favoritee;

    public Long getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(Long favorite_id) {
        this.favorite_id = favorite_id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Company getFavoritor() {
        return favoritor;
    }

    public void setFavoritor(Company favoritor) {
        this.favoritor = favoritor;
    }

    public Company getFavoritee() {
        return favoritee;
    }

    public void setFavoritee(Company favoritee) {
        this.favoritee = favoritee;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (favorite_id != null ? favorite_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favorite)) {
            return false;
        }
        Favorite other = (Favorite) object;
        if ((this.favorite_id == null && other.favorite_id != null) || (this.favorite_id != null && !this.favorite_id.equals(other.favorite_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Favorite[ id=" + favorite_id + " ]";
    }

}
