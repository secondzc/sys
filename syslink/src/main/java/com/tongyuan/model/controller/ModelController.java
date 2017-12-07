package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Star;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.gogs.service.StarService;
import com.tongyuan.gogs.service.WatchService;
import com.tongyuan.model.domain.*;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.enums.VariableType;
import com.tongyuan.model.service.*;
import com.tongyuan.pageModel.ModelWeb;
import com.tongyuan.pageModel.TreeObj;
import com.tongyuan.pageModel.VariableTreeObj;
import com.tongyuan.tools.ServletUtil;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.FileUtils;
import com.tongyuan.util.ModelUtil;
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
public class ModelController extends  BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModelService modelService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private GUserService gUserService;
    @Autowired
    private FileModelService fileModelService;
    @Autowired
    private ResourceUtil resourceUtil;
    @Autowired
    private VariableService variableService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private ModelUtil modelUtil;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private StarService starService;
    @Autowired
    private AuthService authService;

    @Autowired
    private VariableController variableController;

    public void insertData(Map.Entry<String,Map> entry,Map svgPath,Boolean scope,GUser user,FileModel directory,Long directoryId){
        Map<String,Object> xmlMap = entry.getValue();
        Model model = new Model();
        //验证model
     //   Model validateModel = new Model();
        model.setFileId(directory.getId());
        model.setDirectoryId(directoryId);
        model.setParentId(-1);
/*        model.setUserId(nullModel.getUserId());
        model.setScope(nullModel.getScope());
        model.setCreateTime(nullModel.getCreateTime());*/
        model.setUserId(user.getID());
        model.setScope(scope);
        model.setCreateTime(DateUtil.getTimestamp());
        model.setDeleted(false);
        analysisXmlMap(xmlMap,model,svgPath);
        // 修改
        //Model validateModel = modelService.queryModelByName(model.getName());
        Map<String, Object> param = new HashMap<>();
        param.put("fileName",model.getName());
        param.put("directoryId",directoryId);
        Model validateModel = modelService.queryByNameAndDir(param);
        if( validateModel == null){
            modelService.add(model);
        }else{
            model.setLastUpdateTime(DateUtil.getTimestamp());
            model.setId(validateModel.getId());
            modelService.update(model);
        }
        insertVaiable(xmlMap,directoryId);
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
                            if(svgEntry.getKey().equals(entry.getValue()+".mo")){
                                if(svgEntry.getValue().length() <= 2000){
                                    model.setTextInfo(svgEntry.getValue());
                                }
                                else{
                                    model.setTextInfo(svgEntry.getValue().substring(0,1999));
                                }

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
                        model.setImport((String) entry.getValue());
                    }
                }
                else if("Map".equals(type)){
                    Object secondValue =  ((Map)entry.getValue()).get("import");
                    type = decideType(secondValue,type);
                    //当import的数量等于1的时候
                    if("String".equals(type)){
                        if(!StringUtil.isNull(secondValue.toString())){
                            model.setImport(secondValue.toString());
                        }
                    }
                    else if("List".equals(type)){
                        //当import的数量大于1的时候
                        List<String> valueList = (List)secondValue;
                        String importValue="";
                        for(int i= 0;i<valueList.size();i++){
                            type = decideType(valueList.get(i),type);
                            if("String".equals(type)){
                                importValue += valueList.get(i)+",";
                            }
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
                        model.setExtends((String) entry.getValue());
                    }
                }
                else if("Map".equals(type)){
                    Object secondValue =  ((Map)entry.getValue()).get("extends");
                    type = decideType(secondValue,type);
                    //当import的数量等于1的时候
                    if("String".equals(type)){
                        if(!StringUtil.isNull(secondValue.toString())){
                            model.setExtends(secondValue.toString());
                        }
                    }
                    else if("List".equals(type)){
                        //当import的数量大于1的时候
                        List<String> valueList = (List)secondValue;
                        String extendsValue="";
                        for(int i= 0;i<valueList.size();i++){
                            type = decideType(valueList.get(i),type);
                            if("String".equals(type)){
                                extendsValue += valueList.get(i)+",";
                            }
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

    public void insertVaiable(Map<String,Object> xmlMap,Long directoryId) {
        Model model = new Model();
        for (Map.Entry<String, Object> entry : xmlMap.entrySet()) {
            if ("ModelName".equals(entry.getKey())) {
                String type = "";
                //判断value是什么类型
                type = decideType(entry.getValue(), type);
                if ("String".equals(type)) {
                    if (!StringUtil.isNull((String) entry.getValue())) {
                        //修改
                       // model = modelService.queryModelByName((String) entry.getValue());
                        Map<String, Object> param = new HashMap<>();
                        param.put("fileName",(String) entry.getValue());
                        param.put("directoryId",directoryId);
                        model = modelService.queryByNameAndDir(param);
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
                        type = decideType(compList.get(i),type);
                        if("Map".equals(type) && compList.get(i).get("parentName") != null){
                            analysis(compList.get(i),model,compName,variableId);
                        }
                        if("Map".equals(type) && compList.get(i).get("component") != null){
                            type = decideType(compList.get(i).get("component"),type);
                            analysisComponent((HashMap<String, Object>) compList.get(i),model,compName,variableId);
                        }
                    }
                 }
                }
            }
        }
        //刚插入的Component
        List<Component> componentList = componentService.queryListNullComp();
        if(componentList.size() == 0){
            return ;
        }
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

            for(int j = 0; j< componentList.size(); j++){
                //用来判断第二层组件父类
                //定义需要比较的parentName
                String parentName = "";
                String parentNameArr[] = componentList.get(i).getParentName().split("\\;");
                for(int m= 0;m< parentNameArr.length; m++){
                    parentName += parentNameArr[m]+";";
                }
                if(parentName != null && parentName != "" && parentName != ";") {
                    if (parentNameArr.length == 1) {
                        if (parentName.substring(0, parentName.length() - 1).equals(componentList.get(j).getName()) && componentList.get(i).getModelId() == 0 && componentList.get(j).getModelId() == 0  ) {
                            componentList.get(i).setParentId(componentList.get(j).getId());
                        }
                    }
                }
             //   if(parentName != null && parentName != ""){
                if(parentNameArr.length >= 2){
                    if(parentName.substring(0,parentName.length()-1).equals(componentList.get(j).getParentName()+componentList.get(j).getName()) && componentList.get(i).getModelId() == 0 && componentList.get(j).getModelId() == 0){
                        componentList.get(i).setParentId(componentList.get(j).getId());
                    }
                }
            }
//            for (Model modelComp : allModel) {
//                if(modelComp.getName().equals(modelCompName+"."+componentList.get(i).getType())){
//                    componentList.get(i).setModelId(modelComp.getId());
//                }
//                else{
//                    componentList.get(i).setModelId(-1);
//                }
//            }
//            boolean componentResult = componentService.update(componentList.get(i));
        }
        for (int i = 0; i< componentList.size(); i++) {
            for (Model modelComp : allModel) {
                if (modelComp.getName().equals(modelCompName + "." + componentList.get(i).getType())) {
                    componentList.get(i).setModelId(modelComp.getId());
                } else {
                    componentList.get(i).setModelId(-1);
                }
            }
            boolean componentResult = componentService.update(componentList.get(i));
        }
    }

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


    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list(@RequestParam(value = "parent_id",required = false)Long parent_id,
                           @RequestParam(value = "scope",required = false)Boolean scope,
                           @RequestParam(value = "userId",required = false)Long userId,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        List<ModelWeb>  repositoryModelList = new ArrayList<>();
        //过滤后的modelList
        List<Model> searchModel = new ArrayList<>();
        //查询过滤后模型库内的一个组件
//        List<Model> oneOfModel = new ArrayList<>();
        //查询所有direactory
        List<Directory> allDirectory = directoryService.findAllDirectory();
        //查询所有的repository
        List<Repository> allRepository = repositoryService.findAllRepository();
        //查询所有的watch
        List<Watch> allWatch = watchService.findAllWatch();
        //查询所有的star
        List<Star> allStar = starService.findAllStar();
        //存放directory的id
        List<Long> directoryIdList  = new ArrayList<>();
        if(parent_id == null){
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        try {
            List<Directory> rootDirectoryList = directoryService.queryListById(parent_id);
            List<Model> allModelList = modelService.findAllModel();
            if(parent_id != null && parent_id != 0 && rootDirectoryList.size() >0){
                //仅有一个directory
                if(rootDirectoryList.size() >0){
                    Directory oneDirectory = rootDirectoryList.get(0);
                    getModelTree(oneDirectory.getId(),allDirectory,directoryIdList);
                     directoryIdList.add(oneDirectory.getId());
                     //authService.directoryFilter(directoryIdList,getCurrentUserId(request));
                }
                for (Long id : directoryIdList) {
                    for (Model model: allModelList) {
                        if(model.getDirectoryId() == id){
                            if(scope != null){
                                if(model.getParentId() == 0 && model.getScope() == scope ){
                                    searchModel.add(model);
                                }
                            }else{
                                if(model.getParentId() == 0){
                                    searchModel.add(model);
                                }
                            }
                        }
                    }
                }
//                for(int  j= 0; j<= searchModel.size() -1; j++){
//                    for (Model model: allModelList) {
//                        if(model.getParentId() == searchModel.get(j).getId()){
//                            oneOfModel.add(model);
//                            break;
//                        }
//                    }
//                }
            }
      //      if(parent_id != null  && rootDirectoryList.size() >0){
            if(parent_id == 0){
                if(scope != null) {
                    for (Model model : allModelList) {
                        if (model.getParentId() == 0 && model.getScope() == scope) {
                            searchModel.add(model);
                        }
                    }
                }else{
                    for (Model model : allModelList) {
                        if (model.getParentId() == 0 && model.getScope() == true ) {
                            searchModel.add(model);
                        }
                    }
                }
//                for(int  j= 0; j<= searchModel.size() -1; j++){
//                    for (Model model: allModelList) {
//                        if(model.getParentId() == searchModel.get(j).getId()){
//                            oneOfModel.add(model);
//                            break;
//                        }
//                    }
//                }
            }
            for (int i = 0; i <= searchModel.size() -1; i++) {
                ModelWeb modelWeb = new ModelWeb();
                GUser user = gUserService.queryById(searchModel.get(i).getUserId());
                modelWeb.setIndex(searchModel.get(i).getId());
                modelWeb.setTotal(searchModel.size());
                modelWeb.setName(modelUtil.splitName(searchModel.get(i).getName()));
                modelWeb.setRepositoryName(searchModel.get(i).getName().split("\\.")[0]);
                modelWeb.setParentId(searchModel.get(i).getParentId());
                modelWeb.setUserName(user.getLowerName());
                modelWeb.setUserId(user.getID());
                modelWeb.setClasses(searchModel.get(i).getClasses());
                modelWeb.setTextInfo(searchModel.get(i).getTextInfo());
                modelWeb.setDirectoryId(searchModel.get(i).getDirectoryId());
                if(searchModel.get(i).getDiagramSvgPath() != null && searchModel.get(i).getDiagramSvgPath() != ""){
                    modelWeb.setImageUrl("http://syslink.com:8080/FileLibrarys"+searchModel.get(i).getIconSvgPath().substring(7));
                }
                modelWeb.setUploadTime(searchModel.get(i).getCreateTime().getTime());
                modelWeb.setCreateTime(DateUtil.format(searchModel.get(i).getCreateTime(),"yyyy-MM-dd"));
                if(searchModel.get(i).getLastUpdateTime() != null){
                    modelWeb.setUpdateTime(DateUtil.format(searchModel.get(i).getLastUpdateTime(),"yyyy-MM-dd"));
                }
                modelWeb.setDiscription(searchModel.get(i).getDiscription());
                modelWeb.setType(searchModel.get(i).getType());
                modelWeb.setNumberStar(0);
                modelWeb.setNumberWatch(0);
                modelWeb.setAlreadyStar(false);
                modelWeb.setAlreadyWatch(false);

                repositoryModelList.add(modelWeb );
            }
            for (ModelWeb modelWeb : repositoryModelList) {
                for (Repository repository: allRepository) {
                    if(modelWeb.getRepositoryName().equals(repository.getName())){
                        //关注列表
                        List<Watch> watches = new ArrayList<>();
                        for (Watch watch : allWatch){
                            if(repository.getID() == watch.getRepoID()){
                                watches.add(watch);
                            }
                            if(repository.getID() == watch.getRepoID() && modelWeb.getUserId() == watch.getUserID()){
                               modelWeb.setAlreadyWatch(true);
                            }
                        }
                        modelWeb.setNumberWatch(watches.size());
                        //收藏列表
                        List<Star> stars = new ArrayList<>();
                        for (Star star : allStar) {
                            if (repository.getID() == star.getRepoId()){
                                stars.add(star);
                            }
                            if(repository.getID() == star.getRepoId() && modelWeb.getUserId() == star.getUid()){
                                modelWeb.setAlreadyStar(true);
                            }
                        }
                        modelWeb.setNumberStar(stars.size());
                    }
                }
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
            //查询所有的repository
            List<Repository> allRepository = repositoryService.findAllRepository();
            //查询所有的watch
            List<Watch> allWatch = watchService.findAllWatch();
            //查询所有的star
            List<Star> allStar = starService.findAllStar();
            model = modelService.queryModelById(Long.parseLong(modelId));
            variableList = variableService.queryListByModelId(Long.parseLong(modelId));
            GUser user = gUserService.queryById(model.getUserId());
            modelWeb.setUserId(user.getID());
            List<Directory> directoryList = directoryService.queryListById(model.getDirectoryId());
            modelWeb.setDirectoryParentId(directoryList.get(0).getParentId());
            modelWeb.setIndex(Long.parseLong(modelId));
            modelWeb.setName(modelUtil.splitName(model.getName()));
            modelWeb.setType(model.getType());
            modelWeb.setRepositoryName(model.getName().split("\\.")[0]);
            modelWeb.setClasses(model.getClasses());
            modelWeb.setImport(model.getImport());
            modelWeb.setExtends(model.getExtends());
            modelWeb.setUserName(user.getLowerName());
            modelWeb.setDiscription(model.getDiscription());
            modelWeb.setNumberStar(0);
            modelWeb.setNumberWatch(0);
            modelWeb.setAlreadyStar(false);
            modelWeb.setAlreadyWatch(false);
            if(model.getTextInfo() != null){
                modelWeb.setTextInfo(model.getTextInfo().replaceAll("\\n","<\\/br>"));
            }

            if(model.getDiagramSvgPath() != null && model.getDiagramSvgPath() != ""){
                modelWeb.setDiagramSvgPath("http://syslink.com:8080/FileLibrarys"+model.getDiagramSvgPath().substring(7));
            }
            if(model.getIconSvgPath() != null && model.getIconSvgPath() != ""){
                modelWeb.setIconSvgPath("http://syslink.com:8080/FileLibrarys"+model.getIconSvgPath().substring(7));
            }
            if(model.getInfoTextPath() != null && model.getInfoTextPath() != ""){
                modelWeb.setInfoTextPath("http://syslink.com:8080/FileLibrarys"+model.getInfoTextPath().substring(7));
            }
            for (Repository repository: allRepository) {
                if(modelWeb.getRepositoryName().equals(repository.getName())){
                    //关注列表
                    List<Watch> watches = new ArrayList<>();
                    for (Watch watch : allWatch){
                        if(repository.getID() == watch.getRepoID()){
                            watches.add(watch);
                        }
                        if(repository.getID() == watch.getRepoID() && modelWeb.getUserId() == watch.getUserID()){
                            modelWeb.setAlreadyWatch(true);
                        }
                    }
                    modelWeb.setNumberWatch(watches.size());
                    //收藏列表
                    List<Star> stars = new ArrayList<>();
                    for (Star star : allStar) {
                        if (repository.getID() == star.getRepoId()){
                            stars.add(star);
                        }
                        if(repository.getID() == star.getRepoId() && modelWeb.getUserId() == star.getUid()){
                            modelWeb.setAlreadyStar(true);
                        }
                    }
                    modelWeb.setNumberStar(stars.size());
                }
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

//    @RequestMapping(value = "/treeModel",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public JSONObject treeModel(@RequestParam(value = "modelId",required = false)Long modelId,
//                           HttpServletRequest request , HttpServletResponse response){
//        JSONObject jo=new JSONObject();
//        //查询到所有的model
//        List<Model> allModel = modelService.findAllModel();
//        //过滤后的modelList
//        List<Model> searchModel = new ArrayList<>();
//        //树子节点所有id
//        List<Long> modelIdList = new ArrayList<>();
//        TreeObj treeObj = new TreeObj();
//        //返回一个Tree数组对象
//        List<TreeObj> treeObjList = new ArrayList<>();
//        try {
//            //获取根节点模型
//            Model rootModel = modelService.queryModelById(modelId);
//            //获取树节点的所有id
////            getModelChildTree(rootModel.getId(), allModel,modelIdList);
////            modelIdList.add(modelId);
////            //所需要的ModelTree
////            for (Long id: modelIdList) {
////                for (Model model: allModel) {
////                   if(id == model.getId()) {
////                       searchModel.add(model);
////                   }
////                }
////            }
//            //子节点
//            List<TreeObj> treeChild = new ArrayList<>();
//            treeObj.setId(rootModel.getId());
//            treeObj.setLabel(rootModel.getName());
//            getModelChildTree(modelId,allModel,treeChild);
//            treeObj.setChildren(treeChild);
//            treeObjList.add(treeObj);
//
//        }catch(Exception e) {
//            e.printStackTrace();
//            jo.put("status","1");
//            jo.put("code",0);
//            jo.put("msg","ok");
//            return jo;
//        }
//        jo.put("status",1);
//        jo.put("code",0);
//        jo.put("msg","ok");
//        jo.put("data",treeObjList);
//        return (JSONObject) JSONObject.toJSON(jo);
//    }


    @RequestMapping(value = "/treeModel",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject treeModel(@RequestParam(value = "modelId",required = false)Long modelId,
                           HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo=new JSONObject();
        //在这个模型下的所有model（modelList）
        List<VariableTreeObj> modelTreeList = new ArrayList<>();
        List<VariableTreeObj> modelList = new ArrayList<>();
        try {
            //查询到所有的model
            List<Model> allModel = modelService.findAllModel();
            variableController.getSearchModel(modelId,allModel,modelTreeList,modelList);
        }catch(Exception e) {
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","error");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",modelTreeList);
        return  jo;
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
                List<TreeObj> childVar = new ArrayList<>();
                treeObj.setChildren(childVar);
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
                for(int i= 0;i< variableArr.length ; i++){
                    varCompare += variableArr[i]+";";
                }
                if(varCompare == "" || varCompare == null || varCompare.equals(";")){
                    int variableAdd = variableService.add(variable);
                }else{
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

        }
        if(xmlData.get("IsVariable").equals("False")){
               Component component = new Component();
               component.setCurrentModelId(model.getId());
               component.setCreateTime(new Date());
               doComponentSet(xmlData,component);
               int componentResult = componentService.add(component);
               long index_last_id = component.getId();
               if(component.getParentName().equals("")){
                   compName.put(component.getName(),index_last_id);
               }
               compName.put(component.getParentName()+component.getName(),index_last_id);
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
                variable.setType((String) xmlData.get("Type"));
            }
        }
        if (xmlData.get("Value") != null) {
            if(xmlData.get("Value").toString().length() > 250){
                variable.setDefaultValue("默认值过长！");
            }else{
                variable.setDefaultValue((String) xmlData.get("Value"));
            }
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
        String type = "";
        if(map.get("component") != null) {
            type = decideType(map.get("component"), type);
            if("Map".equals(type)){
                analysisComponentVar(map, model, compName, variableId);
//               Map<String,Object> stringObjectMap = (Map<String, Object>) map.get("componentVar");
//               if(stringObjectMap.get("parentName") != null){
//                   analysis(stringObjectMap, model, compName, variableId);
//               }
            }
            if ("List".equals(type)) {
                componentList = (List<HashMap<String, Object>>) map.get("component");
                if (componentList != null) {
                    for (HashMap<String, Object> mapVar : componentList) {
                        type = decideType(mapVar, type);
                        if ("Map".equals(type) && mapVar.get("parentName") != null) {
                            analysis(mapVar, model, compName, variableId);
                        }
                        if ("Map".equals(type) && mapVar.get("component") != null) {
                            //       type = decideType(mapVar.get("component"),type);
                            if (mapVar.get("componentVar") != null) {
                                analysisComponentVar(mapVar, model, compName, variableId);
                            } else {
                                analysisComponent(mapVar, model, compName, variableId);
                            }

                        }
                    }
                }
            }
        }
    }

    public void doComponentSet(Map<String,Object> xmlData,Component component){
        if (xmlData.get("Name") != null ) {
            component.setName((String) xmlData.get("Name"));
        }
        if (xmlData.get("Type") != null) {
            component.setType((String) xmlData.get("Type"));
        }
        if (xmlData.get("Modification") != null) {
            if(xmlData.get("Modification").toString().length() > 200){
                component.setModification("重定义过长！");
            }else{
                component.setModification((String) xmlData.get("Modification"));
            }

        }
        if (xmlData.get("parentName") != null) {
            component.setParentName((String) xmlData.get("parentName"));
        }
        }

        //组件存在conpent和componentVar同时存在
    public void  analysisComponentVar(HashMap<String, Object> map, Model model,Map<String,Long> compName,Map<String,Long> variableId){
                //组件内容
        List<HashMap<String,Object>>  componentList = new ArrayList<>();
        //获取组件内容插入到数据库
        componentList = (List<HashMap<String, Object>>) map.get("componentVar");
        if(componentList != null){
            HashMap<String, Object> componentMap = componentList.get(0);
            //修改变量的parentName
            if(componentMap.get("parentName") != null){
                reviseVariable(componentMap);
            }
            analysis(componentMap,model,compName,variableId);
        }
        else{
            return;
        }
        //子组件内容
        analysisComponentVar((HashMap<String, Object>) map.get("component"),model,compName,variableId);
    }

     public void reviseVariable(HashMap<String, Object> componentMap){
        String parentName = "";
        String realParentName = "";
        parentName = (String) componentMap.get("parentName");
        String variableArr[] = parentName.split("\\;");
        //得出正真的parentName(实际上在得到parentName时无法规避特殊性)
         for(int i= 0; i<variableArr.length-1; i++){
             realParentName += variableArr[i]+";";
         }
         //修改componentMap
         componentMap.put("parentName",realParentName.substring(0,realParentName.length()-1));
     }

    @RequestMapping(value = "/download",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject download(@RequestParam(value = "modelId",required = false)Long modelId,
                                HttpServletRequest request , HttpServletResponse response){

        JSONObject jo=new JSONObject();
        String realUrl ="";
        try{
            Model model = modelService.queryModelById(modelId);
            String name = modelUtil.splitName(model.getName());
            FileUtils.copyFileCover(model.getModelFilePath(),"C:\\Temp\\FileLibrary\\"+name+"\\"+ name +".dom.xml",true);
            FileUtils.copyFileCover(model.getDiagramSvgPath(),"C:\\Temp\\FileLibrary\\"+name+"\\"+ name +".diagram.svg",true);
            FileUtils.copyFileCover(model.getInfoTextPath(),"C:\\Temp\\FileLibrary\\"+name+"\\"+ name +".info.html",true);
            FileUtils.copyFileCover(model.getIconSvgPath(),"C:\\Temp\\FileLibrary\\"+name+"\\"+ name +".icon.svg",true);
            FileUtils.zipFiles("C:\\Temp\\FileLibrary\\","C:\\Temp\\FileLibrary\\" +name,"C:\\Temp\\FileLibrary\\"+name+"Model");
             realUrl = "http://syslink.com:8080/FileLibrarys/FileLibrary/"+name+"Model";
            }catch(Exception e) {
                e.printStackTrace();
                jo.put("status","1");
                jo.put("code",0);
                jo.put("msg","error");
                return jo;
            }
                jo.put("status",1);
                jo.put("code",0);
                jo.put("msg","ok");
                jo.put("data",realUrl);
                return (JSONObject) JSONObject.toJSON(jo);

            }
    @RequestMapping(value = "/deleted",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleted(@RequestParam(value = "modelId",required = false)Long modelId,
                               HttpServletRequest request , HttpServletResponse response){

        JSONObject jo=new JSONObject();
        try{
            Model model = modelService.queryModelById(modelId);
            model.setDeleted(true);
            modelService.update(model);
        }catch(Exception e) {
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","error");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        return (JSONObject) JSONObject.toJSON(jo);

    }

}

