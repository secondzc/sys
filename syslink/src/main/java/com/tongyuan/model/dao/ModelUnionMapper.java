package com.tongyuan.model.dao;

import com.tongyuan.model.domain.ModelRepo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by Administrator on 2017-12-10.
 */
@Mapper
public interface ModelUnionMapper {
    public int add(ModelRepo component);
    boolean update(ModelRepo component);
    boolean deleteByIds(String[] ids);
    ModelRepo queryUnion(Map<String,Object> param);
}
