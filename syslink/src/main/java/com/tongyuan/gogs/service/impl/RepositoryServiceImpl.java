package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.RepositoryMapper;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RepositoryServiceImpl implements RepositoryService{
    @Autowired
    RepositoryMapper repositoryMapper;

    @Override
    public boolean add(Repository repository) {
        return this.repositoryMapper.add(repository);
    }

    @Override
    public Repository queryByName(String Name) {
        return this.repositoryMapper.queryByName(Name);
    }


}
