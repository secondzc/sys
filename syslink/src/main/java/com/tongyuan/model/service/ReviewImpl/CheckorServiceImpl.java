package com.tongyuan.model.service.ReviewImpl;

import com.github.pagehelper.PageHelper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.dao.NodeInstanceAndNodeMapper;
import com.tongyuan.pageModel.CheckorPage;
import com.tongyuan.model.domain.ReviewNodeInstance;
import com.tongyuan.model.service.CheckorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/9.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
class CheckorServiceImpl implements CheckorService {
    @Autowired
    private NodeInstanceAndNodeMapper nodeInstanceAndNodeMapper;
    @Autowired
    private GUserService gUserService;

    @Override
    public List<ReviewNodeInstance> queryAfterAgree(Map<String, Object> map) {
        return nodeInstanceAndNodeMapper.queryAfterAgree(map);
    }

    @Override
    public List<CheckorPage> queryByReviewer(Map<String,Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        List<CheckorPage> checkorPages = nodeInstanceAndNodeMapper.queryByReviewer(params);
        for(CheckorPage checkorPage:checkorPages){
            Long userId = checkorPage.getModel().getUserId();
            GUser guser = gUserService.queryById(userId);
            checkorPage.setUserName(guser.getName());
        }
        return checkorPages;
    }

    @Override
    public List<CheckorPage> queryAllByReviewer(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        List<CheckorPage> checkorPages = nodeInstanceAndNodeMapper.queryAllByReviewer(params);
        for(CheckorPage checkorPage:checkorPages){
            Long userId = checkorPage.getModel().getUserId();
            GUser guser = gUserService.queryById(userId);
            checkorPage.setUserName(guser.getName());
        }
        return checkorPages;
    }
}



