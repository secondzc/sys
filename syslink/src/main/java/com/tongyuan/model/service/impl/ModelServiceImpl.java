package com.tongyuan.model.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.DTO.ModelDto;
import com.tongyuan.model.dao.ModelMapper;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.pageModel.ModelWeb;
import com.tongyuan.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017-6-19.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ModelServiceImpl implements ModelService{
    @Autowired
    ModelMapper modelMapper;


    @Override
    public Long add(Model model) {
        return this.modelMapper.add(model);
    }

    @Override
    public int update(Model model) {
        return this.modelMapper.update(model);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return this.modelMapper.deleteByIds(ids);
    }

    @Override
    public void delete(Long id) {
        this.modelMapper.delete(id);
    }

    @Override
    public List<Model> queryModelList(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        return this.modelMapper.queryModelList(params);
    }

    @Override
    public Model queryModelByName(String name) {
        return this.modelMapper.queryModelByName(name);
    }

    @Override
    public Model queryByNameAndDir(Map<String, Object> params) {
        return this.modelMapper.queryByNameAndDir(params);
    }

    @Override
    public List<Map<String,Object>> findAllModelMap() {
        return this.modelMapper.findAllModelMap();
    }

    @Override
    public List<Model> findAllModel() {
        return this.modelMapper.findAllModel();
    }

    @Override
    public List<Model> findRootModel() {
        return this.modelMapper.findRootModel();
    }

    @Override
    public List<Model> vagueSearchByName(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        return this.modelMapper.vagueSearchByName(params);
    }

    @Override
    public Model queryModelById(Long id) {
        return this.modelMapper.queryModelById(id);
    }

    @Override
    public List<Model> queryModelByParId(Long id) {
        return this.modelMapper.queryModelByParId(id);
    }

    @Override
    public Model queryByNameAndDirId(Map<String, Object> params) {
        return this.modelMapper.queryByNameAndDirId(params);
    }

    @Override
    public List<Model> getNullParId() {
        return this.modelMapper.getNullParId();
    }

    @Override
    public Long  addOneModel(GUser user, Long directoryId, Boolean scope, Map<String, Object> map,Long iconUrlId) {
        Model model = new Model();
        model.setParentId(0);
        model.setName((String) map.get("name"));
        model.setClasses("Package");
        model.setType((String) map.get("region"));
        model.setDirectoryId(directoryId);
        model.setDiscription((String) map.get("desc"));
        model.setDeleted(false);
        model.setUserId(user.getID());
        model.setCreateTime(DateUtil.getTimestamp());
        model.setLastUpdateTime(DateUtil.getTimestamp());
        model.setScope(scope);
        model.setIconFileId(iconUrlId);
        this.modelMapper.add(model);
        Long modelId = model.getId();
        return modelId;

    }

    /**
     * 查询modelica的所有模型
     * @return 模型列表
     */
    @Override
    public List<Model> findAllModelicaModel() {
        return this.modelMapper.findAllModelicaModel();
    }

    @Override
    public void uploadTimeSort(List<ModelWeb> repositoryModelList) {
        Collections.sort(repositoryModelList, new Comparator<ModelWeb>() {
            @Override
            public int compare(ModelWeb o1, ModelWeb o2) {
                if(o1.getUploadTime() > o2.getUploadTime()){
                    return -1;
                }else if(o1.getUploadTime() < o2.getUploadTime()){
                    return 1;
                }else{
                    return  0;
                }
            }
        });
    }

    @Override
    public void insertModelicaData(Map.Entry<String, JSONObject> entry, Boolean scope, GUser user, Attachment directory, Long directoryId) {
        System.out.print("开始插入数据");
        JSONObject jsonComponents = new JSONObject();
        JSONObject xmlJson = entry.getValue();
        Long modelId = (long)0;
        String modelTypeName = this.getxmlJsonType(xmlJson);
        Model model = new Model();
        model.setDirectoryId(directoryId);
        model.setParentId(-1);
        model.setUserId(user.getID());
        model.setScope(scope);
        model.setCreateTime(DateUtil.getTimestamp());
        model.setLastUpdateTime(DateUtil.getTimestamp());
        model.setDeleted(false);
        model.setType(modelTypeName);
        jsonComponents = analysisXmlJsonModel(xmlJson,model);
        Map<String, Object> param = new HashMap<>();
        param.put("fileName",model.getName());
        param.put("directoryId",directoryId);
        Model validateModel = this.queryByNameAndDir(param);
        if( validateModel == null){
            this.add(model);
            modelId = model.getId();
        }else{
            model.setLastUpdateTime(DateUtil.getTimestamp());
            model.setId(validateModel.getId());
            this.update(model);
            modelId = model.getId();
        }
//        anslysisXmlComponents();
//        insertVaiable(xmlMap,directoryId);

    }

    /**
     * 获取一个xml（最顶层的标识）ModelType
     * @param xmlJson
     * @return modetypename
     */
    @Override
    public String getxmlJsonType(JSONObject xmlJson) {
        Set<String> keys = xmlJson.keySet();
        Iterator iterator = keys.iterator();
        String modelTypeName = "";
        while(iterator.hasNext()){
            modelTypeName = (String) iterator.next();
        }
        return  modelTypeName.split("_")[0];
    }

    @Override
    public JSONObject analysisXmlJsonModel(JSONObject xmlJson, Model model) {
        JSONObject anslysisJson = (JSONObject) xmlJson.values().toArray()[0];
        model.setDiscription((String) anslysisJson.get("ModelDescript"));
        model.setClasses(ModelClasses.getValueByKey((String) anslysisJson.get("ModelClass")));
        JSONObject Imports = (JSONObject) anslysisJson.get("Imports");
        JSONObject Extendses = (JSONObject) anslysisJson.get("Extendses");
        String importValue = "";
        String extendValue = "";
        if(Imports != null ){
            for(int i= 0;i<Extendses.getJSONObject("extends").size();i++){
                extendValue = Extendses.getJSONArray("extends").get(i) +",";
            }
        }
        model.setImport(importValue);
        model.setExtends(extendValue);
        JSONObject jsonComponents = (JSONObject) anslysisJson.get("Components");
        return  jsonComponents;
    }

    @Override
    public void anslysisXmlComponents(JSONObject jsonComponents, Long modelId, Long directoryId) {
        JSONArray componentArr = (JSONArray) jsonComponents.get("component");
        for (int i= 0; i< componentArr.size();i++){
            JSONObject component = (JSONObject) componentArr.get(i);
//            if((JSONObject) ((JSONObject) componentArr.get(i)).get())
        }
    }




/*    @Override
    public void insertModelData(String modelDir, String modelName, String modelPath, String description) {
        //获取根目录
        String parentPath = ResourceUtil.getFileDriectory() + modelDir;
        ResourceUtil.getSubFile(parentPath.substring(0,
                parentPath.length() - 1),parentPath.substring(0,
                parentPath.length() - 1),description);
    }*/


}
