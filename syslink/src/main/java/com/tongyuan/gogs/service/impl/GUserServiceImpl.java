package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.dao.LoginstateMapper;
import com.tongyuan.model.dao.UserPermissionMapper;
import com.tongyuan.model.dao.UserRoleMapper;
import com.tongyuan.model.domain.Loginstate;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class GUserServiceImpl implements GUserService {
    @Autowired
    GUserMapper gUserMapper;
    @Autowired
    LoginstateMapper loginstateMapper;

    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    UserPermissionMapper userPermissionMapper;



    @Override
    public boolean add(GUser user) {
        return this.gUserMapper.add(user);
    }

    @Override
    public GUser querListByName(String name) {
        return this.gUserMapper.querListByName(name);
    }

    @Override
    public boolean update(GUser user) {
        return this.gUserMapper.update(user);
    }

    @Override
    public GUser queryById(long id)
    {
        return this.gUserMapper.queryById(id);
    }


    @Override
    public LoginedUserModel CreateLoginedUser(GUser user)
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
        Loginstate loginstate = loginstateMapper.queryLoginstateById(user.getID());

        LoginedUserModel loginedUserModel = new LoginedUserModel();
        loginedUserModel.setProfile(user);
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
    public boolean addGUser(Map<String,Object> map)
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

}
