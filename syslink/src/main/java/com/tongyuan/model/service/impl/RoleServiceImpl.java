package com.tongyuan.model.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.dao.*;
import com.tongyuan.model.domain.Auth;
import com.tongyuan.model.domain.RoleAuth;
import com.tongyuan.model.domain.UserRole;
import com.tongyuan.model.service.AuthService;
import com.tongyuan.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    GUserMapper gUserMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    UserAuthMapper userAuthMapper;
    @Autowired
    AuthService authService;



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
    public boolean updateUserRoles(long uid , JSONArray roles )
    {
        boolean a = userRoleMapper.delete(uid);
        boolean b = true;
        //用户权限中是否含有创建仓库的权限
        boolean c= false;
        //本次所分配的角色中是否包含新建仓库的权限
        boolean d= false;
        GUser user = gUserMapper.queryById(uid);
        for(int i =0;i<roles.size();i++)
        {
            UserRole userRole = new UserRole();
            userRole.setUid(uid);
            userRole.setRoleId(roles.getIntValue(i));
            List<Auth> roleAuths = authMapper.queryAuthByRoleId(roles.getIntValue(i));
            if(roleAuths.size()>0)
            {
                for(Auth auth :roleAuths)
                {

                    if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
                    {
                      d = true;
                    }
                }
            }
            b = b&userRoleMapper.add(userRole);
        }

//        Set<Auth>userAuths = authService.getAuthByUserAuth(uid);
        List<Auth>userAuths = authService.queryAuthByUid(uid);
        for(Auth auth : userAuths)
        {
            if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
            {
                c=true;
            }
        }
        if(c||d)
        {
            user.setAdmin(true);
            gUserMapper.update(user);
        }
        else

        {
            user.setAdmin(false);
            gUserMapper.update(user);
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
    @Override
    public Set<Auth> getAuthByUserRole(Long uid)
    {
        Set<Auth> allauths = new HashSet<>();
        List<UserRole> userRoles = userRoleMapper.query(uid);
        if(userRoles.size()>0)
        {
            for(UserRole userRole:userRoles)
            {
                List<RoleAuth> roleAuths = roleAuthMapper.queryByRoleId(userRole.getRoleId());
                if(roleAuths.size()>0)
                {
                    for(RoleAuth roleAuth :roleAuths)
                    {
                        Auth auth = authMapper.queryById(roleAuth.getAuthId());
                        allauths.add(auth);
                    }
                }
            }
        }
           return allauths;
    }
    @Override
    public Set<Auth> queryByUid(long uid)
    {
        List<UserRole>userRoles = userRoleMapper.query(uid);
        Set<Auth> auths = new HashSet<>();
        for(UserRole userRole : userRoles)
        {
            List<Auth> roleAuths = authMapper.queryAuthByRoleId(userRole.getRoleId());
            if(roleAuths.size()>0)
            {
                auths.addAll(roleAuths);
            }
        }
        return auths;
    }


}
