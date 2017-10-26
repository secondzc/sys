package com.tongyuan.model.wrapper;

import com.tongyuan.model.dao.AuthMapper;
import com.tongyuan.model.dao.DepartMapper;
import com.tongyuan.model.dao.UserAuthMapper;
import com.tongyuan.model.dao.UserDepartMapper;
import com.tongyuan.model.domain.Auth;
import com.tongyuan.model.domain.UserAuth;
import com.tongyuan.model.domain.UserDepart;
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

    @Override
    public void warpTheMap(Map<String, Object> map) {

      String createDate = map.get("createdUnix").toString();
      String  date = UnixToDate.TimeStamp2Date(createDate);
      map.put("createdUnix",date);
      map.remove("passwd");
      map.remove("rands");
      map.remove("salt");
      UserDepart userDepart = userDepartMapper.queryByUid(Long.parseLong(map.get("id").toString()));
      if(userDepart!=null)
      {
          Map<String,Object>depart = departMapper.queryById(userDepart.getDepartId());
          List<Integer>path = new DepartWarpper(depart).getDepartPath(depart);
          Collections.reverse(path);
          path.add(Integer.parseInt(depart.get("id").toString()));

          map.put("departId",path);
          map.put("departName",depart.get("name"));

      }


//      map.remove("");
//      map.remove("");
//      map.remove("");
//      map.remove("");
//      map.remove("");
        List<UserAuth>userAuths = userAuthMapper.queryByUid(Long.parseLong(map.get("id").toString()));
        List<Auth>auths = new ArrayList<>();
        for(UserAuth userAuth:userAuths)
        {
            Auth auth =    authMapper.queryById(userAuth.getAuthId());
            auths.add(auth);
        }

        map.put("auths",auths);


    }

}
