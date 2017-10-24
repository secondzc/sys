package com.tongyuan.model.dao;

import com.tongyuan.model.domain.ModelAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yh on 2017/10/20.
 */
@Mapper
public interface ModelAuthMapper {

    public boolean add(ModelAuth modelAuth);

    public boolean deleteByUid(long uid);

    public boolean deleteByModelId(long modelId);

    public List<ModelAuth> queryByUid(long uid);

    public List<ModelAuth> queryByModelId(long modelId);



}
