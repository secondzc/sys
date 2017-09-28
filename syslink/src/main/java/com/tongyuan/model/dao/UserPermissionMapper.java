package com.tongyuan.model.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/11.
 */
@Mapper
public interface UserPermissionMapper {

    public boolean add(Map<String,Object> map);

    public boolean update(Map<String,Object>map);


    public boolean delete(long userId);

    public List<Map<String,Object>> query(Map<String,Object> map);
}
