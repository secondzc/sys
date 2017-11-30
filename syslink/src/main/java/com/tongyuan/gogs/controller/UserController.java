package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.domain.UserDepart;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.model.service.OperationlogService;
import com.tongyuan.model.service.RoleService;
import com.tongyuan.model.wrapper.GUserWarpper;
import com.tongyuan.model.wrapper.test;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.Convert;
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.EncodePasswd;
import com.tongyuan.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Guard;
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
        //  Map<String,Object>roles = new HashMap<>();
        //   roles.put("roles",map.get("assigned").toString());

//        roles.put("uid",Integer.parseInt(map.get("uid").toString()));
        String a = map.get("assigned").toString();
        a=a.substring(1,a.length()-1);

        Integer []roles =   Convert.toIntArray(",",a);
        //      map.put("roles",map.get("permissionId").toString());
//        permissionItem.setCreateDate(DateUtil.getCurrentTime());

        try
        {
            roleService.updateUserRoles(Integer.parseInt(map.get("uid").toString()),roles);
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
        List<Integer> userRoles = new ArrayList<>();
        long uid = Long.parseLong(map.get("uid").toString());
        try
        {

            userRoles = roleService.queryUserRoleByUid(uid);
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
        //   jo.put("userRoles",new UserRoleWarpper(list).warp());
        jo.put("userRoles",userRoles);
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();


            List<Map<String,Object>> users = new ArrayList<>();
            Page<UserDepart> userDeaprts = new Page<>();

            try
            {
//            users = userService.queryUser(map);
                userDeaprts = userService.queryUserDepart(map);
                if (userDeaprts.size()>0)
                {
                    for(UserDepart userDepart : userDeaprts)
                    {
                        Map<String,Object> user = userService.queryUserById(userDepart.getUid());
                        if(user!=null)
                        {
                            users.add(user);
                        }
                    }


                    new GUserWarpper(users).warp();
                }
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
            jo.put("users",users);
            jo.put("total",userDeaprts.getTotal());
//        jo.put("users",new GUserWarpper(users).warp());
//        jo.put("total",users.getTotal());
            return (JSONObject) JSONObject.toJSON(jo);


    }



    @RequestMapping(value = "/query",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject query(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<>();
        Page<Map<String,Object>> users = new Page<>();
        try
        {
            users = userService.queryUser(map);
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
        jo.put("users",new GUserWarpper(users).warp());
        jo.put("total",users.getTotal());
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
    @RequestMapping(value = "/querySimpleUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject querySimpleUser(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        List<Map<String,Object>> list = new ArrayList<>();
        try
        {
            list = userService.queyrSimpleUser(jsonObject);
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
        jo.put("simpleUser",list);
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUserInfo( HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        LoginedUserModel loginedUserModel = new LoginedUserModel();
        GUser user = userService.queryById(getUserId());

        try
        {
            loginedUserModel = userService.CreateLoginedUser(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","获取用户信息失败");

            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","获取用户信息成功");
        jo.put("userInfo",loginedUserModel);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/getUserInfoFirst",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUserInfoFirst(@RequestBody String userName, HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(userName);

        LoginedUserModel loginedUserModel = new LoginedUserModel();
        GUser user = userService.querListByName(jsonObject.getString("userName"));
        Cookie c = new Cookie("gogs_awesome",user.getName());
        c.setDomain(".modelica-china.com");
        c.setMaxAge(60);
        c.setPath("/");
        response.addCookie(c);
        String lginIp = IpUtil.getIpAddr(request);
        Date loginDate = DateUtil.getTimestamp();
        userService.updateLoginstate(user.getID(),lginIp,loginDate);


        operationlogService.addLog("登录","登录系统",request);



        try
        {
            loginedUserModel = userService.CreateLoginedUser(user);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","获取用户信息失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","获取用户信息成功");
        jo.put("userInfo",loginedUserModel);
        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/assign",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject assign(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        // JSONObject jsonObject = JSON.parseObject(para);
        JSONObject jsonObject = JSONObject.parseObject(para);

        //  JSONArray jsonArray = JSONArray.parseArray(para);
        JSONArray jsonArray = jsonObject.getJSONArray("authIds");
        Integer []authIds = new Integer[jsonArray.size()];

        for(int i=0;i<jsonArray.size();i++)
        {
            authIds[i]=jsonArray.getJSONObject(i).getIntValue("authId");
        }

        Long uid = jsonObject.getLongValue("uid");
        try
        {
            userService.updateAuth(uid,authIds);
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


    @RequestMapping(value = "/modelAuth",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject modelAuth(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        // JSONObject jsonObject = JSON.parseObject(para);
        JSONObject jsonObject = JSONObject.parseObject(para);

        //  JSONArray jsonArray = JSONArray.parseArray(para);
        JSONArray jsonArray = jsonObject.getJSONArray("directoryIds");
        List<Map<String,Object>> models = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++)
        {
            if(jsonArray.getJSONObject(i).get("modelId")!=null)
            {
                models.add(jsonArray.getJSONObject(i));
            }
        }
//        Long []modeIds = new Long[jsonArray.size()];
//
//        for(int i=0;i<jsonArray.size();i++)
//        {
//            modeIds[i]=jsonArray.getJSONObject(i).getLongValue("id");
//        }


        Long uid = jsonObject.getLongValue("uid");


        try
        {
            userService.updateModelAuth(uid,models);
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



    @RequestMapping(value = "/destory",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject destory(HttpServletRequest request, HttpServletResponse response)
    {

        JSONObject jo = new JSONObject();
        HttpSession session = request.getSession(false);
        session.removeAttribute("uid");
        session.removeAttribute("user");
        System.out.println(session.getId());
        jo.put("sessinoId",session.getId());
        jo.put("aaa",session);



        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/sessionJudge",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject sessionJudge(HttpServletRequest request, HttpServletResponse response)
    {

        JSONObject jo = new JSONObject();

        HttpSession session = request.getSession(false);
//        if(session.getAttribute("uid")!=null)
        Long a = getUserId();
        if(getUserId()>0)
        {

            jo.put("session",true);
        }
        else
        {
            jo.put("session",false);
        }
        jo.put("abc",session);
        jo.put("uid",session.getAttribute("uid"));
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/autoPass",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject autoPass(HttpServletRequest request, HttpServletResponse response,@RequestBody String userName)
    {

        JSONObject jo = new JSONObject();
        HttpSession session = request.getSession(true);
        JSONObject jsonObject = JSON.parseObject(userName);
        GUser user = userService.querListByName(jsonObject.getString("userName"));
        session.setAttribute("uid",user.getID());
        session.setAttribute("uname",user.getName());
        session.setAttribute("user",user);



            jo.put("session",true);

        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/personalInfo",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject personalInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody String para) throws InvalidKeySpecException, NoSuchAlgorithmException {

        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long userId = getUserId();
        Map<String,Object> update = new HashMap<>();
        update.put("id",userId);
        update.put("email",jsonObject.getString("email"));
        update.put("fullName",jsonObject.getString("fullName"));
        GUser user = userService.queryById(userId);
        if(jsonObject.getString("oldPassWd").equalsIgnoreCase(""))
        {

            try
            {
                userService.updateUserInfo(update);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                jo.put("flag",false);
                jo.put("msg","修改失败");
                return jo;
            }
            jo.put("flag",true);
            jo.put("msg","修改成功");
            return (JSONObject) JSONObject.toJSON(jo);
        }
        else
        {
            String passwdCheck = EncodePasswd.getEncryptedPassword(jsonObject.getString("oldPassWd"),user.getSalt(),10000,50);
            if(passwdCheck.equalsIgnoreCase(user.getPasswd()))
            {
                update.put("passwd",jsonObject.getString("newPassWd"));

                try
                {
                    userService.updateUserInfo(update);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    jo.put("flag",false);
                    jo.put("msg","修改失败");
                    return jo;
                }
                jo.put("flag",true);
                jo.put("msg","修改成功");
                return (JSONObject) JSONObject.toJSON(jo);

            }
            else
            {
                jo.put("flag",false);
                jo.put("msg","密码错误");
                return (JSONObject) JSONObject.toJSON(jo);
            }

        }


    }
}
