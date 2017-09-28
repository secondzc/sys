package com.tongyuan.model.service;

import com.tongyuan.model.domain.Department;
import com.tongyuan.pageModel.EasyuiTreeNode;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface DepartmentService {
    public boolean add(Map<String,Object> map);
    public boolean delete(long id );

    public List<Map<String,Object>> list(long id);
    public List<Map<String,Object>> list1(long id);

    public Map<String,Object> getDepartment(long id);

    public List<Map<String,Object>> getChildernById(long id);




    public List<Map<String,Object>>getAllChildren(long id);

    /**

    public void setChildren (DepartmentModel model);

    public Integer max ();
     **/

    public Map<String,Object> getPDept(long id);





}
