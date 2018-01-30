package com.tongyuan.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.dao.VariableMapper;
import com.tongyuan.model.domain.Variable;
import com.tongyuan.model.enums.VariableType;
import com.tongyuan.model.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-6-29.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class VariableServiceImpl implements VariableService {
    @Autowired
    VariableMapper variableMapper;

    @Override
    public int add(Variable variable) {
        return this.variableMapper.add(variable);
    }

    @Override
    public int update(Variable variable) {
        return this.variableMapper.update(variable);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return this.variableMapper.deleteByIds(ids);
    }

    @Override
    public List<Variable> queryListByModelId(Long modelId) {
        return this.variableMapper.queryListByModelId(modelId);
    }

    @Override
    public long selectId() {
        return this.variableMapper.selectId();
    }

    @Override
    public List<Variable> findAllVariable() {
        return this.variableMapper.findAllVariable();
    }

    @Override
    public List<Variable> findVarByModelId(Long modelId) {
        return this.variableMapper.findVarByModelId(modelId);
    }

    @Override
    public void DoVariableParaFromXml(JSONObject variableJson,Variable variable) {
        variable.setIsVariable(1);
        variable.setCreateTime(new Date());
        variable.setName((String) variableJson.get("@Name"));
        if("True".equals(variableJson.get("@IsArray"))){
            variable.setType(VariableType.getValueByKey(variableJson.get("@Type")+"[]"));
        }
        else{
            variable.setType((String) variableJson.get("@Type"));
        }
        if (variableJson.get("@Value") != null) {
            if(variableJson.get("@Value").toString().length() > 250){
                variable.setDefaultValue("默认值过长！");
            }else{
                variable.setDefaultValue((String) variableJson.get("@Value"));
            }
        }
        if (variableJson.get("@Unit") != null) {
            variable.setUnits((String) variableJson.get("@Unit"));
        }
        if (variableJson.get("@Min") != null) {
            variable.setLowerBound((String) variableJson.get("@Min"));
        }
        if (variableJson.get("@Max") != null) {
            variable.setUpperBound((String) variableJson.get("@Max"));
        }
        if (variableJson.get("@IsParameter") != null) {
            if ("True".equals(variableJson.get("@IsParameter"))) {
                variable.setIsParam(1);
            } else {
                variable.setIsParam(0);
            }
        }
        variable.setComponnetId(0);
        variable.setIsInput(0);
    }

    @Override
    public void insertVariableFromJsonXml(JSONObject jsonVariable,Long modelId,Long componentId) {
        Variable variable = new Variable();
        this.DoVariableParaFromXml(jsonVariable,variable);
        variable.setModelId(modelId);
        variable.setComponnetId(componentId);
        this.add(variable);
    }
}
