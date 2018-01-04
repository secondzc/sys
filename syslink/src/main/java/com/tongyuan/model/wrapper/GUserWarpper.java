package com.tongyuan.model.wrapper;

import com.tongyuan.model.dao.*;
import com.tongyuan.model.domain.*;
import com.tongyuan.util.SpringContextHolder;
import com.tongyuan.util.UnixToDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/26.
 */
public class GUserWarpper extends BaseControllerWarpper {

    public GUserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    private UserAuthMapper userAuthMapper = SpringContextHolder.getBean(UserAuthMapper.class);

    private UserDepartMapper userDepartMapper = SpringContextHolder.getBean(UserDepartMapper.class);

    private AuthMapper authMapper = SpringContextHolder.getBean(AuthMapper.class);

    private DepartMapper departMapper = SpringContextHolder.getBean(DepartMapper.class);

    private ModelAuthMapper modelAuthMapper = SpringContextHolder.getBean(ModelAuthMapper.class);

    private DirectoryAuthMapper directoryAuthMapper = SpringContextHolder.getBean(DirectoryAuthMapper.class);

    private DirectoryMapper directoryMapper = SpringContextHolder.getBean(DirectoryMapper.class);

    private UserRoleMapper userRoleMapper =  SpringContextHolder.getBean(UserRoleMapper.class);

    @Override
    public void warpTheMap(Map<String, Object> map) {

      long uid  =  Long.parseLong(map.get("id").toString());

      String createDate = map.get("createdUnix").toString();
      String  date = UnixToDate.TimeStamp2Date(createDate);
      map.put("createdUnix",date);
      map.remove("passwd");
      map.remove("rands");
      map.remove("salt");
      UserDepart userDepart = userDepartMapper.queryByUid(uid);
      if(userDepart!=null)
      {
          Map<String,Object>depart = departMapper.queryById(userDepart.getDepartId());
          List<Integer>path = new DepartWarpper(depart).getDepartPath(depart);
          Collections.reverse(path);
          path.add(Integer.parseInt(depart.get("id").toString()));
          path.remove(0);
          map.put("departId",path);
          map.put("departName",depart.get("name"));

      }

        List<ModelAuth> modelAuths = modelAuthMapper.queryByUid(uid);
        map.put("modelAuth",modelAuths);
        List<DirectoryAuth> directoryAuths = directoryAuthMapper.queryByUid(uid);
        map.put("directoryAuth",directoryAuths);
        List<Auth>auths = new ArrayList<>();
        auths=authMapper.queryAuthByUid(uid);
         map.put("auths",auths);
        List<UserRole> userRoles = userRoleMapper.query(uid);
        List<Integer>roles = new ArrayList<>();
        for(UserRole userRole : userRoles)
        {
            roles.add(userRole.getRoleId());
        }
        map.put("roles",roles);
        map.put("tempMode",0);
        map.put("tempBool",false);


    }

}
