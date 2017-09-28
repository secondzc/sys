package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/8/23.
 * 部门Dao方法
 */

@Mapper
public interface DepartmentMapper {
    public boolean add(Map<String,Object>map);

    public boolean delete(@Param("id") long id);

    public Map<String,Object> queryDepartmentById(@Param("id") long id);

    public List<Map<String,Object>> queryDeptList(@Param("id") long id);

    public List<Map<String,Object>> queryDeptList1(@Param("id") long id);

    public List<Map<String,Object>> queryDepartmentByPid(@Param("id") long id);

    public Map<String,Object> getPDept(@Param("id") long id);


}
