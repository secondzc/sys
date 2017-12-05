package com.tongyuan.model.dao;

import com.tongyuan.model.domain.CAE.CaeFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaeFileMapper {
    int add(CaeFile caeFile);
}
