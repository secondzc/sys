package com.tongyuan.model.domain;

import org.springframework.mail.SimpleMailMessage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ReviewMsg {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long fromUserId;
    @Column
    private Long toUserId;
    @Column
    private String content;
    @Column
    private String subject;

    public ReviewMsg(Long fromUserId, Long toUserId, String content, String subject) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
        this.subject = subject;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}