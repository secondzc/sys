
package com.tongyuan.gogs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public GUser queryById(long id) {
        return this.gUserMapper.queryById(id);
    }


    @Override
    public LoginedUserModel CreateLoginedUser(GUser user) {

        List<Auth> userAuths = authMapper.queryAuthByUid(user.getID());
        Set<String> auths = new HashSet<>();
        for (Auth auth : userAuths) {
            auths.add(auth.getAuthCode());
        }
        List<UserRole> userRoles = userRoleMapper.query(user.getID());
        List<RoleAuth> roleAuths = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleAuths.addAll(roleAuthMapper.queryByRoleId(userRole.getRoleId()));
        }
        for (RoleAuth roleAuth : roleAuths) {
            Auth auth = authMapper.queryById(roleAuth.getAuthId());
            auths.add(auth.getAuthCode());
        }

        List<String> modeAuths = new ArrayList<>();
//        List<Long> directoryAuths = new ArrayList<>();
//        List<DirectoryAuth> directoryAuthList = directoryAuthMapper.queryByUid(user.getID());
//        for (DirectoryAuth directoryAuth : directoryAuthList) {
//            directoryAuths.add(directoryAuth.getDirectoryId());
//        }

        List<Map<String,Object>>directoryAuths = queryUserDirectoryAuths(user.getID());

        List<Integer> roles = new ArrayList<>();
        roles = roleService.queryUserRoleByUid(user.getID());


        Loginstate loginstate = loginstateMapper.queryLoginstateById(user.getID());

        LoginedUserModel loginedUserModel = new LoginedUserModel();
        loginedUserModel.setProfile(user);
        loginedUserModel.setAuths(auths);
        loginedUserModel.setModelAuths(modeAuths);
        loginedUserModel.setDirectoryAuths(directoryAuths);
        loginedUserModel.setRoles(roles);
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
    public boolean addGUser(Map<String, Object> map) {

        map.put("lowerName", map.get("name").toString().toLowerCase());
        map.put("loginType", 0);
        map.put("loginSource", 0);
        map.put("loginName", "");
        map.put("type", 0);
        map.put("location", "");
        map.put("website", "");
        map.put("lastRepoVisibility", 0);
        map.put("createdUnix", System.currentTimeMillis() / 1000L);
        map.put("updatedUnix", System.currentTimeMillis() / 1000L);
        map.put("maxRepoCreation", -1);
        map.put("isActive", 1);
        map.put("isAdmin", 0);
        map.put("allowGitHook", 0);
        map.put("allowImportLocal", 0);
        map.put("prohibitLogin", 0);
        map.put("useCustomAvatar", 1);
        map.put("numFollowers", 0);
        map.put("numFollowing", 0);
        map.put("numStars", 0);
        map.put("numRepos", 0);
        map.put("description", "");
        map.put("numTeams", 1);
        map.put("numMembers", 1);
        map.put("avatar", "");
        map.put("avatarEmail", "");
        boolean a = this.gUserMapper.add(map);

        UserDepart userDepart = new UserDepart();
        userDepart.setUid(Long.parseLong(map.get("id").toString()));
        userDepart.setDepartId(Integer.parseInt(map.get("departId").toString()));
        boolean b = this.userDepartMapper.add(userDepart);

        UserRole userRole = new UserRole();
        boolean c = true;
        if (map.get("roleId") != null) {
            Integer roleId = Integer.parseInt(map.get("roleId").toString());
            //查找角色中是否包含新建仓库的权限，
            List<Auth> roleAuths = authMapper.queryAuthByRoleId(roleId);
            for (Auth auth : roleAuths) {
                if (auth.getAuthCode().equalsIgnoreCase("management_repo_add")) {
                    //增加建库权限
                    map.put("isAdmin", 1);
                    gUserMapper.updateUser(map);
                }

            }
            userRole.setRoleId(roleId);
            userRole.setUid(Long.parseLong(map.get("id").toString()));
            c = roleService.addUserRole(userRole);
        }


        return a & b & c;


    }


    @Override
    public boolean updateUser(Map<String, Object> map) {
        if (map.get("name") != null) {
            map.put("lowerName", map.get("name").toString().toLowerCase());
        }
        UserDepart userDepart = userDepartMapper.queryByUid(Long.parseLong(map.get("id").toString()));
        if (userDepart != null) {
            userDepart.setDepartId(Integer.parseInt(map.get("departId").toString()));
            boolean a = userDepartMapper.update(userDepart);
            boolean b = gUserMapper.updateUser(map);
            return a & b;
        } else {
            UserDepart userDepart1 = new UserDepart();
            userDepart1.setDepartId(Integer.parseInt(map.get("departId").toString()));
            userDepart1.setUid(Long.parseLong(map.get("id").toString()));
            boolean a = userDepartMapper.add(userDepart1);
            boolean b = gUserMapper.updateUser(map);
            return a & b;
        }

    }


    @Override
    public boolean updateUserInfo(Map<String, Object> map) {
        if (map.get("name") != null) {
            map.put("lowerName", map.get("name").toString().toLowerCase());
        }
        return this.gUserMapper.updateUser(map);

    }

    @Override
    public boolean deleteUser(long id) {
//        boolean b = userDepartMapper.deleteByUid(id);
////        boolean a = gUserMapper.delete1(id);
////        return a & b;
        return this.gUserMapper.delete1(id);
    }

    @Override
    public Page<Map<String, Object>> queryUser(Map<String, Object> map) {
        Page<Map<String, Object>> page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String, Object>> a = gUserMapper.queryUser(map);
        return page;
    }

    @Override
    public Page<UserDepart> queryUserDepart(Map<String, Object> map) {
        Integer depatrId = Integer.parseInt(map.get("departId").toString());
        Map<String, Object> depart = departMapper.queryById(depatrId);
        List<Integer> departIds = new ArrayList<>();
        List<Map<String, Object>> departs = new DepartWarpper(depart).getChildren(depart);
        for (Map<String, Object> map1 : departs) {
            departIds.add(Integer.parseInt(map1.get("id").toString()));
        }
        departIds.add(Integer.parseInt(map.get("departId").toString()));
        Page<UserDepart> page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<UserDepart> userDepartList = userDepartMapper.queryByDepartIds(departIds);


        return page;
    }

    @Override
    public boolean nameExist(Map<String, Object> map) {
        Map<String, Object> mapName = new HashMap<>();

        mapName.put("name", map.get("name").toString());

        List<Map<String, Object>> name = gUserMapper.nameJudge(mapName);

        return name.size() > 0;

    }

    @Override
    public boolean emailExist(Map<String, Object> map) {
        Map<String, Object> mapEmail = new HashMap<>();
        mapEmail.put("email", map.get("email"));
        List<Map<String, Object>> email = gUserMapper.emailJudge(mapEmail);
        return email.size() > 0;
    }


    @Override
    public List<Map<String, Object>> queyrSimpleUser(Map<String, Object> map) {
        return this.gUserMapper.querySimpleUser(map);
    }

    @Override
    public Map<String, Object> queryUserByName(String name) {
        return this.gUserMapper.queryUserByName(name);
    }

    @Override
    public Map<String, Object> queryUserById(long userId) {
        return this.gUserMapper.queryUserById(userId);
    }

    @Override
    public Map<String, Object> queryOrgByName(String name) {
        return this.gUserMapper.queryOrgByName(name);
    }

    @Override
    public Map<String, Object> queryOrgById(long orgId) {
        return this.gUserMapper.queryOrgById(orgId);
    }


    @Override
    public boolean updateAuth(Long uid, JSONArray authIds) {
        GUser user = gUserMapper.queryById(uid);
        //本次权限分配中是否包含创建仓库的权限
        boolean c = false;
        //角色权限中是否含有仓库权限
        boolean d = false;

        boolean a = userAuthMapper.deleteByUid(uid);
        boolean b = true;
        for (int i = 0; i < authIds.size(); i++) {
            UserAuth userAuth = new UserAuth();
            userAuth.setUid(uid);
            userAuth.setAuthId(authIds.getIntValue(i));
            Auth auth = authMapper.queryById(authIds.getIntValue(i));
            if (auth.getAuthCode().equalsIgnoreCase("management_repo_add")) {
                c = true;
            }

            b = b & userAuthMapper.add(userAuth);
        }
        Set<Auth> roleAuths = roleService.queryByUid(uid);
        if (roleAuths.size() > 0) {
            for (Auth auth1 : roleAuths) {
                if (auth1.getAuthCode().equalsIgnoreCase("management_repo_add")) {
                    d = true;
                }
            }
        }
        if (c || d) {
            user.setAdmin(true);
            gUserMapper.update(user);
        } else {
            user.setAdmin(false);
            gUserMapper.update(user);
        }
        return a & b;
    }


    @Override
    public boolean updateModelAuth(Long uid, List<Map<String, Object>> models) {
        boolean a = modelAuthMapper.deleteByUid(uid);
        boolean b = true;
        for (Map<String, Object> map : models) {
            ModelAuth modelAuth = new ModelAuth();
            modelAuth.setMode(Short.parseShort(map.get("mode").toString()));
            modelAuth.setModelId(Long.parseLong(map.get("modelId").toString()));
            modelAuth.setUid(uid);
            modelAuth.setNodeId(map.get("modelId").toString() + "+" + map.get("mode").toString());
            b = b & modelAuthMapper.add(modelAuth);
        }
        return a & b;

    }

    @Override
    public boolean updateModelAuth1(Long uid, JSONArray directoryIds) {
        boolean a = directoryAuthMapper.deleteByUid(uid);
        boolean b = true;
        for (int i = 0; i < directoryIds.size(); i++) {
            DirectoryAuth directoryAuth = new DirectoryAuth();
            directoryAuth.setDirectoryId(directoryIds.getLongValue(i));
            directoryAuth.setUid(uid);
            b = b & directoryAuthMapper.add(directoryAuth);
        }
        return a & b;

    }

    @Override
    public boolean updateModelAuth2(Long uid, List<JSONObject> directoryIds) {
        boolean a = directoryAuthMapper.deleteByUid(uid);
        boolean b = true;
        for (int i = 0; i < directoryIds.size(); i++) {

            DirectoryAuth directoryAuth = new DirectoryAuth();
            directoryAuth.setUid(uid);
            directoryAuth.setDirectoryId(directoryIds.get(i).getLongValue("id"));
            directoryAuth.setMode(directoryIds.get(i).getIntValue("mode"));
            if(directoryAuth.getMode()>0)
            {
                b = b & directoryAuthMapper.add(directoryAuth);
            }

        }
        return a & b;
    }

    @Override
    public Page<Map<String, Object>> queryUserByDirectoryAuth(Map<String, Object> map) {
        Page<Map<String, Object>> page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String, Object>> a = gUserMapper.queryUserByDirectoryAuth(Long.parseLong(map.get("directoryId").toString()));
        return page;

    }

    @Override
    public Page<Map<String, Object>> queryUserWithOutDirectoryAuth(Map<String, Object> map)
    {
        Page<Map<String, Object>> page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String, Object>> a = gUserMapper.queryUserWithOutDirectoryAuth(Long.parseLong(map.get("directoryId").toString()),(List<Integer>) map.get("departIds"));
        return page;

    }

    @Override
    public Page<Map<String,Object>> queryUserByDepartId(Map<String, Object> map)
    {
        Page<Map<String, Object>> page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String, Object>> a = gUserMapper.queryUserByDepartId((List<Integer>) map.get("departIds"));
        return page;

    }


    @Override
    public List<Map<String,Object>> queryUserDirectoryAuths(long uid)
    {
           List<Integer>roleIds = roleService.queryUserRoleByUid(uid);
           return this.directoryAuthMapper.queryUserDirectoryAuth(uid,roleIds);
    }




    @Override
    public List<ReviewUserPage> queryAll() {
        return gUserMapper.queryAll();
    }




}

