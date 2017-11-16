package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.Component;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.Variable;
import com.tongyuan.model.service.ComponentService;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.model.service.VariableService;
import com.tongyuan.pageModel.VariableTreeObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyx on 2017-9-27.
 */
@Controller
@RequestMapping("/api/variable")
public class VariableController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private VariableService variableService;


    @RequestMapping(value = "/variableTree",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject variableTree(@RequestParam(value = "modelId",required = false)Long modelId,
                                HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //在这个模型下的所有model（modelList）
        List<VariableTreeObj> modelTreeList = new ArrayList<>();
        List<VariableTreeObj> modelList = new ArrayList<>();
        //只有一个模型（不包含模型名称）
        List<VariableTreeObj> variableTreeObjList = new ArrayList<>();
        try {
            //查询到所有的model
            List<Model> allModel = modelService.findAllModel();
            //查询到所有的Comp（组件）
            List<Component> allComp = componentService.findAllComp();
            //查询到所有的变量
            List<Variable> allVariable = variableService.findAllVariable();
            //获取列表model
            getSearchModel(modelId,allModel,modelTreeList,modelList);
            if(modelList.size() >0){
                //生成组件树（含变量）= 变量树
                for (VariableTreeObj varibaleTreeObj: modelList) {
                    //这个模型下的组件树
                    List<VariableTreeObj> compTreeList = new ArrayList<>();
                    varibaleTreeObj.setChildren(compTreeList);
                    getCompFromModel(varibaleTreeObj.getId(),allComp,varibaleTreeObj.getChildren(),allVariable);
                    //不包含模型名称
                    getCompFromModel(varibaleTreeObj.getId(),allComp,variableTreeObjList,allVariable);
                    //当变量没有父类组件的时直接添加进去
                    //查询这个model下所有的顶层变量
                    List<Variable> topVariableList = variableService.findVarByModelId(varibaleTreeObj.getId());
                    for (Variable variable: topVariableList) {
                        VariableTreeObj variableTreeObj = new VariableTreeObj();
                        doSetVarTree(variableTreeObj,variable);
                        varibaleTreeObj.getChildren().add(variableTreeObj);
                    }
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",variableTreeObjList);
        jo.put("rootData",modelList);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    /*
    *获取这个package下的所有model
     */
     public void getSearchModel(Long modelId,List<Model> allModel,List<VariableTreeObj> modelTreeList,List<VariableTreeObj> modelList){

         for(int i=0; i<allModel.size(); i++){
             if(allModel.get(i).getParentId() == modelId) {
                 VariableTreeObj treeObj = new VariableTreeObj();
                 treeObj.setId(allModel.get(i).getId());
                 treeObj.setName(splitName(allModel.get(i).getName()));
                 List<VariableTreeObj> childVar = new ArrayList<>();
                 treeObj.setChildren(childVar);
                 modelTreeList.add(treeObj);
//                 modelList.add(treeObj);
             }
             if(allModel.get(i).getId() == modelId && allModel.get(i).getParentId() != 0){
                 VariableTreeObj treeObj = new VariableTreeObj();
                 treeObj.setId(allModel.get(i).getId());
                 treeObj.setName(splitName(allModel.get(i).getName()));
                 modelList.add(treeObj);
             }
         }
         if( modelTreeList != null){
             for (VariableTreeObj treechild: modelTreeList) {
                 getSearchModel(treechild.getId(),allModel,treechild.getChildren(),modelList);
             }
         }
     }

    /*
    * 获取这个模型下的所有组件生成树状图
    * */
    public void getCompFromModel(Long currModelId,List<Component> allComp,List<VariableTreeObj> compTreeList,List<Variable> allVariable){
        //获取对应model 的所有组件
        List<Component> searchComp = new ArrayList<>();
            for(int i= 0;i<allComp.size();i++){
                if(allComp.get(i).getCurrentModelId() == currModelId){
                    searchComp.add(allComp.get(i));
                }
            }
            if(searchComp.size() >0){
                for (Component component: searchComp) {
                    if(component.getParentId() == 0){
                        //组件根目录
                        VariableTreeObj compRoot = new VariableTreeObj();
                        compRoot.setId(component.getId());
                        compRoot.setName(component.getName());
                        List<VariableTreeObj> rootChild = new ArrayList<>();
                        compRoot.setChildren(rootChild);
                        compTreeList.add(compRoot);
                    }
                }
            }

            if(compTreeList.size() >0){
                //添加组件的子组件
                for (VariableTreeObj rootComp: compTreeList) {
                    addChildComp(rootComp.getId(),allComp,rootComp.getChildren(),allVariable);
                }
            }
    }

    /*
    * 添加组件的子组件
    * */
    public void addChildComp(Long compId,List<Component> allComp,List<VariableTreeObj> compChildList,List<Variable> allVariable){
        for (Component component: allComp) {
            if(component.getParentId() == compId){
                //组件子组件
                VariableTreeObj compChild = new VariableTreeObj();
                compChild.setId(component.getId());
                compChild.setName(component.getName());
                List<VariableTreeObj> variable = new ArrayList<>();
                compChild.setChildren(variable);
                compChildList.add(compChild);
            }
        }
        if(compChildList.size() == 0){
            for (Variable variable: allVariable) {
                if(variable.getComponnetId() == compId){
                    //添加变量节点
                    VariableTreeObj varChild = new VariableTreeObj();
                    doSetVarTree(varChild,variable);
                    compChildList.add(varChild);
                }
            }
        }

        if(compChildList != null){
            for (VariableTreeObj childComp: compChildList) {
                if(childComp.getChildren() == null){
                    List<VariableTreeObj> variable = new ArrayList<>();
                    childComp.setChildren(variable);
                }
                addChildComp(childComp.getId(),allComp,childComp.getChildren(),allVariable);
            }
        }

    }

    public void doSetVarTree(VariableTreeObj varChild,Variable variable){
        varChild.setId(variable.getId());
        varChild.setName(variable.getName());
        varChild.setType(variable.getType());
        varChild.setDefaultValue(variable.getDefaultValue());
        varChild.setUnits(variable.getUnits());
        varChild.setLowerBound(variable.getLowerBound());
        varChild.setUpperBound(variable.getUpperBound());
        if( String.valueOf(variable.getIsParam()) != null && String.valueOf(variable.getIsParam()) != "" ){
            if(variable.getIsParam() == 0){
                varChild.setIsParam("否");
            }else{
                varChild.setIsParam("是");
            }
        }
        if( String.valueOf(variable.getIsInput()) != null && String.valueOf(variable.getIsInput()) != "" ){
            if(variable.getIsInput() == 0){
                varChild.setIsInput("否");
            }else{
                varChild.setIsInput("是");
            }
        }
    }


    //获取组件名称（点后面的名字）
    public String splitName(String name){
        String splitName[] = name.split("\\.");
        if(splitName.length >1) {
            return splitName[splitName.length-1];
        }else{
            return name;
        }

    }

     //组件浏览器（点击某个指定的模型，展示组件变量，切模型名要全称，组件变量名称要简写）
    @RequestMapping(value = "/contAndVariableTree",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject contAndVariableTree(@RequestParam(value = "modelId",required = false)Long modelId,
                                   HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //只有一个模型（包含模型名称）
        List<VariableTreeObj> variableTreeObjList = new ArrayList<>();
        try{
            //查询指定的模型
            Model model = modelService.queryModelById(modelId);
            if(model == null ){
                jo.put("status","1");
                jo.put("code",0);
                jo.put("msg","error");
                return jo;
            }
            //查询到所有的Comp（组件）
            List<Component> allComp = componentService.findAllComp();
            //查询到所有的变量
            List<Variable> allVariable = variableService.findAllVariable();
            //模型树对象（root）
            VariableTreeObj variableTreeObj = new VariableTreeObj();
            variableTreeObj.setId(model.getId());
            variableTreeObj.setName(model.getName());
            //创建子对象
            List<VariableTreeObj> modelChild = new ArrayList<>();
            variableTreeObj.setChildren(modelChild);
            variableTreeObjList.add(variableTreeObj);
            getCompFromModel(variableTreeObj.getId(),allComp,variableTreeObj.getChildren(),allVariable);
            //当变量没有父类组件的时直接添加进去
            //查询这个model下所有的顶层变量
            List<Variable> topVariableList = variableService.findVarByModelId(variableTreeObj.getId());
            for (Variable variable: topVariableList) {
                //模型的变量
                VariableTreeObj modelVar = new VariableTreeObj();
                doSetVarTree(modelVar,variable);
                variableTreeObj.getChildren().add(modelVar);
            }

        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","error");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("rootData",variableTreeObjList);
        return  jo;
    }

}
