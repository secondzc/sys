package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.GUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by xyx on 2017-9-18.
 */
@Mapper
public interface GUserMapper {
    boolean add(GUser user);
    GUser querListByName(String name);
    boolean update(GUser user);
}
