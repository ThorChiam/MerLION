/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;

import CRMS.Entity.Forume_Replies;
import CRMS.Entity.Post;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ForumSessionLocal {

   public void createEnquiry(String email, String title, String content, String time);

   public List<Post> getAllPostTitles();
   
   public Post getPostDetail(long aId);
   
   public void addreply(String reply,long id,String email);
   
   public List<Forume_Replies> getreplylist(long id);
}
