package com.tongyuan.model.domain;

/**
 * Created by zhangcy on 2017/9/30.
 */
public class NodePage {
    private Long nodeId;
    private String reviewNodeName;
    private String description;
    private String userName;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getReviewNodeName() {
        return reviewNodeName;
    }

    public void setReviewNodeName(String reviewNodeName) {
        this.reviewNodeName = reviewNodeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
