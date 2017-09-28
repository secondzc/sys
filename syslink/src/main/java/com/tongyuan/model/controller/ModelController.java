package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.*;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.enums.VariableType;
import com.tongyuan.model.service.*;
import com.tongyuan.pageModel.ModelWeb;
import com.tongyuan.pageModel.TreeObj;
import com.tongyuan.tools.ServletUtil;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * model方法
 * Created by xieyx on 2017-6-21.
 */
@Controller
@RequestMapping("/api/model")
public class ModelController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Date nowDate = new Date();

    @Autowired
    private ModelService modelService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileModelService fileModelService;
    @Autowired
    private ResourceUtil resourceUtil;
    @Autowired
    private VariableService variableService;
    @Autowired
    private ComponentService componentService;

//    @RequestMapping(value = "/test",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public void test(HttpServletRequest request , HttpServletResponse response){
//        JSONObject result=new JSONObject();
//    //    String name = request.getParameter("name");
//        //获取到userName,password
//        // 查询到userId的方法 findByNameAndpassword
//        //xmlMap 把xml转化成map的格式
//        Map<String, Object> xmlMap = new HashMap<String, Object>();
//        //存放解析的所有xmlMap
//        Map<String,Map> xmlAnalysisMap = new HashMap<>();
//        //存放解析svg，info文件所在位置的Map
//        Map<String,String> svgPath = new HashMap<>();
//        String name = "syslink";
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("name",name);
//        //查找到项目所在的位置
//        List<FileModel> directoryList = fileModelService.queryListByName(params);
//        //选取最近push的一个directory对象
//        FileModel directory = new FileModel();
//        if(!directoryList.isEmpty()){
//            directory = directoryList.get(0);
//        }else {
//            return ;
//        }
//        //获取文件所在位置，寻找xml文件所在的路径，解析xml吧所需的数据插入到数据库中
//        //文件所在位置
//        String filePath = directory.getAbsoluteAddress();
//        //获取到xml所在的文件位置
//        String xmlPath = "";
//        xmlPath= resourceUtil.getXmlPath(filePath,xmlPath);
//        //对xml进行解析,遍历xml文件下所有文件
//        if(StringUtil.isNull(xmlPath)){
//            result.put("message","没有找到xml文件！");
//            result.put("flag",false);
//            ServletUtil.createSuccessResponse(200, result, response);
//            return ;
//        }
//        File xmlFilePath = new File(xmlPath);
//        String[] subFiles = xmlFilePath.list();
//        Model model = new Model();
//        model.setName(subFiles[0].split("\\.")[0]);
//        model.setDirectoryId(directory.getId());
//        model.setClasses(ModelClasses.Package.getKey());
//        model.setModelFilePath(filePath);
//        model.setScope(false);
//        model.setUserId(1);
//        model.setDeleted(false);
//        if(modelService.queryModelByName(subFiles[0].split("\\.")[0]) == null){
//            modelService.add(model);
//        }
//        //查找最外层空的model
//        Model nullModel = modelService.queryModelByName(subFiles[0].split("\\.")[0]);
//        for (int i = 0; i < subFiles.length; i++) {
//            //查看文件的格式
//            String [] fileNames = subFiles[i].split("\\.");
//            //文件的类型
//            String filePreType = fileNames[fileNames.length-2];
//            String fileType = fileNames[fileNames.length-1];
//            if(("xml").equals(fileType)){
//                xmlMap =  resourceUtil.analysisXmlPath(xmlFilePath +"/" +subFiles[i]);
//                xmlAnalysisMap.put(subFiles[i],xmlMap);
//                svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
//            }else if("svg".equals(fileType)){
//                  if("icon".equals(filePreType)){
//                      svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
//                  }else if("diagram".equals(filePreType)){
//                      svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
//                  }
//            }else if("html".equals(fileType)){
//                svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
//            }
//        }
//        //遍历xmlMap进行数据的插入
//        for(Map.Entry<String,Map> entry : xmlAnalysisMap.entrySet()){
//            //解析xmlmap 把数据存放到数据库
//            insertData(entry,svgPath,nullModel,directory);
//        }
//        result.put("message","xml解析成功!");
//        result.put("flag",true);
//        ServletUtil.createSuccessResponse(200, result, response);
//        return ;
//    }


    public void insertData(Map.Entry<String,Map> entry,Map svgPath,Model nullModel,FileModel directory,Long directoryId){
        Map<String,Object> xmlMap = entry.getValue();
        Model model = new Model();
        //验证model
     //   Model validateModel = new Model();
        model.setFileId(directory.getId());
        model.setDirectoryId(directoryId);
        model.setParentId(nullModel.getId());
        model.setUserId(nullModel.getUserId());
        model.setScope(false);
        model.setCreateTime(nowDate);
        model.setDeleted(false);
        analysisXmlMap(xmlMap,model,svgPath);
        Model validateModel = modelService.queryModelByName(model.getName());
        if( validateModel == null){
            modelService.add(model);
        }else{
            model.setLastUpdateTime(nowDate);
            model.setId(validateModel.getId());
            modelService.update(model);
        }
        insertVaiable(xmlMap);
    }

    public void analysisXmlMap(Map<String,Object> xmlMap,Model model,Map<String,String> svgPath){
        for (Map.Entry<String ,Object> entry : xmlMap.entrySet()) {
            if("ModelName".equals(entry.getKey())){
                String type = "";
                //判断value是什么类型
                type = decideType(entry.getValue(),type);
                if("String".equals(type)){
                    if(!StringUtil.isNull((String) entry.getValue())){
                        model.setName((String) entry.getValue());
                        for(Map.Entry<String ,String> svgEntry : svgPath.entrySet()){
                            if (svgEntry.getKey().equals(entry.getValue()+".diagram.svg")){
                                model.setDiagramSvgPath(svgEntry.getValue());
                            }
                            if (svgEntry.getKey().equals(entry.getValue()+".icon.svg")){
                                model.setIconSvgPath(svgEntry.getValue());
                            }
                            if (svgEntry.getKey().equals(entry.getValue()+".info.html")){
                                model.setInfoTextPath(svgEntry.getValue());
                            }
                            if (svgEntry.getKey().equals(entry.getValue()+".dom.xml")){
                                model.setModelFilePath(svgEntry.getValue());
                            }
                        }
                    }
            }

            }
            if(entry.getKey().endsWith("_Model")){
               String [] modelNames = entry.getKey().split("\\_");
                 model.setType(modelNames[0]);
            }
            if("ModelDescript".equals(entry.getKey())){
                String type = "";
                //判断value是什么类型
                type = decideType(entry.getValue(),type);
                if("String".equals(type)){
                    if(!StringUtil.isNull((String) entry.getValue())){
                    model.setDiscription((String) entry.getValue());
                    }
                }
            }
            if("ModelClass".equals(entry.getKey())){
                String type = "";
                //判断value是什么类型
                type = decideType(entry.getValue(),type);
                if("String".equals(type)){
                    if(!StringUtil.isNull((String) entry.getValue())){
                        model.setClasses(ModelClasses.getValueByKey((String) entry.getValue()));
                    }
                }
            }
            if("Imports".equals(entry.getKey())){
                String type = "";
                type = decideType(entry.getValue(),type);
                //当imports 一个组件的时候，或者没有的时候
                if("String".equals(type)){
                    if(!StringUtil.isNull((String) entry.getValue())){
                        model.setImport((String) ((String) entry.getValue()).split(" ")[1]);
                    }
                }
                else if("Map".equals(type)){
                    Object secondValue =  ((Map)entry.getValue()).get("import");
                    type = decideType(secondValue,type);
                    //当import的数量等于1的时候
                    if("String".equals(type)){
                        if(!StringUtil.isNull(secondValue.toString())){
                            model.setImport((String) (secondValue.toString().split(" ")[1]));
                        }
                    }
                    else if("List".equals(type)){
                        //当import的数量大于1的时候
                        List<String> valueList = (List)secondValue;
                        String importValue="";
                        for(int i= 0;i<valueList.size();i++){
                            importValue += valueList.get(i).split(" ")[1];
                        }
                        model.setImport(importValue);
                    }
                }
            }

            if("Extendses".equals(entry.getKey())){
                String type = "";
                type = decideType(entry.getValue(),type);
                //当imports 一个组件的时候，或者没有的时候
                if("String".equals(type)){
                    if(!StringUtil.isNull((String) entry.getValue())){
                        model.setImport((String) ((String) entry.getValue()).split(" ")[1]);
                    }
                }
                else if("Map".equals(type)){
                    Object secondValue =  ((Map)entry.getValue()).get("extends");
                    type = decideType(secondValue,type);
                    //当import的数量等于1的时候
                    if("String".equals(type)){
                        if(!StringUtil.isNull(secondValue.toString())){
                            model.setImport((String) (secondValue.toString().split(" ")[1]));
                        }
                    }
                    else if("List".equals(type)){
                        //当import的数量大于1的时候
                        List<String> valueList = (List)secondValue;
                        String extendsValue="";
                        for(int i= 0;i<valueList.size();i++){
                            extendsValue += valueList.get(i);
                        }
                        model.setExtends(extendsValue);
                    }
                }
            }
        }
    }

    public String decideType(Object value,String type){
        boolean string = value instanceof  String ;
        boolean list = value instanceof  List;
        boolean map = value instanceof  Map;
        if(string){
            type = "String";
        }
        else if(list){
            type = "List";
        }
        else if(map){
            type = "Map";
        }
        return type;
    }

    public void insertVaiable(Map<String,Object> xmlMap) {
        Model model = new Model();
        for (Map.Entry<String, Object> entry : xmlMap.entrySet()) {
            if ("ModelName".equals(entry.getKey())) {
                String type = "";
                //判断value是什么类型
                type = decideType(entry.getValue(), type);
                if ("String".equals(type)) {
                    if (!StringUtil.isNull((String) entry.getValue())) {
                        model = modelService.queryModelByName((String) entry.getValue());
                    }
                }
            }
        }
        //插入到variable表中
        insertV(xmlMap,model);
      }
    public  void insertV(Map<String,Object> xmlMap,Model model) {
        //存储组件的id和parentName
        Map<String,Long> compName = new HashMap<String,Long>();

        //存储变量的自身id和parentname
        Map<String,Long> variableId = new HashMap<>();
        for (Map.Entry<String, Object> entry : xmlMap.entrySet()) {
            if ("Components".equals(entry.getKey())) {
                String type = "";
                //判断value是什么类型
                type = decideType(entry.getValue(), type);
                if("Map".equals(type)){
                Map<String, List> compMap = (Map<String, List>) entry.getValue();
                type = decideType(compMap.get("component"),type);
                if("List".equals(type)){
                    List<Map<String, Object>> compList = compMap.get("component");
                    for(int i = 0;i<compList.size(); i++){
                        if(compList.get(i).size() == 13){
                           analysis(compList.get(i),model,compName,variableId);
                        }
                        if(compList.get(i).size() == 2){
                           analysisComponent((HashMap<String, Object>) compList.get(i),model,compName,variableId);
                        }
                    }
//                    for (int i = 0; i < compList.size(); i++) {
//                        Variable variable = new Variable();
//                        variable.setModelId(model.getId());
//                        //判断这个组件是否需要插入到数据库
//                        int isVar = 0;
//                        for (Map.Entry<String, String> entryComp : compList.get(i).entrySet()) {
//                            if ("IsVariable".equals(entryComp.getKey())) {
//                                if ("False".equals(entryComp.getValue())) {
//                                    isVar =1;
//                                    continue;
//                                }
//                            }
//                            if ("Name".equals(entryComp.getKey())) {
//                                variable.setName(entryComp.getValue());
//                            }
//                            if ("Type".equals(entryComp.getKey())) {
//                                if ("True".equals(compList.get(i).get("IsArray"))) {
//                                    variable.setType(VariableType.getValueByKey(entryComp.getValue()+"[]"));
//                                }else {
//                                    variable.setType(VariableType.getValueByKey(entryComp.getValue()));
//                                }
//                            }
//                            if ("Value".equals(entryComp.getKey())) {
//                                variable.setDefaultValue(entryComp.getValue());
//                            }
//                            if ("Unit".equals(entryComp.getKey())) {
//                                variable.setUnits(entryComp.getValue());
//                            }
//                            if ("Min".equals(entryComp.getKey())) {
//                                variable.setLowerBound(entryComp.getValue());
//                            }
//                            if ("Max".equals(entryComp.getKey())) {
//                                variable.setUpperBound(entryComp.getValue());
//                            }
//                            if ("IsParameter".equals(entryComp.getKey())) {
//                                if ("True".equals(entryComp.getValue())) {
//                                    variable.setIsParam(1);
//                                } else {
//                                    variable.setIsParam(0);
//                                }
//                            }
/*                            if ("IsArray".equals(entryComp.getKey())) {
                                if ("True".equals(entryComp.getValue())) {
                                    variable.setIsInput(1);
                                } else {
                                    variable.setIsInput(0);
                                }
                            }*/
//                        }
//                        if ( 0 == isVar){
//                            variable.setCreateTime(new Date());
//                            variableService.add(variable);
//                        }
//                    }
                }else if ("Map".equals(type)){
                    Map<String,String> compotentMap = (Map<String, String>) (Map<String, String>) compMap.get("component");
                    Variable variable = new Variable();
                    variable.setModelId(model.getId());
//                    if(compotentMap.get("IsVariable").equals("False")){
//                         return;
//                    }
                    if(compotentMap.get("IsVariable").equals("True")){
                        variable.setIsVariable(1);
                    }
                    else {
                        variable.setIsVariable(0);
                    }
                    variable.setName(compotentMap.get("Name"));
                    variable.setType(VariableType.getValueByKey(compotentMap.get("Type")));
                    variable.setDefaultValue(compotentMap.get("Value"));
                    variable.setUnits(compotentMap.get("Unit"));
                    variable.setLowerBound(compotentMap.get("Min"));
                    variable.setUpperBound(compotentMap.get("Max"));
                    if(compotentMap.get("IsParameter").equals("True")){
                        variable.setIsParam(1);
                    }
                    else {
                        variable.setIsParam(0);
                    }
                    if(compotentMap.get("IsArray").equals("True")){
                        variable.setIsInput(1);
                    }
                    else {
                        variable.setIsInput(0);
                    }
                    variable.setCreateTime(nowDate);
                    variableService.add(variable);
                 }
                }
            }
        }
        //刚插入的Component
        List<Component> componentList = componentService.queryListNullComp();
        //获取组件model的id
        long modelId = 0;
        modelId = componentList.get(0).getCurrentModelId();
        //获取组件当前model对象
        Model currModel = modelService.queryModelById(modelId);
        //获取所有的model
        List<Model> allModel = modelService.findAllModel();
        //模型实例化组件的模型名称
        String modelCompName = currModel.getName().split("\\.")[0];
        for (int i = 0; i< componentList.size(); i++){
            for (Model modelComp : allModel) {
                if(modelComp.getName().equals(modelCompName+"."+componentList.get(i).getType())){
                    componentList.get(i).setModelId(modelComp.getId());
                }
                else{
                    componentList.get(i).setModelId(-1);
                }
            }
            for(int j = 0; j< componentList.size(); j++){
                //用来判断第二层组件父类
                //定义需要比较的parentName
                String parentName = "";
                String parentNameArr[] = componentList.get(i).getParentName().split("\\;");
                for(int m= 0;m< parentNameArr.length -1; m++){
                    parentName += parentNameArr[m]+";";
                }
                if(parentName != null && parentName != ""){
                    if(parentNameArr.length == 2){
                        if(parentName.substring(0,parentName.length()-1).equals(componentList.get(j).getName())){
                            componentList.get(i).setParentId(componentList.get(j).getId());
                        }
                    }
                    if(parentName.substring(0,parentName.length()-1).equals(componentList.get(j).getParentName())){
                        componentList.get(i).setParentId(componentList.get(j).getId());
                    }
                }
            }
            boolean componentResult = componentService.update(componentList.get(i));
        }
//        for (Map.Entry<String,Long> varName : variableName.entrySet()) {
//             String variableArr[] = varName.getKey().split("\\;");
//            //用来比较的变量父类名
//            String varCompare = "";
//             for(int i= 0;i< variableArr.length -1; i++){
//                 varCompare += variableArr[i];
//             }
//            for (Map.Entry<String,Long> comp: compName.entrySet()) {
//                if(comp.getKey().equals(varCompare)){
//                    varName.setValue(comp.getValue());
//                }
//            }
//        }
    }

