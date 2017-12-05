package com.tongyuan.model.dao;

import com.tongyuan.model.domain.CAE.CaeVariable;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaeVariableMapper {
    int add(CaeVariable caeVariable);
}
