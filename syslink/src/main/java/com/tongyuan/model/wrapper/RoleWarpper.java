package com.tongyuan.model.wrapper;

import com.tongyuan.model.dao.AuthMapper;
import com.tongyuan.model.dao.RoleAuthMapper;
import com.tongyuan.model.dao.RoleDirectoryAuthMapper;
import com.tongyuan.model.domain.Auth;
import com.tongyuan.model.domain.RoleAuth;
import com.tongyuan.model.domain.RoleDirectoryAuth;
import com.tongyuan.util.SpringContextHolder;

import java.util.*;

/**
 * Created by yh on 2017/9/8.
 */
public class RoleWarpper extends BaseControllerWarpper {
    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    private RoleAuthMapper roleAuthMapper = SpringContextHolder.getBean(RoleAuthMapper.class);

    private AuthMapper authMapper = SpringContextHolder.getBean(AuthMapper.class);

    private RoleDirectoryAuthMapper roleDirectoryAuthMapper = SpringContextHolder.getBean(RoleDirectoryAuthMapper.class);


    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer roleId = Integer.parseInt(map.get("id").toString());
        String createDate = map.get("createDate").toString();
        map.put("createDate",createDate);
        List<Auth> roleAuths = authMapper.queryAuthByRoleId(roleId);
        map.put("permissions",roleAuths);
        List<RoleDirectoryAuth> roleDirectoryAuths = roleDirectoryAuthMapper.queryByRoleId(Integer.parseInt(map.get("id").toString()));
        map.put("directoryAuth",roleDirectoryAuths);
    }
}
