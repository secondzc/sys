package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Star;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
@Mapper
public interface StarMapper {
    boolean add(Star star);
    public Star queryListByParam(Map<String, Object> params);
    boolean delete(Long id);
    public List<Star> findAllStar();
}
