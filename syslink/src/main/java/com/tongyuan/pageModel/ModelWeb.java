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
    //上传者id
    private Long userId;
    //directoryparentId
    private  Long directoryParentId;
    private  Long directoryId;
    private Long createTime;
    private Long updateTime;
    private Long uploadTime;
    private String repositoryName;
    //模型库数量
    private Integer total;
    //模型库关注数
    private Integer numberWatch;
    //模型库收藏数
    private Integer numberStar;
    //本用户是否已经观察
    private Boolean alreadyWatch;
    //本用户是否已经收藏
    private Boolean alreadyStar;
    //text信息（.mo文件）
    private String textInfo;


    public Boolean getAlreadyWatch() {
        return alreadyWatch;
    }

    public void setAlreadyWatch(Boolean alreadyWatch) {
        this.alreadyWatch = alreadyWatch;
    }

    public Boolean getAlreadyStar() {
        return alreadyStar;
    }

    public void setAlreadyStar(Boolean alreadyStar) {
        this.alreadyStar = alreadyStar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getNumberWatch() {
        return numberWatch;
    }

    public void setNumberWatch(Integer numberWatch) {
        this.numberWatch = numberWatch;
    }

    public Integer getNumberStar() {
        return numberStar;
    }

    public void setNumberStar(Integer numberStar) {
        this.numberStar = numberStar;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
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

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public Long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
    }
}
