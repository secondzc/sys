package com.tongyuan.model.service;

import com.alibaba.fastjson.JSONObject;
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
    public List<Variable> findVarByModelId(Long modelId);
    //添加xmlJson中vaiable参数的数据
    public void DoVariableParaFromXml(JSONObject variableJson,Variable variable);
    //添加一个variable根据jsonVariable
    public void insertVariableFromJsonXml(JSONObject jsonVariable,Long modelId,Long componentId);

}
