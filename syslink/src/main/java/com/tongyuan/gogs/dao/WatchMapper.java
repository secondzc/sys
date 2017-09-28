package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Watch;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2017-9-18.
 */
@Mapper
public interface WatchMapper {
    boolean add(Watch watch);
}
