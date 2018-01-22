package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.Component;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.Variable;
import com.tongyuan.model.service.ComponentService;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.model.service.VariableService;
import com.tongyuan.pageModel.ComponentTreeObj;
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
@RequestMapping("/api/component")
public class ComponentController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private VariableService variableService;


    @RequestMapping(value = "/componentTree",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject componentTree(@RequestParam(value = "modelId",required = false)Long modelId,
                                HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //在这个模型下的所有model（modelList）
        List<ComponentTreeObj> modelTreeList = new ArrayList<>();
        List<ComponentTreeObj> modelList = new ArrayList<>();
        List<ComponentTreeObj> componentTreeObjList = new ArrayList<>();
        try {
            //查询到所有的model
            List<Model> allModel = modelService.findAllModelicaModel();
            //查询到所有的Comp（组件）
            List<Component> allComp = componentService.findAllComp();
            //查询到所有的变量
            List<Variable> allVariable = variableService.findAllVariable();
            //获取列表model

            getSearchModel(modelId,allModel,modelTreeList,modelList);
            if(modelList.size() >0){
                //生成组件树（含变量）= 变量树
                for (ComponentTreeObj varibaleTreeObj: modelList) {
                    //这个模型下的组件树
                    List<ComponentTreeObj> compTreeList = new ArrayList<>();
                    varibaleTreeObj.setChildren(compTreeList);
                 //   getCompFromModel(varibaleTreeObj.getId(),allComp,varibaleTreeObj.getChildren(),allVariable);
                    //不含模型名称
                    getCompFromModel(varibaleTreeObj.getId(),allComp,componentTreeObjList,allVariable);
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
        jo.put("data",componentTreeObjList);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    /*
    *获取这个package下的所有model
     */
     public void getSearchModel(Long modelId,List<Model> allModel,List<ComponentTreeObj> modelTreeList,List<ComponentTreeObj> modelList){

         for(int i=0; i<allModel.size(); i++){
             if(allModel.get(i).getParentId() == modelId) {
                 ComponentTreeObj treeObj = new ComponentTreeObj();
                 treeObj.setId(allModel.get(i).getId());
                 treeObj.setName(allModel.get(i).getName());
                 List<ComponentTreeObj> childVar = new ArrayList<>();
                 treeObj.setChildren(childVar);
                 modelTreeList.add(treeObj);
//                 modelList.add(treeObj);
             }
             if(allModel.get(i).getId() == modelId && allModel.get(i).getParentId() != 0){
                 ComponentTreeObj treeObj = new ComponentTreeObj();
                 treeObj.setId(allModel.get(i).getId());
                 treeObj.setName(allModel.get(i).getName());
                 modelList.add(treeObj);
             }
         }
         if( modelTreeList != null){
             for (ComponentTreeObj treechild: modelTreeList) {
                 getSearchModel(treechild.getId(),allModel,treechild.getChildren(),modelList);
             }
         }
     }

    /*
    * 获取这个模型下的所有组件生成树状图
    * */
    public void getCompFromModel(Long currModelId,List<Component> allComp,List<ComponentTreeObj> compTreeList,List<Variable> allVariable){
        //获取对应model 的所有组件
        List<Component> searchComp = new ArrayList<>();
            for(int i= 0;i<allComp.size();i++){
                if(allComp.get(i).getModelId() == currModelId){
                    searchComp.add(allComp.get(i));
                }
            }
            if(searchComp.size() >0){
                for (Component component: searchComp) {
                    if(component.getParentId() == 0){
                        //组件根目录
                        ComponentTreeObj compRoot = new ComponentTreeObj();
                        compRoot.setId(component.getId());
                        compRoot.setName(component.getName());
                        List<ComponentTreeObj> rootChild = new ArrayList<>();
                        compRoot.setChildren(rootChild);
                        compTreeList.add(compRoot);
                    }
                }
            }

            if(compTreeList.size() >0){
                //添加组件的子组件
                for (ComponentTreeObj rootComp: compTreeList) {
                    addChildComp(rootComp.getId(),allComp,rootComp.getChildren(),allVariable);
                }
            }
    }

    /*
    * 添加组件的子组件
    * */
    public void addChildComp(Long compId,List<Component> allComp,List<ComponentTreeObj> compChildList,List<Variable> allVariable){
        for (Component component: allComp) {
            if(component.getParentId() == compId){
                //组件子组件
                ComponentTreeObj compChild = new ComponentTreeObj();
                compChild.setId(component.getId());
                compChild.setName(component.getName());
                compChild.setModification(component.getModification());
                compChild.setType(component.getType());
                List<ComponentTreeObj> variable = new ArrayList<>();
                compChild.setChildren(variable);
                compChildList.add(compChild);
            }
        }
        if(compChildList != null){
            for (ComponentTreeObj childComp: compChildList) {
                if(childComp.getChildren() == null){
                    List<ComponentTreeObj> variable = new ArrayList<>();
                    childComp.setChildren(variable);
                }
                addChildComp(childComp.getId(),allComp,childComp.getChildren(),allVariable);
            }
        }

    }

}
