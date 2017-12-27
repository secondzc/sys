package com.tongyuan.model.service;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.Directory;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-6-21.
 */
public interface DirectoryService {
    public List<Directory> queryListByPath(String absoluteAddress);
    public Long add(Directory directory);
    public boolean update(Directory directory);
    public boolean deleteByIds(String[] ids);
    public List<Directory> queryListByName(Map<String, Object> params);
    public List<Directory> findAllDirectory();
    public List<Directory> findRootDirectory();
    public List<Directory> findRootDirectoryList(Map<String, Object> params);
    public List<Directory> queryListByParentId(Long id);
    public List<Directory> queryListById(Long id);
    public List<Directory> queryByParentName(Map<String, Object> params);
    public List<Map<String,Object>> queryMapListByParentId(Long id);
    public Map<String,Object>queryPublicRoot();
    public Map<String,Object> queryMapById(Long id);
    public List<Directory> selectDefaultDirectory ();
    //获取公有目录（唯一）
    public Directory getPublicDir();
    //获取我的模型目录（唯一）
    public Directory getPrivateDir(String userName);
    //创建个人的私有模型目录根节点
    public void createPersonalModelRoot(Map<String,Object> map);
    //获取directory目录(不包含子目录)
    public void getDirExpChild(List<JSONObject> directoryModelList,Long parent_id
    ,Boolean scope,String userName);
    //web端增加模型目录
    public Directory addOneDir(String name,Long parent_id,String userName);


}
