package com.tongyuan.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * 附件表
 * Created by xyx on 2017-12-20.
 */
@Entity
public class Attachment {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    //文件ID
    private long id;
    //文件名称
    @Column( length = 128)
    private String name;
    //文件后缀
    @Column(length = 128)
    private String ext;
    //静态图标路径
    @Column( length = 128)
    private String iconUrl;
    //文件大小
    @Column
    private long size;
    //本地相对路径
    @Column( length = 128)
    private String filePath ;
    //Url相对路径
    @Column( length = 128)
    private String httpPath ;
    //创建时间
    @Column
    private Timestamp createTime;
    @Column
    //是否为文件夹 1为是文件夹 0为文件file
    private Boolean isFloder;
    @Column
    //父类目录ID
    private long parentId;
    @Column
    //模型id
    private long modelId;
    //web端相对路径
    @Column
    private String tempRelativePath;
    @Column
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Boolean getFloder() {
        return isFloder;
    }

    public void setFloder(Boolean floder) {
        isFloder = floder;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getTempRelativePath() {
        return tempRelativePath;
    }

    public void setTempRelativePath(String tempRelativePath) {
        this.tempRelativePath = tempRelativePath;
    }
}
