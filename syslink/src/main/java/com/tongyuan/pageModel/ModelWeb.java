package com.tongyuan.pageModel;

/**
 * Created by xyx on 2017-8-22.
 */
public class ModelWeb {
    private Long index;
    private Long parentId;
    private String  imageUrl;
    private String name;
    private String discription;
    private String type;
    //模型受限类型
    private String classes;
    //导入包名
    private String Import;
    //基类名
    private String Extends;
    //模型文件Url
    private String modelFilePath;
    //Icon视图svg路径
    private String iconSvgPath;
    //Diagram视图svg路径
    private String diagramSvgPath;
    //Info视图文本路径
    private String infoTextPath;
    //上传者
    private String userName;
    //directoryparentId
    private  Long directoryParentId;
    private String createTime;
    private Long uploadTime;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getDirectoryParentId() {
        return directoryParentId;
    }

    public void setDirectoryParentId(Long directoryParentId) {
        this.directoryParentId = directoryParentId;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
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

    public String getModelFilePath() {
        return modelFilePath;
    }

    public void setModelFilePath(String modelFilePath) {
        this.modelFilePath = modelFilePath;
    }

    public String getIconSvgPath() {
        return iconSvgPath;
    }

    public void setIconSvgPath(String iconSvgPath) {
        this.iconSvgPath = iconSvgPath;
    }

    public String getDiagramSvgPath() {
        return diagramSvgPath;
    }

    public void setDiagramSvgPath(String diagramSvgPath) {
        this.diagramSvgPath = diagramSvgPath;
    }

    public String getInfoTextPath() {
        return infoTextPath;
    }

    public void setInfoTextPath(String infoTextPath) {
        this.infoTextPath = infoTextPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
