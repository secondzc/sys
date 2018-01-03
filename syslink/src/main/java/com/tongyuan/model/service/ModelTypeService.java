package com.tongyuan.model.service;

import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.ModelType;

import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
public interface ModelTypeService {
    public List<ModelTypeDto> getModelTypeList();
    //根据类型名获取modeltype对象
    public ModelType getByType(String typeName);
}
