package com.tongyuan.pageModel;

import java.util.List;

/**
 * Created by xyx on 2017-9-14.
 */
public class TreeObj {
    //树节点id
    private Long id;
    //树节点名称
    private String label;
    private List<TreeObj> children;

    public List<TreeObj> getChildren() {
        return children;
    }

    public void setChildren(List<TreeObj> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
