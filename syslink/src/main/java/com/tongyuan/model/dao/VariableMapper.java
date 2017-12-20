package com.tongyuan.model.dao;


import com.tongyuan.model.domain.Variable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2017-6-29.
 */
@Mapper
public interface VariableMapper {
    int add(Variable variable);
    int update(Variable variable);
    int deleteByIds(String[] ids);
    List<Variable>  queryListByModelId(Long modelId);
    public long selectId();
    public List<Variable> findAllVariable();
    public List<Variable> findVarByModelId(Long modelId);
}
