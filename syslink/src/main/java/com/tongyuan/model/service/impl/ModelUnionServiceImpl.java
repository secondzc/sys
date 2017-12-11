package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.ModelUnionMapper;
import com.tongyuan.model.domain.ModelUnion;
import com.tongyuan.model.service.ModelUnionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017-12-10.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ModelUnionServiceImpl implements ModelUnionService{

    @Autowired
    ModelUnionMapper modelUnionMapper;
    @Override
    public int add(ModelUnion model) {
        return this.modelUnionMapper.add(model);
    }

    @Override
    public boolean update(ModelUnion model) {
        return this.modelUnionMapper.update(model);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return this.deleteByIds(ids);
    }

    @Override
    public ModelUnion queryUnion(Map<String, Object> param) {
        return this.modelUnionMapper.queryUnion(param);
    }
}
