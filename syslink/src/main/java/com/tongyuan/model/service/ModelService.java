package com.tongyuan.model.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.DTO.ModelDto;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Model;
import com.tongyuan.pageModel.ModelWeb;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-6-19.
 */
public interface ModelService {
    Long add(Model model);
    int update(Model model);
    int deleteByIds(String[] ids);
    public void delete(Long id);
    public List<Model> queryModelList(Map<String, Object> params);
     Model queryModelByName(String name);
     //根据Name和模型分类Id查找非package模型
     public Model queryByNameAndDir(Map<String, Object> params);
    public List<Model> findAllModel();
    public List<Map<String,Object>> findAllModelMap();
    public List<Model> findRootModel();
    public List<Model> vagueSearchByName(Map<String, Object> params);
    public Model queryModelById(Long id);
    List<Model> queryModelByParId(Long id);
    //查看是否在目录下重名
    public Model queryByNameAndDirId(Map<String, Object> params);
    public List<Model> getNullParId();
    public Long addOneModel(GUser user, Long directoryId, Boolean scope, Map<String,Object> map,Long iconUrlId);
    public List<Model> findAllModelicaModel();
    //对ModelWeb列表进行uploadTime排序
    public void uploadTimeSort(List<ModelWeb> repositoryModelList);
    //插入modelica模型数据
    public void insertModelicaData(Map.Entry<String, JSONObject> entry, Boolean scope, GUser user, Attachment directory, Long directoryId);
    //从xmlJson对象获取xml的类型
    public String  getxmlJsonType(JSONObject xmlJson);
    //解析xmljson最外层的模型层
    public JSONObject analysisXmlJsonModel(JSONObject xmlJson, Model model);
    //解析jsonCpmponent
    public void anslysisXmlComponents(JSONObject jsonComponents,Long modelId,Long directoryId);
}
