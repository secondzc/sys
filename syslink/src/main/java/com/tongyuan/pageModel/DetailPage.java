package com.tongyuan.pageModel;

import com.tongyuan.model.domain.ReviewNode;

import com.tongyuan.gogs.domain.GUser;

import java.sql.Timestamp;

/**
 * Created by Y470 on 2017/9/6.
 */
public class DetailPage {
    private Long id;
    private Byte status;
    private Timestamp createTime;
    private  Timestamp lastUpdateTime;
    private ReviewNode node;
    private GUser user;

    public GUser getUser() {
        return user;
    }

    public void setUser(GUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public ReviewNode getNode() {
        return node;
    }

    public void setNode(ReviewNode node) {
        this.node = node;
    }
}
