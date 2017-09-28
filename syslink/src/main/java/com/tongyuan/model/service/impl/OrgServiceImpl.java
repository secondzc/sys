package com.tongyuan.model.service.impl;

import com.tongyuan.model.daoExtra.GUserMapper;
import com.tongyuan.model.daoExtra.OrgUserMapper;
import com.tongyuan.model.daoExtra.TeamMapper;
import com.tongyuan.model.daoExtra.TeamUserMapper;
import com.tongyuan.model.service.OrgService;
import com.tongyuan.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yh on 2017/9/25.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class OrgServiceImpl implements OrgService{
    @Autowired
    private GUserMapper gUserMapper;
    @Autowired
    private OrgUserMapper orgUserMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamUserMapper teamUserMapper;


    @Override
    public List<Map<String,Object>> query(Map<String,Object>map)
    {
        return this.gUserMapper.queryOrg(map);
    }
    @Override
    public boolean addOrg(Map<String,Object>map){

        map.put("lowerName",map.get("name").toString());
        map.put("fullName","");
        map.put("email","");
        map.put("passwd","");
        String a = UUID.randomUUID().toString().replaceAll("-","");
        String b = UUID.randomUUID().toString().replaceAll("-","");
        map.put("rands",a.substring(0,10));
        map.put("salt",b.substring(0,10));
        map.put("loginType",0);
        map.put("loginSource",0);
        map.put("loginName","");
        map.put("type",1);
        map.put("location","");
        map.put("website","");
        map.put("lastRepoVisibility",0);
        map.put("createdUnix", System.currentTimeMillis()/1000L);
        map.put("updatedUnix",System.currentTimeMillis()/1000L);
        map.put("maxRepoCreation",-1);
        map.put("isActive",1);
        map.put("isAdmin",0);
        map.put("allowGitHook",0);
        map.put("allowImportLocal",0);
        map.put("prohibitLogin",0);
        map.put("useCustomAvatar",1);
        map.put("numFollowers",0);
        map.put("numFollowing",0);
        map.put("numStars",0);
        map.put("numRepos",0);
        map.put("description","");
        map.put("numTeams",0);
        map.put("numMembers",0);
        map.put("avatar","");
        map.put("avatarEmail","");
        boolean success = gUserMapper.add(map);

        Map<String,Object> orgUser = new HashMap<>();
        orgUser.put("uid",Long.parseLong(map.get("uid").toString()));
        orgUser.put("orgId",Long.parseLong(map.get("id").toString()));
        orgUser.put("isPublic",0);
        orgUser.put("isOwner",0);
        orgUser.put("numTeams",1);
        success=success&orgUserMapper.add(orgUser);


        Map<String,Object> team = new HashMap<>();
        team.put("orgId",Long.parseLong(map.get("id").toString()));
        team.put("lowerName","owners");
        team.put("name","Owners");
        team.put("description","");
        team.put("authorize",4);
        team.put("numRepos",0);
        team.put("numMembers",0);
        success=success&teamMapper.add(team);


        Map<String,Object> teamUser = new HashMap<>();
        teamUser.put("orgId",Long.parseLong(map.get("id").toString()));
        team.put("teamId",Long.parseLong(team.get("id").toString()));
        team.put("uid",Long.parseLong(map.get("uid").toString()));
        success=success&teamUserMapper.add(teamUser);




        return  success;
    }

    @Override
    public boolean updateOrg(Map<String,Object>map)
    {
        return this.gUserMapper.updateOrg(map);
    }

    @Override
    public boolean deleteOrg(long id)
    {
        return  teamMapper.delete(id)&orgUserMapper.delete(id)&teamUserMapper.delete(id)&gUserMapper.delete(id);
    }


    @Override
    public boolean nameExist(Map<String,Object>map)
    {
        Map<String,Object> mapName = new HashMap<>();

        mapName.put("name",map.get("name").toString());

        List<Map<String,Object>> name  =  gUserMapper.queryOrg(map);

        return name.size()>0;

    }


    @Override
    public List<Map<String,Object>> getOrgUser(Map<String,Object>map)
    {

       List<Map<String,Object>> orgUser = orgUserMapper.query(getOrgIdByName(map));

       for(Map<String,Object>objectMap:orgUser)
       {
            Map<String,Object> simpleUser = gUserMapper.querySimpleUser(Long.parseLong(objectMap.get("uid").toString()));
            objectMap.put("name",simpleUser.get("name"));
            objectMap.put("fullName",simpleUser.get("fullName"));
       }
       return orgUser;

    }


    @Override
    public Long getOrgIdByName(Map<String,Object>map)
    {
        List<Map<String,Object>>list  =  gUserMapper.queryOrg(map);
        if(list.size()>0)
        {
            return Long.parseLong(list.iterator().next().get("id").toString()) ;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Map<String,Object>> getTeam(Map<String,Object>map)
    {
       long orgId = getOrgIdByName(map);
       return this.teamMapper.query(orgId);
    }




}
