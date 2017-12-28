package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.OrgService;
import com.tongyuan.gogs.service.TeamService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.wrapper.GUserWarpper;
import com.tongyuan.model.wrapper.TeamWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yh on 2017/9/25.
 */
@Controller
@RequestMapping("/api/org")
public class OrgController extends BaseController {

    @Autowired
    private OrgService orgService;
    @Autowired
    private GUserService userService;
    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestBody Map<String,Object>map, HttpServletRequest request) {

        JSONObject jo=new JSONObject();

        if(orgService.nameExist(map))
        {
            jo.put("flag",false);
            jo.put("msg","该名称已存在");
            return jo;
        }
        long uid = getUserId();
        map.put("uid",uid);


        try{
            orgService.addOrg(map);
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("flag",false);
            jo.put("code",0);
            jo.put("msg","创建失败");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("flag",true);
        jo.put("msg","创建成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@RequestBody Map<String,Object>map, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        if(orgService.nameExist(map))
        {
            jo.put("flag",false);
            jo.put("msg","该名称已存在");
            return jo;
        }

        try{
            orgService.updateOrg(map);
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("flag",false);
            jo.put("code",0);
            jo.put("msg","编辑失败");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("flag",true);
        jo.put("msg","编辑成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        long id = jsonObject.getIntValue("id");


        try{
            orgService.deleteOrg(id);
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("flag",false);
            jo.put("code",0);
            jo.put("msg","删除失败");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("flag",true);
        jo.put("msg","删除成功");
        return (JSONObject) JSONObject.toJSON(jo);
    }


    @RequestMapping(value = "/query",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject query(@RequestBody Map<String,Object> map, HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject jo=new JSONObject();
        List<Map<String,Object>> orgs = new ArrayList<>();

        try{
            orgs = orgService.query(map);
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
        jo.put("orgs",new GUserWarpper(orgs).warp());
        return (JSONObject) JSONObject.toJSON(jo);

    }


    @RequestMapping(value = "/myOrg",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject myOrg(@RequestBody String para, HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject jo=new JSONObject();
//        Map<String,Object> map = new HashMap<>();
//        map.put("uid",getCurrentUserId(request));
        JSONObject jsonObject = JSON.parseObject(para);
        Long uid = jsonObject.getLongValue("uid");
        List<Map<String,Object>> orgs = new ArrayList<>();
        try{
            orgs = orgService.getMyOrg(uid);
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
        jo.put("orgs",new GUserWarpper(orgs).warp());
        return (JSONObject) JSONObject.toJSON(jo);

    }


    @RequestMapping(value = "/orgUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject orgUser(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        String name = jsonObject.getString("name");
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        List<Map<String,Object>> orgUser = new ArrayList<>();
        try{
            orgUser = orgService.getOrgUser(map);
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
        jo.put("orgUser",orgUser);
        return (JSONObject) JSONObject.toJSON(jo);

    }


    @RequestMapping(value = "/team",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject team(@RequestBody String para, HttpServletRequest request)  {
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        String orgName = jsonObject.getString("orgName");
    //    Map<String,Object> map = new HashMap<>();
    //    map.put("orgName",name);
        long orgId = orgService.getOrgIdByName(orgName);
        List<Map<String,Object>> team = new ArrayList<>();
        try{
            team = teamService.queryTeamByOrgId(orgId);
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
        jo.put("team",new TeamWarpper(team).warp());
        return (JSONObject) JSONObject.toJSON(jo);

    }
    @RequestMapping(value = "/addOrgUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addOrgUser(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        Long orgId = orgService.getOrgIdByName(jsonObject.getString("orgName"));
        jsonObject.put("orgId",orgId);
        GUser user = userService.querListByName(jsonObject.getString("name"));
        jsonObject.put("uid",user.getID());
        Map<String,Object>orgUser = orgService.queryByUAndO(jsonObject);
        if(orgUser==null)
        {
            try{
                orgService.addOrgUser(jsonObject);
            }
            catch (Exception e){
                e.printStackTrace();
                jo.put("flag",false);
                jo.put("msg","操作失败");
                return jo;
            }
            jo.put("flag",true);
            jo.put("msg","操作成功");
            return (JSONObject) JSONObject.toJSON(jo);
        }
        else
        {
            jo.put("flag",false);
            jo.put("msg","该用户已存在，请不要重复添加");
            return jo;
        }


    }


    @RequestMapping(value = "/updateOrgUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject updateOrgUser(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        List<Map<String,Object>> orgUser = new ArrayList<>();
        try{
           orgService.updateOrgUser(jsonObject);
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","操作失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","操作成功");
        return (JSONObject) JSONObject.toJSON(jo);

    }

    @RequestMapping(value = "/deleteOrgUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteOrgUser(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = JSON.parseObject(para);
        Map<String,Object> orgUser = orgService.queryByUAndO(jsonObject);
        List<Map<String,Object>>orgUsers = orgService.queryOrgUserByOrgId(jsonObject.getLongValue("orgId"));
        int ownerCount = 0;
        for(Map<String,Object>map:orgUsers)
        {
            if(map.get("isOwner").toString().equalsIgnoreCase("true"))
            {
                ownerCount++;
            }
        }
        if(ownerCount<=1&&(orgUser.get("isOwner").toString().equalsIgnoreCase("true")))
        {
            jo.put("flag",false);
            jo.put("msg","被移除用户为最后一位管理员。请添加一位新的管理员再进行移除成员操作！");
            return jo;
        }
        else
        {
            //List<Map<String,Object>> orgUser = new ArrayList<>();
            try{
                orgService.deleteOrgUser(jsonObject);
            }
            catch (Exception e){
                e.printStackTrace();
                jo.put("flag",false);
                jo.put("msg","操作失败");
                return jo;
            }
            jo.put("flag",true);
            jo.put("msg","操作成功");
            return (JSONObject) JSONObject.toJSON(jo);
        }




    }

}
