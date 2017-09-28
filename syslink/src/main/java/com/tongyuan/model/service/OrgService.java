package com.tongyuan.model.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/25.
 */
public interface OrgService {
    public List<Map<String,Object>> query(Map<String,Object>map);

    public boolean addOrg(Map<String,Object>map);

    public boolean updateOrg(Map<String,Object>map);

    public boolean deleteOrg(long id);

    public boolean nameExist(Map<String,Object>map);

    public List<Map<String,Object>> getOrgUser(Map<String,Object>map);

    public Long getOrgIdByName(Map<String,Object>map);


    public List<Map<String,Object>> getTeam(Map<String,Object>map);


}
