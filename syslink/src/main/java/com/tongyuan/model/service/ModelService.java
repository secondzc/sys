package com.tongyuan.model.service;

import com.tongyuan.model.domain.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-6-19.
 */
public interface ModelService {
    Long add(Model model);
    int update(Model model);
    int deleteByIds(String[] ids);
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
    //model解析文件目录，插入数据库
  //  public void insertModelData(String modelDir,String modelName,String modelPath,String description);
    public Model queryByNameAndDirId(Map<String, Object> params);
    public List<Model> getNullParId();
}
