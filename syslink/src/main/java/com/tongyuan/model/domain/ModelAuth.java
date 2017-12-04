package com.tongyuan.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by yh on 2017/10/20.
 */
@Entity
public class ModelAuth {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private long id ;
    @Column(name = "uid", nullable = false)
    private long uid;
    @Column(name = "modelId", nullable = false)
    private long modelId;
    @Column(name = "mode", nullable = false)
    private short mode;
    @Column(name = "nodeId", nullable = false)
    private String nodeId ;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public short getMode() {
        return mode;
    }

    public void setMode(short mode) {
        this.mode = mode;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
