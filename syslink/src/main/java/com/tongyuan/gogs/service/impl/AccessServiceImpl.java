package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.AccessMapper;
import com.tongyuan.gogs.domain.Access;
import com.tongyuan.gogs.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-12-13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    AccessMapper accessMapper;

    @Override
    public boolean add(Access access) {
        return this.accessMapper.add(access);
    }


    @Override
    public boolean delete(Long id) {
        return this.accessMapper.delete(id);
    }

    @Override
    public void addDefault(Long userId, Long repositoryId) {
        Access access = new Access();
        access.setUserId(userId);
        access.setRepoId(repositoryId);
        access.setMode(2);
        this.accessMapper.add(access);
    }
}
