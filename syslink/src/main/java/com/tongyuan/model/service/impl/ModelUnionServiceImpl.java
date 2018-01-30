package com.tongyuan.model.service.impl;

import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.model.dao.ModelUnionMapper;
import com.tongyuan.model.domain.ModelRepo;
import com.tongyuan.model.service.ModelUnionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-12-10.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ModelUnionServiceImpl implements ModelUnionService{

    @Autowired
    ModelUnionMapper modelUnionMapper;
    @Autowired
    RepositoryService repositoryService;
    @Override
    public int add(ModelRepo model) {
        return this.modelUnionMapper.add(model);
    }

    @Override
    public boolean update(ModelRepo model) {
        return this.modelUnionMapper.update(model);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return this.deleteByIds(ids);
    }

    @Override
    public ModelRepo queryUnion(Map<String, Object> param) {
        return this.modelUnionMapper.queryUnion(param);
    }

    @Override
    public void addModelUnion(GUser user, String fileName,Long modelId) {
        //获取仓库的名字
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> unionParam = new HashMap<>();
        param.put("userId", user.getID());
        param.put("repositoryName", fileName.toLowerCase());
        Repository repository = repositoryService.queryByNameAndUserId(param);
        unionParam.put("model_id", modelId);
        if (repository != null) {
            unionParam.put("repository_id", repository.getID());
        }
        ModelRepo modelUnion = this.queryUnion(unionParam);
        if (modelUnion == null) {
            ModelRepo Union = new ModelRepo();
            Union.setModelId(modelId);
            Union.setRepositoryId(repository.getID());
            this.add(Union);
        }
    }
}
