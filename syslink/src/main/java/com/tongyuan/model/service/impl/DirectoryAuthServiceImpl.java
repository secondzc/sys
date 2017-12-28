package com.tongyuan.model.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.dao.DirectoryAuthMapper;
import com.tongyuan.model.dao.RoleDirectoryAuthMapper;
import com.tongyuan.model.domain.DirectoryAuth;
import com.tongyuan.model.domain.RoleDirectoryAuth;
import com.tongyuan.model.service.DirectoryAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class DirectoryAuthServiceImpl implements DirectoryAuthService{

    @Autowired
    DirectoryAuthMapper directoryAuthMapper;
    @Autowired
    RoleDirectoryAuthMapper roleDirectoryAuthMapper;
    @Override
    public boolean update(long uid,long diretoryId,Integer mode)
    {
        return this.directoryAuthMapper.update(uid,diretoryId,mode);
    }
    @Override
    public boolean add(DirectoryAuth directoryAuth)
    {
        return this.directoryAuthMapper.add(directoryAuth);
    }

    @Override
    public boolean delete(long uid,long diretoryId)
    {
        return this.directoryAuthMapper.delete(uid, diretoryId);
    }

    @Override
    public boolean roleAdd(Integer roleId, List<JSONObject> directoryAuth)
    {

        boolean b= roleDirectoryAuthMapper.deleteByRoleId(roleId);
        boolean a =true;
       for(int i=0;i<directoryAuth.size();i++)
       {
           RoleDirectoryAuth roleDirectoryAuth = new RoleDirectoryAuth();
           roleDirectoryAuth.setRoleId(roleId);
           roleDirectoryAuth.setDirectoryId(directoryAuth.get(i).getLongValue("id"));
           roleDirectoryAuth.setMode(directoryAuth.get(i).getIntValue("mode"));
           if(roleDirectoryAuth.getMode()>0)
           {
               a=a&roleDirectoryAuthMapper.add(roleDirectoryAuth);
           }

       }
       return a&b;
    }
}
