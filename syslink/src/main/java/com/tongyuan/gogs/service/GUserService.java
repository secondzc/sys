package com.tongyuan.gogs.service;

import com.github.pagehelper.Page;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.domainmodel.LoginedUserModel;

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

    public Page<Map<String,Object>> queryUser(Map<String, Object> map);

    public boolean deleteUser(long id);


    public boolean nameExist(Map<String, Object> map);

    public boolean emailExist(Map<String, Object> map);

    public List<Map<String,Object>> queyrSimpleUser(Map<String, Object> map);


    public Map<String,Object> queryUserByName(String name);

    public Map<String,Object> queryOrgByName(String name);

    public Map<String,Object> queryOrgById(long orgId);

    public boolean updateAuth(Long uid, Integer[] authId);

    public boolean updateModelAuth(Long uid,Long[] directoryIds);


    Page<GUser>test1(Map<String, Object> map);
}
