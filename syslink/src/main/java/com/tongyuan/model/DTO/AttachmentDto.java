package com.tongyuan.model.DTO;

/**
 * Created by xyx on 2017-12-25.
 */
public class AttachmentDto {
    private long id;
    //文件名称
    private String name;
    //文件后缀
    private String ext;
    //静态图标路径
    private String iconUrl;
    //文件大小
    private long size;
    //本地相对路径
    private String filePath ;
    //Url相对路径
    private String httpPath ;
    //创建时间
    private String createTime;
    //是否为文件夹 1为是文件夹 0为文件file
    private Boolean isFloder;
    //父类目录ID
    private long parentId;
    //模型id
    private long modelId;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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
}
