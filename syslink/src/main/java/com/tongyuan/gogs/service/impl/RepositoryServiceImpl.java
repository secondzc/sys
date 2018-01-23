package com.tongyuan.gogs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongyuan.gogs.dao.CollaborationMapper;
import com.tongyuan.gogs.dao.RepositoryMapper;
import com.tongyuan.gogs.domain.Collaboration;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.*;
import com.tongyuan.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class RepositoryServiceImpl implements RepositoryService{
    @Autowired
    RepositoryMapper repositoryMapper;
    @Autowired
    private GUserService guserService;
    @Autowired
    private CollaborationService collaborationService;
    @Autowired
    private AccessService accessService;
    @Autowired
    private WatchService watchService;

    @Override
    public Long add(Repository repository) {
        return this.repositoryMapper.add(repository);
    }

    @Override
    public Repository queryByName(String Name) {
        return this.repositoryMapper.queryByName(Name);
    }

    @Override
    public List<Repository> findAllRepository() {
        return this.repositoryMapper.findAllRepository();
    }
    @Override
    public Page<Map<String,Object>> queryByUid(Map<String,Object> map)
    {
        Page<Map<String,Object>>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String,Object>> repos = repositoryMapper.queryMyRepo(map);
        return page;
    }
    @Override
    public Map<String,Object> queryById(long id)
    {
        return this.repositoryMapper.queryById(id);
    }

    @Override
    public Page<Map<String,Object>> getCollaboration(Map<String,Object>map)
    {
        Page<Map<String,Object>>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<Map<String,Object>>cRepos = repositoryMapper.getc(map);
        return page;
    }

    @Override
    public boolean update(Repository repository) {
        return this.repositoryMapper.update(repository);
    }

    @Override
    public Repository queryByNameAndUserId(Map<String, Object> param) {
        return this.repositoryMapper.queryByNameAndUserId(param);
    }

    @Override
    public Repository setParameter(Repository repository) {
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
        return repository;
    }

    @Override
    public Long forkRepository(Repository repository, GUser gUser,Long adminId) {
        Repository forkRepository = new Repository();
        forkRepository.setOwnerID(adminId);
        forkRepository.setLowerName(gUser.getLowerName()+repository.getLowerName());
        forkRepository.setName(gUser.getName()+repository.getName());
        forkRepository.setDefaultBranch("master");
        forkRepository.setSize(repository.getSize());
        forkRepository.setNumWatches(1);
        forkRepository.setNumStars(0);
        forkRepository.setNumForks(0);
        forkRepository.setAllowPublicIssues(false);
        forkRepository.setNumIssues(0);
        forkRepository.setNumClosedIssues(0);
        forkRepository.setNumPulls(0);
        forkRepository.setNumClosedPulls(0);
        forkRepository.setNumMilestones(0);
        forkRepository.setNumClosedMilestones(0);
        forkRepository.setIsPrivate(false);
        forkRepository.setIsBare(false);
        forkRepository.setIsMirror(false);
        forkRepository.setEnableWiki(true);
        forkRepository.setAllowPublicWiki(false);
        forkRepository.setEnableExternalWiki(false);
        forkRepository.setEnableIssues(true);
        forkRepository.setAllowPublicWiki(false);
        forkRepository.setEnableExternalTracker(false);
        forkRepository.setExternalTrackerStyle("numeric");
        forkRepository.setEnablePulls(false);
        forkRepository.setIsFork(true);
        forkRepository.setForkID(repository.getID());
        forkRepository.setCreatedUnix(new Date().getTime() / 1000);
        this.repositoryMapper.add(forkRepository);
        Long forkRepositoryId = forkRepository.getID();
        return  forkRepositoryId;
    }

    @Override
    public void forkAndCollaboration(String userName, String repositoryName) {
        GUser gUser = guserService.querListByName(userName);
        GUser admin = guserService.querListByName("admin");
        gUser.setNumRepos(gUser.getNumRepos()+1);
        guserService.update(gUser);
        Map<String,Object> param = new HashMap<>();
        param.put("userId",gUser.getID());
        param.put("repositoryName",repositoryName.toLowerCase());
        Repository repository = this.queryByNameAndUserId(param);
        collaborationService.addDefault(admin.getID(),repository.getID());
        accessService.addDefault(admin.getID(),repository.getID());
        Long forkRepositoryId = this.forkRepository(repository,gUser,admin.getID());
        Watch watch = new Watch();
        watch.setRepoID(forkRepositoryId);
        watch.setUserID(gUser.getID());
        boolean watchResult = watchService.add(watch);
        repository.setNumForks(repository.getNumForks()+1);
        this.update(repository);
        //第一次执行时把fork对象的库复制到用户下面
        ModelUtil.copyDirectory(System.getProperty("user.home")+"/gogs-repositories/"+ userName.toLowerCase()+"/" + repositoryName.toLowerCase() +".git",System.getProperty("user.home")+"/gogs-repositories/"+ admin.getLowerName()+"/");
        File file = new File(System.getProperty("user.home")+"/gogs-repositories/"+ admin.getLowerName()+"/" + repositoryName.toLowerCase() +".git");
        file.renameTo(new File(System.getProperty("user.home")+"/gogs-repositories/"+ admin.getLowerName()+"/" + userName.toLowerCase() + repositoryName.toLowerCase() +".git"));
    }
}
