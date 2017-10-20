package com.tongyuan.model.dao;

import com.tongyuan.model.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 82509 on 2017/9/10.
 */
@Mapper
public interface UserRoleMapper {
    public boolean add(UserRole userRole);



    public boolean delete(long uid);

    public List<UserRole> query(long uid);
}
