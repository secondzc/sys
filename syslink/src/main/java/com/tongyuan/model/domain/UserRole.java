package com.tongyuan.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 82509 on 2017/9/10.
 */
@Entity
public class UserRole {
    @Id

    @Column(nullable = false)
    private long userId ;

    @Column
    private String roles;

    @Column
    private Date createDate;

    public UserRole(String roles, Date createDate) {
        this.roles = roles;
        this.createDate = createDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
