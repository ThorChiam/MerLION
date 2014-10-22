/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import java.util.List;
import javax.ejb.Local;
import CRMS.Entity.Favorite;

/**
 *
 * @author ThorChiam
 */
@Local
public interface FavoritesSessionBeanLocal {

    public List<Favorite> getFavoriteList(String email);

    public Favorite getFavorite(String email, Long companyId);

    public void addFavorite(String email, Long companyId, String remark);

    public void deleteFavorite(String email, Long companyId);

    public String updateFavorite(String email, Long companyId, String remark);
}
