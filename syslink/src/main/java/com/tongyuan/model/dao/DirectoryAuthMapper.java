package com.tongyuan.model.dao;

import com.tongyuan.model.domain.DirectoryAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yh on 2017/10/26.
 */
@Mapper
public interface DirectoryAuthMapper {

    public boolean add(DirectoryAuth directoryAuth);

    public boolean deleteByUid(long uid);

    public List<DirectoryAuth> queryByUid(long uid);



}
