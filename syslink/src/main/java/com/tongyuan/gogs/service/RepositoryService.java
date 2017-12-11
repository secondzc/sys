package com.tongyuan.gogs.service;

import com.github.pagehelper.Page;
import com.tongyuan.gogs.domain.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-9-18.
 */
public interface RepositoryService {
    boolean add(Repository repository);
//    public Repository setData(Repository repository);
    Repository queryByName(String Name);
    public List<Repository> findAllRepository();
    Page<Map<String,Object>> queryByUid(Map<String,Object>map);
    Map<String,Object> queryById(long id);
    Page<Map<String,Object>> getCollaboration(Map<String,Object>map);
    boolean update(Repository repository);
    Repository queryByNameAndUserId(Map<String,Object> param);

}
