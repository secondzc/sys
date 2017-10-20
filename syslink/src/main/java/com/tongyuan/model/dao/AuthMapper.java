package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/19.
 */
@Mapper
public interface AuthMapper {

    public boolean add(Map<String, Object> map);

    public boolean update(Map<String, Object> map);


    public boolean deleteById(Integer id);

    public boolean deleteByPid(Integer pid);

    public List<Map<String,Object>> query();
    public Auth queryById(Integer authId);

    public List<Map<String,Object>> getChildren(Map<String, Object> map);
}
