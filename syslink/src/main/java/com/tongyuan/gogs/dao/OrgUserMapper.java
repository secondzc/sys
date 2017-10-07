package com.tongyuan.gogs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/27.
 */
@Mapper
public interface OrgUserMapper {

    public boolean add(Map<String, Object> map);

    public boolean update(Map<String, Object> map);

    public boolean updateByUid(Map<String, Object> map);

    /**
     * 删除组织时使用
     * @param id
     * @return
     */
    public boolean delete(long id);

    /**
     * 从组织中删除用户时使用
     */
    public boolean deleteOrgUser(Map<String, Object> map);

    public List<Map<String,Object>> queryByOrgId(@Param("orgId") long orgId);

    public List<Map<String,Object>> queryByUid(@Param("uid") long uid);




}
