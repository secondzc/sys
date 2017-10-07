package com.tongyuan.gogs.service;

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

    public List<Map<String,Object>> queryUser(Map<String, Object> map);

    public boolean deleteUser(long id);


    public boolean nameExist(Map<String, Object> map);

    public boolean emailExist(Map<String, Object> map);
}
