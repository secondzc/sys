package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.ReviewMsgMapper;
import com.tongyuan.model.domain.ReviewMsg;
import com.tongyuan.model.service.ReviewMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewMsgServiceImpl implements ReviewMsgService {
    @Autowired
    private ReviewMsgMapper reviewMsgMapper;
    @Override
    public int add(ReviewMsg reviewMsg) {
        return reviewMsgMapper.add(reviewMsg);
    }

    @Override
    public int delete(String[] ids) {
        return reviewMsgMapper.delete(ids);
    }

    @Override
    public List<ReviewMsg> queryByToUserId(Long toUserId) {
        return reviewMsgMapper.queryByToUserId(toUserId);
    }

    @Override
    public int queryCount(Long toUserId) {
        return reviewMsgMapper.queryCount(toUserId);
    }
}
