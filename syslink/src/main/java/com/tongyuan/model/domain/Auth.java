package com.tongyuan.model.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by yh on 2017/9/19.
 */
@Entity
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer authId;
    @Column(nullable = false)
    private String authName;
    @Column(nullable = false)
    private String authCode;
    @Column
    private String description;
    @Column
    private Integer ParentId;
    @Column
    private String url;
    @Column
    private Date createDate;

    public Auth(){};

    public Auth(Integer authId, String authName, String authCode, String description, Integer parentId, String url, Date createDate) {
        this.authId = authId;
        this.authName = authName;
        this.authCode = authCode;
        this.description = description;
        ParentId = parentId;
        this.url = url;
        this.createDate = createDate;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParentId() {
        return ParentId;
    }

    public void setParentId(Integer parentId) {
        ParentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
