package com.tongyuan.model.service;

import com.tongyuan.model.domain.ModelRepo;

import java.util.Map;

/**
 * Created by Administrator on 2017-12-10.
 */
public interface ModelUnionService {
    int add(ModelRepo model);
    boolean update(ModelRepo model);
    int deleteByIds(String[] ids);
    ModelRepo queryUnion(Map<String,Object> param);
}
