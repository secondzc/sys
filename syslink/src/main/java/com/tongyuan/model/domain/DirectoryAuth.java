package com.tongyuan.model.domain;

import javax.persistence.*;

/**
 * Created by yh on 2017/10/26.
 */
@Entity
public class DirectoryAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;
    @Column
    private long directoryId;
    @Column(nullable = false)
    private long uid;
    @Column
    private int mode;


    public DirectoryAuth(){};
    public DirectoryAuth(long directoryId, long uid) {
        this.directoryId = directoryId;
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(long directoryId) {
        this.directoryId = directoryId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
