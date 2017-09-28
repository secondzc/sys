package com.tongyuan.model.service;

import com.tongyuan.model.domain.PermissionGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/7.
 */
public interface PermissionService {

    public boolean addGroup(PermissionGroup permissionGroup);
    public boolean updateGroup(PermissionGroup permissionGroup);
    public boolean deleteGroup(long id);
    public List<Map<String,Object>>queryGroup();

    public boolean addItem(Map<String,Object> map);
    public boolean editItem(Map<String,Object>map);
    public boolean deleteItem(long id);
    public List<Map<String,Object>>queryItem(Map<String,Object> map);


    public boolean addPermission(Map<String,Object> map);
    public boolean updatePermission(Map<String,Object> map);
    public boolean deletePermission(long userId);
    public List<Map<String,Object>> queryPermission(Map<String,Object> map);


}
