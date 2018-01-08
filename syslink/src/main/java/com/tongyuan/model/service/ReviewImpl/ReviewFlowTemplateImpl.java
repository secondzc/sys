package com.tongyuan.model.service.ReviewImpl;

import com.github.pagehelper.PageHelper;
import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.model.dao.ReviewFlowTemplateMapper;
import com.tongyuan.model.domain.ReviewFlowTemplate;
import com.tongyuan.model.service.ReviewService.ReviewFlowTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/6/29.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ReviewFlowTemplateImpl implements ReviewFlowTemplateService {
    @Autowired
    private ReviewFlowTemplateMapper reviewFlowTemplateMapper;

    @Override
    public int add(ReviewFlowTemplate reviewFlowTemplate){
        return reviewFlowTemplateMapper.add(reviewFlowTemplate);
    };

    @Override
    public List<ReviewFlowTemplate> queryByName(Map<String,Object> map){
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("rows").toString()));
        return reviewFlowTemplateMapper.queryByName(map);
    };

    @Override
    public ReviewFlowTemplate queryById(Long templateId){
        return reviewFlowTemplateMapper.queryById(templateId);
    };

    @Override
    public int delete(String[] templateIds){
        return reviewFlowTemplateMapper.delete(templateIds);
    };


    @Override
    public int updateTime(Map<String,Object> map){
        return reviewFlowTemplateMapper.updateTime(map);
    }

    @Override
    public ReviewFlowTemplate getTemplateByDefault() throws SqlNumberException{
        List<ReviewFlowTemplate> reviewFlowTemplates = reviewFlowTemplateMapper.getTemplateByDefault();
        if(reviewFlowTemplates.isEmpty()|| reviewFlowTemplates.size()>1){
            throw new SqlNumberException("默认模板数目应该有且仅有一个");
        }
        return reviewFlowTemplates.get(0);
    }

    @Override
    public Long checkDefault() throws Exception{
        Long[] defaultNumb = reviewFlowTemplateMapper.checkDefault();
        if(defaultNumb!=null && defaultNumb.length>1){
            throw new SqlNumberException("默认模板数目不应该大于1");
        }else{
            if(defaultNumb==null||defaultNumb.length==0){
                return null;
            }else{
                return defaultNumb[0];
            }
        }
    }

    @Override
    public int setDefaultTrue(Long templateId) {
        return reviewFlowTemplateMapper.setDefaultTrue(templateId);
    }

    @Override
    public int setDefaultFalse(Long templateId) {
        return reviewFlowTemplateMapper.setDefaultFalse(templateId);
    }

    @Override
    public int setAlreadyConfig(Long templateId) {
        return reviewFlowTemplateMapper.setAlreadyConfig(templateId);
    }
}
