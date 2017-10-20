package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.service.AuthService;
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
 * Created by yh on 2017/9/19.
 */

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list()
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<>();
        try
        {
            List <Map<String,Object>>groupList = authService.query();
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
        jo.put("group",new AuthWarpper(list).warp());
   //     jo.put("group",list);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/addAuth",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addGroup(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        map.put("createDate", DateUtil.getCurrentTime());
     //   permissionGroup.setCreateDate(DateUtil.getCurrentTime());
        try
        {
           // permissionService.addGroup(permissionGroup);
            authService.add(map);
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

    @RequestMapping(value = "/editAuth",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject editGroup(HttpServletRequest request,@RequestBody Map<String,Object> map)
    {
        JSONObject jo = new JSONObject();

        try
        {
            authService.update(map);
      //      permissionService.updateGroup(permissionGroup);
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
    public JSONObject deleteGroup(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        Integer id = jsonObject.getIntValue("id");

        try
        {
            authService.delete(id);
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


}
