package com.tongyuan.model.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/8.
 */
public interface RoleService {
    public boolean add(Map<String,Object> map);
    public boolean update(Map<String,Object> map);
    public boolean updatePermission(Map<String,Object> map);
    public boolean delete(long id);
    public List<Map<String,Object>> query();

    public boolean addUserRole(Map<String,Object> map);
    public boolean updateUserRole(Map<String,Object> map);
     public boolean deleteUserRole(long userId);
    public List<Map<String,Object>> queryUserRole(Map<String,Object> map);





}
