package com.tongyuan.model.domain;

import javax.persistence.*;

@Entity
public class RoleAuth {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id ;

    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private Integer authId;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }


}
