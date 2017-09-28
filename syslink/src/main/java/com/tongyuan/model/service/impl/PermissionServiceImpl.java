package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.PermissionGroupMapper;
import com.tongyuan.model.dao.PermissionItemMapper;
import com.tongyuan.model.dao.UserPermissionMapper;
import com.tongyuan.model.domain.PermissionGroup;
import com.tongyuan.model.domain.PermissionItem;
import com.tongyuan.model.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/7.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionGroupMapper permissionGroupMapper;
    @Autowired
    PermissionItemMapper permissionItemMapper;
    @Autowired
    UserPermissionMapper userPermissionMapper;
    @Override
    public boolean addGroup(PermissionGroup permissionGroup)
    {
        return this.permissionGroupMapper.add(permissionGroup);
    }
    @Override
    public  boolean updateGroup(PermissionGroup permissionGroup)
    {
        return  this.permissionGroupMapper.update(permissionGroup);
    }
    @Override
    public boolean deleteGroup(long id)
    {
        Map<String,Object> item = new HashMap<>();
        item.put("id",id);
        List<Map<String,Object>> items = permissionItemMapper.query(item);
        boolean delete = true;
        for(int i= 0;i<items.size();i++)
        {
            long itemId = Integer.parseInt(items.get(i).get("id").toString());
            boolean a = permissionItemMapper.delete(itemId);
            boolean b =true;
            delete = a&b;
        }
        return this.permissionGroupMapper.delete(id)&delete;
    }
    @Override
    public List<Map<String,Object>> queryGroup()
    {
        return this.permissionGroupMapper.query();
    }


    @Override
    public boolean addItem(Map<String,Object> map)
    {
        return  this.permissionItemMapper.add(map);
    }

    @Override
    public boolean editItem(Map<String,Object>map)
    {
        return  this.permissionItemMapper.update(map);
    }
    @Override
    public boolean deleteItem(long id)
    {
        return  this.permissionItemMapper.delete(id);
    }

    @Override
    public List<Map<String,Object>>queryItem(Map<String,Object>map)
    {
        return this.permissionItemMapper.query(map);
    }






    @Override
    public boolean addPermission(Map<String,Object> map)
    {
        return this.userPermissionMapper.add(map);
    }
    @Override
    public boolean updatePermission(Map<String,Object> map)
    {
        return  this.userPermissionMapper.update(map);
    }
    @Override
    public boolean deletePermission(long userId)
    {
        return this.userPermissionMapper.delete(userId);
    }
    @Override
    public List<Map<String,Object>> queryPermission(Map<String,Object> map)
    {
        return this.userPermissionMapper.query(map);
    }

}
