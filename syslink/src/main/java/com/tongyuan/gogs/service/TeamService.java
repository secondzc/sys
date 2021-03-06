package com.tongyuan.gogs.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/30.
 */
public interface TeamService {

    public boolean add(Map<String, Object> map);

    public boolean update(Map<String, Object> map);

    public boolean addTeamUser(Map<String, Object> map);



    public boolean deleteTeamUser(Map<String, Object> map);

    public boolean addTeamMember(Map<String, Object> map);



    public Map<String,Object> queryByTeamId(long teamId);

    public Map<String,Object> queryByTeamName(Map<String, Object> map);


    public List<Map<String,Object>> queryTeamByOrgId(long orgId);

    public Long getIdByName(Map<String, Object> map);

    public Map<String,Object> queryByTeamIdAndUid(Map<String, Object> map);

    public boolean nameExist(Map<String, Object> map);

}
