package com.tongyuan.gogs.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.domain.Action;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.ActionService;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.gogs.service.WatchService;
import com.tongyuan.util.ResourceUtil;
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
import java.io.File;
import java.util.Date;

/**
 * Created by Administrator on 2017-9-18.
 */
@Controller
@RequestMapping("/api/repository")
public class RepositoryController {
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
                          HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        GUser user = guserService.querListByName(name);
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
        action.setActUserName(name);
        action.setRepoID(repositoryData.getID());
        action.setRepoUserName(name);
        action.setRepoName(repository.getName());
        action.setPrivate(false);
        action.setCreatedUnix(new Date().getTime() / 1000);
        boolean actionResult = actionService.add(action);

        String  Url = RepositoryController.class.getClassLoader().getResource("lizi").getPath();
        String liziZip = Url.substring(1);
        resourceUtil.UnZip(liziZip,"C:/Users/Administrator/gogs-repositories/"+name.toLowerCase());
        File file = new File("C:/Users/Administrator/gogs-repositories/"+name.toLowerCase()+"/lizi.git");
        if(!file.exists() || file.isDirectory()){
            file.renameTo(new File("C:/Users/Administrator/gogs-repositories/"+name.toLowerCase()+"/"+fileName+".git"));
        }
        return jo;
    }






}


