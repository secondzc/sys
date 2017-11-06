package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.WatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-11-2.
 */

@Controller
@RequestMapping("/api/watch")
public class WatchController {
    @Autowired
    private WatchService watchService;
    @Autowired
    private GUserService gUserService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestParam(value = "userId",required = false)Long user_id,
                          @RequestParam(value = "repoId",required = false)Long repo_id,
                          HttpServletRequest request , HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        try{
            Watch watch = new Watch();
            if(user_id != null && !user_id.equals("") && repo_id != null && !repo_id.equals("") ){
                watch.setUserID(user_id);
                watch.setRepoID(repo_id);
                boolean resukt = watchService.add(watch);
                GUser gUser = gUserService.queryById(user_id);
                gUser.setNumRepos(gUser.getNumRepos()+1);
                boolean updateResult =gUserService.update(gUser);
            }
        }catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","error");
            return jo;
        }
        jo.put("status","1");
        jo.put("code",0);
        jo.put("msg","ok");
        return jo;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "userId",required = false)Long user_id,
                          @RequestParam(value = "repoId",required = false)Long repo_id,
                          HttpServletRequest request , HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        try{
            if(user_id != null && !user_id.equals("") && repo_id != null && !repo_id.equals("") ){
                Map<String, Object> params = new HashMap<>();
                params.put("uid",user_id);
                params.put("repo_id",repo_id);
                 Watch watch = watchService.queryListByParam(params);
                 boolean result = watchService.delete(watch.getID());
                GUser gUser = gUserService.queryById(user_id);
                gUser.setNumRepos(gUser.getNumRepos()-1);
                boolean updateResult =gUserService.update(gUser);
            }
        }catch (Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","error");
            return jo;
        }
        jo.put("status","1");
        jo.put("code",0);
        jo.put("msg","ok");
        return jo;
    }
}
