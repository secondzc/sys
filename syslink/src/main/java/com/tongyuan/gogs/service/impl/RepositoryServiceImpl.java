package com.tongyuan.gogs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    public Page<Map<String,Object>> queryByUid(Map<String,Object> map)
    {
        Page<Map<String,Object>>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String,Object>> repos = repositoryMapper.queryMyRepo(map);
        return page;
    }
    @Override
    public Map<String,Object> queryById(long id)
    {
        return this.repositoryMapper.queryById(id);
    }

    @Override
    public Page<Map<String,Object>> getCollaboration(Map<String,Object>map)
    {
        Page<Map<String,Object>>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String,Object>>cRepos = repositoryMapper.getc(map);
        return page;
    }

    @Override
    public boolean update(Repository repository) {
        return this.repositoryMapper.update(repository);
    }

    @Override
    public Repository queryByNameAndUserId(Map<String, Object> param) {
        return this.repositoryMapper.queryByNameAndUserId(param);
    }

}
