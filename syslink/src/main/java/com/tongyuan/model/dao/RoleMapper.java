package com.tongyuan.model.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/8.
 */
@Mapper
public interface RoleMapper {

    public boolean add(Map<String,Object> map);

    public boolean update(Map<String,Object>map);

    public boolean updatePermission(Map<String,Object>map);

    public boolean delete(long id);

    public List<Map<String,Object>> query();


}
