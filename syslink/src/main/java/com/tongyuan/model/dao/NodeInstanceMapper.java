package com.tongyuan.model.dao;

import com.tongyuan.pageModel.CommentPage;
import com.tongyuan.pageModel.DetailPage;
import com.tongyuan.model.domain.ReviewNodeInstance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/6.
 */
@Mapper
public interface NodeInstanceMapper {
    int add(ReviewNodeInstance reviewNodeInstance);
    ReviewNodeInstance queryById(Long id);
    int updateStatus(Map<String, Object> map);
    int updateTime(Map<String, Object> map);

    List<DetailPage> details(Long instanceId);
    int updateComment(Map<String, Object> map);
    List<CommentPage> queryCommentPages(Long instanceId);
    List<ReviewNodeInstance> queryByInstanceId(Long instanceId);
}
