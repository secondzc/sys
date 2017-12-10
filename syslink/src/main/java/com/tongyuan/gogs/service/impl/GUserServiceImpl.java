
package com.tongyuan.gogs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.dao.*;
import com.tongyuan.model.domain.*;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.model.service.AuthService;
import com.tongyuan.model.service.RoleService;
import com.tongyuan.model.wrapper.DepartWarpper;
import com.tongyuan.pageModel.ReviewUserPage;
import com.tongyuan.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Guard;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
    UserAuthMapper userAuthMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    RoleAuthMapper roleAuthMapper;
    @Autowired
    UserDepartMapper userDepartMapper;
    @Autowired
    DirectoryAuthMapper directoryAuthMapper;
    @Autowired
    ModelAuthMapper modelAuthMapper;
    @Autowired
    AuthService authService;
    @Autowired
    RoleService roleService;
    @Autowired
    DepartMapper departMapper;



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

        List<UserAuth> userAuths  = userAuthMapper.queryByUid(user.getID());
        Set<String> auths = new HashSet<>();
        for(UserAuth userAuth : userAuths)
        {
            Auth auth = authMapper.queryById(userAuth.getAuthId());
            auths.add(auth.getAuthCode());
        }
        List<UserRole> userRoles = userRoleMapper.query(user.getID());
        List<RoleAuth> roleAuths = new ArrayList<>();
        for(UserRole userRole:userRoles)
        {
            roleAuths.addAll(roleAuthMapper.queryByRoleId(userRole.getRoleId()));
        }
        for(RoleAuth roleAuth :roleAuths)
        {
            Auth auth = authMapper.queryById(roleAuth.getAuthId());
            auths.add(auth.getAuthCode());
        }

        List<String>modeAuths = new ArrayList<>();
//        List<ModelAuth> modelAuthList = modelAuthMapper.queryByUid(user.getID());
//        for(ModelAuth modelAuth :modelAuthList)
//        {
//            modeAuths.add(modelAuth.getNodeId());
//        }
        List<Long> directoryAuths = new ArrayList<>();
        List<DirectoryAuth> directoryAuthList = directoryAuthMapper.queryByUid(user.getID());
        for(DirectoryAuth directoryAuth :directoryAuthList)
        {
            directoryAuths.add(directoryAuth.getDirectoryId());
        }

        List<Integer> roles = new ArrayList<>();
        roles=roleService.queryUserRoleByUid(user.getID());





        Loginstate loginstate = loginstateMapper.queryLoginstateById(user.getID());

        LoginedUserModel loginedUserModel = new LoginedUserModel();
        loginedUserModel.setProfile(user);
        loginedUserModel.setAuths(auths);
        loginedUserModel.setModelAuths(modeAuths);
        loginedUserModel.setDirectoryAuths(directoryAuths);
        loginedUserModel.setRoles(roles);
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
        boolean a = this.gUserMapper.add(map);
        UserDepart userDepart = new UserDepart();
        userDepart.setUid(Long.parseLong(map.get("id").toString()));
        userDepart.setDepartId(Integer.parseInt(map.get("departId").toString()));
        boolean b = this.userDepartMapper.add(userDepart);



