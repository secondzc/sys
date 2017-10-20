package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.AuthMapper;
import com.tongyuan.model.dao.RoleAuthMapper;
import com.tongyuan.model.dao.UserAuthMapper;
import com.tongyuan.model.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/19.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthMapper authMapper;
    @Autowired
    UserAuthMapper userAuthMapper;
    @Autowired
    RoleAuthMapper roleAuthMapper;

    @Override
    public boolean add(Map<String,Object> map)
    {
        return this.authMapper.add(map);
    }
    @Override
    public boolean update(Map<String,Object>map)
    {
        return this.authMapper.update(map);
    }

    @Override
    public boolean delete(Integer id)
    {
        return this.authMapper.deleteById(id)&this.authMapper.deleteByPid(id)&this.userAuthMapper.deleteByAuthId(id)&roleAuthMapper.deleteByAuthId(id);
    }

    @Override
    public List<Map<String,Object>> query()
    {
        return  this.authMapper.query();
    }

    @Override
    public List<Map<String,Object>> getChildren(Map<String,Object>map)
    {
        return this.authMapper.getChildren(map);
    }



}
