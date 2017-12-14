package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Access;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-9-18.
 */
@Mapper
public interface AccessMapper {
    boolean add(Access access);
    boolean delete(Long id);
}
