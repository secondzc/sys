package com.tongyuan.model.service.ReviewService;

import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.model.domain.ReviewFlowTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/6/24.
 */

public interface ReviewFlowTemplateService {
    int add(ReviewFlowTemplate reviewFlowTemplate);
    List<ReviewFlowTemplate> queryByName(Map<String, Object> map);
    ReviewFlowTemplate queryById(Long templateId);
    int delete(String[] templateIds);
    int updateTime(Map<String, Object> map);
    ReviewFlowTemplate getTemplateByDefault() throws SqlNumberException;

    Long checkDefault() throws Exception;
    int setDefaultTrue(Long templateId);
    int setDefaultFalse(Long templateId);
    int setAlreadyConfig(Long templateId);
}
