package com.tongyuan.model.dao;

import com.tongyuan.model.domain.RoleAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleAuthMapper {

    boolean add(RoleAuth roleAuth);

    boolean update(RoleAuth roleAuth);

    boolean deleteByRoleId(Integer roleId);

    boolean deleteByAuthId(Integer authId);

    List<RoleAuth> queryByRoleId(Integer roleId);


}
