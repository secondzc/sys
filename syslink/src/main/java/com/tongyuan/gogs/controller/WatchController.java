package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
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
    @Autowired
    private RepositoryService repositoryService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestParam(value = "userId",required = false)Long user_id,
                          @RequestParam(value = "repoName",required = false)String repoName,
                          HttpServletRequest request , HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        try{
            Repository repository = repositoryService.queryByName(repoName);
            Watch watch = new Watch();
            if(user_id != null && !user_id.equals("") && repository!= null  ){
                watch.setUserID(user_id);
                watch.setRepoID(repository.getID());
                boolean resukt = watchService.add(watch);
                GUser gUser = gUserService.queryById(user_id);
                gUser.setNumRepos(gUser.getNumRepos()+1);
                boolean updateResult =gUserService.update(gUser);
                repository.setNumWatches(repository.getNumWatches()+1);
                repositoryService.update(repository);
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
                          @RequestParam(value = "repoName",required = false)String repoName,
                          HttpServletRequest request , HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        try{
            Repository repository = repositoryService.queryByName(repoName);
            if(user_id != null && !user_id.equals("") && repository != null  ){
                Map<String, Object> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("repo_id",repository.getID());
                 Watch watch = watchService.queryListByParam(params);
                 boolean result = watchService.delete(watch.getID());
                GUser gUser = gUserService.queryById(user_id);
                gUser.setNumRepos(gUser.getNumRepos()-1);
                boolean updateResult =gUserService.update(gUser);
                repository.setNumWatches(repository.getNumWatches()-1);
                repositoryService.update(repository);
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
