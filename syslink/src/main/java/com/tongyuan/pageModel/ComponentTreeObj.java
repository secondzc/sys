package com.tongyuan.pageModel;

import java.util.List;

/**
 * Created by xieyx on 2017-9-27.
 */
public class ComponentTreeObj {
    //树节点id
    private Long id;
    //树节点名称
    private String name;
    //组件类型
    private String Type;
    //组件限制
    private String Modification;
    private List<ComponentTreeObj> children;
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getModification() {
        return Modification;
    }

    public void setModification(String modification) {
        Modification = modification;
    }

    public List<ComponentTreeObj> getChildren() {
        return children;
    }

    public void setChildren(List<ComponentTreeObj> children) {
        this.children = children;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
