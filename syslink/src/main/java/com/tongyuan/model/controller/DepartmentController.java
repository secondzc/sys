package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.dao.DepartmentMapper;
import com.tongyuan.model.domain.Department;
import com.tongyuan.model.domain.Operationlog;
import com.tongyuan.model.service.DepartmentService;
import com.tongyuan.model.service.OperationlogService;
import com.tongyuan.pageModel.EasyuiTreeNode;
import com.tongyuan.tools.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
@RequestMapping("/api/department")
public class DepartmentController extends  BaseController{
    @RequestMapping("/Index")
    public String index()
    {
        return "Department/Index";
    }

 //   @Autowired
 //    DepartmentMapper departmentMapper;

    @Autowired
    private  DepartmentService departmentService;
    @Autowired
    private  OperationlogService operationlogService;

    @RequestMapping("/add")
    @ResponseBody
    public JSONObject addDepart(HttpServletRequest request , HttpServletResponse response, Map<String,Object>map){
    //    deptSetPids(department);
    //    String a = getCurrentUserId(request);
        JSONObject jo = new JSONObject();
        try
        {
       //     department.setSort(departmentService.max());
            boolean index = departmentService.add(map);
            Operationlog operationlog = new Operationlog();
            operationlogService.addLog("添加用户","2",request);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        return (JSONObject) JSONObject.toJSON(jo);
    }
/**
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<Department> tree(Integer id) {
        return departmentMapper.queryDeptList(id);
    }
**/

/**
    @RequestMapping("/tree")
    @ResponseBody
    public JSONObject list(@RequestParam(value = "parent_id",required = false) long  parent_id) {
        JSONObject jo=new JSONObject();
        List<JSONObject> deptModelList = new ArrayList<>();
        Map<String,Object> department = departmentService.getDepartment(1);
        List< Map<String,Object>>departments = departmentService.getChildernById(parent_id);
      //  List< Map<String,Object>>departmentList = departmentService.getAllChildren(1);


        try{
            List< Map<String,Object>> rootDepartmentList = departmentService.list1(parent_id);

            for(Department model : rootDepartmentList){
                DepartmentModel departmentModel = new DepartmentModel(model);
                departmentService.setChildren(departmentModel);
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(departmentModel);
                deptModelList.add(jsonObject );

            }
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",deptModelList);
        return (JSONObject) JSONObject.toJSON(jo);

    }


    @RequestMapping(value = "/root",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject root() {
        JSONObject jo=new JSONObject();
        List<JSONObject> deptModelList = new ArrayList<>();



        try{
            List<Department> rootDepartmentList = departmentService.list1(0);
            for(Department model : rootDepartmentList){
                DepartmentModel departmentModel = new DepartmentModel(model);
                        departmentService.setChildren(departmentModel);
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(departmentModel);
                deptModelList.add(jsonObject );

            }

        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",deptModelList);
        return (JSONObject) JSONObject.toJSON(jo);

    }
 **/


    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject query(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();

        List<Map<String,Object>>depts = new ArrayList<>();
        try{
               depts=departmentService.getChildernById(Integer.parseInt(para));
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",depts);
        return (JSONObject) JSONObject.toJSON(jo);

    }



/**
    @RequestMapping(value = "/children",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject children(@RequestBody String para) {
        JSONObject jo=new JSONObject();
        List<JSONObject> deptModelList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(para);
        long id = jsonObject.getIntValue("id");

        try{
            List<Department> childrenDeptList = departmentService.list1(id);
            for(Department model : childrenDeptList){
                DepartmentModel departmentModel = new DepartmentModel(model);
                //        departmentService.setChildren(departmentModel);
                JSONObject dept = (JSONObject) JSONObject.toJSON(departmentModel);
                deptModelList.add(dept );

            }

        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",deptModelList);
        return (JSONObject) JSONObject.toJSON(jo);

    }



    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "id",required = false)Integer id,
                             HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        try{

            List<Department> departmentList = departmentService.getAllChildren(id);
            departmentService.delete(id);
            Iterator<Department>iterator = departmentList.iterator();
            while (iterator.hasNext())
            {
                departmentService.delete(iterator.next().getId());
            }
   //         for (Department department:departmentList)
//            {
//                departmentService.delete(department.getId());
//            }
       //     rootDirectoryList.get(0).setDeleted(true);
      //      boolean res = directoryService.update(rootDirectoryList.get(0));

        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",id);
        return (JSONObject) JSONObject.toJSON(jo);

    }

 **/


}
