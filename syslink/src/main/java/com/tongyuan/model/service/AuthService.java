package com.tongyuan.model.service;

import com.tongyuan.model.domain.Auth;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yh on 2017/9/19.
 */
public interface AuthService {


    public boolean add(Map<String, Object> map);
    public boolean update(Map<String, Object> map);
    public boolean delete(Integer id);
    public List<Map<String,Object>> query();
    public List<Map<String,Object>> getChildren(Map<String, Object> map);


    public void directoryFilter(List<Long> dirctoryIdList, long uid);

    public Set<Auth> getAuthByUserAuth(Long uid);
}
