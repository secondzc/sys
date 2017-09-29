package com.tongyuan.model.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.*;
import com.tongyuan.gogs.dao.GUserMapper1;

import com.tongyuan.model.domain.*;
import com.tongyuan.model.domainmodel.*;
import com.tongyuan.gogs.domain.GUser1;
import com.tongyuan.model.service.UserService;
import com.tongyuan.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017-7-11.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginstateMapper loginstateMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private GUserMapper1 gUserMapper;




    @Override
    public boolean add(User user) {

        boolean add = userMapper.add(user);
        Map<String,Object> userRole = new HashMap<>();
        userRole.put("userId",user.getId());
        userRole.put("createDate",DateUtil.getTimestamp());
        userRoleMapper.add(userRole);

        Map<String,Object> userPermission = new HashMap<>();
        userPermission.put("userId",user.getId());
        userPermission.put("createDate",DateUtil.getTimestamp());
        userPermissionMapper.add(userPermission);

        return add;
    }

    @Override
    public boolean update(User user) {

        return this.userMapper.update(user);
    }

    @Override
    public boolean delete(long id)
    {
        userRoleMapper.delete(id);
        userPermissionMapper.delete(id);
        return this.userMapper.delete(id);
    }
/**
    @Override
    public void setDept(UserModel model)
    {
        Department department = departmentMapper.queryDepartmentById(model.getDepartmentId());
        DepartmentModel departmentModel = new DepartmentModel(department);
        model.setDepartmentModel(departmentModel);
    }
**/


    @Override
    public User queryUserById(Long id) {
        return this.userMapper.queryUserById(id);
    }

    @Override
    public User querUserByName(Map<String,Object> params) {
        return this.userMapper.querUserByName(params);
    }

//    @Override
//    public List<Map<String,Object>> findAllUsers()
//    {
//        return this.userMapper.findAllUsers();
//    }

    @Override
    public Page<Map<String,Object>> findAllUsers(Map<String,Object> map)
    {
        Page<Map<String,Object>>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        userMapper.findAllUsers(map);
        return page;
    }
    @Override
    public  List<Map<String,Object>> findAllName()
    {
        return this.userMapper.findAllName();
    }
