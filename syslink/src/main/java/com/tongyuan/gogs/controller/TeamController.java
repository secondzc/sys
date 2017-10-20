package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.OrgService;
import com.tongyuan.gogs.service.TeamService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.wrapper.TeamWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yh on 2017/9/30.
 */
@Controller
@RequestMapping("/api/team")
public class TeamController extends BaseController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private GUserService userService;



    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestBody Map<String,Object> map,HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        long orgId = orgService.getOrgIdByName(map.get("orgName").toString());
        map.put("lowerName",map.get("name").toString().toLowerCase());
        map.put("orgId",orgId);
        map.put("numRepos",0);
        map.put("numMembers",0);
         if(teamService.nameExist(map))
         {
             jo.put("flag",false);
             jo.put("msg","团队名称重复");
             return jo;
         }
         else
         {
             try
             {

                 teamService.add(map);

             }
             catch (Exception e)
             {
                 e.printStackTrace();
                 jo.put("flag",false);
                 jo.put("msg","创建团队失败");
                 return jo;
             }
             jo.put("flag",true);
             jo.put("msg","创建团队成功");
             return (JSONObject) JSONObject.toJSON(jo);
         }


    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@RequestBody Map<String,Object> map,HttpServletRequest request)
    {
        JSONObject jo = new JSONObject();
        long orgId = orgService.getOrgIdByName(map.get("orgName").toString());
        map.put("lowerName",map.get("name").toString().toLowerCase());
        map.put("orgId",orgId);

        if(teamService.nameExist(map))
        {
            jo.put("flag",false);
            jo.put("msg","团队名称重复");
            return jo;
        }
        else
        {
            try
            {

                teamService.update(map);

            }
            catch (Exception e)
            {
                e.printStackTrace();
                jo.put("flag",false);
                jo.put("msg","编辑团队失败");
                return jo;
            }
            jo.put("flag",true);
            jo.put("msg","编辑团队成功");
            return (JSONObject) JSONObject.toJSON(jo);
        }


    }

    @RequestMapping(value = "/queryTeamById",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject queryTeamById(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        long id = Long.parseLong(map.get("teamId").toString());
        Map<String ,Object> team = new HashMap<>();

        try{
           team = teamService.queryByTeamId(id);

        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","操作失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","操作成功");
        jo.put("team",team);
        return (JSONObject) JSONObject.toJSON(jo);

    }

    @RequestMapping(value = "/queryTeamByName",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject queryTeamByName(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        String teamName = map.get("teamName").toString();
        long orgId = orgService.getOrgIdByName(map.get("orgName").toString());
        Map<String,Object> query = new HashMap<>();
        query.put("teamName",teamName);
        query.put("orgId",orgId);
        Map<String ,Object> team = new HashMap<>();

        try{
            team = teamService.queryByTeamName(query);
        }
        catch (Exception e){
            e.printStackTrace();
            jo.put("flag",false);
            jo.put("msg","操作失败");
            return jo;
        }
        jo.put("flag",true);
        jo.put("msg","操作成功");
        jo.put("team", new TeamWarpper(team).warp());
        return (JSONObject) JSONObject.toJSON(jo);
    }
    @RequestMapping(value = "/addTeamUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addOrgUser(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        map.put("uid",getCurrentUserId(request));

        try{
            teamService.addTeamUser(map);
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


    @RequestMapping(value = "/deleteTeamUser",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteTeamUser(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        Long orgId = orgService.getOrgIdByName(map.get("orgName").toString());

        Map<String ,Object>team = teamService.queryByTeamId(Long.parseLong(map.get("teamId").toString()));
        Long authorize = Long.parseLong(team.get("authorize").toString());
        Integer numMembers = Integer.parseInt(team.get("numMembers").toString());
        if(authorize==4)
        {
            if(numMembers<=1)
            {
                jo.put("flag",false);
                jo.put("msg","被移除用户为最后一位管理员。请添加一位新的管理员再进行移除成员操作！");
                return jo;
            }
        }
        map.put("uid",getCurrentUserId(request));
        try{
            teamService.deleteTeamUser(map);
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

    @RequestMapping(value = "/addTeamMember",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addTeamMember(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        GUser user = userService.querListByName(map.get("name").toString());
        long uid = user.getID();
        long orgId = orgService.getOrgIdByName(map.getString("orgName"));
        map.put("orgId",orgId);
        long teamId = teamService.getIdByName(map);
        if(user!=null)
        {
            Map<String,Object> teamUser = teamService.queryByTeamIdAndUid(map);
            if(teamUser==null)
            {
                try{
                    teamService.addTeamMember(map);
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
                return (JSONObject) JSONObject.toJSON(jo);
            }

        }
        else
        {
            jo.put("flag",false);
            jo.put("msg","该用户不存在");
            return (JSONObject) JSONObject.toJSON(jo);
        }



    }


    @RequestMapping(value = "/deleteTeamMember",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleteTeamMember(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        Long orgId = orgService.getOrgIdByName(map.get("orgName").toString());

        Long uid = map.getLongValue("uid");
        if(uid==null)
        {
            map.put("uid",getCurrentUserId(request));
        }

        Map<String ,Object>team = teamService.queryByTeamId(Long.parseLong(map.get("teamId").toString()));
        Long authorize = Long.parseLong(team.get("authorize").toString());
        Integer numMembers = Integer.parseInt(team.get("numMembers").toString());
        if(authorize==4)
        {
            if(numMembers<=1)
            {
                jo.put("flag",false);
                jo.put("msg","被移除用户为最后一位管理员。请添加一位新的管理员再进行移除成员操作！");
                return jo;
            }
        }

        try{
            teamService.deleteTeamUser(map);
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

    @RequestMapping(value = "/addTeam",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addTeam(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        Map<String,Object>team = teamService.queryByTeamId(Long.parseLong(map.getString("teamId")));
        team.put("authorize",map.getIntValue("authorize"));
        team.put("name",map.getString("name"));
        team.put("decription",map.getString("description"));

        try{
            teamService.update(team);
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
    @RequestMapping(value = "/updateTeam",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject updateTeam(@RequestBody String para, HttpServletRequest request) {
        JSONObject jo=new JSONObject();
        JSONObject map = JSON.parseObject(para);
        Map<String,Object>team = teamService.queryByTeamId(Long.parseLong(map.getString("teamId")));
        team.put("authorize",map.getIntValue("authorize"));
        team.put("name",map.getString("name"));
        team.put("decription",map.getString("description"));

            try{
                teamService.update(team);
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
