package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Watch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
@Mapper
public interface WatchMapper {
    boolean add(Watch watch);
    boolean delete(Long id);
    public Watch queryListByParam(Map<String, Object> params);
    public List<Watch> findAllWatch();
}
