package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.AuthMapper;
import com.tongyuan.model.dao.DirectoryAuthMapper;
import com.tongyuan.model.dao.RoleAuthMapper;
import com.tongyuan.model.dao.UserAuthMapper;
import com.tongyuan.model.domain.Auth;
import com.tongyuan.model.domain.DirectoryAuth;
import com.tongyuan.model.domain.UserAuth;
import com.tongyuan.model.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yh on 2017/9/19.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthMapper authMapper;
    @Autowired
    UserAuthMapper userAuthMapper;
    @Autowired
    RoleAuthMapper roleAuthMapper;
    @Autowired
    DirectoryAuthMapper directoryAuthMapper;

    @Override
    public boolean add(Map<String,Object> map)
    {
        return this.authMapper.add(map);
    }
    @Override
    public boolean update(Map<String,Object>map)
    {
        return this.authMapper.update(map);
    }

    @Override
    public boolean delete(Integer id)
    {
        return this.authMapper.deleteById(id)&this.authMapper.deleteByPid(id)&this.userAuthMapper.deleteByAuthId(id)&roleAuthMapper.deleteByAuthId(id);
    }

    @Override
    public List<Map<String,Object>> query()
    {
        return  this.authMapper.query();
    }

    @Override
    public List<Map<String,Object>> getChildren(Map<String,Object>map)
    {
        return this.authMapper.getChildren(map);
    }


    @Override
    public void directoryFilter(List<Long> directoryIdList,long uid)
    {
        List<DirectoryAuth> list = directoryAuthMapper.queryByUid(uid);
        List<Long> filter = new ArrayList<>();
        List<Long> remove = new ArrayList<>();
        for(DirectoryAuth directoryAuth : list)
        {
            filter.add(directoryAuth.getDirectoryId());
        }
        for(Long directoryId : directoryIdList)
        {
            if(!filter.contains(directoryId))
            {
               remove.add(directoryId);
            }
        }
        directoryIdList.removeAll(remove);

    }

    @Override
    public Set<Auth> getAuthByUserAuth(Long uid)
    {
        Set<Auth> allAuths = new HashSet<>();
        List<UserAuth> userAuths = userAuthMapper.queryByUid(uid);
        if(userAuths.size()>0)
        {
            for(UserAuth userAuth:userAuths)
            {
                Auth auth = authMapper.queryById(userAuth.getAuthId());
                allAuths.add(auth);
            }
        }
        return allAuths;
    }



}
