package com.tongyuan.model.service;

import com.alibaba.fastjson.JSONArray;
import com.tongyuan.model.domain.Auth;
import com.tongyuan.model.domain.UserRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yh on 2017/9/8.
 */
public interface RoleService {
    public boolean add(Map<String, Object> map);
    public boolean update(Map<String, Object> map);
    public boolean updatePermission(Integer roleId, Integer[] authIds);
    public boolean delete(long id);
    public List<Map<String,Object>> query();

    public Set<Auth> queryByUid(long uid);





    public boolean addUserRole(UserRole userRole);
    public boolean deleteUserRole(long uid);
    public List<UserRole> queryUserRole(long uid);



    public boolean updateUserRoles(long uid, JSONArray roles);

    public List<Integer> queryUserRoleByUid(long uid);


    public Set<Auth> getAuthByUserRole(Long uid);





}
