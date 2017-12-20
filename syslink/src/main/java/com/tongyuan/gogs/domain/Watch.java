package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by xyx on 2017-9-18.
 */
public class Watch {
    private long ID;
    private long UserID;
    private long RepoID;
    public Watch(){
    }

    public Watch(long ID, long userID, long repoID) {
        this.ID = ID;
        UserID = userID;
        RepoID = repoID;
    }

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

    public long getRepoID() {
        return RepoID;
    }

    public void setRepoID(long repoID) {
        RepoID = repoID;
    }
}
