package com.tongyuan.model.dao;

import com.tongyuan.model.domain.FileModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 目录dao方法
 * Created by xieyx on 2017-6-21.
 */
@Mapper
public interface FileModelMapper {
    public List<FileModel> queryListByPath(String absoluteAddress);
    boolean add(FileModel fileModel);
    boolean update(FileModel fileModel);
    boolean deleteByIds(String[] ids);
    public List<FileModel> queryListByName(Map<String, Object> params);
    public List<FileModel> findAllDirectory();
    public List<FileModel> findRootDirectory();
    List<FileModel> findRootDirectoryList(Map<String, Object> params);
    public List<FileModel> queryListByParentId(Long id);
    public List<FileModel> queryListById(Long id);
    public List<FileModel> queryByParentName(Map<String, Object> params);
}
