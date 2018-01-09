package com.tongyuan.model.service;

import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.ModelType;

import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
public interface ModelTypeService {
    //获取刚刚插入的iconModeltype
    public List<ModelTypeDto> getModelTypeList();
    //根据类型名获取modeltype对象
    public ModelType getByType(String typeName);
    //添加modelType
    public Long add(ModelType modelType);
    //修改modelType
    public void update(ModelType modelType);
    //删除modelType
    public void delete(String modelTypeName);
}
