package com.tongyuan.gogs.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/27.
 */
@Mapper
public interface TeamUserMapper {

    public boolean add(Map<String,Object> map);

    public boolean update(Map<String,Object>map);

    public boolean delete(long id);

    public List<Map<String,Object>> query(Map<String,Object>map);
}
