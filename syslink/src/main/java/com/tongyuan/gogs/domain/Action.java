package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by xyx on 2017-9-18.
 */
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    @Column
    private long UserID;
    @Column
    private Integer OpType;
    @Column
    private long ActUserID;
    @Column
    private String ActUserName;
    @Column
    private long RepoID;
    @Column
    private String RepoUserName;
    @Column
    private String RepoName;
    @Column
    private String RefName;
    @Column
    private Boolean IsPrivate;
    @Column
    private String Content;
    @Column
    private Long CreatedUnix;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public Integer getOpType() {
        return OpType;
    }

    public void setOpType(Integer opType) {
        OpType = opType;
    }

    public long getActUserID() {
        return ActUserID;
    }

    public void setActUserID(long actUserID) {
        ActUserID = actUserID;
    }

    public String getActUserName() {
        return ActUserName;
    }

    public void setActUserName(String actUserName) {
        ActUserName = actUserName;
    }

    public long getRepoID() {
        return RepoID;
    }

    public void setRepoID(long repoID) {
        RepoID = repoID;
    }

    public String getRepoUserName() {
        return RepoUserName;
    }

    public void setRepoUserName(String repoUserName) {
        RepoUserName = repoUserName;
    }

    public String getRepoName() {
        return RepoName;
    }

    public void setRepoName(String repoName) {
        RepoName = repoName;
    }

    public String getRefName() {
        return RefName;
    }

    public void setRefName(String refName) {
        RefName = refName;
    }

    public Boolean getPrivate() {
        return IsPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        IsPrivate = aPrivate;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getCreatedUnix() {
        return CreatedUnix;
    }

    public void setCreatedUnix(Long createdUnix) {
        CreatedUnix = createdUnix;
    }
}
