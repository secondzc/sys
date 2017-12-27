package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/8/30.
 */
@Mapper
public interface LogMapper {

    public boolean add(Log log);
    public boolean delete(long id);

    public List<Log>findAllLog();

    public List<Log>queryLog(Map<String,Object> map);
}
