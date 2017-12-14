package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.AccessMapper;
import com.tongyuan.gogs.dao.CollaborationMapper;
import com.tongyuan.gogs.domain.Access;
import com.tongyuan.gogs.domain.Collaboration;
import com.tongyuan.gogs.service.AccessService;
import com.tongyuan.gogs.service.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-12-13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CollaborationServiceImpl implements CollaborationService {

    @Autowired
    CollaborationMapper collaborationMapper;

    @Override
    public boolean add(Collaboration collaboration) {
        return this.collaborationMapper.add(collaboration);
    }


    @Override
    public boolean delete(Long id) {
        return this.collaborationMapper.delete(id);
    }

    @Override
    public void addDefault(Long userId, Long repositoryId) {
        Collaboration collaboration = new Collaboration();
        collaboration.setUserId(userId);
        collaboration.setRepoId(repositoryId);
        collaboration.setMode(2);
        this.collaborationMapper.add(collaboration);
    }
}
