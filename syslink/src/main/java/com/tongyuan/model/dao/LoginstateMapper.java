package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Loginstate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yh on 2017/9/1.
 */
@Mapper
public interface LoginstateMapper {
    public boolean add(Loginstate loginstate);
    public boolean update(Loginstate loginstate);
    public Loginstate queryLoginstateById(@Param("id") long id);

}
