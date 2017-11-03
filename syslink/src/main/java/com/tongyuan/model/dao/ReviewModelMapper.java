package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/3.
 */
@Mapper
public interface ReviewModelMapper {
    List<Model> queryAll(Map<String, Object> map);
    Model queryByModelId(Long modelId);
    List<Model> queryByModelName(Map<String, Object> map);
}
