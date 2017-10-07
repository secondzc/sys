package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.service.OperationlogService;
import com.tongyuan.model.service.RoleService;
import com.tongyuan.model.wrapper.GUserWarpper;
import com.tongyuan.model.wrapper.UserRoleWarpper;
import com.tongyuan.util.EncodePasswd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;


/**
 * Created by yh on 2017/8/31.
 */
@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    private GUserService userService;
    @Autowired
    private OperationlogService operationlogService;
    @Autowired
    private RoleService roleService;



   

    @RequestMapping(value = "/assignRole",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject assignRole(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        Map<String,Object>roles = new HashMap<>();
        roles.put("roles",map.get("assigned").toString());
        roles.put("userId",Integer.parseInt(map.get("userId").toString()));

  //      map.put("roles",map.get("permissionId").toString());
//        permissionItem.setCreateDate(DateUtil.getCurrentTime());

        try
        {
            roleService.updateUserRole(roles);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","分配角色失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","分配角色成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/queryUserRoles",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject queryUserRoles(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<>();
        try
        {
            List <Map<String,Object>>userRoles = roleService.queryUserRole(map);
            list.addAll(userRoles);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","获取用户角色失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","获取用户角色成功");
        jo.put("userRoles",new UserRoleWarpper(list).warp());
        return (JSONObject) JSONObject.toJSON(jo);
    }




    @RequestMapping(value = "/query",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject query(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<>();
        try
        {
               list = userService.queryUser(map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","获取用户列表失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","获取用户列表成功");
        jo.put("users",new GUserWarpper(list).warp());
        return (JSONObject) JSONObject.toJSON(jo);
    }





    @RequestMapping(value = "/addGuser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addGUser(@RequestBody Map<String,Object> map,HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        JSONObject jo = new JSONObject();

        if(userService.nameExist(map))
        {
            jo.put("flag",false);
            jo.put("msg","该用户名已存在");
            return jo;
        }
        else if (userService.emailExist(map))
        {
            jo.put("flag",false);
            jo.put("msg","该邮箱已存在");
            return jo;
        }


        String a = UUID.randomUUID().toString().replaceAll("-","");
        String b = UUID.randomUUID().toString().replaceAll("-","");
        map.put("rands",a.substring(0,10));
        map.put("salt",b.substring(0,10));
        String passwd = map.get("passwd").toString();
        String newPasswd = EncodePasswd.getEncryptedPassword(passwd,b.substring(0,10),10000,50);
        map.put("passwd",newPasswd);


        try
        {

            userService.addGUser(map);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","添加用户失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","添加用户成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/updateGUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject updateGUser(@RequestBody Map<String,Object> map,HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {


        JSONObject jo = new JSONObject();
        map.put("updatedUnix",System.currentTimeMillis()/1000L);
        if(map.get("passwd")!=null)
        {
            if(map.get("passwd").toString().equalsIgnoreCase(""))
            {
                map.remove("passwd");
            }
            else
            {
                String a = UUID.randomUUID().toString().replaceAll("-","");
                String b = UUID.randomUUID().toString().replaceAll("-","");
                map.put("rands",a.substring(0,10));
                map.put("salt",b.substring(0,10));
                String passwd = map.get("passwd").toString();
                String newPasswd = EncodePasswd.getEncryptedPassword(passwd,b.substring(0,10),10000,50);
                map.put("passwd",newPasswd);

            }
        }


        try
        {

            userService.updateUser(map);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","编辑用户失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","编辑用户成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/deleteGUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteGUser(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long id = jsonObject.getIntValue("id");

        try
        {
            userService.deleteUser(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除用户失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除用户成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }




    @RequestMapping(value = "/deleteGUsers",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteGUsers(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        JSONArray ids = jsonObject.getJSONArray("ids");

        try
        {
            for(int i=0;i<ids.size();i++)
            {
                userService.deleteUser(ids.getIntValue(i));
            }
            //   userService.delete(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","删除用户失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","删除用户成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/getUserId",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUserId(HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();


        long uerId  = getCurrentUserId(request);
        jo.put("userId",uerId);
        return (JSONObject) JSONObject.toJSON(jo);
    }

}
