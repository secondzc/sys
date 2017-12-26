package com.tongyuan.model.service;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.DirectoryAuth;
import com.tongyuan.model.domain.RoleDirectoryAuth;

import java.util.List;

public interface DirectoryAuthService {

    public boolean add (DirectoryAuth directoryAuth);
    public boolean update(long uid,long diretoryId,Integer mode);
    public boolean delete(long uid,long diretoryId);



    public boolean roleAdd(Integer roleId, List<JSONObject> directoryAuth);

}
