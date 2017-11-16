package com.tongyuan.model.domain;

import java.util.ArrayList;
import java.util.List;

public class ReviewUserNode {
    //部门和人员名子公用的数据结构，若为人员名字，则isLeaf=true
    private boolean isLeaf;
    private String label;
    private List<ReviewUserNode> children;
    private Integer deptId;
    //若isLeaf为true，则disabled为false
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public ReviewUserNode(){
        children = new ArrayList<ReviewUserNode>();
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ReviewUserNode> getChildren() {
        return children;
    }

    public void setChildren(List<ReviewUserNode> children) {
        this.children = children;
    }
}
