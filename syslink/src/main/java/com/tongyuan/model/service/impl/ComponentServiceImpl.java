package com.tongyuan.model.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.dao.ComponentMapper;
import com.tongyuan.model.domain.Component;
import com.tongyuan.model.domain.Variable;
import com.tongyuan.model.service.ComponentService;
import com.tongyuan.model.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 组件数据操作
 * Created by xieyx on 2017-6-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentMapper componentMapper;
    @Autowired
    VariableService variableService;

    @Override
    public int add(Component component) {
        return this.componentMapper.add(component);
    }

    @Override
    public boolean update(Component component) {
        return this.componentMapper.update(component);
    }

    @Override
    public boolean deleteByIds(String[] ids) {
        return false;
    }

    @Override
    public long selectId() {
        return this.componentMapper.selectId();
    }

    @Override
    public List<Component> queryListNullComp() {
        return this.componentMapper.queryListNullComp();
    }

    @Override
    public List<Component> findAllComp() {
        return this.componentMapper.findAllComp();
    }

    @Override
    public void doComponentParam(JSONObject componentJson,Component component) {
        if (componentJson.get("@Name") != null ) {
            component.setName((String) componentJson.get("@Name"));
        }
        if (componentJson.get("@Type") != null) {
            component.setType((String) componentJson.get("@Type"));
        }
        if (componentJson.get("@Modification") != null) {
            if(componentJson.get("@Modification").toString().length() > 200){
                component.setModification("重定义过长！");
            }else{
                component.setModification((String) componentJson.get("@Modification"));
            }
        }
    }

    /**
     * 解析父类的componnet并返回组件Id
     * @param componentJson
     * @param modelId
     * @return 模型id
     */
    @Override
    public Long insertComponentFromXml(JSONObject componentJson,Long modelId,Long parentComponentId) {
            Component component = new Component();
            component.setModelId(modelId);
            component.setCreateTime(new Date());
            this.doComponentParam(componentJson,component);
            component.setParentId(parentComponentId);
            this.add(component);
            Long componentId = component.getId();
            return  componentId;
    }

    /**
     * 对jsonArray数组的component对象进行解析
     * @param componentJsonArr
     * @param modelId
     * @param parentComponentId
     */
    @Override
    public void insertChildComponentJsonArr(JSONArray componentJsonArr, Long modelId, Long parentComponentId) {
        for(int i = 0;i<componentJsonArr.size(); i++){
           JSONObject jsonObject = (JSONObject) componentJsonArr.get(i);
            if("True".equals(jsonObject.get("@IsVariable"))){
                Variable variable = new Variable();
                variableService.DoVariableParaFromXml(jsonObject,variable);
                variable.setModelId(modelId);
                variable.setComponnetId(parentComponentId);
                variableService.add(variable);
            }
            else{
                Long componentId = this.insertComponentFromXml(jsonObject,modelId,parentComponentId);
                if(jsonObject.get("component") != null){
                    if(jsonObject.get("component") instanceof JSONArray){
                        this.insertChildComponentJsonArr((JSONArray) jsonObject.get("component"),modelId,componentId);
                    }else{
                        JSONObject componentJson = (JSONObject) jsonObject.get("component");
                        this.insertChildComponentJsonObject(componentJson,modelId,componentId);
                    }
                }
            }
        }
    }

    /**
     * 对jsonObject数组的component对象进行解析
     * @param componentJsonObject
     * @param modelId
     * @param parentComponentId
     */
    @Override
    public void insertChildComponentJsonObject(JSONObject componentJsonObject, Long modelId, Long parentComponentId) {
        if("True".equals(componentJsonObject.get("@IsVariable"))){
            Variable variable = new Variable();
            variableService.DoVariableParaFromXml(componentJsonObject,variable);
            variable.setModelId(modelId);
            variable.setComponnetId(parentComponentId);
            variableService.add(variable);
        }else{
            Long componentId = this.insertComponentFromXml(componentJsonObject,modelId,parentComponentId);
            if(componentJsonObject.get("component") != null){
                if(componentJsonObject.get("component") instanceof JSONArray){
                    this.insertChildComponentJsonArr((JSONArray) componentJsonObject.get("component"),modelId,componentId);
                }else{
                    JSONObject componentJson = (JSONObject) componentJsonObject.get("component");
                    this.insertChildComponentJsonObject(componentJson,modelId,componentId);
                }
            }

        }
    }
}
