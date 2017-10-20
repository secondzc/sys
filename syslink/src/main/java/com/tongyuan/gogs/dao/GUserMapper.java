package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.GUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-9-18.
 */
@Mapper
public interface GUserMapper {
    boolean add(GUser user);
    GUser querListByName(String name);

    GUser queryById(long id);
    boolean update(GUser user);


    boolean add(Map<String, Object> map);

    boolean updateUser(Map<String, Object> map);

    boolean updateOrg(Map<String, Object> map);

    boolean addOrg(Map<String, Object> map);

    boolean delete(long id);


    List<Map<String,Object>> queryOrg(Map<String, Object> map);

    List<Map<String,Object>> queryUser(Map<String, Object> map);



    List<Map<String,Object>> querySimpleUser(Map<String, Object> map);

    Map<String,Object> queryOrgById(long id);

    Map<String,Object> queryUserById(long id);

    Map<String,Object> queryUserByName(String name);

    Map<String,Object> queryOrgByName(String name);

    List<Map<String,Object>>test2(Map<String, Object> map);

    List<GUser>test1(Map<String, Object> map);
}
