package com.tongyuan.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;


/**
 * 模型基本表
 * Created by Xieyx on 2017-6-17.
 */
@Entity
public class Model {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    //目录ID
    private long id;

    @Column(name = "parentId")
    //父类目录ID
    private long parentId;

    @Column(name = "name", length = 128)
    //模型名称
    private String name;

    @Column(name = "classes", nullable = false, length = 32)
    //模型受限类型
    private String classes;

    @Column(name = "type", length = 32)
    //模型类型
    private String type;

    @Column(name = "directoryId")
    //模型目录ID
    private long directoryId;

    @Column(name = "discription", length = 256)
    //模型描述
    private String discription;

    @Column(name = "Import", length = 128)
    //导入包名
    private String Import;

    @Column(name = "Extends", length = 128)
    //基类名
    private String Extends;

    @Column
    //删除标记为
    private Boolean isDeleted;

    @Column(name = "userId" ,nullable = false)
    //作者Id
    private long userId;

    @Column
    //创建时间
    private Timestamp createTime;

    @Column
    //最后修改时间
    private Timestamp lastUpdateTime;

    @Column( nullable = false)
    //是否为公共的模型 0；非公共的
    private Boolean scope;

    @Column
    //icon文件id
    private long iconFileId;
    @Column
    //diagram文件id
    private long diagramFileId;
    @Column
    //info文件id
    private long infoFileId;

    //modeltext信息（.mo文件）
    @Column
    private String modelText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(long directoryId) {
        this.directoryId = directoryId;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImport() {
        return Import;
    }

    public void setImport(String anImport) {
        Import = anImport;
    }

    public String getExtends() {
        return Extends;
    }

    public void setExtends(String anExtends) {
        Extends = anExtends;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getScope() {
        return scope;
    }

    public void setScope(Boolean scope) {
        this.scope = scope;
    }

    public long getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(long iconFileId) {
        this.iconFileId = iconFileId;
    }

    public long getDiagramFileId() {
        return diagramFileId;
    }

    public void setDiagramFileId(long diagramFileId) {
        this.diagramFileId = diagramFileId;
    }

    public long getInfoFileId() {
        return infoFileId;
    }

    public void setInfoFileId(long infoFileId) {
        this.infoFileId = infoFileId;
    }

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }
}
