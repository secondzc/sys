package com.tongyuan.model.service.impl;

import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.ReviewFlowInstanceMapper;
import com.tongyuan.model.domain.ReviewFlowInstance;
import com.tongyuan.model.service.ReviewFlowInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/6/29.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ReviewFlowInstanceImpl implements ReviewFlowInstanceService{
    @Autowired
    private ReviewFlowInstanceMapper reviewFlowInstanceMapper;

    @Override
    public int add(ReviewFlowInstance reviewFlowInstance){
        return reviewFlowInstanceMapper.add(reviewFlowInstance);
    };

    @Override
    public ReviewFlowInstance queryByInstanceId(Long instanceId){
        return reviewFlowInstanceMapper.queryByInstanceId(instanceId);
    };

    @Override
    public int deleteByInstanceIds(String[] instanceIds){
        return reviewFlowInstanceMapper.deleteByInstanceIds(instanceIds);
    };

    @Override
    public int setStatus(Map<String,Object> map){
        return reviewFlowInstanceMapper.setStatus(map);
    }; //返回修改的数目，返回1修改成功，返回0修改失败。map的key为status和instanceId

    @Override
    public List<ReviewFlowInstance> queryByNameAndStatus(Map<String,Object> map){
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("rows").toString()));
        return reviewFlowInstanceMapper.queryByNameAndStatus(map);
    };

    @Override
    public int updateTime(Map<String,Object> map){
        return reviewFlowInstanceMapper.updateTime(map);
    }
}
