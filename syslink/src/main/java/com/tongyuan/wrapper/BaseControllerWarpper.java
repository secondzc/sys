package com.tongyuan.model.wrapper;

import com.tongyuan.model.dao.AuthMapper;
import com.tongyuan.model.dao.DepartmentMapper;
import com.tongyuan.model.dao.PermissionItemMapper;
import com.tongyuan.model.domain.Department;
import com.tongyuan.util.SpringContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器查询结果的包装类基类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:49:36
 */
public abstract class BaseControllerWarpper {

    public Object obj = null;


    private DepartmentMapper departmentMapper = SpringContextHolder.getBean(DepartmentMapper.class);
    private PermissionItemMapper permissionItemMapper =SpringContextHolder.getBean(PermissionItemMapper.class);
    private AuthMapper authMapper =SpringContextHolder.getBean(AuthMapper.class);


    public Map<String,Object> getDept(long deptId)
    {
        Map<String,Object> department = departmentMapper.queryDepartmentById(deptId);
        return department;
    }



    public List<String> getDepts (Map<String,Object> department)
    {
        List<String> depts = new ArrayList<>();
        depts.add(new Integer((int) department.get("id")).toString());
        Map<String,Object> pDept  = departmentMapper.queryDepartmentById(Integer.parseInt(department.get("id").toString()));
        if(pDept!=null)
        {
            depts.addAll(getDepts(pDept));

        }
        return depts;
    }

    public String[] toArray(List<String> depts)
    {
        String[] deptArray = {};
        for(int i=0;i<depts.size();i++)
        {
            deptArray[i]=depts.get(i);
        }
        return  deptArray;
    }


    public List<Integer> getDeptIds( Map<String,Object> department)
    {
       Integer id  =  Integer.parseInt(department.get("id").toString());
       List<Integer> deptIds = new ArrayList<>();
       deptIds.add(id);
       Map<String ,Object> pDept  = departmentMapper.getPDept(Integer.parseInt(department.get("pid").toString()));

      return  deptIds;
    }



    public List<Map<String,Object>> getPermissionItem(Map<String,Object>map)
    {
   //    List<Map<String,Object>> list = permissionItemMapper.query(map);
        List<Map<String,Object>> list = authMapper.getChildren(map);
       for (Map<String,Object> objectMap : list)
       {
           objectMap.put("createDate",objectMap.get("createDate").toString());
       }
       return list;
    }


    public BaseControllerWarpper(Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings("unchecked")
    public Object warp() {
        if (this.obj instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) this.obj;
            for (Map<String, Object> map : list) {
                warpTheMap(map);
            }
            return list;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            warpTheMap(map);
            return map;
        } else {
            return this.obj;
        }
    }

    protected abstract void warpTheMap(Map<String, Object> map);




}
