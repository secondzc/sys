package com.tongyuan.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017-12-10.
 */
@Entity
public class ModelUnion {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    //目录ID
    private long id;

    @Column( nullable = false)
    //上传模型包（在model表里）Id
    private long modelId;
    @Column( nullable = false)
    //gogs仓库Id
    private long repositoryId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(long repositoryId) {
        this.repositoryId = repositoryId;
    }
}
