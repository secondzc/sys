package com.tongyuan.model.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.tongyuan.gogs.controller.UserController;
import com.tongyuan.model.domain.DirectoryAuth;
import com.tongyuan.model.domain.RoleDirectoryAuth;
import com.tongyuan.model.domain.UserDepart;
import com.tongyuan.model.service.DirectoryAuthService;
import com.tongyuan.model.wrapper.GUserWarpper;
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

@Controller
@RequestMapping("/api/DirectoryAuth")
public class DirectoryAuthController extends BaseController{

    @Autowired
    DirectoryAuthService directoryAuthService;

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
          JSONArray jsonArray = JSON.parseArray(para);

        try
        {

           for(int i=0;i<jsonArray.size();i++)
            {
                JSONObject jsonObject  = jsonArray.getJSONObject(i);
                DirectoryAuth directoryAuth = new DirectoryAuth();
                directoryAuth.setUid(jsonObject.getLongValue("uid"));
                directoryAuth.setDirectoryId(jsonObject.getLongValue("directoryId"));
                if(jsonObject.getIntValue("mode")>0)
                {
                    directoryAuth.setMode(jsonObject.getIntValue("mode"));
                    directoryAuthService.add(directoryAuth);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加成功");

        return (JSONObject) JSONObject.toJSON(jo);


    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        List<Map<String,Object>> users = new ArrayList<>();
        long uid = jsonObject.getLongValue("uid");
        long directoryId = jsonObject.getLongValue("directoryId");
        Integer mode = jsonObject.getIntValue("mode");
        try
        {
              directoryAuthService.update(uid,directoryId,mode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","编辑失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","编辑成功");

        return (JSONObject) JSONObject.toJSON(jo);


    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = JSON.parseArray(para);
        try
        {

            for(int i=0;i<jsonArray.size();i++)
            {
                JSONObject jsonObject  = jsonArray.getJSONObject(i);

                directoryAuthService.delete(jsonObject.getLongValue("uid"),jsonObject.getLongValue("directoryId"));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }




    @RequestMapping(value = "/roleAdd",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject roleAdd(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        Integer roleId = jsonObject.getIntValue("roleId");
        JSONObject directory = jsonObject.getJSONArray("directoryAuth").getJSONObject(0);
        List<JSONObject>directoryAuth = new UserController().getDirectoryAuthList(directory);
        try
        {
           directoryAuthService.roleAdd(roleId,directoryAuth);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }
}
