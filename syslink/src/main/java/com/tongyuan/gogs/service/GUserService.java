

package com.tongyuan.gogs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.domain.UserDepart;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.pageModel.ReviewUserPage;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
public interface GUserService {
    boolean add(GUser user);
    GUser querListByName(String name);
    GUser queryById(long id);
    boolean update(GUser user);

    public LoginedUserModel CreateLoginedUser(GUser user);

    public void updateLoginstate(long userId, String loginIp, Date loginDate);



    public boolean addGUser(Map<String, Object> map);

    public boolean updateUser(Map<String, Object> map);

    public boolean updateUserInfo(Map<String, Object> map);

    public Page<Map<String,Object>> queryUser(Map<String, Object> map);

    public Page<UserDepart> queryUserDepart(Map<String, Object> map);

    public boolean deleteUser(long id);


    public boolean nameExist(Map<String, Object> map);

    public boolean emailExist(Map<String, Object> map);

    public List<Map<String,Object>> queyrSimpleUser(Map<String, Object> map);


    public Map<String,Object> queryUserByName(String name);

    public Map<String,Object> queryUserById(long userId);

    public Map<String,Object> queryOrgByName(String name);

    public Map<String,Object> queryOrgById(long orgId);

    public boolean updateAuth(Long uid, JSONArray authId);

    public boolean updateModelAuth(Long uid, List<Map<String, Object>> models);

    public boolean updateModelAuth1(Long uid, JSONArray directoryIds);

    public boolean updateModelAuth2(Long uid, List<JSONObject> directoryIds);

    public Page<Map<String,Object>>queryUserByDirectoryAuth(Map<String, Object> map);

    public Page<Map<String,Object>>queryUserWithOutDirectoryAuth(Map<String, Object> map);

    public Page<Map<String,Object>> queryUserByDepartId(Map<String, Object> map);

    public List<Map<String,Object>> queryUserDirectoryAuths(long uid);


    //这个没有分页 by：zhangcy
    public List<ReviewUserPage> queryAll();


}

