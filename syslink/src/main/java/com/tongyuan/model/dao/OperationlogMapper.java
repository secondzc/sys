package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Operationlog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/8/30.
 */
@Mapper
public interface OperationlogMapper {

    public boolean add(Operationlog operationlog);
    public boolean delete(long id);

    public List<Operationlog>findAllLog();

    public List<Operationlog>queryLog(Map<String,Object> map);
}
