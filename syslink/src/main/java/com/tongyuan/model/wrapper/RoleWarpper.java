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
        List<RoleAuth> roleAuths = roleAuthMapper.queryByRoleId(Integer.parseInt(map.get("id").toString()));
        Set<Integer> parentAuthIds = new HashSet<>();
        List<Auth> auths = new ArrayList<>();
        for(RoleAuth roleAuth:roleAuths)
        {
            Auth auth = authMapper.queryById(roleAuth.getAuthId());
            auths.add(auth);
        }



        /**
        List<Map<String,List<Integer>>> auths = new ArrayList<>();
        for(RoleAuth roleAuth : roleAuths)
        {
            parentAuthIds.add(roleAuth.getParentAuthId());
        }
        for(Integer pid : parentAuthIds)
        {
            List<RoleAuth> roleAuthList = roleAuthMapper.queryByRoleIdAndParentId(roleId,pid);
            List<Integer>authId = new ArrayList<>();
            for(RoleAuth roleAuth: roleAuthList)
            {
                authId.add(roleAuth.getAuthId());
            }
            Auth auth = authMapper.queryById(pid);
            Map<String,List<Integer>> authmap = new HashMap<>();
            authmap.put(auth.getAuthName(),authId);
            auths.add(authmap);
        }
         **/
      //  List<List<Integer>> auths = new ArrayList<>();

     //   List<RoleAuth> roleAuths1 = roleAuthMapper.queryByRoleIdAndParentId(5,14);
     //   map.put("permissions",auths);
        /**
        if(map.get("permissions")!=null&&(!map.get("permissions").equals("[]")&&(!map.get("permissions").equals(""))))
        {
            String a = map.get("permissions").toString();
            String b = a.replaceAll("[\\[\\]]", "");
            String []c = b.split(",");
            Integer []d = new Integer[c.length];
            for(int i = 0;i<c.length;i++)
            {
                d[i] = Integer.parseInt(c[i]);
            }
            Map<String ,Object> map1 = new HashMap<>();
            map.put("permissions",d);


        }
         **/

        map.put("permissions",auths);

        List<RoleDirectoryAuth> roleDirectoryAuths = roleDirectoryAuthMapper.queryByRoleId(Integer.parseInt(map.get("id").toString()));
        map.put("directoryAuth",roleDirectoryAuths);
    }
}
