package com.tongyuan.model.service;

import com.tongyuan.model.domain.ReviewMsg;

import java.util.List;

public interface ReviewMsgService {
    int add(ReviewMsg reviewMsg);
    int delete(String[] ids);
    List<ReviewMsg> queryByToUserId(Long toUserId);
    int queryCount(Long toUserId);
}
