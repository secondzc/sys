package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.service.OrgService;
import com.tongyuan.gogs.service.TeamService;
import com.tongyuan.model.controller.BaseController;
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
        long orgId = orgService.getOrgIdByName(map);
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
        jo.put("team",team);
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






}
