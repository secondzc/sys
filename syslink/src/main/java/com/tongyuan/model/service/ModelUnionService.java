package com.tongyuan.model.service;

import com.tongyuan.model.domain.ModelUnion;

import java.util.Map;

/**
 * Created by Administrator on 2017-12-10.
 */
public interface ModelUnionService {
    int add(ModelUnion model);
    boolean update(ModelUnion model);
    int deleteByIds(String[] ids);
    ModelUnion queryUnion(Map<String,Object> param);
}
