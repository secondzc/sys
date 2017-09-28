package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class GUserServiceImpl implements GUserService {
    @Autowired
    GUserMapper gUserMapper;

    @Override
    public boolean add(GUser user) {
        return this.gUserMapper.add(user);
    }

    @Override
    public GUser querListByName(String name) {
        return this.gUserMapper.querListByName(name);
    }

    @Override
    public boolean update(GUser user) {
        return this.gUserMapper.update(user);
    }
}
