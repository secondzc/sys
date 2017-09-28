package com.tongyuan.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yh on 2017/9/11.
 */
@Entity
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long userId ;

    @Column
    private String permissions;

    @Column
    private Date createDate;


    public UserPermission(String permissions, Date createDate) {
        this.permissions = permissions;
        this.createDate = createDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
