package com.tongyuan.model.domain;

import javax.persistence.*;

/**
 * Created by yh on 2017/12/25.
 */
@Entity
public class RoleDirectoryAuth {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;
    @Column
    private long directoryId;
    @Column(nullable = false)
    private long roleId;
    @Column
    private int mode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(long directoryId) {
        this.directoryId = directoryId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
