package com.tongyuan.model.domain;

import javax.persistence.*;

/**
 * Created by yh on 2017/10/25.
 */
@Entity
public class UserDepart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id ;

    @Column(nullable = false)
    private long uid;

    @Column(nullable = false)
    private Integer departId;

    public UserDepart(){};
    public UserDepart(long uid, Integer departId) {
        this.uid = uid;
        this.departId = departId;
    }

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

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }
}
