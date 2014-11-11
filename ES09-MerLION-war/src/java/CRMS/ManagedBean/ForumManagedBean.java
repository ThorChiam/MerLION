/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Post;
import CRMS.Session.ForumSessionLocal;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author USER
 */
@ManagedBean(name = "fomb")
@SessionScoped
public class ForumManagedBean implements Serializable {

    @EJB
    ForumSessionLocal fsl;
    private long id;
    private String email;
    private String title;
    private String content;
    private Date edate;
    private String createdate;
    private String statusMessage;
    private List<Post> postTitleList;
    private List<Post> postDetail;
    
    public ForumManagedBean(){
        
    }
    
    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
    }
    
    public String createPost(){
        java.util.Date date = new java.util.Date();
        
        edate = new Timestamp(date.getTime());
       
        
        Timestamp tmp = new Timestamp(edate.getTime());
        
        createdate = tmp.toString();
        fsl.createEnquiry(email, title, content, createdate);
        
        statusMessage="Post successfully!";
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
        
        return "forumHome";
    }
    
    public List<Post> getAllTitles(){
        postTitleList=fsl.getAllPostTitles();
        
        return fsl.getAllPostTitles();
        
    }
    
    public List<Post> viewPostDetail(long aId){
        System.out.println("************forumManagedBean: input aId"+aId);
        postDetail=new ArrayList();
        postDetail.add(fsl.getPostDetail(aId));
        
        return postDetail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Post> getPostTitleList() {
        return postTitleList;
    }

    public void setPostTitleList(List<Post> postTitleList) {
        this.postTitleList = postTitleList;
    }

    public List<Post> getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(List<Post> postDetail) {
        this.postDetail = postDetail;
    }

    
}
