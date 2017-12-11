package com.tongyuan.model.dao;

import com.tongyuan.model.domain.ModelUnion;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by Administrator on 2017-12-10.
 */
@Mapper
public interface ModelUnionMapper {
    public int add(ModelUnion component);
    boolean update(ModelUnion component);
    boolean deleteByIds(String[] ids);
    ModelUnion queryUnion(Map<String,Object> param);
}
