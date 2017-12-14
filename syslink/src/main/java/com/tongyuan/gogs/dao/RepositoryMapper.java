
package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
@Mapper
public interface RepositoryMapper {
    Long add(Repository repository);
    Repository queryByName(String Name);
    public List<Repository> findAllRepository();
    List<Map<String,Object>> queryMyRepo(Map<String,Object>map);
    List<Map<String,Object>> getc(Map<String,Object>map);
    Map<String,Object> queryById(long id);
    boolean update(Repository repository);
    Repository queryByNameAndUserId(Map<String,Object> param);
}
