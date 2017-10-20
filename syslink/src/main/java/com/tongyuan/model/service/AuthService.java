package com.tongyuan.model.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/19.
 */
public interface AuthService {


    public boolean add(Map<String, Object> map);
    public boolean update(Map<String, Object> map);
    public boolean delete(Integer id);
    public List<Map<String,Object>> query();
    public List<Map<String,Object>> getChildren(Map<String, Object> map);
}
