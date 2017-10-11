package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.TeamMapper;
import com.tongyuan.gogs.dao.TeamUserMapper;
import com.tongyuan.gogs.service.OrgService;
import com.tongyuan.gogs.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TeamMapper teamMapper;
    @Autowired
    private TeamUserMapper teamUserMapper;

    @Override
    public Map<String, Object> queryByTeamId(long teamId) {
        return teamMapper.queryByTeamId(teamId);
    }

    @Override
    public Map<String, Object> queryByTeamName(Map<String,Object>map)
    {
        return this.teamMapper.queryByTeamName(map);
    }


    @Override
    public List<Map<String,Object>> getTeam(Map<String,Object>map)
    {
        long orgId = orgService.getOrgIdByName(map);
        return this.teamMapper.query(orgId);
    }

    @Override
    public boolean addTeamUser(Map<String,Object>map)
    {
        long teamId  = Long.parseLong(map.get("teamId").toString());
        Map<String,Object> team = teamMapper.queryByTeamId(teamId);
        team.put("numMembers",Long.parseLong(team.get("numMembers").toString())+1);
        map.put("orgId",orgService.getOrgIdByName(map));

        teamUserMapper.add(map);
        return this.teamMapper.update(team);
    }

    @Override
    public boolean deleteTeamUser(Map<String,Object>map)
    {
        long teamId  = Long.parseLong(map.get("teamId").toString());
        Map<String,Object> team = teamMapper.queryByTeamId(teamId);
        team.put("numMembers",Long.parseLong(team.get("numMembers").toString())-1);
        map.put("orgId",orgService.getOrgIdByName(map));
        teamUserMapper.deleteTeamUser(map);
        return this.teamMapper.update(team);
    }

}