//    //获取模型树状图
//    @RequestMapping(value = "/getModelTree",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public Map<String,List<EasyuiTreeNode>> getModelTree(HttpServletRequest request , HttpServletResponse response){
//        String name = request.getParameter("name");
//        //获取到userName,password
//        // 查询到userId的方法 findByNameAndpassword
//        long userId = 1;
//        Map<String ,List<EasyuiTreeNode>> modelTree = new HashMap<>();
//        List<Model> allModelList = modelService.findAllModel();
//        List<Model> rootModelList = modelService.findRootModel();
//        //公有库模型的树
//        List<EasyuiTreeNode> publicModelTree = new ArrayList<EasyuiTreeNode>();
//        //个人库模型树
//        List<EasyuiTreeNode> privateModelTree = new ArrayList<EasyuiTreeNode>();
//        for (Model model: rootModelList) {
//            if(!model.getScope()){
//                if(model.getUserId() == userId){
//                    privateModelTree.add(tree(model,allModelList,true));
//                }
//            }
//            publicModelTree.add(tree(model,allModelList,true));
//        }
//        modelTree.put("publicModelTree",publicModelTree);
//        modelTree.put("privateModelTree",privateModelTree);
//        return modelTree;
//    }


//    /**
//     * 递归
//     * @param model
//     * @param recursive
//     * @return
//     */
//    private EasyuiTreeNode tree(Model model,List<Model> allModelList, boolean recursive) {
//        EasyuiTreeNode node = new EasyuiTreeNode();
//        Map<String, Object> attributes = new HashMap<String, Object>();
//        //    attributes.put("taskId", directory.getTask().getTaskId());
//        if(model != null){
//            node.setId(model.getModelFilePath());
//            node.setText(model.getName());
//        }
//        //    node.setAttributes(attributes);
//        if (model != null
//                && allModelList.size() > 0) {
//            if (recursive) {
//                List<Model> modelList = new ArrayList<Model>();
//                for (Model  fileDir: allModelList) {
//                    if(fileDir.getParentId() == model.getId()){
//                        modelList.add(fileDir);
//                    }
//                }
//                //Collections.sort(directoryList, new FilelibraryComparator());
//                List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
//                for (Model m : modelList) {
//                    node.setState("closed");
//                    EasyuiTreeNode t = tree(m,allModelList, true);
//                    children.add(t);
//
//                }
//                node.setChildren(children);
//            }
//        }
//        return node;
//    }

    //获取模型列表
    @RequestMapping(value = "/getModelList",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public void getModelList(HttpServletRequest request , HttpServletResponse response){
        //获取到userName,password
        // 查询到userId的方法 findByNameAndpassword
        JSONObject jo=new JSONObject();
        Map<String,List<Model>> modelListMap = new HashMap<>();
        List<Model> allModelList = modelService.findAllModel();
        //所有的公有模型列表
        List<Model> publicModelList = new ArrayList<>();
        //个人私有模型列表
        List<Model> privateModelList = new ArrayList<>();
        //所有私有的模型列表
        List<Model> allPrivateModelLsit = new ArrayList<>();
        for (Model allmodel: allModelList) {
            if(0 != allmodel.getParentId()){
                publicModelList.add(allmodel);
                if(false == allmodel.getScope()){
                    allPrivateModelLsit.add(allmodel);
   //                 if(user.getId() != null && user.getId() == allmodel.getUserId()){
   //                 privateModelList.add(allmodel);
 //                   }
                }
            }
        }
//        modelListMap.put("publicModelList",publicModelList);
//        modelListMap.put("privateModelList",privateModelList);
//        modelListMap.put("allPrivateModelLsit",allPrivateModelLsit);
        jo.put("publicModelList",publicModelList);
        jo.put("privateModelList",privateModelList);
        jo.put("allPrivateModelLsit",allPrivateModelLsit);
        jo.put("message","查询成功!");
        jo.put("flag",true);
        ServletUtil.createSuccessResponse(200, jo, response);
        return;
  //      return modelListMap;
    }

//    //模糊查询model列表
//    @RequestMapping(value = "/vagueSearchModelList",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public void vagueSearchModelList(HttpServletRequest request , HttpServletResponse response){
//        Map<String,Object> params = new HashMap<String,Object>();
//        JSONObject jo=new JSONObject();
//        String name = request.getParameter("name");
//        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
//        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
//        params.put("name",name);
//        params.put("page", page);
//        params.put("rows", rows);
//        List<Model> modelList = modelService.vagueSearchByName(params);
//        PageInfo<Model> pageInfo =new PageInfo<Model>(modelList);
//        jo.put("message","查询成功!");
//        jo.put("flag",true);
//        jo.put("rows", modelList);
//        jo.put("total", pageInfo.getPages());//总页数
//        jo.put("records",pageInfo.getTotal());//查询出的总记录数
//        ServletUtil.createSuccessResponse(200, jo, response);
//    }

    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list(@RequestParam(value = "parent_id",required = false)Long parent_id,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        List<ModelWeb>  repositoryModelList = new ArrayList<>();
        //过滤后的modelList
        List<Model> searchModel = new ArrayList<>();
        //查询所有direactory
        List<Directory> allDirectory = directoryService.findAllDirectory();
        //存放directory的id
        List<Long> directoryIdList  = new ArrayList<>();
        List<Directory> rootDirectoryList = directoryService.queryListById(parent_id);
        try {
            List<Model> allModelList = modelService.findAllModel();
            if(parent_id != null && parent_id != 0 && rootDirectoryList.size() >0){
                //仅有一个directory
                if(rootDirectoryList.size() >0){
                    Directory oneDirectory = rootDirectoryList.get(0);
                    getModelTree(oneDirectory.getId(),allDirectory,directoryIdList);
                    directoryIdList.add(oneDirectory.getId());
                }
                for (Long id : directoryIdList) {
                    for (Model model: allModelList) {
                        if(model.getDirectoryId() == id){
                            if(model.getParentId() == 0){
                                searchModel.add(model);
                            }

                        }
                    }
                }


//                for(int  j= 0; j<= rootDirectoryList.size() -1; j++){
//                    for (Model model: allModelList) {
//                        if(model.getDirectoryId() == rootDirectoryList.get(j).getId()){
//                            searchModel.add(model);
//                        }
//                    }
//                }
            }
      //      if(parent_id != null  && rootDirectoryList.size() >0){
            if(parent_id == 0){
                for (Model model: allModelList) {
                    if(model.getParentId() == 0) {
                        searchModel.add(model);
                    }
                }
            }
            for (int i = 0; i <= searchModel.size() -1; i++) {
                ModelWeb modelWeb = new ModelWeb();
                modelWeb.setIndex(searchModel.get(i).getId());
                modelWeb.setName(searchModel.get(i).getName());
//                modelWeb.setImageUrl("../../assets/test1.png");
                if(searchModel.get(i).getDiagramSvgPath() != null && searchModel.get(i).getDiagramSvgPath() != ""){
                    modelWeb.setImageUrl("http://gogs.modelica-china.com:8080/FileLibrarys"+searchModel.get(i).getDiagramSvgPath().substring(7));
                }
                modelWeb.setDiscription(searchModel.get(i).getDiscription());
                modelWeb.setType(searchModel.get(i).getType());
    //            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(modelWeb);
                repositoryModelList.add(modelWeb );
            }
        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("repositories",repositoryModelList);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/modelVariable",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject modelVariable(@RequestParam(value = "modelId",required = false)String modelId,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //获取model对象
        Model model =  new Model();
        List<Variable> variableList = new ArrayList<>();
        ModelWeb modelWeb = new ModelWeb();
        try {
            model = modelService.queryModelById(Long.parseLong(modelId));
            variableList = variableService.queryListByModelId(Long.parseLong(modelId));
            User user = userService.queryUserById(model.getUserId());
            List<Directory> directoryList = directoryService.queryListById(model.getDirectoryId());
            modelWeb.setDirectoryParentId(directoryList.get(0).getParentId());
            modelWeb.setIndex(Long.parseLong(modelId));
            modelWeb.setName(model.getName());
            modelWeb.setType(model.getType());
            modelWeb.setClasses(model.getClasses());
            modelWeb.setImport(model.getImport());
            modelWeb.setExtends(model.getExtends());
            modelWeb.setUserName(user.getUserName());
            modelWeb.setDiscription(model.getDiscription());
            if(model.getDiagramSvgPath() != null && model.getDiagramSvgPath() != ""){
                modelWeb.setDiagramSvgPath("http://gogs.modelica-china.com:8080/FileLibrarys"+model.getDiagramSvgPath().substring(7));
            }
            if(model.getIconSvgPath() != null && model.getIconSvgPath() != ""){
                modelWeb.setIconSvgPath("http://gogs.modelica-china.com:8080/FileLibrarys"+model.getIconSvgPath().substring(7));
            }
            if(model.getInfoTextPath() != null && model.getInfoTextPath() != ""){
                modelWeb.setInfoTextPath("http://gogs.modelica-china.com:8080/FileLibrarys"+model.getInfoTextPath().substring(7));
            }
        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("tableData",variableList);
        jo.put("form", modelWeb);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/treeModel",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject treeModel(@RequestParam(value = "modelId",required = false)Long modelId,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //查询到所有的model
        List<Model> allModel = modelService.findAllModel();
        //过滤后的modelList
        List<Model> searchModel = new ArrayList<>();
        //树子节点所有id
        List<Long> modelIdList = new ArrayList<>();
        TreeObj treeObj = new TreeObj();
        //返回一个Tree数组对象
        List<TreeObj> treeObjList = new ArrayList<>();
        try {
            //获取根节点模型
            Model rootModel = modelService.queryModelById(modelId);
            //获取树节点的所有id
//            getModelChildTree(rootModel.getId(), allModel,modelIdList);
//            modelIdList.add(modelId);
//            //所需要的ModelTree
//            for (Long id: modelIdList) {
//                for (Model model: allModel) {
//                   if(id == model.getId()) {
//                       searchModel.add(model);
//                   }
//                }
//            }
            //子节点
            List<TreeObj> treeChild = new ArrayList<>();
            treeObj.setId(rootModel.getId());
            treeObj.setLabel(rootModel.getName());
            getModelChildTree(modelId,allModel,treeChild);
            treeObj.setChildren(treeChild);
            treeObjList.add(treeObj);

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
        jo.put("data",treeObjList);
        return (JSONObject) JSONObject.toJSON(jo);
    }



    //获取点击树后所有的模型对象
    public static void getModelTree(Long directoryId,List<Directory> allDirectory,List<Long> directoryIdList){
        for(int i= 0; i< allDirectory.size() ; i++){
            if(allDirectory.get(i).getParentId() == directoryId){
                directoryIdList.add(allDirectory.get(i).getId());
                getModelTree(allDirectory.get(i).getId(),allDirectory,directoryIdList);
            }
        }
    }

    //获取model树所有子节点的id
    public static void getModelChildTree(Long modelId,List<Model> allModel,List<TreeObj> treeChild ){
        for(int i=0; i<allModel.size(); i++){
            if(allModel.get(i).getParentId() == modelId){
                 TreeObj treeObj = new TreeObj();
                 treeObj.setId(allModel.get(i).getId());
                 treeObj.setLabel(allModel.get(i).getName());
                 treeChild.add(treeObj);
            }
        }
        if( treeChild != null){
            for (TreeObj treechild: treeChild) {
                getModelChildTree(treechild.getId(),allModel,treechild.getChildren());
            }
        }

    }

    /*
    * 用来解析xml的参数存储
    * */
    public  void analysis(Map<String,Object> xmlData,Model model,Map<String,Long> compName,Map<String,Long> variableId){
        //存储变量的parentName 和组件id（包含变量的组件）
        Map<String,Long> variableName = new HashMap<>();
        if(xmlData.get("IsVariable").equals("True")){
            Variable variable = new Variable();
            variable.setModelId(model.getId());
            //判断这个组件是否需要插入到数据库
            doSet(xmlData,variable);
//          int variableAdd = variableService.add(variable);
           variableName.put(variable.getParentName(), (long) -1);
            for (Map.Entry<String,Long> varName : variableName.entrySet()) {
                String variableArr[] = varName.getKey().split("\\;");
                //用来比较的变量父类名
                String varCompare = "";
                for(int i= 0;i< variableArr.length -1; i++){
                    varCompare += variableArr[i]+";";
                }
                for (Map.Entry<String,Long> comp: compName.entrySet()) {
                    if(comp.getKey().equals(varCompare.substring(0,varCompare.length()-1))){
                        varName.setValue(comp.getValue());
                        variable.setComponnetId(comp.getValue());
                     //   int variableUp = variableService.update(variable);
                        int variableAdd = variableService.add(variable);
                    }
                }
            }

        }
        if(xmlData.get("IsVariable").equals("False")){
               Component component = new Component();
               component.setCurrentModelId(model.getId());
               component.setCreateTime(nowDate);
               doComponentSet(xmlData,component);
               boolean componentResult = componentService.add(component);
               long index_last_id = componentService.selectId();
               compName.put(component.getParentName(),index_last_id);
        }

    }

    /**
     * 获取到参数插入到对象中
     */
    public static void doSet(Map<String,Object> xmlData,Variable variable){
        if (xmlData.get("Name") != null ) {
            variable.setName((String) xmlData.get("Name"));
        }
        if (xmlData.get("Type") != null) {
            if ("True".equals(xmlData.get("IsArray"))) {
                variable.setType(VariableType.getValueByKey(xmlData.get("Type")+"[]"));
            }else {
                variable.setType(VariableType.getValueByKey((String) xmlData.get("Type")));
            }
        }
        if (xmlData.get("Value") != null) {
            variable.setDefaultValue((String) xmlData.get("Value"));
        }
        if (xmlData.get("Unit") != null) {
            variable.setUnits((String) xmlData.get("Unit"));
        }
        if (xmlData.get("Min") != null) {
            variable.setLowerBound((String) xmlData.get("Min"));
        }
        if (xmlData.get("Max") != null) {
            variable.setUpperBound((String) xmlData.get("Max"));
        }
        if (xmlData.get("IsParameter") != null) {
            if ("True".equals(xmlData.get("IsParameter"))) {
                variable.setIsParam(1);
            } else {
                variable.setIsParam(0);
            }
        }
        if (xmlData.get("parentName") != null) {
            variable.setParentName((String) xmlData.get("parentName"));
        }
        variable.setCreateTime(new Date());
    }

    //解析组件
    public void analysisComponent(HashMap<String, Object> map, Model model,Map<String,Long> compName,Map<String,Long> variableId){
        //组件内容
        List<HashMap<String,Object>>  componentList = new ArrayList<>();
        //获取组件内容插入到数据库
        componentList = (List<HashMap<String, Object>>) map.get("componentVar");
        if(componentList != null){
            HashMap<String, Object> componentMap = componentList.get(0);
            analysis(componentMap,model,compName,variableId);
        }
        else{
            return;
        }
        //子组件内容
//        Map<String,Object> componentChild = new HashMap<>();
//        componentChild = (HashMap<String, Object>) map.get("component");
        analysisComponent((HashMap<String, Object>) map.get("component"),model,compName,variableId);
    }

    public void doComponentSet(Map<String,Object> xmlData,Component component){
        if (xmlData.get("Name") != null ) {
            component.setName((String) xmlData.get("Name"));
        }
        if (xmlData.get("Type") != null) {
            component.setType((String) xmlData.get("Type"));
        }
        if (xmlData.get("Modification") != null) {
            component.setModification((String) xmlData.get("Modification"));
        }
        if (xmlData.get("parentName") != null) {
            component.setParentName((String) xmlData.get("parentName"));
        }
        }

}
