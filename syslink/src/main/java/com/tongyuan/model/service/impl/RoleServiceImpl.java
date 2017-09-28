package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.RoleMapper;
import com.tongyuan.model.dao.UserRoleMapper;

import com.tongyuan.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public boolean add(Map<String,Object>map)
    {
        return this.roleMapper.add(map);
    }
    @Override
    public boolean update(Map<String,Object>map)
    {
        return this.roleMapper.update(map);
    }
    @Override
    public boolean updatePermission(Map<String,Object>map)
    {
        return this.roleMapper.updatePermission(map);
    }

    @Override
    public boolean delete(long id)
    {
        return this.roleMapper.delete(id);
    }

    @Override
    public List<Map<String,Object>>query()
    {
        return  this.roleMapper.query();
    }



    @Override
    public boolean addUserRole(Map<String,Object> map)
    {
        return this.userRoleMapper.add(map);
    }
    @Override
    public boolean updateUserRole(Map<String,Object> map)
    {
        return this.userRoleMapper.update(map);
    }
    @Override
    public boolean deleteUserRole(long userId)
    {
        return this.userRoleMapper.delete(userId);
    }
    @Override
    public List<Map<String,Object>> queryUserRole(Map<String,Object>map)
    {
        return  this.userRoleMapper.query(map);
    }


}
