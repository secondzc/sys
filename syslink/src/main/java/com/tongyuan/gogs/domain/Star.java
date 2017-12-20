package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2017-11-2.
 */
public class Star {
    private long ID;
    private long Uid;
    private long RepoId;
    public Star(){}

    public Star(long ID, long uid, long repoId) {
        this.ID = ID;
        Uid = uid;
        RepoId = repoId;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getUid() {
        return Uid;
    }

    public void setUid(long uid) {
        Uid = uid;
    }

    public long getRepoId() {
        return RepoId;
    }

    public void setRepoId(long repoId) {
        RepoId = repoId;
    }
}
