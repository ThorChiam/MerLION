/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.web.managedbean;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import merlion.ejb.local.FavoritesSessionBeanLocal;
import merlion.entity.Favorite;

/**
 *
 * @author ThorChiam
 */
@ManagedBean(name = "favoritesManagedBean")
@SessionScoped
public class FavoritesManagedBean implements Serializable {

    /**
     * Creates a new instance of FavoritesManagedBean
     */
    private FavoritesSessionBeanLocal fsbl;
    private Long companyId;
    private String remark;
    public FavoritesManagedBean() {
    }
    public List<Favorite> getFavoriteList(String email){
        return fsbl.getFavoriteList(email);
    }

    public Favorite getFavorite(String email, Long companyId){
        return fsbl.getFavorite(email, companyId);
    }

    public void addFavorite(String email){
        fsbl.addFavorite(email, companyId, remark);
    }

    public void deleteFavorite(String email){
        fsbl.deleteFavorite(email, companyId);
    }

    public String updateFavorite(String email){
        return fsbl.updateFavorite(email, companyId, remark);
    }
}
