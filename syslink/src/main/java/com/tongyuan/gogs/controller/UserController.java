package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.domain.Auth;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.model.service.*;
import com.tongyuan.model.wrapper.DepartWarpper;
import com.tongyuan.model.wrapper.GUserWarpper;
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.EncodePasswd;
import com.tongyuan.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private LogService logService;
    @Autowired
    private DepartService departService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthService authService;
    @Value("${defaultPassWord}")
    private String defaultPassWord;








//    @RequestMapping(value = "/queryUserRoles",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public JSONObject queryUserRoles(@RequestBody Map<String,Object> map, HttpServletRequest request)
//    {
//        JSONObject jo = new JSONObject();
//        List<Integer> userRoles = new ArrayList<>();
//        long uid = Long.parseLong(map.get("uid").toString());
//        try
//        {
//            userRoles = roleService.queryUserRoleByUid(uid);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            jo.put("flag",false);
//            jo.put("msg","获取用户角色失败");
//            return jo;
//        }
//        jo.put("flag",true);
//        jo.put("msg","获取用户角色成功");
//        //   jo.put("userRoles",new UserRoleWarpper(list).warp());
//        jo.put("userRoles",userRoles);
//        return (JSONObject) JSONObject.toJSON(jo);
//    }


    @RequestMapping(value = "/list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        Page<Map<String,Object>> users = new Page<>();
        List<Integer>departIds =new ArrayList<>();
        map.put("id",map.get("departId"));
        List<Map<String,Object>>children =  new DepartWarpper(map).getChildren(map);
        for(Map<String,Object>child:children)
        {
            departIds.add(Integer.parseInt(child.get("id").toString()));
        }
        departIds.add(Integer.parseInt(map.get("departId").toString()));

        map.put("departIds",departIds);
        if(map.get("name")==null)
        {
            map.put("name","");
        }

        try
        {
            users=userService.queryUserByDepartId(map);
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
//       jo.put("users",new GUserWarpper(users).warp());
//       jo.put("total",users.getTotal());
        return (JSONObject) JSONObject.toJSON(jo);
    }
    @RequestMapping(value = "/list1",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list1(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        Page<Map<String,Object>> users = new Page<>();
        long directoryId = Long.parseLong(map.get("directoryId").toString());


        try
        {
        users=userService.queryUserByDirectoryAuth(map);
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

    @RequestMapping(value = "/list2",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject list2(@RequestBody Map<String,Object> map, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        Page<Map<String,Object>> users = new Page<>();
        long directoryId = Long.parseLong(map.get("directoryId").toString());
        List<Integer>departIds =new ArrayList<>();
        map.put("id",map.get("departId"));
        List<Map<String,Object>>children =  new DepartWarpper(map).getChildren(map);
        for(Map<String,Object>child:children)
        {
            departIds.add(Integer.parseInt(child.get("id").toString()));

        }
        departIds.add(Integer.parseInt(map.get("departId").toString()));

        map.put("departIds",departIds);


        try
        {
            users=userService.queryUserWithOutDirectoryAuth(map);
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
        String a = UUID.randomUUID().toString().replaceAll("-","");
        String b = UUID.randomUUID().toString().replaceAll("-","");
        map.put("rands",a.substring(0,10));
        map.put("salt",b.substring(0,10));
        String passwd ="";
        if(map.get("passwd")==null)
        {
            passwd = defaultPassWord;
        }
        else
        {
             passwd = map.get("passwd").toString();
        }

        String newPasswd = EncodePasswd.getEncryptedPassword(passwd,b.substring(0,10),10000,50);
        map.put("passwd",newPasswd);


        try
        {

            userService.addGUser(map);
            directoryService.createPersonalModelRoot(map);
//            mailService.sendSimpleMail(map.get("email").toString(),"Syslink注册成功通知","您的账号已完成注册,初始密码是'111111',请尽快登录修改！");

//            for(int i=1;i<100;i++)
//            {
//                map.put("id",Long.parseLong(map.get("id").toString())+i);
//                map.put("name","test"+i);
//                map.put("fullName","测试"+i);
//                map.put("departId",map.get("departId"));
//                map.put("email","test"+i+"@syslink.com");
//                userService.addGUser(map);
//
//            }



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
                if(ids.getIntValue(i)!=1)
                {
                    userService.deleteUser(ids.getIntValue(i));
                }

            }
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
        String lginIp = IpUtil.getIpAddr(request);
        Date loginDate = DateUtil.getTimestamp();
        userService.updateLoginstate(user.getID(),lginIp,loginDate);
        String title = "登录";
        String content = "用户:"+jsonObject.getString("userName")+"登录系统";
        logService.addLog(title,content);



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

        JSONObject jsonObject = JSONObject.parseObject(para);


        JSONArray authIds = jsonObject.getJSONArray("authIds");
        JSONArray uids = jsonObject.getJSONArray("uids");
        List<Auth>  userAuths =  new ArrayList<>();

        if(uids.size()>0)
        {
           for(int i=0;i<uids.size();i++)
           {
               try {
                   userService.updateAuth(uids.getLongValue(i),authIds);
                   userAuths= authService.queryAuthByUid(uids.getLongValue(i));


//                  String title = "分配权限";
//                  String content = "用户:"+getUserName()+"给用户:"

               }
               catch (Exception e)
               {
                   e.printStackTrace();
                   jo.put("flag",false);
                   jo.put("msg","分配权限失败");
                   return jo;
               }

           }
        }
        else
        {
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
        }

        jo.put("flag",true);
        jo.put("msg","分配权限成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/assignRole",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject assignRole(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(para);
        JSONArray roles = jsonObject.getJSONArray("assigned");
        JSONArray uids = jsonObject.getJSONArray("uids");
        if(uids.size()>0)
        {
            for(int i=0;i<uids.size();i++)
            {
                try
                {
                    roleService.updateUserRoles(uids.getLongValue(i),roles);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    jo.put("flag",false);
                    jo.put("msg","分配角色失败");
                    return jo;
                }
            }
        }
        else
        {
            long uid = jsonObject.getLongValue("uid");
            try
            {
                roleService.updateUserRoles(uid,roles);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                jo.put("flag",false);
                jo.put("msg","分配角色失败");
                return jo;
            }
        }



        jo.put("flag",true);
        jo.put("msg","分配角色成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }



    @RequestMapping(value = "/modelAuth",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject modelAuth(@RequestBody String para, HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(para);
        JSONArray uids = jsonObject.getJSONArray("uids");
        JSONArray directoryIds = jsonObject.getJSONArray("directoryIds");
        JSONObject directory = jsonObject.getJSONArray("directoryAuth").getJSONObject(0);
        List<JSONObject>ttt = getDirectoryAuthList(directory);

        if(uids.size()>0)
        {
            for(int i=0;i<uids.size();i++)
            {
                try
                {
                    userService.updateModelAuth2(uids.getLongValue(i),ttt);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    jo.put("flag",false);
                    jo.put("msg","分配权限失败");
                    return jo;
                }
            }

        }
        else
        {
            Long uid = jsonObject.getLongValue("uid");
            try
            {
                userService.updateModelAuth2(uid,ttt);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                jo.put("flag",false);
                jo.put("msg","分配权限失败");
                return jo;
            }

        }

         List<Map<String,Object>> directories = new ArrayList<>();

        jo.put("flag",true);
        jo.put("msg","分配权限成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }





    public List<JSONObject> getDirectoryAuthList(JSONObject directory)
    {
        List<JSONObject> directoryAuth = new ArrayList<>();
        directoryAuth.add(directory);
        if(directory.getJSONArray("children")!=null)
        {
            JSONArray c = directory.getJSONArray("children");
            if(c.size()>0)
            {
                for(int i=0;i<c.size();i++)
                {
                    directoryAuth.addAll(getDirectoryAuthList(c.getJSONObject(i)));
                    ;
                }
            }
        }

        return directoryAuth;
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
        Long a = getUserId();
        if(getUserId()>0)
        {
            jo.put("session",true);
        }
        else
        {
            jo.put("session",false);
        }
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
        session.setAttribute("loginIp",IpUtil.getIpAddr(request));

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
                update.put("passwd", EncodePasswd.getEncryptedPassword(jsonObject.getString("newPassWd"),user.getSalt(),10000,50));

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



    @RequestMapping(value = "/nameExist",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject nameExist(HttpServletRequest request, HttpServletResponse response,@RequestBody String para)
    {

        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        boolean exist = false;

        try {
           exist= userService.nameExist(jsonObject);
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


    @RequestMapping(value = "/emailExist",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject emailExist(HttpServletRequest request, HttpServletResponse response,@RequestBody String para)
    {

        JSONObject jo = new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        boolean exist = false;

        try {
            exist= userService.emailExist(jsonObject);
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
