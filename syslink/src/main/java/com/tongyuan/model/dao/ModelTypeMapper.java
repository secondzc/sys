package com.tongyuan.model.dao;

import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.ModelType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
@Mapper
public interface ModelTypeMapper {
    public List<ModelTypeDto> getModelTypeList();
}
