package com.tongyuan.model.service;

import com.tongyuan.model.domain.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/3.
 */
public interface ReviewModelService {
    List<Model> queryAll(Map<String, Object> map);
    Model queryByModelId(Long modelId);
    List<Model> queryByModelName(Map<String, Object> map);
}
