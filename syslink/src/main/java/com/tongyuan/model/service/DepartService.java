package com.tongyuan.model.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/10/25.
 */
public interface DepartService {

    public boolean addDepart(Map<String, Object> map);

    public boolean updateDepart(Map<String, Object> map);

    public boolean deleteDepart(Integer id);

    public List<Map<String,Object>> query();

    public List<Map<String,Object>> queryAllDeparts();

    public List<Map<String,Object>> queryByPid(Integer parentId);

    public Map<String,Object> queryById(Integer id);


}
