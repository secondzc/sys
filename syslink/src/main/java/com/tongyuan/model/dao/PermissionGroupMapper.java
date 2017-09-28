package com.tongyuan.model.dao;

import com.tongyuan.model.domain.PermissionGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/7.
 */
@Mapper
public interface PermissionGroupMapper {
    public boolean add(PermissionGroup permissiongroup);
    public boolean update(PermissionGroup permissiongroup);
    public boolean delete(long id);
    public List<Map<String,Object>>query();
}
