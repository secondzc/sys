package com.tongyuan.model.dao;

import com.tongyuan.model.domain.UserAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yh on 2017/9/11.
 */
@Mapper
public interface UserAuthMapper {

    public boolean add(UserAuth userAuth);

    public boolean update(UserAuth userAuth);


    public boolean deleteByUid(long uid);

    public boolean deleteByAuthId(Integer authId);

    public List<UserAuth> queryByUid(long uid);

}
