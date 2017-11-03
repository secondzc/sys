package com.tongyuan.model.service.impl;

import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.ReviewModelMapper;
import com.tongyuan.model.dao.ReviewNodeMapper;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.service.ReviewModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/3.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ReviewModelServiceImpl implements ReviewModelService{
    @Autowired
    private ReviewModelMapper reviewModelMapper;

    @Override
    public List<Model> queryAll(Map<String,Object> map){
        return reviewModelMapper.queryAll(map);
    }
    @Override
    public Model queryByModelId(Long modelId){
        return reviewModelMapper.queryByModelId(modelId);
    }
    @Override
    public List<Model> queryByModelName(Map<String,Object> map){
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("rows").toString()));
        return reviewModelMapper.queryByModelName(map);
    }
}
