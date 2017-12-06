package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.DepartMapper;
import com.tongyuan.model.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/10/25.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class DepartServiceImpl implements DepartService {

    @Autowired
    DepartMapper departMapper ;
    @Override
    public boolean addDepart(Map<String,Object>map)
    {
        return this.departMapper.add(map);
    }
    @Override
    public boolean updateDepart(Map<String,Object>map)
    {
        return this.departMapper.update(map);
    }
    @Override
    public boolean deleteDepart(Integer id)
    {
        return  this.departMapper.deleteById(id);
    }
    @Override
    public List<Map<String,Object>> query()
    {
        return this.departMapper.query();
    }
    @Override
    public List<Map<String,Object>> queryAllDeparts()
    {
        return this.departMapper.queryAllDeparts();
    }

    @Override
    public List<Map<String,Object>> queryByPid(Integer parentId)
    {
        return this.departMapper.queryByPid(parentId);
    }



}
