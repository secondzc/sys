package com.tongyuan.model.service;

import com.tongyuan.model.domain.ModelAuth;

import java.util.List;

/**
 * Created by yh on 2017/11/30.
 */
public interface ModelAuthService {
    public boolean add(ModelAuth modelAuth);
    public boolean deleteByUid(long uid);
    public boolean deleteByModelId(long modelId);
    public List<ModelAuth> queryByUid(long uid);
    public List<ModelAuth> queryByModelId(long modelId);
}
