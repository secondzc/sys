package com.tongyuan.model.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yh on 2017/9/7.
 */
@Entity
public class PermissionItem {
//主键
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;
//    权限组id
    @Column
    private long groupId;
//    权限对应的请求路径
    @Column
    private String url;

//    权限名称
    @Column
    private String name;
//  权限代码
    @Column
    private String code;
//  创建日期
    @Column
    private Timestamp createDate;
//  描述
    @Column
    private String description;
    public PermissionItem()
    {};


    public PermissionItem(long groupId, String url, String name, String code, Timestamp createDate, String description) {
        this.groupId = groupId;
        this.url = url;
        this.name = name;
        this.code = code;
        this.createDate = createDate;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
