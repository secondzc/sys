package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.service.DepartService;
import com.tongyuan.model.wrapper.DepartWarpper;
import com.tongyuan.tools.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/10/25.
 */
@Controller
@RequestMapping("/api/depart")
public class DepartController {

    @Autowired
    private DepartService departService;


    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject queryDepart()
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<>();
        try
        {
            List <Map<String,Object>>depart = departService.query();
            list.addAll(depart);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","获取列表失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","获取列表成功");
        jo.put("depart",new DepartWarpper(list).warp());
     //   jo.put("role",new RoleWarpper(list).warp());
        return (JSONObject) JSONObject.toJSON(jo);
    }




    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
//        permissionItem.setCreateDate(DateUtil.getCurrentTime());
        map.put("createDate", DateUtil.getCurrentTime());
        try
        {
            departService.addDepart(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加部门失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加部门成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();

        try
        {
            departService.updateDepart(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","编辑部门失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","编辑部门成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        Integer id  = jsonObject.getIntValue("id");
        if(id==1)
        {
            jo.put("flag",false);
            jo.put("msg","根部门不能删除！");
            return (JSONObject) JSONObject.toJSON(jo);
        }

        try
        {
            departService.deleteDepart(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除部门失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除部门成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/nameExist",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject nameExist(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        String name = jsonObject.getString("name");
        List<Map<String,Object>> departs = new ArrayList<>();
        boolean exist = false;

        try
        {
             departs=departService.queryAllDeparts();
             for(Map<String,Object>depart:departs)
             {
                 if(depart.get("name").toString().equalsIgnoreCase(name))
                 {
                     exist=true;
                 }
             }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","");
            return jo;
        }
        jo.put("flag",exist);
        jo.put("msg","");
        return (JSONObject) JSONObject.toJSON(jo);
    }
}
