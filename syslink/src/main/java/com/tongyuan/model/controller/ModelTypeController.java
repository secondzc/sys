package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.ModelType;
import com.tongyuan.model.service.ModelTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
@Controller
@RequestMapping("/api/modeltype")
public class ModelTypeController extends  BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ModelTypeService modelTypeService;

    @RequestMapping(value = "/getModelTypeList",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getModelTypeList(HttpServletRequest request , HttpServletResponse response){
        JSONObject jo = new JSONObject();
        List<ModelTypeDto> modelTypeList = new ArrayList<>();
        try {
            modelTypeList = modelTypeService.getModelTypeList();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取模型类型列表失败");
            return returnErrorInfo(jo);
        }
        jo.put("modelTypeList",modelTypeList);
        return returnSuccessInfo(jo);

    }

}
