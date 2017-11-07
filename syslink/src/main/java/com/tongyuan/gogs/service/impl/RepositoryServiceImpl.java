package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.CollaborationMapper;
import com.tongyuan.gogs.dao.RepositoryMapper;
import com.tongyuan.gogs.domain.Collaboration;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RepositoryServiceImpl implements RepositoryService{
    @Autowired
    RepositoryMapper repositoryMapper;
    @Autowired
    CollaborationMapper collaborationMapper;

    @Override
    public boolean add(Repository repository) {
        return this.repositoryMapper.add(repository);
    }

    @Override
    public Repository queryByName(String Name) {
        return this.repositoryMapper.queryByName(Name);
    }

    @Override
    public List<Repository> findAllRepository() {
        return this.repositoryMapper.findAllRepository();
    }
    @Override
    public List<Map<String,Object>> queryByUid(long uid)
    {
        return this.repositoryMapper.queryByUid(uid);
    }
    @Override
    public Map<String,Object> queryById(long id)
    {
        return this.repositoryMapper.queryById(id);
    }
    @Override
    public List<Map<String,Object>> getCollaboration(long uid)
    {
        List<Map<String,Object>>cRepos = new ArrayList<>();
        List<Collaboration>collaborations = collaborationMapper.queryByUid(uid);
        for(Collaboration collaboration : collaborations)
        {
            Map<String,Object> repo = repositoryMapper.queryById(collaboration.getRepoId());
            cRepos.add(repo);
        }
        return cRepos;
    }

}
