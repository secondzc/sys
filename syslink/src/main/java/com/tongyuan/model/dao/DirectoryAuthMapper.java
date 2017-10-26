package com.tongyuan.model.dao;

import com.tongyuan.model.domain.DirectoryAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yh on 2017/10/26.
 */
@Mapper
public interface DirectoryAuthMapper {

    public boolean add(DirectoryAuth directoryAuth);

    public boolean deleteByUid(long uid);

    public boolean queryByUid(long uid);



}
