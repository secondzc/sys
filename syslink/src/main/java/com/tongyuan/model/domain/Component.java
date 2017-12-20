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
    //自身model的id
    private long modelId;

    @Column
    //作为组件时，所属模型的id
    private long parentModelId;

    @Column( nullable = false, length = 128)
    //组件名称
    private String name;

    @Column( nullable = false, length = 128)
    //组件类型名字
    private String Type;

    @Column
    //变更
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
    @Column
    //组件类别 class,model,connector,block,record
    private String mClass;

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

    public long getParentModelId() {
        return parentModelId;
    }

    public void setParentModelId(long parentModelId) {
        this.parentModelId = parentModelId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }
}
