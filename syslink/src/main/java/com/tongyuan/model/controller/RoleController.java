package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.service.RoleService;
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
 * Created by yh on 2017/9/8.
 */
@Controller
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;



    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject queryRole()
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> rolelist = new ArrayList<>();
        try
        {
            rolelist = roleService.query();
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
        jo.put("role",rolelist);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        map.put("createDate", DateUtil.getCurrentTime());
        try
        {
            roleService.add(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加角色失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加角色成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/edit",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject edit(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        try
        {
            roleService.update(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","编辑角色失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","编辑角色成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/assign",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject assign(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(para);
        JSONArray jsonArray = jsonObject.getJSONArray("authIds");
        Integer []authIds = new Integer[jsonArray.size()];

        for(int i=0;i<jsonArray.size();i++)
        {
            authIds[i]=jsonArray.getJSONObject(i).getIntValue("authId");
        }

          Integer roleId = jsonObject.getIntValue("roleId");


        try
        {
            roleService.updatePermission(roleId,authIds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","分配权限失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","分配权限成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long id = jsonObject.getIntValue("id");

        try
        {
            roleService.delete(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除角色失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除角色成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/nameExist",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject nameExist(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        String name = jsonObject.getString("name");
        List<Map<String,Object>>roles = new ArrayList<>();
        boolean exist = false;
        try
        {
            roles=roleService.query();
            for(Map<String,Object>map:roles)
            {
                if(name.equalsIgnoreCase(map.get("name").toString()))
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
