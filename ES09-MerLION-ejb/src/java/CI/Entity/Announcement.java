package CI.Entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author xiya
 */
@Entity
@Table(name = "Announcement")
public class Announcement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    private Long annId;
    private String content;
    private String releasedate;

    //@ManyToMany(cascade={CascadeType.ALL},mappedBy="announcements")
    //private Set<Account> accounts=new HashSet<>();
    
    @ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER,optional=false)
    private MerlionAdmin admin;

    public Announcement() {
        this.setAnnId(System.nanoTime());
    }
    
    public void add(Long announ_Id, String content) {
        this.annId=announ_Id;
        this.content=content;
    }   

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }
    
    public MerlionAdmin getMerlionAdmin() {
        return admin;
    }

    public void setMerlionAdmin(MerlionAdmin admin) {
        this.admin = admin;
    }

    /*
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    */

    public long getAnnId() {
        return annId;
    }

    public void setAnnId(Long annId) {
        this.annId = annId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annId != null ? annId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Announcement)) {
            return false;
        }  
        Announcement other = (Announcement) object;
        return (this.annId != null || other.annId == null) && (this.annId == null || this.annId.equals(other.annId));
    }

    @Override
    public String toString() {
        return "merlion_ejb.Announcement[ annId=" + annId + " ]";
    }
    
}
