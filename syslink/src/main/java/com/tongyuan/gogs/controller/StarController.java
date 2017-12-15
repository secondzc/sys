package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Star;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.gogs.service.StarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-11-2.
 */

@Controller
@RequestMapping("/api/star")
public class StarController {
    @Autowired
    private StarService starService;
    @Autowired
    private GUserService gUserService;
    @Autowired
    private RepositoryService repositoryService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestParam(value = "userId",required = false)Long user_id,
                          @RequestParam(value = "repoName",required = false)String repoName,
                          @RequestParam(value = "repoOwner",required = false)String repoOwner,
                          HttpServletRequest request , HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        try{
            GUser owner = gUserService.querListByName(repoOwner);
            Star star = new Star();
 //           Repository repository = repositoryService.queryByName(repoName);
            Map<String,Object> param = new HashMap<>();
            param.put("userId",owner.getID());
            param.put("repositoryName",repoName.toLowerCase());
            Repository repository = repositoryService.queryByNameAndUserId(param);
            if(user_id != null && !user_id.equals("")  && repository!= null  ){
                star.setUid(user_id);
                star.setRepoId(repository.getID());
                boolean result = starService.add(star);
                GUser gUser = gUserService.queryById(user_id);
                gUser.setNumStars(gUser.getNumStars()+1);
                boolean updateResult =gUserService.update(gUser);
                repository.setNumStars(repository.getNumStars()+1);
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
                             @RequestParam(value = "repoOwner",required = false)String repoOwner,
                          HttpServletRequest request , HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        try{
            GUser owner = gUserService.querListByName(repoOwner);
            Map<String,Object> param = new HashMap<>();
            param.put("userId",owner.getID());
            param.put("repositoryName",repoName.toLowerCase());
            Repository repository = repositoryService.queryByNameAndUserId(param);
//            Repository repository = repositoryService.queryByName(repoName);
            if(user_id != null && !user_id.equals("")  && repository != null  ){
                Map<String, Object> params = new HashMap<>();
                params.put("uid",user_id);
                params.put("repo_id",repository.getID());
                 Star star = starService.queryListByParam(params);
                 boolean result = starService.delete(star.getID());
                GUser gUser = gUserService.queryById(user_id);
                gUser.setNumStars(gUser.getNumStars()-1);
                boolean updateResult =gUserService.update(gUser);
                repository.setNumStars(repository.getNumStars()-1);
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
