package com.tongyuan.gogs.service;

import com.tongyuan.gogs.domain.Repository;

import java.util.List;

/**
 * Created by xyx on 2017-9-18.
 */
public interface RepositoryService {
    boolean add(Repository repository);
//    public Repository setData(Repository repository);
    Repository queryByName(String Name);
    public List<Repository> findAllRepository();
}
