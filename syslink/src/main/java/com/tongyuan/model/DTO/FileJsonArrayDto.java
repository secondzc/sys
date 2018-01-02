package com.tongyuan.model.DTO;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-28.
 */
public class FileJsonArrayDto implements Serializable {

    /**
     * 版本信息
     */
    private static final long serialVersionUID = -2349692708851483212L;

    private Boolean paused;
    private Boolean isRoot;
    private Boolean aborted;
    private Boolean allError;
    private Long _lastProgressCallback;
    private Integer averageSpeed;
    private Boolean error;
    private Integer currentSpeed;
    private Integer _progeressId;
    private Boolean isFolder;
    private Integer size;
    private String relativePath;
    private String name;
    private Integer _prevUploadedSize;
    private Integer id;
    private Integer _prevProgress;
    private String fileType;
    private String uniqueIdentifier;
    private String path;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public Boolean getAborted() {
        return aborted;
    }

    public void setAborted(Boolean aborted) {
        this.aborted = aborted;
    }

    public Boolean getAllError() {
        return allError;
    }

    public void setAllError(Boolean allError) {
        this.allError = allError;
    }

    public Long get_lastProgressCallback() {
        return _lastProgressCallback;
    }

    public void set_lastProgressCallback(Long _lastProgressCallback) {
        this._lastProgressCallback = _lastProgressCallback;
    }

    public Integer getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Integer averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Integer currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Integer get_progeressId() {
        return _progeressId;
    }

    public void set_progeressId(Integer _progeressId) {
        this._progeressId = _progeressId;
    }

    public Boolean getFolder() {
        return isFolder;
    }

    public void setFolder(Boolean folder) {
        isFolder = folder;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer get_prevUploadedSize() {
        return _prevUploadedSize;
    }

    public void set_prevUploadedSize(Integer _prevUploadedSize) {
        this._prevUploadedSize = _prevUploadedSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer get_prevProgress() {
        return _prevProgress;
    }

    public void set_prevProgress(Integer _prevProgress) {
        this._prevProgress = _prevProgress;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
