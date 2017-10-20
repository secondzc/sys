package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.RoleAuthMapper;
import com.tongyuan.model.dao.RoleMapper;
import com.tongyuan.model.dao.UserRoleMapper;
import com.tongyuan.model.domain.RoleAuth;
import com.tongyuan.model.domain.UserRole;
import com.tongyuan.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleAuthMapper roleAuthMapper;


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
    public boolean updatePermission(Integer roleId,Integer []authIds)
    {
        boolean a = roleAuthMapper.deleteByRoleId(roleId);
        boolean b = true;
        for(int i=0;i<authIds.length;i++)
        {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setRoleId(roleId);
            roleAuth.setAuthId(authIds[i]);
            b = b&roleAuthMapper.add(roleAuth);
        }
        return a&b;
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
    public boolean addUserRole(UserRole userRole)
    {

        return this.userRoleMapper.add(userRole);
    }

    @Override
    public boolean deleteUserRole(long uid)
    {
        return this.userRoleMapper.delete(uid);
    }

    @Override
    public List<UserRole> queryUserRole(long uid)
    {
        return  this.userRoleMapper.query(uid);
    }

    @Override
    public boolean updateUserRoles(long uid , Integer []roles )
    {
        boolean a = userRoleMapper.delete(uid);
        boolean b = true;
        for(int i =0;i<roles.length;i++)
        {
            UserRole userRole = new UserRole();
            userRole.setUid(uid);
            userRole.setRoleId(roles[i]);
            b = b&userRoleMapper.add(userRole);
        }
        return  a&b;
    }

    @Override
    public List<Integer> queryUserRoleByUid(long uid)
    {
        List<Integer> roles = new ArrayList<>();
        List<UserRole> userRoles = userRoleMapper.query(uid);
        if(userRoles!=null)
        {
           for(UserRole userRole:userRoles)
           {
               roles.add(userRole.getRoleId());
           }
        }

        return roles;
    }

}
