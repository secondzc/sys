package com.tongyuan.model.dao;

import com.tongyuan.model.domain.ReviewMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMsgMapper {
    int add(ReviewMsg reviewMsg);
    int delete(String[] ids);
    List<ReviewMsg> queryByToUserId(Long toUserId);
    int queryCount(Long toUserId);
}
