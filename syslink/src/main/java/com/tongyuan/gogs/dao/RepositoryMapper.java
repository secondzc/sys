package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Repository;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2017-9-18.
 */
@Mapper
public interface RepositoryMapper {
    boolean add(Repository repository);
    Repository queryByName(String Name);
}
