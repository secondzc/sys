package com.tongyuan.model.DTO;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2018-1-5.
 */
public class ModelDto {
    private long id;

    //父类目录ID
    private long parentId;

    //模型名称
    private String name;

    //模型受限类型
    private String classes;

    //模型类型
    private String type;

    //模型目录ID
    private long directoryId;

    //模型描述
    private String discription;

    //导入包名
    private String Import;

    //基类名
    private String Extends;

    //删除标记为
    private Boolean isDeleted;

    //作者Id
    private long userId;

    //创建时间
    private Timestamp createTime;

    //最后修改时间
    private Timestamp lastUpdateTime;

    //是否为公共的模型 0；非公共的
    private Boolean scope;

    //icon文件id
    private long iconFileId;
    //diagram文件id
    private long diagramFileId;
    //info文件id
    private long infoFileId;

    //modeltext信息（.mo文件）
    private String modelText;

    //iconUrl
    private String iconUrl;
    //iconRealUrl
    private String iconRealUrl;

    public String getIconRealUrl() {
        return iconRealUrl;
    }

    public void setIconRealUrl(String iconRealUrl) {
        this.iconRealUrl = iconRealUrl;
    }

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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
