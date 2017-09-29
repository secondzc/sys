package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.tongyuan.model.domain.Operationlog;
import com.tongyuan.model.domain.User;
import com.tongyuan.model.domainmodel.UserModel;
import com.tongyuan.model.service.OperationlogService;
import com.tongyuan.model.service.RoleService;
import com.tongyuan.model.service.UserService;
import com.tongyuan.model.wrapper.GUserWarpper;
import com.tongyuan.model.wrapper.UserRoleWarpper;
import com.tongyuan.model.wrapper.UserWarpper;
import com.tongyuan.tools.DateUtil;
import com.tongyuan.util.EncodePasswd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;



/**
 * Created by yh on 2017/8/31.
 */
@Controller
@RequestMapping("/api/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private OperationlogService operationlogService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getAllUsers(HttpServletRequest request, HttpServletResponse response,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>>userList = new ArrayList<>();
//        List<UserModel>list = userService.getAllUsers();
//        Map<String,Object> a  = userService.queryUserById((long)1);
        List<Map<String,Object>> users = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(para);
        Page<Map<String,Object>> page = new Page<>();
        Map<String,Object> map = new HashMap<String ,Object>();
        map.put("realName",jsonObject.getString("realName"));
        map.put("content",jsonObject.getString("content"));
        map.put("pageIndex",jsonObject.getIntValue("pageIndex"));
        map.put("pageSize",jsonObject.getIntValue("pageSize"));



        try
        {
          users = userService.findAllName();
            userList.addAll(users);
//            List<Map<String,Object>>list = new UserWarpper(users).warp();
            page=userService.findAllUsers(map);



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
        jo.put("user",  new UserWarpper(page).warp());
        jo.put("userName",userList);
        jo.put("total",page.getTotal());
        return (JSONObject) JSONObject.toJSON(jo);
    }

    /**
     * 添加用户
     * @param user
     * @param request
     * @return
     */

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addUser(@RequestBody User user,HttpServletRequest request)
    {
         JSONObject jo = new JSONObject();
         user.setCreateUser(getCurrentRealName(request));
         user.setCreateDate(DateUtil.getCurrentTime());
         user.setDeleted((short) 0);
         user.setDeletable((short)1);
         user.setStatus((short)0);
      //   String []deptArray = user.getDeptArray();
      //   user.setDepartmentId(Integer.parseInt(deptArray[deptArray.length-1]));



         try
        {
         userService.add(user);


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

    @RequestMapping(value = "/edit",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject editUser(HttpServletRequest request,@RequestBody User user)
    {
        JSONObject jo = new JSONObject();
        user.setUpdateUser(getCurrentRealName(request));
        user.setUpdateDate(DateUtil.getCurrentTime());
        try
        {
            userService.update(user);
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


    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteUser(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long id = jsonObject.getIntValue("id");

        try
        {
            userService.delete(id);
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

    @RequestMapping(value = "/deleteUsers",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteUsers(HttpServletRequest request,@RequestBody String para)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        JSONArray ids = jsonObject.getJSONArray("ids");

        try
        {
            for(int i=0;i<ids.size();i++)
            {
                userService.delete(ids.getIntValue(i));
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
