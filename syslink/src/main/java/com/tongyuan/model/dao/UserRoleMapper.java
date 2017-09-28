package com.tongyuan.model.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 82509 on 2017/9/10.
 */
@Mapper
public interface UserRoleMapper {
    public boolean add(Map<String,Object> map);

    public boolean update(Map<String,Object>map);


    public boolean delete(long userId);

    public List<Map<String,Object>> query(Map<String,Object>map);
}
