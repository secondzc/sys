package com.tongyuan.model.service.ReviewService;

import com.tongyuan.pageModel.CommentPage;
import com.tongyuan.pageModel.DetailPage;
import com.tongyuan.model.domain.ReviewNodeInstance;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/6.
 */
public interface NodeInstanceService {
    int add(ReviewNodeInstance reviewNodeInstance);
    ReviewNodeInstance queryById(Long id);
    int updateStatus(Map<String, Object> map);  //map中有 id、 status（3或4）
    int updateTime(Map<String, Object> map);

    List<DetailPage> details(Long instanceId);
    List<CommentPage> queryCommentPages(Long instanceId);
    int updateComment(Map<String, Object> map);
    List<ReviewNodeInstance> queryByInstanceId(Long instanceId);
}
