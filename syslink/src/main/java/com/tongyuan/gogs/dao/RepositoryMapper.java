package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2017-9-18.
 */
@Mapper
public interface RepositoryMapper {
    boolean add(Repository repository);
    Repository queryByName(String Name);
    public List<Repository> findAllRepository();
}
