package com.tongyuan.model.dao;

import com.tongyuan.model.domain.RoleDirectoryAuth;

import java.util.List;

/**
 * Created by yh on 2017/12/25.
 */
public interface RoleDirectoryAuthMapper {

    public boolean add(RoleDirectoryAuth roleDirectoryAuth);
    public List<RoleDirectoryAuth> queryByRoleId(Integer roleId);
    public boolean deleteByRoleId(Integer roleId);

}
