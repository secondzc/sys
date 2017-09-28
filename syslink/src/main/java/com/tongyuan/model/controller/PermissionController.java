package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.PermissionGroup;
import com.tongyuan.model.service.PermissionService;
import com.tongyuan.model.wrapper.AuthWarpper;
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
 * Created by yh on 2017/9/7.
 */
@Controller
@RequestMapping("/api/permission")
public class PermissionController extends BaseController{

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject finGroup()
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<>();
        try
        {
            List <Map<String,Object>>groupList = permissionService.queryGroup();
            list.addAll(groupList);

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
  //      jo.put("group",new AuthWarpper(list).warp());
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/addGroup",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addGroup(@RequestBody PermissionGroup permissionGroup, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        permissionGroup.setCreateDate(DateUtil.getCurrentTime());
        try
        {
            permissionService.addGroup(permissionGroup);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加权限组失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加权限组成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/editGroup",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject editGroup(HttpServletRequest request,@RequestBody PermissionGroup permissionGroup)
    {
        JSONObject jo = new JSONObject();

        try
        {
            permissionService.updateGroup(permissionGroup);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","编辑权限组失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","编辑权限组成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/deleteGroup",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteGroup(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long id = jsonObject.getIntValue("id");

        try
        {
            permissionService.deleteGroup(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除权限组失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除权限组成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/addItem",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addItem(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
//        permissionItem.setCreateDate(DateUtil.getCurrentTime());
        map.put("createDate",DateUtil.getCurrentTime());
        try
        {
            permissionService.addItem(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加权限项失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加权限项成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/editItem",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject editItem(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
//        permissionItem.setCreateDate(DateUtil.getCurrentTime());

        try
        {
            permissionService.editItem(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","编辑权限项失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","编辑权限项成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/deleteItem",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteItem(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long id = jsonObject.getIntValue("id");

        try
        {
            permissionService.deleteItem(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除权限项失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除权限项成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }
}
