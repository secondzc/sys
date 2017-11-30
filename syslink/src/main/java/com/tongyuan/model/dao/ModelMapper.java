package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-6-17.
 */
@Mapper
public interface ModelMapper {
    Long add(Model model);
    int update(Model model);
    int deleteByIds(String[] ids);
    public List<Model> queryModelList(Map<String, Object> params);
    public Model queryModelByName(String name);
    //根据Name和模型分类Id查找非package模型
    public Model queryByNameAndDir(Map<String, Object> params);
    public List<Model> findAllModel();
    public List<Map<String,Object>> findAllModelMap();
    public List<Model> findRootModel();
    public List<Model> vagueSearchByName(Map<String, Object> params);
    public Model queryModelById(Long id);
    List<Model> queryModelByParId(Long id);
    //查询模型分类下是否存在这个模型
    public Model queryByNameAndDirId(Map<String, Object> params);
}
