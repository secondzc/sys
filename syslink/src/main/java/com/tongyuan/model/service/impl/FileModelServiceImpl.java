package com.tongyuan.model.service.impl;

import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.FileModelMapper;
import com.tongyuan.model.domain.FileModel;
import com.tongyuan.model.service.FileModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 目录数据操作
 * Created by xieyx on 2017-6-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class FileModelServiceImpl implements FileModelService{
    @Autowired
    private FileModelMapper fileMapper;

    @Override
    public List<FileModel> queryListByPath(String absoluteAddress) {
        return this.fileMapper.queryListByPath(absoluteAddress);
    }

    @Override
    public boolean add(FileModel file) {
        return this.fileMapper.add(file);
    }

    @Override
    public boolean update(FileModel file) {
        return this.fileMapper.update(file);
    }

    @Override
    public boolean deleteByIds(String[] ids) {
        return this.fileMapper.deleteByIds(ids);
    }

    @Override
    public List<FileModel> queryListByName(Map<String, Object> params) {
        return this.fileMapper.queryListByName(params);
    }

    @Override
    public List<FileModel> findAllDirectory() {
        return this.fileMapper.findAllDirectory();
    }

    @Override
    public List<FileModel> findRootDirectory() {
        return this.fileMapper.findRootDirectory();
    }

    @Override
    public List<FileModel> findRootDirectoryList(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        return this.fileMapper.findRootDirectoryList(params);
    }

    @Override
    public List<FileModel> queryListByParentId(Long id) {
        return this.fileMapper.queryListByParentId(id);
    }

    @Override
    public List<FileModel> queryListById(Long id) {
        return this.fileMapper.queryListById(id);
    }

    @Override
    public List<FileModel> queryByParentName(Map<String, Object> params) {
        return this.fileMapper.queryByParentName(params);
    }


}
