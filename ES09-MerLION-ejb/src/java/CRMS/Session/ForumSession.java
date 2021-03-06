/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.Session;


import CI.Entity.Account;
import CRMS.Entity.Forume_Replies;
import CRMS.Entity.Post;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@Stateless
public class ForumSession implements ForumSessionLocal {

    @PersistenceContext
    private EntityManager em;
    private Post post;
    
    @Override
    public void createEnquiry(String email, String title, String content, String time){
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.email=:email");
        q.setParameter("email", email);
        
        post=new Post();
        post.setAccount((Account)q.getSingleResult());
       
        post.setTitle(title);
        post.setContent(content);
        post.setPost_date(time);
        
        em.persist(post);
    }
    
    @Override
    public List<Post> getAllPostTitles(){
        Query q=em.createQuery("SELECT p FROM Post P");
        return q.getResultList();
        
    }
    
    @Override
    public Post getPostDetail(long aId){
        Query q=em.createQuery("SELECT p FROM Post p WHERE p.id=:aId");
        q.setParameter("aId", aId);
        return (Post)q.getSingleResult();
    }
    
    @Override
    public void addreply(String reply,long id,String email){
        Query q=em.createQuery("SELECT p FROM Account p WHERE p.email=:email");
        q.setParameter("email", email);
        Account account=(Account)q.getSingleResult();
        Post tmp=getPostDetail(id);
        Forume_Replies a=new Forume_Replies();
        a.setComment(reply);
        a.setAccount(account);
        a.setPost(tmp);
        em.persist(a);
    }
    
    @Override
    public List<Forume_Replies> getreplylist(long id){
        Query q=em.createQuery("SELECT p FROM Post p WHERE p.id=:aId");
        q.setParameter("aId", id);
        Post post=(Post)q.getSingleResult();
        return post.getReplies();
    }
}
