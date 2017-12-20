package com.tongyuan.gogs.domain;

public class Collaboration {

    private long id;

    private long repoId;

    private long userId;

    private Integer mode;

    public Collaboration(){};

    public Collaboration(long id, long repoId, long userId, Integer mode) {
        this.id = id;
        this.repoId = repoId;
        this.userId = userId;
        this.mode = mode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRepoId() {
        return repoId;
    }

    public void setRepoId(long repoId) {
        this.repoId = repoId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
