package com.tongyuan.model.service;

import com.tongyuan.model.domain.Variable;

import java.util.List;

/**
 * Created by Administrator on 2017-6-29.
 */
public interface VariableService {
    int add(Variable variable);
    int update(Variable variable);
    int deleteByIds(String[] ids);
    List<Variable> queryListByModelId(Long modelId);
    public long selectId();
    public List<Variable> findAllVariable();
}
