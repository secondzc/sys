package com.tongyuan.model.domain;

import javax.persistence.*;

/**
 * Created by yh on 2017/9/11.
 */
@Entity
public class UserAuth {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id ;

    @Column(nullable = false)
    private long uid;

    @Column(nullable = false)
    private Integer authId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }
}