//        Integer name = Integer.parseInt(map.get("name").toString());
//
//        for(int i=1;i<=1000;i++)
//        {
//            map.put("id",Long.parseLong(map.get("id").toString())+1);
//            map.put("name",name+i);
//            map.put("lowerName",name+i);
//            String email = name+i+"@gogs.com";
//            map.put("email",email);
//            map.put("fullName","");
//
//            //  map.put("email","");
//            //  map.put("passwd","");
//            map.put("loginType",0);
//            map.put("loginSource",0);
//            map.put("loginName","");
//            map.put("type",0);
//            map.put("location","");
//            map.put("website","");
//            map.put("lastRepoVisibility",0);
//            map.put("createdUnix", System.currentTimeMillis()/1000L);
//            map.put("updatedUnix",System.currentTimeMillis()/1000L);
//            map.put("maxRepoCreation",-1);
//            map.put("isActive",1);
//            map.put("isAdmin",0);
//            map.put("allowGitHook",0);
//            map.put("allowImportLocal",0);
//            map.put("prohibitLogin",0);
//
//            map.put("useCustomAvatar",1);
//            map.put("numFollowers",0);
//            map.put("numFollowing",0);
//            map.put("numStars",0);
//            map.put("numRepos",0);
//            map.put("description","");
//
//            map.put("numTeams",1);
//
//            map.put("numMembers",1);
//            map.put("avatar","");
//            map.put("avatarEmail","");
//            gUserMapper.add(map);
//            UserDepart userDepart1 = new UserDepart();
//            userDepart1.setUid(Long.parseLong(map.get("id").toString()));
//            userDepart1.setDepartId(Integer.parseInt(map.get("departId").toString()));
//            userDepartMapper.add(userDepart1);
//        }
        return  a&b;






    }





    @Override
    public boolean updateUser(Map<String,Object>map)
    {
        if(map.get("name")!=null)
        {
            map.put("lowerName",map.get("name").toString().toLowerCase());
        }
        UserDepart userDepart = userDepartMapper.queryByUid(Long.parseLong(map.get("id").toString()));
        if(userDepart!=null)
        {
            userDepart.setDepartId(Integer.parseInt(map.get("departId").toString()));
            boolean a =userDepartMapper.update(userDepart);
            boolean b =gUserMapper.updateUser(map);
            return a&b;
        }
        else
        {
            UserDepart userDepart1 = new UserDepart();
            userDepart1.setDepartId(Integer.parseInt(map.get("departId").toString()));
            userDepart1.setUid(Long.parseLong(map.get("id").toString()));
            boolean a = userDepartMapper.add(userDepart1);
            boolean b =gUserMapper.updateUser(map);
            return a&b;
        }

    }


    @Override
    public boolean updateUserInfo(Map<String,Object>map)
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


        boolean b = userDepartMapper.deleteByUid(id);
        boolean a = gUserMapper.delete(id);
        return a&b;
    }

    @Override
    public Page<Map<String,Object>> queryUser(Map<String,Object>map)
    {
        Page<Map<String,Object>>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String,Object>> a= gUserMapper.queryUser(map);
        return page;
    }

    @Override
    public Page<UserDepart> queryUserDepart(Map<String,Object>map)
    {
        Integer depatrId = Integer.parseInt(map.get("departId").toString());
        Map<String,Object> depart = departMapper.queryById(depatrId);
        List<Integer> departIds = new ArrayList<>();
        List<Map<String,Object>> departs = new DepartWarpper(depart).getChildren(depart);
        for(Map<String,Object>map1 : departs)
        {
            departIds.add(Integer.parseInt(map1.get("id").toString()));
        }
        departIds.add(Integer.parseInt(map.get("departId").toString()));
        Page<UserDepart>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<UserDepart> userDepartList = userDepartMapper.queryByDepartIds(departIds);




        return  page;
    }

    @Override
    public boolean nameExist(Map<String,Object>map)
    {
        Map<String,Object> mapName = new HashMap<>();

        mapName.put("name",map.get("name").toString());

        List<Map<String,Object>> name  =  gUserMapper.nameJudge(mapName);

        return name.size()>0;

    }

    @Override
    public boolean emailExist(Map<String,Object>map)
    {
        Map<String,Object> mapEmail = new HashMap<>();
        mapEmail.put("email",map.get("email"));
        List<Map<String,Object>> email =  gUserMapper.emailJudge(mapEmail);
        return email.size()>0;
    }


    @Override
    public List<Map<String,Object>> queyrSimpleUser(Map<String,Object>map)
    {
        return this.gUserMapper.querySimpleUser(map);
    }

    @Override
    public Map<String,Object> queryUserByName(String name)
    {
        return this.gUserMapper.queryUserByName(name);
    }

    @Override
    public Map<String,Object> queryUserById (long userId)
    {
        return this.gUserMapper.queryUserById(userId);
    }

    @Override
    public Map<String,Object> queryOrgByName(String name)
    {
        return this.gUserMapper.queryOrgByName(name);
    }
    @Override
    public Map<String,Object> queryOrgById(long orgId)
    {
        return this.gUserMapper.queryOrgById(orgId);
    }


    @Override
    public boolean updateAuth(Long uid, Integer[] authIds)
    {
        GUser user = gUserMapper.queryById(uid);
        if(user.getAdmin())
        {
            //用户权限中是否含有仓库权限
            boolean c = false;
            //角色权限中是否含有仓库权限
            boolean d = false;
//            List<UserAuth> userAuths = userAuthMapper.queryByUid(uid);
//
//            if(userAuths.size()>0)
//            {
//                for(UserAuth userAuth:userAuths)
//                {
//                    Auth auth = authMapper.queryById(userAuth.getAuthId());
//                    if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
//                    {
//                        // user.setAdmin(false);
//                        // gUserMapper.update(user);
//                        c = true;
//                    }
//                }
//            }
//
//            List<UserRole> userRoles = userRoleMapper.query(uid);
//            if(userRoles.size()>0)
//            {
//                for (UserRole userRole : userRoles)
//                {
//                    List<RoleAuth> roleAuths = roleAuthMapper.queryByRoleId(userRole.getRoleId());
//                    for(RoleAuth roleAuth :roleAuths)
//                    {
//                        Auth auth = authMapper.queryById(roleAuth.getAuthId());
//                        if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
//                        {
//                            d = true;
//                        }
//                    }
//                }
//            }

            Set<Auth> userAuths = authService.getAuthByUserAuth(uid);
            if(userAuths.size()>0)
            {
                for(Auth auth :userAuths)
                {
                    if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
                    {
                        c=true;
                    }
                }
            }
            Set<Auth> userRoleAuths = roleService.getAuthByUserRole(uid);
            if(userRoleAuths.size()>0)
            {
                for(Auth auth :userRoleAuths)
                {
                    if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
                    {
                        d=true;
                    }
                }
            }

            if(c&!d)
            {
                user.setAdmin(false);
                gUserMapper.update(user);
            }


        }

        boolean a = userAuthMapper.deleteByUid(uid);
        boolean b = true;
        for(int i=0;i<authIds.length;i++)
        {
            UserAuth userAuth = new UserAuth();
            userAuth.setUid(uid);
            userAuth.setAuthId(authIds[i]);
            Auth auth = authMapper.queryById(authIds[i]);
            if(auth.getAuthCode().equalsIgnoreCase("management_repo_add"))
            {
                user.setAdmin(true);
                gUserMapper.update(user);
            }
            b = b&userAuthMapper.add(userAuth);
        }
        return a&b;
    }


    @Override
    public boolean updateModelAuth(Long uid,List<Map<String,Object>> models)
    {
        boolean a = modelAuthMapper.deleteByUid(uid);
        boolean b =true;
        for(Map<String,Object>map:models)
        {
            ModelAuth modelAuth = new ModelAuth();
            modelAuth.setMode(Short.parseShort(map.get("mode").toString()));
            modelAuth.setModelId(Long.parseLong(map.get("modelId").toString()));
            modelAuth.setUid(uid);
            modelAuth.setNodeId(map.get("modelId").toString()+"+"+map.get("mode").toString());
            b=b&modelAuthMapper.add(modelAuth);
        }
        return a&b;

    }

    @Override
    public boolean updateModelAuth1(Long uid,List<Map<String,Object>> directories)
    {
        boolean a = directoryAuthMapper.deleteByUid(uid);
        boolean b =true;
        for(Map<String,Object>map:directories)
        {
            DirectoryAuth directoryAuth = new DirectoryAuth();
            directoryAuth.setDirectoryId(Long.parseLong(map.get("id").toString()));
            directoryAuth.setUid(uid);
            b=b&directoryAuthMapper.add(directoryAuth);
        }
        return a&b;

    }



    @Override
    public List<ReviewUserPage> queryAll() {
        return gUserMapper.queryAll();
    }




}

