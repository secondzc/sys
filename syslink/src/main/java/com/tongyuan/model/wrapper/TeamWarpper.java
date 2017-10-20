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

    public TeamWarpper(Map<String ,Object>map){super(map);}

    @Override
    public void warpTheMap(Map<String, Object> map) {

         List<Map<String,Object>> teamUser = teamUserMapper.queryByTeamId(Long.parseLong(map.get("id").toString()));
         List<Map<String,Object>> simpleUser = new ArrayList<>();
         for(Map<String,Object>map1:teamUser)
         {
             Map<String,Object>map2 = gUserMapper.querySimpleUser(map1).iterator().next();
             simpleUser.add(map2);
         }
         if(map.get("description").toString()==null||map.get("description").toString().equalsIgnoreCase(""))
         {
             map.put("description","该团队暂无描述");
         }
         map.put("member",simpleUser);
         Integer authorize = Integer.parseInt(map.get("authorize").toString());

         if(authorize==1)
         {
             map.put("authDes","该团队拥有对所属仓库的 读取 权限，团队成员可以进行查看和克隆等只读操作。 ");
         }
         if(authorize==2)
         {
             map.put("authDes","该团队拥有对所属仓库的 读取 和 写入 的权限。 ");
         }
        if(authorize==3)
        {
            map.put("authDes","该团队拥有一定的 管理 权限，团队成员可以读取、克隆、推送以及添加其它仓库协作者。 ");
        }
        if(authorize==4)
        {
            map.put("authDes","管理员团队对 所有仓库 具有操作权限，且对组织具有 管理员权限。 ");
        }
    }
}
