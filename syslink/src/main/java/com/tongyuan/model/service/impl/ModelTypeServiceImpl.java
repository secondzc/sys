package com.tongyuan.model.service.impl;

import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.dao.ModelTypeMapper;
import com.tongyuan.model.domain.ModelType;
import com.tongyuan.model.service.ModelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ModelTypeServiceImpl implements ModelTypeService{

    @Autowired
    ModelTypeMapper modelTypeMapper;
    @Override
    public List<ModelTypeDto> getModelTypeList() {
        return this.modelTypeMapper.getModelTypeList();
    }
}
