package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tongyuan.gogs.domain.Action;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.ActionService;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.gogs.service.WatchService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.wrapper.RepositoryWarpper;
import com.tongyuan.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2017-9-18.
 */
@Controller
@RequestMapping("/api/repository")
public class RepositoryController extends BaseController{
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private WatchService watchService;
    @Autowired
    private GUserService guserService;

    @Autowired
    private ResourceUtil resourceUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "fileName", required = false) String fileName,
                          @RequestParam(value = "scope", required = false) Boolean scope,
                          HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        jo = this.addRepository(name, fileName,scope);
        return  jo;
    }





    @RequestMapping(value = "/myRepo",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject myRepo(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String,Object> map)
    {
        JSONObject jo = new JSONObject();
        long uid = getUserId();
        Page<Map<String,Object>> repos = new Page<>();

        try
        {

            repos=repositoryService.queryByUid(map);

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
        jo.put("myRepo",new RepositoryWarpper(repos).warp());
        jo.put("total",repos.getTotal());


        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value = "/cRepo",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject cRepo(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String,Object> map)
    {
        JSONObject jo = new JSONObject();
        long uid = getUserId();
        Page<Map<String,Object>> cRepos = new Page<>();




        try
        {
            cRepos=repositoryService.getCollaboration(map);

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
        jo.put("cRepos",new RepositoryWarpper(cRepos).warp());
        jo.put("total",cRepos.getTotal());


        return (JSONObject) JSONObject.toJSON(jo);
    }


    public  JSONObject addRepository(String name,String fileName,Boolean scope){
        JSONObject jo = new JSONObject();
        //查看模型库是否存在
        String userName = "";
        if(scope == null){
            userName = name.toLowerCase();
        }else {
            userName = "admin";
        }
        File fileExits = new File(System.getProperty("user.home")+"/gogs-repositories/"+ userName+"/" + fileName+".git");
        if(fileExits.exists()){
            System.out.println("file exists");
            return jo;
        }

        GUser user = guserService.querListByName(userName);
        user.setNumRepos(user.getNumRepos()+1);
        boolean UserResult = guserService.update(user);
        Repository repository = new Repository();
        repository.setOwnerID(user.getID());
        repository.setLowerName(fileName.toLowerCase());
        repository.setName(fileName);
        repository.setDefaultBranch("master");
        repository.setSize(0);
        repository.setNumWatches(1);
        repository.setNumStars(0);
        repository.setNumForks(0);
        repository.setAllowPublicIssues(false);
        repository.setNumIssues(0);
        repository.setNumClosedIssues(0);
        repository.setNumPulls(0);
        repository.setNumClosedPulls(0);
        repository.setNumMilestones(0);
        repository.setNumClosedMilestones(0);
        repository.setIsPrivate(false);
        repository.setIsBare(true);
        repository.setIsMirror(false);
        repository.setEnableWiki(true);
        repository.setEnableExternalWiki(false);
        repository.setAllowPublicWiki(false);
        repository.setEnableIssues(true);
        repository.setAllowPublicWiki(false);
        repository.setEnableExternalTracker(false);
        repository.setExternalTrackerStyle("numeric");
        repository.setEnablePulls(true);
        repository.setIsFork(false);
        repository.setForkID((long) 0);
        repository.setCreatedUnix(new Date().getTime() / 1000);
        boolean result = repositoryService.add(repository);
        //查找插入的仓库对象
        Repository repositoryData = repositoryService.queryByName(fileName);
        Watch watch = new Watch();
        watch.setRepoID(repositoryData.getID());
        watch.setUserID(user.getID());
        boolean watchResult = watchService.add(watch);
        Action action = new Action();
        action.setUserID(user.getID());
        action.setOpType(1);
        action.setActUserID(user.getID());
        action.setActUserName(userName);
        action.setRepoID(repositoryData.getID());
        action.setRepoUserName(userName);
        action.setRepoName(repository.getName());
        action.setPrivate(false);
        action.setCreatedUnix(new Date().getTime() / 1000);
        boolean actionResult = actionService.add(action);

        String  Url = RepositoryController.class.getClassLoader().getResource("lizi").getPath();
        String liziZip = Url.substring(1);
        resourceUtil.UnZip(liziZip,System.getProperty("user.home")+"/gogs-repositories/"+userName);
        File file = new File(System.getProperty("user.home")+"/gogs-repositories/"+userName+"/lizi.git");
        if(!file.exists() || file.isDirectory()){
            file.renameTo(new File(System.getProperty("user.home")+"/gogs-repositories/"+userName+"/"+fileName+".git"));
        }
        return  jo;
    }



}


