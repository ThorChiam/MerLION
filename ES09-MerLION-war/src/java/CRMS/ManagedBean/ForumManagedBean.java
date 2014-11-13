/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMS.ManagedBean;

import CRMS.Entity.Forume_Replies;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author USER
 */
@ManagedBean(name = "fomb")
@ViewScoped
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
    private boolean checkreply;
    private long tmp_id;
    private String replycontent;
    private List<Forume_Replies> replylist;
    private boolean checkdetail;
    private long tmp_detail_id;

    public ForumManagedBean() {

    }

    @PostConstruct
    public void init() {
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
        Long postId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("postId");
        if(postId!=null){
            id = postId;
            replylist=fsl.getreplylist(id);
        }
    }
    
    public void initId(long id){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("postId", id);
    }

    public String createPost() {
        java.util.Date date = new java.util.Date();

        edate = new Timestamp(date.getTime());

        Timestamp tmp = new Timestamp(edate.getTime());

        createdate = tmp.toString();
        fsl.createEnquiry(email, title, content, createdate);

        statusMessage = "Post successfully!";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));

        return "forumHome";
    }

    public List<Post> getAllTitles() {
        postTitleList = fsl.getAllPostTitles();

        return fsl.getAllPostTitles();

    }

    public List<Post> viewPostDetail() {
       
        Long aId = (long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("postId");
        replylist=fsl.getPostDetail(aId).getReplies();
        System.out.println("************forumManagedBean: input aId" + aId);
        postDetail = new ArrayList();
        postDetail.add(fsl.getPostDetail(aId));

        return postDetail;
    }

    public List<Forume_Replies> getreplylist() {
        Long aid = (long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("postId");
        
        replylist = fsl.getreplylist(aid);
        return replylist;
    }

    public void addreply() {
        System.out.println(replycontent);
        fsl.addreply(replycontent, tmp_id, email);
        statusMessage = "Reply added successfully!";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                statusMessage, ""));
        this.setCheckreply(false, 0);
    }

    //******************Getter and Setter
    
    public boolean isCheckdetail() {
        return checkdetail;
    }

    public void setCheckdetail(boolean checkdetail,long id) {
        this.checkdetail = checkdetail;
        this.tmp_detail_id=id;
    }
    
    public long getTmp_detail_id() {
        return tmp_detail_id;
    }

    public void setTmp_detail_id(long tmp_detail_id) {
        this.tmp_detail_id = tmp_detail_id;
    }


    public List<Forume_Replies> getReplylist() {
        return replylist;
    }

    public void setReplylist(List<Forume_Replies> replylist) {
        this.replylist = replylist;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public long getTmp_id() {
        return tmp_id;
    }

    public void setTmp_id(long tmp_id) {
        this.tmp_id = tmp_id;
    }

    public boolean getCheckreply() {
        return checkreply;
    }

    public void setCheckreply(boolean checkreply, long id) {
        this.checkreply = checkreply;
        this.setTmp_id(id);
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
