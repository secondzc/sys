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
//        List<DirectoryAuth> directoryAuths = directoryAuthMapper.queryByUid(Long.parseLong(map.get("id").toString()));
//        List<Map<String,Object>> modelAuths = new ArrayList<>();
//        for(DirectoryAuth directoryAuth:directoryAuths)
//        {
//            Map<String,Object> directory = directoryMapper.queryMapById(directoryAuth.getDirectoryId());
//            modelAuths.add(directory);
//        }
//
//        map.put("modelAuth",modelAuths);
        List<ModelAuth> modelAuths = modelAuthMapper.queryByUid(Long.parseLong(map.get("id").toString()));
        map.put("modelAuth",modelAuths);


//      map.remove("");
//      map.remove("");
//      map.remove("");
//      map.remove("");
//      map.remove("");
        List<UserAuth>userAuths = userAuthMapper.queryByUid(Long.parseLong(map.get("id").toString()));
        List<Auth>auths = new ArrayList<>();
        for(UserAuth userAuth:userAuths)
        {
            Auth auth =  authMapper.queryById(userAuth.getAuthId());
            auths.add(auth);
        }

        map.put("auths",auths);


    }

}
