package com.tongyuan.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewSuccessMapper {
    int add(Long packageId);
}
