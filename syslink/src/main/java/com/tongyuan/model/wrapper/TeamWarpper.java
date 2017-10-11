package com.tongyuan.model.wrapper;

import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.dao.TeamUserMapper;
import com.tongyuan.util.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/30.
 */
public class TeamWarpper extends BaseControllerWarpper {

    private TeamUserMapper teamUserMapper = SpringContextHolder.getBean(TeamUserMapper.class);

    private GUserMapper gUserMapper = SpringContextHolder.getBean(GUserMapper.class);

    public TeamWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

         List<Map<String,Object>> teamUser = teamUserMapper.queryByTeamId(Long.parseLong(map.get("id").toString()));
         List<Map<String,Object>> simpleUser = new ArrayList<>();
         for(Map<String,Object>map1:teamUser)
         {
             Map<String,Object>map2 = gUserMapper.querySimpleUser(map1).iterator().next();
             simpleUser.add(map2);
         }
         map.put("member",simpleUser);


    }
}
