package com.tongyuan.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xieyx on 2017-9-25.
 */
@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    //组件ID
    private long id;

    @Column( nullable = false)
    //模型Id
    private long modelId;

    @Column
    //当前模型Id
    private long currentModelId;

    @Column( nullable = false, length = 128)
    //组件名称
    private String name;

    @Column( nullable = false, length = 128)
    //组件类型
    private String Type;

    @Column
    //组件限制
    private String Modification;

    @Column
    //组件父类名称
    private String parentName;

    @Column( nullable = false)
    //组件父类Id
    private long parentId;

    @Column
    //创建时间
    private Date createTime;

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getCurrentModelId() {
        return currentModelId;
    }

    public void setCurrentModelId(long currentModelId) {
        this.currentModelId = currentModelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
