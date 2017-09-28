package com.tongyuan.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Created by tengj on 2017/3/29.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
//    用户名
    @Column(unique=true)
    private String userName;
//    密码
    @Column
    private String passWord;

    @Column
    private String salt;
//    部门
    @Column
    private long departmentId;



    //性别
    @Column
    private Short gender;
    //真实姓名
    @Column
    private String realName;
    //电子邮箱
    @Column
    private String email;


    //手机号码
    @Column
    private String mobile;
    //用户状态
    @Column
    private Short status;
    //创建人
    @Column
    private String createUser;
    //创建日期
    @Column
    private Date createDate;
    //修改人
    @Column
    private String updateUser;
    //修改时间
    @Column
    private Date updateDate;
    //是否允许删除
    @Column
    private Short deletable;
    //是否删除
    @Column
    private Short deleted;


    public User()
     {

     }


    public User(String userName, String passWord, String salt, long departmentId, Short gender,
                String realName, String email, String mobile, Short status, String createUser,
                Date createDate, String updateUser, Date updateDate, Short deletable, Short deleted) {
        this.userName = userName;
        this.passWord = passWord;
        this.salt = salt;
        this.departmentId = departmentId;
        this.gender = gender;
        this.realName = realName;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
        this.deletable = deletable;
        this.deleted = deleted;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Short getDeletable() {
        return deletable;
    }

    public void setDeletable(Short deletable) {
        this.deletable = deletable;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }
}


