package com.tongyuan.gogs.service;

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
    List<Map<String,Object>> queryByUid(long uid);
    Map<String,Object> queryById(long id);
    List<Map<String,Object>> getCollaboration(long uid);
    boolean update(Repository repository);
    Repository queryByNameAndUserId(Map<String,Object> param);

}
