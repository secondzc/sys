package com.tongyuan.gogs.service;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/25.
 */
public interface OrgService {
    public Page<Map<String,Object>> query(Map<String, Object> map);

    public boolean addOrg(Map<String, Object> map);

    public boolean updateOrg(Map<String, Object> map);

    public boolean deleteOrg(long id);


    public boolean nameExist(Map<String, Object> map);

    public List<Map<String ,Object>> queryOrgUserByOrgId(long orgId);

    public List<Map<String,Object>> getOrgUser(Map<String, Object> map);


    public List<Map<String,Object>> getMyOrg(long uid);

    public Long getOrgIdByName(String name);





    public boolean addOrgUser(Map<String, Object> map);

    public boolean updateOrgUser(Map<String, Object> map);

    public boolean deleteOrgUser(Map<String, Object> map);

    public Map<String,Object>queryByUAndO(Map<String, Object> map);
}
