package com.tongyuan.model.service.impl;

import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.dao.NodeInstanceMapper;
import com.tongyuan.pageModel.DetailPage;
import com.tongyuan.model.domain.ReviewNodeInstance;
import com.tongyuan.model.service.NodeInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/6.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class NodeInstanceServiceImpl implements NodeInstanceService {
    @Autowired
    private NodeInstanceMapper nodeInstanceMapper;
    @Autowired
    private GUserService gUserService;

    @Override
    public int add(ReviewNodeInstance reviewNodeInstance){
        return nodeInstanceMapper.add(reviewNodeInstance);
    }
    @Override
    public ReviewNodeInstance queryById(Long id){
        return nodeInstanceMapper.queryById(id);
    }
    @Override
    public int updateStatus(Map<String,Object> map){
        return nodeInstanceMapper.updateStatus(map);
    }
    @Override
    public int updateTime(Map<String,Object> map) {
        return nodeInstanceMapper.updateTime(map);
    }

    @Override
    public List<DetailPage> details(Long instanceId){
        List<DetailPage> detailPageList = nodeInstanceMapper.details(instanceId);
        for(DetailPage detailPage:detailPageList){
            Long userId = detailPage.getNode().getUserId();
            GUser gUser = new GUser();
            gUser.setName(gUserService.queryById(userId).getName());
            detailPage.setUser(gUser);
        }
        return detailPageList;
    }
<<<<<<< HEAD

    @Override
    public List<CommentPage> queryCommentPages(Long instanceId) {
        //按照review_node的sequence由小到大的顺序 查询instanceId下的commentPages,只查询审核过的，
        //也就是3同意，4不同意
        List<CommentPage> commentPages = nodeInstanceMapper.queryCommentPages(instanceId);
        for(CommentPage commentPage:commentPages){
            if(commentPage.getStatus()==3){
                commentPage.setShowStatus("同意");
            }else{
                commentPage.setShowStatus("不同意");
            }
            //查询gogs库，添加user属性,因为guser分库了，所以不能联合查询，
            Long checkorId = commentPage.getCheckorId();
            commentPage.setCheckorName(gUserService.queryById(checkorId).getName());
        }
        return commentPages;
    }

    @Override
    public int updateComment(Map<String, Object> map) {
        return nodeInstanceMapper.updateComment(map);
    }
=======
>>>>>>> master
}
