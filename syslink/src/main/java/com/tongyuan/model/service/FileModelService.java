package com.tongyuan.model.service;

import com.tongyuan.model.domain.FileModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-6-21.
 */
public interface FileModelService {
    public List<FileModel> queryListByPath(String absoluteAddress);
    public boolean add(FileModel fileModel);
    public boolean update(FileModel fileModel);
    public boolean deleteByIds(String[] ids);
    public List<FileModel> queryListByName(Map<String, Object> params);
    public List<FileModel> findAllDirectory();
    public List<FileModel> findRootDirectory();
    public List<FileModel> findRootDirectoryList(Map<String, Object> params);
    public List<FileModel> queryListByParentId(Long id);
    public List<FileModel> queryListById(Long id);
    public List<FileModel> queryByParentName(Map<String, Object> params);
}
