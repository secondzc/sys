package com.tongyuan.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.DirectoryMapper;
import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.service.DirectoryService;
import com.tongyuan.pageModel.DirectoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 目录数据操作
 * Created by xieyx on 2017-6-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class DirectoryServiceImpl implements DirectoryService{
    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    public List<Directory> queryListByPath(String absoluteAddress) {
        return this.directoryMapper.queryListByPath(absoluteAddress);
    }

    @Override
    public Long add(Directory directory) {
        return this.directoryMapper.add(directory);
    }


    @Override
    public boolean update(Directory directory) {
        return this.directoryMapper.update(directory);
    }

    @Override
    public boolean deleteByIds(String[] ids) {
        return this.directoryMapper.deleteByIds(ids);
    }

    @Override
    public List<Directory> queryListByName(Map<String, Object> params) {
        return this.directoryMapper.queryListByName(params);
    }

    @Override
    public List<Directory> findAllDirectory() {
        return this.directoryMapper.findAllDirectory();
    }

    @Override
    public List<Directory> findRootDirectory() {
        return this.directoryMapper.findRootDirectory();
    }

    @Override
    public List<Directory> findRootDirectoryList(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        return this.directoryMapper.findRootDirectoryList(params);
    }

    @Override
    public List<Directory> queryListByParentId(Long id) {
        return this.directoryMapper.queryListByParentId(id);
    }

    @Override
    public List<Directory> queryListById(Long id) {
        return this.directoryMapper.queryListById(id);
    }

    @Override
    public List<Directory> queryByParentName(Map<String, Object> params) {
        return this.directoryMapper.queryByParentName(params);
    }

    @Override
    public List<Map<String,Object>> queryMapListByParentId(Long id)
    {
        return this.directoryMapper.queryMapListByParentId(id);
    }

    @Override
    public Map<String,Object> queryMapById(Long id)
    {
        return this.directoryMapper.queryMapById(id);
    }

    @Override
    public List<Directory> selectDefaultDirectory() {
        return this.directoryMapper.selectDefaultDirectory();
    }

    @Override
    public Directory getPublicDir() {
        return this.directoryMapper.getPublicDir();
    }

    @Override
    public Directory getPrivateDir(String userName) {
        return this.directoryMapper.getPrivateDir(userName);
    }

    @Override
    public void createPersonalModelRoot(Map<String, Object> map) {
        Date date = new Date();
        Directory directory = new Directory();
        directory.setName((String)map.get("name")+"的模型");
        directory.setUserName((String) map.get("name"));
        directory.setDeleted(false);
        directory.setCreateTime(date);
        directory.setLastUpdateTime(date);
        directory.setParentId(0);
        this.directoryMapper.add(directory);
    }

    @Override
    public void getDirExpChild(List<JSONObject> directoryModelList, Long parent_id, Boolean scope, String userName) {
        //顶层目录的时候
        Directory rooDir = new Directory();
        List<Directory> rootDirectoryList = new ArrayList<>();
        if (scope != null && parent_id == 0) {
            rooDir = this.directoryMapper.getPrivateDir(userName);
            rootDirectoryList.add(rooDir);
            } else if (parent_id == 0) {
            rooDir = this.directoryMapper.getPublicDir();
            rootDirectoryList.add(rooDir);
            } else {
                rootDirectoryList = this.directoryMapper.queryListByParentId(parent_id);
            }

            for (int i = 0; i <= rootDirectoryList.size() - 1; i++) {
                if (scope != null && userName.equals(rootDirectoryList.get(i).getUserName())) {
                    DirectoryModel directoryModel = new DirectoryModel();
                    directoryModel.setId(rootDirectoryList.get(i).getId());
                    directoryModel.setName(rootDirectoryList.get(i).getName());
                    directoryModel.setParentId(rootDirectoryList.get(i).getParentId() + "");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                    directoryModelList.add(jsonObject);
                } else {
                    DirectoryModel directoryModel = new DirectoryModel();
                    directoryModel.setId(rootDirectoryList.get(i).getId());
                    directoryModel.setName(rootDirectoryList.get(i).getName());
                    directoryModel.setParentId(rootDirectoryList.get(i).getParentId() + "");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                    directoryModelList.add(jsonObject);
                }
            }
    }

    @Override
    public Directory addOneDir(String name, Long parent_id, String userName) {
        Date date = new Date();
        Directory directory = new Directory();
        directory.setName(name);
        directory.setParentId(parent_id);
        directory.setUserName(userName);
        directory.setDeleted(false);
        directory.setCreateTime(date);
        directory.setLastUpdateTime(date);
        this.directoryMapper.add(directory);
        return directory;
    }


}