/**
    @Override
    public List<UserModel> getAllUsers()
    {
        List<User> users = userMapper.findAllUsers();
        List<UserModel>models = new ArrayList<>();
        for(User user : users)
        {
            Department department = departmentMapper.queryDepartmentById(user.getDepartmentId());
            if(department!=null)
            {
                DepartmentModel model = new DepartmentModel(department);
                UserModel userModel = new UserModel(model,user);
                models.add(userModel);
            }
        }
       return  models;
    }
**/

    @Override
    public LoginedUserModel CreateLoginedUser(User user) {
        // 个人角色
     //   List<RoleModel> roles = roleService.getAllBySystemIdAndUserId(systemId,
      //          user.getId());
        // 个人权限
     //   Map<String, PermissionValueModel> permissions = getUserPermissions(
      //          systemId, user.getId(), roles);
        // 登录情况
  //      LoginStateModel loginState = new LoginStateModel(loginStateDao.get(
  //              Loginstate.class, new LoginstateId(user.getId(), systemId)));
        Loginstate loginstate = loginstateMapper.queryLoginstateById(user.getId());

        LoginedUserModel loginedUserModel = new LoginedUserModel();
  //      loginedUserModel.setProfile(user);
  //      loginedUserModel.setRoles(roles);
  //      loginedUserModel.setPermissions(permissions);
        loginedUserModel.setLoginState(loginstate);
        return loginedUserModel;
    }

    @Override
    public LoginedUserModel CreateLoginedUser(GUser1 guser)
    {
        // 个人角色
        //   List<RoleModel> roles = roleService.getAllBySystemIdAndUserId(systemId,
        //          user.getId());
        // 个人权限
        //   Map<String, PermissionValueModel> permissions = getUserPermissions(
        //          systemId, user.getId(), roles);
        // 登录情况
        //      LoginStateModel loginState = new LoginStateModel(loginStateDao.get(
        //              Loginstate.class, new LoginstateId(user.getId(), systemId)));
        Loginstate loginstate = loginstateMapper.queryLoginstateById(guser.getId());

        LoginedUserModel loginedUserModel = new LoginedUserModel();
        loginedUserModel.setProfile(guser);
        //      loginedUserModel.setRoles(roles);
        //      loginedUserModel.setPermissions(permissions);
        loginedUserModel.setLoginState(loginstate);
        return loginedUserModel;
    }



    @Override
    public void updateLoginstate(long userId,
                                 String loginIp, Date loginDate) {
        Loginstate loginstate = loginstateMapper.queryLoginstateById(userId);
        if (loginstate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String a = sdf.format(loginstate.getNowLoginDate());
            Date b = DateUtil.format(a);
            loginstate.setLoginCount(loginstate.getLoginCount() + 1);
            loginstate.setLastLoginIp(loginstate.getNowLoginIp());
         //   loginstate.setLastLoginDate(loginstate.getNowLoginDate());
            loginstate.setLastLoginDate(b);
            loginstate.setNowLoginIp(loginIp);
            loginstate.setNowLoginDate(DateUtil.getTimestamp());
            loginstateMapper.update(loginstate);
        } else {
            loginstate = new Loginstate();
            Timestamp currentTimeStamp = DateUtil.getTimestamp();
            loginstate.setId(userId);
            loginstate.setLastLoginIp(loginIp);
            loginstate.setLastLoginDate(currentTimeStamp);
            loginstate.setNowLoginDate(currentTimeStamp);
            loginstate.setNowLoginIp(loginIp);
            loginstate.setCreateDate(currentTimeStamp);
            loginstate.setLoginCount(1);
            loginstateMapper.add(loginstate);
        }
    }


    @Override
    public boolean addGUser(Map<String,Object>map)
    {

        map.put("lowerName",map.get("name").toString().toLowerCase());
        map.put("fullName","");

      //  map.put("email","");
      //  map.put("passwd","");
        map.put("loginType",0);
        map.put("loginSource",0);
        map.put("loginName","");
        map.put("type",0);
        map.put("location","");
        map.put("website","");
        map.put("lastRepoVisibility",0);
        map.put("createdUnix", System.currentTimeMillis()/1000L);
        map.put("updatedUnix",System.currentTimeMillis()/1000L);
        map.put("maxRepoCreation",-1);
        map.put("isActive",1);
        map.put("isAdmin",0);
        map.put("allowGitHook",0);
        map.put("allowImportLocal",0);
        map.put("prohibitLogin",0);
//
        map.put("useCustomAvatar",1);
        map.put("numFollowers",0);
        map.put("numFollowing",0);
        map.put("numStars",0);
        map.put("numRepos",0);
        map.put("description","");
//
        map.put("numTeams",1);
//
        map.put("numMembers",1);
        map.put("avatar","");
        map.put("avatarEmail","");
       return this.gUserMapper.add(map);
    }





    @Override
    public boolean updateUser(Map<String,Object>map)
    {
        if(map.get("name")!=null)
        {
            map.put("lowerName",map.get("name").toString().toLowerCase());
        }
        return this.gUserMapper.updateUser(map);
    }

    @Override
    public boolean deleteUser(long id)
    {
        return this.gUserMapper.delete(id);
    }

    @Override
    public List<Map<String,Object>> queryUser(Map<String,Object>map)
    {
        return this.gUserMapper.queryUser(map);
    }

    @Override
    public boolean nameExist(Map<String,Object>map)
    {
        Map<String,Object> mapName = new HashMap<>();

        mapName.put("name",map.get("name").toString());

        List<Map<String,Object>> name  =  gUserMapper.queryUser(mapName);

        return name.size()>0;

    }

    @Override
    public boolean emailExist(Map<String,Object>map)
    {
        Map<String,Object> mapEmail = new HashMap<>();
        mapEmail.put("email",map.get("email"));
        List<Map<String,Object>> email =  gUserMapper.queryUser(mapEmail);
        return email.size()>0;
    }


     @Override
     public User getUserByName(String userName)
     {
          return  this.userMapper.getUserByName(userName);
     }
}
