package com.tongyuan.model.dao;

import com.tongyuan.model.domain.CAE.CaeComponent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaeComponentMapper {
    int add(CaeComponent caeComponent);
}
