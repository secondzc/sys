package com.tongyuan.gogs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/27.
 */
@Mapper
public interface TeamMapper {

    public boolean add(Map<String, Object> map);

    public boolean update(Map<String, Object> map);

    public boolean delete(long id);

    public List<Map<String,Object>> query(@Param("orgId") long orgId);

    public List<Map<String,Object>> queryByOrgId(@Param("orgId") long orgId);

    public Map<String,Object> queryByTeamId(long id);

    public Map<String,Object> queryByTeamName(Map<String, Object> map);


}
