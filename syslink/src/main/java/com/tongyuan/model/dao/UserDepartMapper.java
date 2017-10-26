package com.tongyuan.model.dao;

import com.tongyuan.model.domain.UserDepart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yh on 2017/10/25.
 */
@Mapper
public interface UserDepartMapper {

    public boolean add(UserDepart userDepart);

    public boolean update(UserDepart userDepart);

    public boolean deleteByUid(long uid);

    public boolean deleteByDepartId(Integer departId);

    public UserDepart queryByUid(long uid);

    public List<UserDepart> queryByDepartId(Integer departId);
}
