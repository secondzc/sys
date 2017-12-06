package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.ModelAuthMapper;
import com.tongyuan.model.domain.ModelAuth;
import com.tongyuan.model.service.ModelAuthService;
import com.tongyuan.model.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yh on 2017/11/30.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ModelAuthServiceImpl implements ModelAuthService{

    @Autowired
    ModelAuthMapper modelAuthMapper;

    @Override
    public boolean add(ModelAuth modelAuth)
    {
        return this.modelAuthMapper.add(modelAuth);
    }
    @Override
    public boolean deleteByUid(long uid)
    {
        return this.modelAuthMapper.deleteByUid(uid);
    }

    @Override
    public boolean deleteByModelId(long modelId)
    {
        return this.modelAuthMapper.deleteByModelId(modelId);
    }

    @Override
    public List<ModelAuth> queryByUid(long uid)
    {
        return this.modelAuthMapper.queryByUid(uid);
    }

    @Override
    public List<ModelAuth> queryByModelId(long modelId)
    {
        return this.modelAuthMapper.queryByModelId(modelId);
    }

}
