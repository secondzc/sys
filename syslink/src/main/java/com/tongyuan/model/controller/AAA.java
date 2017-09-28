package com.tongyuan.model.controller;

import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.Variable;
import com.tongyuan.model.enums.VariableType;

import java.util.*;

/**
 * Created by Administrator on 2017-9-15.
 */
public class AAA {
    public static void MM(){
        List<Map<String, String>> compList = new ArrayList<>();
         for(int i = 0;i<compList.size(); i++){
                if(compList.get(i).size() == 13){
//                    analysis(compList.get(i),model);
                }
                if(compList.get(i).size() == 2){

                }
         }
    }


    public static void analysis(Map<String,String> xmlData,Model model){
        if(xmlData.get("IsVariable").equals("True")){
            Variable variable = new Variable();
            variable.setModelId(model.getId());
            //判断这个组件是否需要插入到数据库
              doSet(xmlData,variable);
//               variableService.add(variable);
            }
            if(xmlData.get("IsVariable").equals("False")){
//                Component component = new Component() ;

        }

    }


    public static void doSet(Map<String,String> xmlData,Variable variable){
        if (xmlData.get("Name") != null ) {
            variable.setName(xmlData.get("Name"));
        }
        if (xmlData.get("Type") != null) {
            if ("True".equals(xmlData.get("IsArray"))) {
                variable.setType(VariableType.getValueByKey(xmlData.get("Type")+"[]"));
            }else {
                variable.setType(VariableType.getValueByKey(xmlData.get("Type")));
            }
        }
        if (xmlData.get("Value") != null) {
            variable.setDefaultValue(xmlData.get("Value"));
        }
        if (xmlData.get("Unit") != null) {
            variable.setUnits(xmlData.get("Unit"));
        }
        if (xmlData.get("Min") != null) {
            variable.setLowerBound(xmlData.get("Min"));
        }
        if (xmlData.get("Max") != null) {
            variable.setUpperBound(xmlData.get("Max"));
        }
        if (xmlData.get("IsParameter") != null) {
            if ("True".equals(xmlData.get("IsParameter"))) {
                variable.setIsParam(1);
            } else {
                variable.setIsParam(0);
            }
        }
        if (xmlData.get("parentName") != null) {
            variable.setParentName(xmlData.get("parentName"));
        }
        variable.setCreateTime(new Date());
    }

    //解析组件
    public static void analysisComponent(HashMap<String,Object> map,Model model){
        //组件内容
        List<HashMap<String,String>>  componentList = new ArrayList<>();
        //获取组件内容插入到数据库
        componentList = (List<HashMap<String, String>>) map.get("componentVar");
        if(componentList != null){
            Map<String,String> componentMap = componentList.get(0);
            analysis(componentMap,model);
        }
        else{
            return;
        }
        //子组件内容
//        Map<String,Object> componentChild = new HashMap<>();
//        componentChild = (HashMap<String, Object>) map.get("component");
        analysisComponent((HashMap<String, Object>) map.get("component"),model);
    }

}
