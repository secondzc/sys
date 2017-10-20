package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.dao.OrgUserMapper;
import com.tongyuan.gogs.dao.TeamMapper;
import com.tongyuan.gogs.dao.TeamUserMapper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.OrgService;
import com.tongyuan.gogs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/30.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private OrgService orgService;
    @Autowired
    private GUserService userService;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamUserMapper teamUserMapper;
    @Autowired
    private OrgUserMapper orgUserMapper;
    @Autowired
    private GUserMapper userMapper;

    @Override
    public boolean add(Map<String,Object>map)
    {
        return this.teamMapper.add(map);
    }


    @Override
    public boolean update(Map<String,Object>map)
    {
        return this.teamMapper.update(map);
    }

    @Override
    public Map<String, Object> queryByTeamId(long teamId) {
        return teamMapper.queryByTeamId(teamId);
    }

    /**
     * 查询team
     * @param map orgId，teamName
     * @return
     */
    @Override
    public Map<String, Object> queryByTeamName(Map<String,Object>map)
    {
        return this.teamMapper.queryByTeamName(map);
    }


    @Override
    public List<Map<String,Object>> queryTeamByOrgId(long orgId)
    {

        return this.teamMapper.queryByOrgId(orgId);
    }

    @Override
    public boolean addTeamUser(Map<String,Object>map)
    {
        long teamId  = Long.parseLong(map.get("teamId").toString());
        Map<String,Object> team = teamMapper.queryByTeamId(teamId);
        team.put("numMembers",Long.parseLong(team.get("numMembers").toString())+1);
        map.put("orgId",orgService.getOrgIdByName(map.get("orgName").toString()));

        teamUserMapper.add(map);
        return this.teamMapper.update(team);
    }

    @Override
    public boolean deleteTeamUser(Map<String,Object>map)
    {
        long teamId  = Long.parseLong(map.get("teamId").toString());
        Map<String,Object> team = teamMapper.queryByTeamId(teamId);
        team.put("numMembers",Long.parseLong(team.get("numMembers").toString())-1);
        map.put("orgId",orgService.getOrgIdByName(map.get("orgName").toString()));
        teamUserMapper.deleteTeamUserByTeamId(map);
        Map<String ,Object>orgUser = orgUserMapper.queryByUAndO(map);
        if(Long.parseLong(team.get("authorize").toString())==4)
        {
            orgUser.put("isOwner",0);
        }
        orgUser.put("numTeams",Integer.parseInt(orgUser.get("numTeams").toString())-1);
        return this.teamMapper.update(team)&this.orgUserMapper.update(orgUser);
    }

    /**
     * 获取teamId
     * @param map teamName orgId
     * @return
     */
    @Override
    public Long getIdByName(Map<String,Object>map)
    {
        Map<String ,Object>team = teamMapper.queryByTeamName(map);
        Long teamId = Long.parseLong(team.get("id").toString());
        return teamId;
    }


    @Override
    public boolean addTeamMember(Map<String,Object>map)
    {
        Long orgId = orgService.getOrgIdByName(map.get("orgName").toString());
        Map<String ,Object>queryTeam = new HashMap<>();
        queryTeam.put("orgId",orgId);
        queryTeam.put("teamName",map.get("teamName").toString());
        Long teamId = getIdByName(queryTeam);
        Map<String,Object>team = queryByTeamId(teamId);
        GUser user = userService.querListByName(map.get("name").toString());
        Long uid = user.getID();
        Map<String,Object>teamUser = new HashMap<>();
        teamUser.put("orgId",orgId);
        teamUser.put("teamId",teamId);
        teamUser.put("uid",uid);
        Map<String ,Object>orgUser = orgService.queryByUAndO(teamUser);
        if(orgUser==null)
        {
            Map<String ,Object>addOrgUser = new HashMap<>();
            addOrgUser.put("orgId",orgId);
            addOrgUser.put("uid",uid);
            if(Integer.parseInt(team.get("authorize").toString())==4)
            {
                addOrgUser.put("isOwner",1);
            }
            else
            {
                addOrgUser.put("isOwner",0);
            }

            addOrgUser.put("isPubic",0);
            addOrgUser.put("numTeams",1);
            team.put("numMembers",Integer.parseInt(team.get("numMembers").toString())+1);
            Map<String,Object>org = userService.queryOrgById(orgId);
            org.put("numMembers",Integer.parseInt(org.get("numMembers").toString())+1);
            return this.teamUserMapper.add(teamUser)&this.orgUserMapper.add(addOrgUser)&this.teamMapper.update(team)&this.userMapper.updateOrg(org);
        }
        else
        {
            if(Integer.parseInt(team.get("authorize").toString())==4)
            {
                orgUser.put("isOwner", 1);
            }
            orgUser.put("numTeams",Integer.parseInt(orgUser.get("numTeams").toString())+1);
            team.put("numMembers",Integer.parseInt(team.get("numMembers").toString())+1);


            return this.teamUserMapper.add(teamUser)&this.orgUserMapper.update(orgUser)&this.teamMapper.update(team);
        }
    }


    @Override
    public Map<String,Object> queryByTeamIdAndUid(Map<String ,Object>map)
    {
        return this.teamUserMapper.queryByTeamIdAndUid(map);
    }

    @Override
    public boolean nameExist(Map<String,Object>map)
    {
        Map<String ,Object>originTeam = queryByTeamId(Long.parseLong(map.get("id").toString()));
        if (map.get("name").toString().equalsIgnoreCase(originTeam.get("name").toString()))
        {
            return false;
        }
        else {
            boolean exist = false;
            long orgId = orgService.getOrgIdByName(map.get("orgName").toString());
            List<Map<String,Object>>teams = queryTeamByOrgId(orgId);

            if(teams.size()>0)
            {
                for(Map<String,Object> team:teams)
                {
                    if(map.get("name").toString().equalsIgnoreCase(team.get("name").toString()))
                    {
                        exist = true;
                    }

                }
            }
            return exist;
        }




    }

}
