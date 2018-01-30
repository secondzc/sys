package com.tongyuan.model.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.Component;

import java.util.List;

/**
 * Created by xieyx on 2017-6-21.
 */
public interface ComponentService {
    public int add(Component component);
    public boolean update(Component component);
    public boolean deleteByIds(String[] ids);
    public long selectId();
    public List<Component> queryListNullComp();
    public List<Component> findAllComp();
    //对组件必要参数进行插入
    public void doComponentParam(JSONObject componentJson,Component component);
    //解析父类的componnet并返回组件Id
    public Long insertComponentFromXml(JSONObject component,Long modelId,Long parentComponentId);
    //解析component子component
    public void insertChildComponentJsonArr(JSONArray componentJsonArr,Long modelId,Long parentComponentId);
    //解析component子component
    public void insertChildComponentJsonObject(JSONObject componentJsonObject,Long modelId,Long parentComponentId);
}
