package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.ReviewSuccessMapper;
import com.tongyuan.model.service.ReviewSuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewSuccessServiceImpl implements ReviewSuccessService{
    @Autowired
    private ReviewSuccessMapper reviewSuccessMapper;

    @Override
    public int add(Long packageId) {
        return reviewSuccessMapper.add(packageId);
    }
}
