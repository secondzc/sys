package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.DepartmentMapper;
import com.tongyuan.model.domain.Department;
import com.tongyuan.model.service.DepartmentService;
import com.tongyuan.pageModel.EasyuiTreeNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Created by Administrator on 2017/8/23.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 添加部门
     * @param
     * @return
     */
    @Override
    public boolean add(Map<String,Object> map) {
        return this.departmentMapper.add(map);
    }

    @Override
    public boolean delete(long id ){
        return this.departmentMapper.delete(id);
    }

    /**
     * 获取部门
     * @param id
     * @return
     */
    @Override
    public Map<String,Object> getDepartment(long id){
        return  this.departmentMapper.queryDepartmentById(id);
    }


    /**
     * 获取子部门
     * @param id
     * @return
     */
    @Override
    public List<Map<String,Object>> getChildernById(long id)
    {
        List<Map<String,Object>>departments = departmentMapper.queryDepartmentByPid(id);
        return  departments;
    }

    @Override
    public Map<String,Object> getPDept(long id)
    {
        Map<String,Object> map = departmentMapper.getPDept(id);
        return  map;
    }

    @Override
    public List<Map<String,Object>>getAllChildren(long id)
    {
        List<Map<String,Object>>departments = departmentMapper.queryDepartmentByPid(id);
        Iterator<Map<String,Object>> iterator = departments.iterator();
        List<Map<String,Object>>departmentList = new ArrayList<>();
        departmentList.addAll(departments);
        while (iterator.hasNext())
        {
                List<Map<String,Object>>departmentList1 = getAllChildren(Integer.parseInt(iterator.next().get("id").toString()));
                if(departmentList1.size()>0)
                {
                    departmentList.addAll(departmentList1);
                }
        }
        return departmentList;
    }
/**
    public void setChildren (DepartmentModel model)
    {
        List<Department>departments = departmentMapper.queryDepartmentByPid(model.getId());

        List<DepartmentModel>departmentModels = toDeptModelList(departments);
        Iterator<DepartmentModel>iterator = departmentModels.iterator();
        model.setChildren(departmentModels);
        while (iterator.hasNext())
        {
            setChildren(iterator.next());
        }

    }

 **/

    /**
     * 获取已存在数据中排序的最大值，
     * 返回值加一
     * @return

    public Integer max ()
    {
        List<Department>departmentList = departmentMapper.queryDepartmentByPid(0);
       // Department department = departmentList.get(0);
        List<Department>departments = getAllChildren(0);
      //  Iterator<Department>iterator = departments.iterator();
        Integer max = 0;
        for(Department d : departments)
        {
            if(d.getSort()>max)
            {
                max=d.getSort();
            }
        }
            return ++max;
    }

     */


    /**
     * 将List<Department>转换成List<DepartmentModel>
     * @param
     * @return

   public List<DepartmentModel> toDeptModelList(List<Department> departments)
   {
       List<DepartmentModel> departmentModels = new ArrayList<>();
       for(Department department : departments)
       {
           DepartmentModel departmentModel = new DepartmentModel(department);
           departmentModels.add(departmentModel);
       }
       return departmentModels;
   }
     */



   @Override
    public List<Map<String,Object>> list(long id)
    {
        List<Map<String,Object>> list = departmentMapper.queryDeptList(id);
        return list;
    }

    public List<Map<String,Object>> list1(long id)
    {
        List<Map<String,Object>>list = departmentMapper.queryDeptList1(id);
        return list;
    }








}
