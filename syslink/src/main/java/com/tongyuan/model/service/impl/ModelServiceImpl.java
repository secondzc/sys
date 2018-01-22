package com.tongyuan.model.service.impl;

import com.github.pagehelper.PageHelper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.DTO.ModelDto;
import com.tongyuan.model.dao.ModelMapper;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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




/*    @Override
    public void insertModelData(String modelDir, String modelName, String modelPath, String description) {
        //获取根目录
        String parentPath = ResourceUtil.getFileDriectory() + modelDir;
        ResourceUtil.getSubFile(parentPath.substring(0,
                parentPath.length() - 1),parentPath.substring(0,
                parentPath.length() - 1),description);
    }*/


}
