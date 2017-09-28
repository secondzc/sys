package com.tongyuan.model.daoExtra;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/25.
 */
@Mapper
public interface GUserMapper {
    public boolean add(Map<String,Object>map);

    public boolean updateUser(Map<String ,Object>map);

    public boolean updateOrg(Map<String,Object>map);

    public boolean addOrg (Map<String ,Object>map);

    public boolean delete (long id);



    public List<Map<String,Object>> queryOrg(Map<String,Object>map);

    public List<Map<String,Object>> queryUser(Map<String,Object>map);

    public Map<String,Object> querySimpleUser(Long uid);

}
