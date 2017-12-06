package com.tongyuan.model.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/10/25.
 */
@Mapper
public interface DepartMapper {

    public  boolean add(Map<String, Object> map);

    public  boolean update(Map<String, Object> map);

    public  boolean deleteById(Integer id);

    public  List<Map<String,Object>> query();

    public  List<Map<String,Object>> queryAllDeparts();

    public  List<Map<String,Object>> queryByPid(Integer parentId);

    public  Map<String,Object> queryById(Integer id);
}
