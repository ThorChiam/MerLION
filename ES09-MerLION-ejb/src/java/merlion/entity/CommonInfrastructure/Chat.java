/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merlion.entity.CommonInfrastructure;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author sunny
 */
@Entity
public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String anotherChatter;
    private String chatContent;
    private long chatTime;
    
    @ManyToOne
    private Account chat_sender;

    @ManyToOne
    private Account chat_receiver;

    public Chat(){
       setId(System.nanoTime());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Account getChat_sender() {
        return chat_sender;
    }

    public void setChat_sender(Account chat_sender) {
        this.chat_sender = chat_sender;
    }

    public Account getChat_receiver() {
        return chat_receiver;
    }

    public void setChat_receiver(Account chat_receiver) {
        this.chat_receiver = chat_receiver;
    }
    
    public String getAnotherChatter() {
        return anotherChatter;
    }

    public void setAnotherChatter(String anotherChatter) {
        this.anotherChatter = anotherChatter;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public long getChatTime() {
        return chatTime;
    }

    public void setChatTime(long chatTime) {
        this.chatTime = chatTime;
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
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "merlion_new_enetity.Chat[ id=" + id + " ]";
    }
    
}
