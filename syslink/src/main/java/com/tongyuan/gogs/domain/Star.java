package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2017-11-2.
 */
@Entity
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    @Column
    private long Uid;
    @Column
    private long RepoId;

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
