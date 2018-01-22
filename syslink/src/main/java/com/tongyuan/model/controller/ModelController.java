package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Star;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.gogs.service.StarService;
import com.tongyuan.gogs.service.WatchService;
import com.tongyuan.model.DTO.AttachmentDto;
import com.tongyuan.model.DTO.FileJsonArrayDto;
import com.tongyuan.model.DTO.FileTypeDto;
import com.tongyuan.model.domain.*;
import com.tongyuan.model.domain.enums.ConstNodeInstanceStatus;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.enums.VariableType;
import com.tongyuan.model.service.*;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.model.service.ReviewService.StatusChangeService;
import com.tongyuan.pageModel.ModelWeb;
import com.tongyuan.pageModel.TreeObj;
import com.tongyuan.pageModel.VariableTreeObj;
import com.tongyuan.tools.ServletUtil;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.DeleteFileUtil;
import com.tongyuan.util.ModelUtil;
import com.tongyuan.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private VariableController variableController;
    @Autowired
    private StatusChangeService statusChangeService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ModelTypeService modelTypeService;
	@Autowired
    private LogService logService;
	@Autowired
    private FileTypeService fileTypeService;

    public void insertData(Map.Entry<String,Map> entry,Map svgPath,Boolean scope,GUser user,Attachment directory,Long directoryId){
        Map<String,Object> xmlMap = entry.getValue();
        Model model = new Model();
        model.setDirectoryId(directoryId);
        model.setParentId(-1);
        model.setUserId(user.getID());
        model.setScope(scope);
        model.setCreateTime(DateUtil.getTimestamp());
        model.setLastUpdateTime(DateUtil.getTimestamp());
        model.setDeleted(false);
        analysisXmlMap(xmlMap,model,svgPath);
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
//                            if (svgEntry.getKey().equals(entry.getValue()+".diagram.svg")){
//                                model.setDiagramSvgPath(svgEntry.getValue());
//                            }
//                            if (svgEntry.getKey().equals(entry.getValue()+".icon.svg")){
//                                model.setIconSvgPath(svgEntry.getValue());
//                            }
//                            if (svgEntry.getKey().equals(entry.getValue()+".info.html")){
//                                model.setInfoTextPath(svgEntry.getValue());
//                            }
//                            if (svgEntry.getKey().equals(entry.getValue()+".dom.xml")){
//                                model.setModelFilePath(svgEntry.getValue());
//                            }
//                            if(svgEntry.getKey().equals(entry.getValue()+".mo")){
//                                if(svgEntry.getValue().length() <= 2000){
//                                    model.setTextInfo(svgEntry.getValue());
//                                }
//                                else{
//                                    model.setTextInfo(svgEntry.getValue().substring(0,1999));
//                                }
//
//                            }
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
        modelId = componentList.get(0).getModelId();
        //获取组件当前model对象
        Model currModel = modelService.queryModelById(modelId);
        //获取所有的model
        List<Model> allModel = modelService.findAllModelicaModel();
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
        List<Model> allModelList = modelService.findAllModelicaModel();
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
        //查询公有上传申签通过的模型
        List<Model> reviewOfModel = new ArrayList<>();
        //查询所有direactory
        List<Directory> allDirectory = directoryService.findAllDirectory();
        //查询所有的repository
        List<Repository> allRepository = repositoryService.findAllRepository();
        //查询所有的watch
        List<Watch> allWatch = watchService.findAllWatch();
        //查询所有的star
        List<Star> allStar = starService.findAllStar();
        //查询所有的审核模型
        List<ReviewFlowInstance> allReviewFlow = reviewFlowInstanceService.allReviewFlow();
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

                }
                for (Long id : directoryIdList) {
                    for (Model model: allModelList) {
                        if(model.getDirectoryId() == id){
                            if(scope != null){
                                if(model.getParentId() == 0 && model.getScope() == scope ){
                                    searchModel.add(model);
                                    reviewOfModel.add(model);
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
                            reviewOfModel.add(model);
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
            if(scope == null){
                for (Model model :searchModel) {
                    for (ReviewFlowInstance reviewFlowInstance:allReviewFlow) {
                        if(reviewFlowInstance.getModelId() == model.getId() && reviewFlowInstance.getStatus() == 3){
                            reviewOfModel.add(model);
                        }
                    }
                }
            }
            for (int i = 0; i <= reviewOfModel.size() -1; i++) {
                ModelWeb modelWeb = new ModelWeb();
                GUser user = gUserService.queryById(reviewOfModel.get(i).getUserId());
                this.insertModelWeb(modelWeb ,reviewOfModel,i,user);
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
//            if(model.getTextInfo() != null){
//                modelWeb.setTextInfo(model.getTextInfo().replaceAll("\\n","<\\/br>"));
//            }
//
//            if(model.getDiagramSvgPath() != null && model.getDiagramSvgPath() != ""){
//                modelWeb.setDiagramSvgPath("http://"+resourceUtil.getLocalPath()+"/FileLibrarys"+model.getDiagramSvgPath().substring(7));
//            }
//            if(model.getIconSvgPath() != null && model.getIconSvgPath() != ""){
//                modelWeb.setIconSvgPath("http://"+resourceUtil.getLocalPath()+"/FileLibrarys"+model.getIconSvgPath().substring(7));
//            }
//            if(model.getInfoTextPath() != null && model.getInfoTextPath() != ""){
//                modelWeb.setInfoTextPath("http://"+resourceUtil.getLocalPath()+"/FileLibrarys"+model.getInfoTextPath().substring(7));
//            }
            for (Repository repository: allRepository) {
                if(model.getScope()){
                    if((user.getName()+modelWeb.getRepositoryName()).equals(repository.getName())){
                        setWebModelWatchAndStar(allWatch,repository,modelWeb,allStar);
                    }
                }
                else{
                        if(modelWeb.getRepositoryName().equals(repository.getName())){
                            setWebModelWatchAndStar(allWatch,repository,modelWeb,allStar);
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
        jo.put("tableData",variableList);
        jo.put("form", modelWeb);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    public void setWebModelWatchAndStar(List<Watch> allWatch,Repository repository,ModelWeb modelWeb,List<Star> allStar){
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



    @RequestMapping(value = "/treeModel",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject treeModel(@RequestParam(value = "modelId",required = false)Long modelId,
                           HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo=new JSONObject();
        //在这个模型下的所有model（modelList）
        List<VariableTreeObj> modelTreeList = new ArrayList<>();
        List<VariableTreeObj> modelList = new ArrayList<>();
        try {
            //获取根节点模型
            Model rootModel = modelService.queryModelById(modelId);
            VariableTreeObj treeRoot = new VariableTreeObj();
            treeRoot.setId(rootModel.getId());
            treeRoot.setName(rootModel.getName());
            List<VariableTreeObj> rootChild = new ArrayList<>();
            treeRoot.setChildren(rootChild);
            modelTreeList.add(treeRoot);
            //查询到所有的model
            List<Model> allModel = modelService.findAllModelicaModel();
            variableController.getSearchModel(modelId,allModel,rootChild,modelList);
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
               component.setModelId(model.getId());
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
//            String name = modelUtil.splitName(model.getName());
//             realUrl = "http://"+resourceUtil.getLocalPath()+"/FileLibrarys"+model.getModelFilePath().substring(7);
            //下载之前先清除上次下载存在的同名文件
            String modelDir = resourceUtil.getunzipPath()+ ResourceUtil.getXiaZai() + model.getName();
            String modelZip = modelDir+".zip";
            DeleteFileUtil.delete(modelDir);
            DeleteFileUtil.delete(modelZip);
            List<Attachment> attachmentList = attachmentService.getAttachmentsByModelId(modelId);
            if(attachmentList.size() >0){
                realUrl = "http://"+resourceUtil.getLocalPath()+ resourceUtil.getMapped()+ attachmentService.getZipUrl(attachmentList,model).substring(7);
            }
            }catch(Exception e) {
                e.printStackTrace();
                return returnErrorInfo(jo);
            }
                jo.put("data",realUrl);
                return returnSuccessInfo(jo);

            }

    @RequestMapping(value = "/downloadAttach",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject downloadAttach(@RequestParam(value = "attachmentId",required = false)Long attachmentId,
                               HttpServletRequest request , HttpServletResponse response){

        JSONObject jo=new JSONObject();
        String realUrl ="";
        try{
            Attachment attachment = attachmentService.queryById(attachmentId);
             realUrl = "http://"+resourceUtil.getLocalPath()+ resourceUtil.getMapped()+ resourceUtil.getunzipPath().substring(7) + attachment.getFilePath();
        }catch(Exception e) {
            e.printStackTrace();
            return returnErrorInfo(jo);
        }
        jo.put("data",realUrl);
        return returnSuccessInfo(jo);

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
            String title = "删除模型";
            String content = "用户\t"+getUserName()+"\t删除模型\t"+model.getName();
            logService.addLog(title,content);
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

    public void insertCAEData(Map.Entry<String,Map> entry,Map svgPath,Boolean scope,GUser user,Attachment directory,Long directoryId,String modelFilePath){
        Map<String,Object> xmlMap = entry.getValue();
        Model model = new Model();
        long modelId = 0;
        String type = "";
        Map<String,String> generalInfo = new HashMap<>();
        if((Map<String, String>) xmlMap.get("GeneralInfo") != null){
            generalInfo = (Map<String, String>) xmlMap.get("GeneralInfo");
            model.setDirectoryId(directoryId);
            model.setUserId(user.getID());
            model.setScope(scope);
            model.setCreateTime(DateUtil.getTimestamp());
            model.setLastUpdateTime(DateUtil.getTimestamp());
            if (!StringUtil.isNull((String) xmlMap.get("ModelType"))) {
                model.setType((String) xmlMap.get("ModelType"));
            }
            model.setDeleted(false);
            model.setClasses("Model");
//            if(!StringUtil.isNull(generalInfo.get("IconFile"))){
//                model.setIconSvgPath(directory.getRelativeAddress() + "/"+generalInfo.get("IconFile").split("\\/")[generalInfo.get("IconFile").split("\\/").length-1]);
//            }
            if (!StringUtil.isNull(generalInfo.get("Description"))) {
                model.setDiscription(generalInfo.get("Description"));
            }
            if (!StringUtil.isNull(generalInfo.get("Name"))) {
                model.setName(generalInfo.get("Name"));
            }
            Map<String, Object> param = new HashMap<>();
            param.put("fileName",model.getName());
            param.put("directoryId",directoryId);
            Model validateModel = modelService.queryByNameAndDir(param);
            if( validateModel == null){
                modelService.add(model);
                modelId = model.getId();
            }else{
                model.setLastUpdateTime(DateUtil.getTimestamp());
                model.setId(validateModel.getId());
                modelService.update(model);
                modelId = validateModel.getId();
            }
        }
        this.getCAEVariable(xmlMap,type,modelId);
        try {
            Long instanceId = reviewFlowInstanceService.startInstance(modelId);
            statusChangeService.updateStatus(instanceId, "1",ConstNodeInstanceStatus.ACTIVE);
        } catch (SqlNumberException e) {
            e.printStackTrace();
        }
    }

    public void toolInsertCAE(Map<String,Object> xmlMap,boolean isScopeDir,GUser user,Long dirID,String modelFilePath){
        Model model = new Model();
        long modelId = 0;
        String type = "";
        Map<String,String> generalInfo = new HashMap<>();
        model.setDirectoryId(dirID);
        this.insertCAEModel(xmlMap,generalInfo,model,user,isScopeDir, modelFilePath);
        Map<String, Object> param = new HashMap<>();
        param.put("fileName",model.getName());
        param.put("directoryId",dirID);
        Model validateModel = modelService.queryByNameAndDir(param);
        if( validateModel == null){
            modelService.add(model);
            modelId = model.getId();
        }else{
            model.setLastUpdateTime(DateUtil.getTimestamp());
            model.setId(validateModel.getId());
            modelService.update(model);
            modelId = validateModel.getId();
        }
        this.getCAEVariable(xmlMap,type,modelId);
        try {
            Long instanceId = reviewFlowInstanceService.startInstance(modelId);
            statusChangeService.updateStatus(instanceId, "1",ConstNodeInstanceStatus.ACTIVE);
        } catch (SqlNumberException e) {
            e.printStackTrace();
        }

    }



    public void insertCAEVariable(Map<String,String> variable,Long modelId){
        Variable caeVariable = new Variable();
        caeVariable.setModelId(modelId);
        caeVariable.setName(variable.get("Name"));
        caeVariable.setType(variable.get("Type"));
        caeVariable.setUnits(variable.get("Units"));
        caeVariable.setUpperBound(variable.get("Max"));
        caeVariable.setLowerBound(variable.get("Min"));
        caeVariable.setCreateTime(DateUtil.getTimestamp());
        if("".equals(variable.get("parentName"))){
            variableService.add(caeVariable);
        }
    }


    public void insertCAEModel(Map<String, Object> xmlMap, Map<String, String> generalInfo, Model model, GUser user, boolean scope, String modelRelativePath) {
        if ((Map<String, String>) xmlMap.get("GeneralInfo") != null) {
            generalInfo = (Map<String, String>) xmlMap.get("GeneralInfo");
            model.setUserId(user.getID());
            model.setScope(scope);
            model.setCreateTime(DateUtil.getTimestamp());
            model.setLastUpdateTime(DateUtil.getTimestamp());
            if (!StringUtil.isNull((String) xmlMap.get("ModelType"))) {
                model.setType((String) xmlMap.get("ModelType"));
            }
            model.setDeleted(false);
            model.setClasses("Model");
            //这里增加一个icon地址
//            if(!StringUtil.isNull(generalInfo.get("IconFile"))){
//                model.setIconSvgPath(directory.getRelativeAddress() + "/"+generalInfo.get("IconFile").split("\\/")[generalInfo.get("IconFile").split("\\/").length-1]);
//            }
            //模型源文件路径
            String modelFullPath = resourceUtil.getunzipPath()+modelRelativePath;
            //将xml中的base64内容保存为文件
            String clientIconFile = generalInfo.get("IconFile");
            String base64IconData = generalInfo.get("IconData");
            if (!StringUtil.isNull(clientIconFile))
            {
                String serverIconFile = Paths.get(modelFullPath).getParent().toString()
                        +"/"+Paths.get(clientIconFile).getFileName();
                byte[] byIconData = Base64.getDecoder().decode(base64IconData);
                try {
                    OutputStream out = new FileOutputStream(serverIconFile);
                    out.write(byIconData);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                model.setIconSvgPath(serverIconFile);
            }

            if (!StringUtil.isNull(generalInfo.get("Description"))) {
                model.setDiscription(generalInfo.get("Description"));
            }
            if (!StringUtil.isNull(generalInfo.get("Name"))) {
                model.setName(generalInfo.get("Name"));
            }
        }
    }

    public void getCAEVariable( Map<String,Object> xmlMap,String type,long modelId){
        if((Map<String, Object>) xmlMap.get("Files") != null){
            Map<String,Object> filesMap = (Map<String, Object>) xmlMap.get("Files");
            type = decideType(filesMap.get("File"),type);
            if("List".equals(type)){
                List<Map<String,Object>> fileList = (List<Map<String, Object>>) filesMap.get("File");
                for (Map<String,Object> file:fileList) {
                    if(file.get("Variables") != null){
                        Map<String,Object> variables = (Map<String, Object>) file.get("Variables");
                        if(variables.get("Variable") != null){
                            List<Map<String,String>> variableList = (List<Map<String, String>>) variables.get("Variable");
                            for (Map<String,String> variable :variableList) {
                                insertCAEVariable(variable,modelId);
                            }
                        }

                    }
                }
            }
        }
    }


    public void insertModelWeb(ModelWeb modelWeb ,List<Model> reviewOfModel,int i,GUser user){
        modelWeb.setIndex(reviewOfModel.get(i).getId());
        modelWeb.setTotal(reviewOfModel.size());
        modelWeb.setName(modelUtil.splitName(reviewOfModel.get(i).getName()));
        modelWeb.setRepositoryName(reviewOfModel.get(i).getName().split("\\.")[0]);
        modelWeb.setParentId(reviewOfModel.get(i).getParentId());
        modelWeb.setUserName(user.getLowerName());
        modelWeb.setUserId(user.getID());
        modelWeb.setClasses(reviewOfModel.get(i).getClasses());
//        modelWeb.setTextInfo(reviewOfModel.get(i).getTextInfo());
        modelWeb.setDirectoryId(reviewOfModel.get(i).getDirectoryId());
        modelWeb.setType(reviewOfModel.get(i).getType());
        if(!StringUtil.isNull(reviewOfModel.get(i).getIconUrl())){
            modelWeb.setImageUrl("http://"+resourceUtil.getLocalPath()+resourceUtil.getMappedPackage()+resourceUtil.getunzipPath().substring(7)+reviewOfModel.get(i).getIconUrl());
        }
        modelWeb.setUploadTime(reviewOfModel.get(i).getCreateTime().getTime());
        modelWeb.setCreateTime(DateUtil.format(reviewOfModel.get(i).getCreateTime(),"yyyy-MM-dd"));
        if(reviewOfModel.get(i).getLastUpdateTime() != null){
            modelWeb.setUpdateTime(DateUtil.format(reviewOfModel.get(i).getLastUpdateTime(),"yyyy-MM-dd"));
        }
        modelWeb.setDiscription(reviewOfModel.get(i).getDiscription());
        modelWeb.setType(reviewOfModel.get(i).getType());
        modelWeb.setNumberStar(0);
        modelWeb.setNumberWatch(0);
        modelWeb.setAlreadyStar(false);
        modelWeb.setAlreadyWatch(false);
    }

    @RequestMapping(value = "/uploadModelIcon", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void uploadModelIcon(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "directoryId", required = false) Long directoryId,
                                  @RequestParam(value = "scope", required = false) Boolean scope,
                                    HttpServletRequest request, HttpServletResponse response) {
        StandardMultipartHttpServletRequest multiRequest = (StandardMultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();
        Long fileSize = map.get("file").get(0).getSize();
//        String fileNames2[] = map.get("file").get(0).getOriginalFilename().split("\\.");
        String fileName = "";
        byte[] bytes = new byte[0];
//        if (fileNames2.length >= 1) {
//            fileName = fileNames2[0];
//        }
        fileName = map.get("file").get(0).getOriginalFilename();
        //相对地址
        String relativePath = "";
        //绝对路径
        String absolutePath = "";
        relativePath = resourceUtil.getStorePath(name,fileName);
        //把文件写入绝对路径
        absolutePath = resourceUtil.getunzipPath() + relativePath;
        try {
            bytes = map.get("file").get(0).getBytes();
            resourceUtil.writeFile(absolutePath,0,fileSize,bytes);
            //创建一个attachment对象
            attachmentService.addIconOfModel(fileName,relativePath,fileSize);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件写入出错!");
        }
    }


    @RequestMapping(value = "/uploadModelFloder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void uploadModelFloder(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "directoryId", required = false) Long directoryId,
                                  @RequestParam(value = "scope", required = false) Boolean scope,
                                  HttpServletRequest request, HttpServletResponse response) {
        StandardMultipartHttpServletRequest multiRequest = (StandardMultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();
        Long fileSize = map.get("file").get(0).getSize();
        String fileName = "";
        fileName = map.get("file").get(0).getOriginalFilename();
        byte[] bytes = new byte[0];
        //相对地址
        String relativePath = "";
        //绝对路径
        String absolutePath = "";
        //web端相对路径
        String tempRelativePath = "";
        String uniqueIdentifier = "" ;
        try {
            tempRelativePath = modelUtil.getFileContent((FileInputStream) multiRequest.getPart("relativePath").getInputStream());
            uniqueIdentifier = modelUtil.getFileContent((FileInputStream) multiRequest.getPart("identifier").getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        relativePath = resourceUtil.getStorePath(name,fileName);
        //把文件写入绝对路径
        absolutePath = resourceUtil.getunzipPath() + relativePath;
        try {
            bytes = map.get("file").get(0).getBytes();
            resourceUtil.writeFile(absolutePath,0,fileSize,bytes);
            //创建一个attachment对象
            attachmentService.addFileOfModel(fileName,relativePath,fileSize,tempRelativePath,uniqueIdentifier);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件写入出错!");
        }
    }

    @RequestMapping(value = "/uploadFloder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject uploadFloder(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "directoryId", required = false) Long directoryId,
                                  @RequestParam(value = "scope", required = false) Boolean scope,
                             @RequestBody Map<String,Object> map,
                                  HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));

        try {
            //上传的文件夹列表
            List<Attachment> floderList = new ArrayList<>();
            GUser user = gUserService.querListByName(name);
            //图标列表
            List<Attachment> iconList = new ArrayList<>();
            long iconUrlId = 0;
            boolean iconUpdate = (boolean) map.get("showPicture");
            String modelType = (String) map.get("region");
            if(iconUpdate){
                ModelType type = modelTypeService.getByType(modelType);
                iconUrlId = type.getIcon();
            }else{
                iconList = attachmentService.getInsertIcon();
                for (Attachment icon: iconList) {
                    if(map.get("photoName").equals(icon.getName())){
                        iconUrlId = icon.getId();
                    }
                }
            }
            System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
            Long modelId = modelService.addOneModel(user, directoryId, scope, map,iconUrlId);
            //把刚刚上传的图标修改modelId
           if(iconList.size() >0 && !iconUpdate){
               for (Attachment icon: iconList) {
                   if(icon.getId() == iconUrlId){
                       icon.setModelId(modelId);
                       attachmentService.update(icon);
                       break;
                   }
               }
           }
            List<FileJsonArrayDto> fileJsonArrayDtoList = JSONArray.parseArray(map.get("fileLists").toString(), FileJsonArrayDto.class);
            for (FileJsonArrayDto fileJsonDto : fileJsonArrayDtoList) {
                if (fileJsonDto.getFolder()) {
                    attachmentService.addFileJsonDto(fileJsonDto, modelId);
                }
            }
            System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
            //查询刚插入的attachments
            List<Attachment> attachmentFileList = attachmentService.queryNullModelId(modelId);
            System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) + "查询");
            //过滤掉没有提交到表单的文件
            attachmentFileList = attachmentService.getRealFileList(attachmentFileList,fileJsonArrayDtoList,floderList);
            System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date())+ "过滤");
            //更新上传文件的modelId和parentId
            attachmentService.UpdateModelFrame(attachmentFileList,modelId,floderList);
            System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date())+ "修改");
            //删除不必要的文件
            List<Attachment> deleteFileList = attachmentService.getDeleteAttach();
            for (Attachment deleteFile: deleteFileList) {
                attachmentService.delete(deleteFile.getId());
            }
            System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) + "删除");
            if (scope) {
                try {
                    Long instanceId = reviewFlowInstanceService.startInstance(modelId);
                    statusChangeService.updateStatus(instanceId, "1", ConstNodeInstanceStatus.ACTIVE);
                    Map<String,Object> directory = directoryService.queryMapById(directoryId);
                    String title = "上传模型";
                    String content ="用户\t"+user.getName()+"\t上传模型\t"+map.get("name")+"\t到分类\t"+directory.get("name");
                    logService.addLog(title,content);
                } catch (SqlNumberException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("模型目录更新失败");
            return returnErrorInfo(jo);
        }
        return  returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/treeModelCatalog",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject treeModelCatalog(@RequestParam(value = "modelId",required = false)Long modelId,
                                HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<VariableTreeObj> modelCatalogList = new ArrayList<>();
        try{
            //模型的所有文件（包含文件夹）
            List<Attachment> modelFiles = attachmentService.getModelFiles(modelId);
            modelCatalogList = attachmentService.getModelCatalog(modelCatalogList,modelFiles);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取模型目录失败");
        }
        jo.put("data",modelCatalogList);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/getModelDetail",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getModelDetail(@RequestParam(value = "modelId",required = false)Long modelId,
                                     @RequestParam(value = "catalogId",required = false)Long catalogId,
                                     HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<AttachmentDto> modelDetail = new ArrayList<>();
        FileTypeDto fileTypeDto = new FileTypeDto();
        try{
            //查找默认的fileType图标
            fileTypeDto = fileTypeService.getDefaultIcon();
            //模型目录下的文件和文件
            if(catalogId == null){
                List<AttachmentDto> modelFiles = attachmentService.getModelDetail(modelId);
                modelDetail = attachmentService.getModelDetailList(modelFiles,modelId,modelDetail);
            }else{
                List<AttachmentDto> modelFiles = attachmentService.getAttachByParentId(catalogId);
                modelDetail =attachmentService.getDetailListByAttachId(modelFiles,modelDetail,catalogId);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取模型目录失败");
        }
        for (AttachmentDto attachmentDto:modelDetail) {
            if(StringUtil.isNull(attachmentDto.getFileIconUrl())){
                attachmentDto.setFileIconUrl("http://"+resourceUtil.getLocalPath()+ fileTypeDto.getIconPath());
            }else{
                attachmentDto.setFileIconUrl("http://"+resourceUtil.getLocalPath()+ resourceUtil.getMapped()+ resourceUtil.getunzipPath().substring(7) + attachmentDto.getFileIconUrl());
            }
            attachmentDto.setFileSize(ModelUtil.getFileSize(attachmentDto.getSize()));
            attachmentDto.setCreateTime(attachmentDto.getCreateTime().substring(0,10));
            attachmentDto.setName(ModelUtil.getFileName(attachmentDto.getName()));
        }
        jo.put("data",modelDetail);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/getModelFiles",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getModelFiles(@RequestParam(value = "modelId",required = false)Long modelId,
                                     HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<AttachmentDto> modelFiles = new ArrayList<>();
        try{
            //模型目录下的文件和文件
            modelFiles = attachmentService.getModelDetail(modelId);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取模型目录失败");
        }
        for (AttachmentDto attachmentDto:modelFiles) {
            attachmentDto.setName(ModelUtil.getFileName(attachmentDto.getName()));
        }
        jo.put("data",modelFiles);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/getParentFiles",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getParentFiles(@RequestParam(value = "catalogId",required = false)Long catalogId,
                                    HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        Attachment parentAttach = new Attachment();
        List<AttachmentDto> modelDetail = new ArrayList<>();
        try{
            //模型目录下的文件和文件
            parentAttach = attachmentService.getParentAttach(catalogId);
            if(parentAttach == null){
                return returnSuccessInfo(jo);
            }
            List<AttachmentDto> modelFiles = attachmentService.getAttachByParentId(parentAttach.getId());
            modelDetail =attachmentService.getDetailListByAttachId(modelFiles,modelDetail,parentAttach.getId());
            for (AttachmentDto attachmentDto:modelDetail) {
                attachmentDto.setName(ModelUtil.getFileName(attachmentDto.getName()));
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取模型目录失败");
        }
        jo.put("parentAttach",parentAttach);
        jo.put("data",modelDetail);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/moveModel",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject moveModel(@RequestParam(value = "CurrentNodeId",required = false)Long CurrentNodeId,
                                @RequestParam(value = "SelectedNodeId",required = false)Long SelectedNodeId,
                                     HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        try{
            Model model = modelService.queryModelById(CurrentNodeId);
            Map<String,Object> oldDirectory = directoryService.queryMapById(model.getDirectoryId());
            model.setDirectoryId(SelectedNodeId);
            Map<String,Object> newDirectory = directoryService.queryMapById(SelectedNodeId);
            modelService.update(model);
            String title = "移动模型";
            String content = "用户\t"+getUserName()+"\t将模型\t"+model.getName()+"\t从\t"+oldDirectory.get("name").toString()+"\t移动到\t"+newDirectory.get("name").toString();
            logService.addLog(title,content);

        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取模型目录失败");
            return  returnErrorInfo(jo);
        }
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/getAllFiles",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getAllFiles(@RequestParam(value = "scope",required = false)Boolean scope,
            HttpServletRequest request , HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<AttachmentDto> allFiles = new ArrayList<>();
        FileTypeDto fileTypeDto = new FileTypeDto();
        try{
            allFiles = attachmentService.getAllFiles(scope);
            fileTypeDto = fileTypeService.getDefaultIcon();
            for (AttachmentDto attach: allFiles) {
                if(StringUtil.isNull(attach.getFileIconUrl())){
                    attach.setFileIconUrl("http://"+resourceUtil.getLocalPath()+ fileTypeDto.getIconPath());
                }else{
                    attach.setFileIconUrl("http://"+resourceUtil.getLocalPath()+ resourceUtil.getMapped()+ resourceUtil.getunzipPath().substring(7) + attach.getFileIconUrl());
                }
                attach.setName(ModelUtil.getFileName(attach.getName()));
                attach.setFileSize(ModelUtil.getFileSize(attach.getSize()));
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取模型目录失败");
            return  returnErrorInfo(jo);
        }
        jo.put("data", allFiles);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/checkName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject checkName(@RequestParam(value = "checkModelName", required = false) String checkModelName,
                                @RequestParam(value = "directoryId", required = false) String directoryId,
                                HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        Model model = new Model();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("fileName", checkModelName);
            params.put("directoryId", directoryId);
            model = modelService.queryByNameAndDirId(params);
            if (model == null){
                return returnSuccessInfo(jo);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("获取模型类型列表失败");
            return returnErrorInfo(jo);
        }
        return returnErrorInfo(jo);
    }

    @RequestMapping(value = "/deleteModel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteModel(@RequestParam(value = "modelName", required = false) String modelName,
                                @RequestParam(value = "directoryId", required = false) String directoryId,
                                HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        Model model = new Model();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("fileName", modelName);
            params.put("directoryId", directoryId);
            model = modelService.queryByNameAndDirId(params);
            if (model == null){
                return returnErrorInfo(jo);
            }else{
                model.setDeleted(true);
                modelService.update(model);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("获取模型类型列表失败");
            return returnErrorInfo(jo);
        }
        return returnSuccessInfo(jo);
    }





}

