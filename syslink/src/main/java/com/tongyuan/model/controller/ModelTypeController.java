package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.ModelType;
import com.tongyuan.model.service.AttachmentService;
import com.tongyuan.model.service.ModelTypeService;
import com.tongyuan.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-12-22.
 */
@Controller
@RequestMapping("/api/modeltype")
public class ModelTypeController extends  BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ModelTypeService modelTypeService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ResourceUtil resourceUtil;

    @RequestMapping(value = "/getModelTypeList",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getModelTypeList(HttpServletRequest request , HttpServletResponse response){
        JSONObject jo = new JSONObject();
        List<ModelTypeDto> modelTypeList = new ArrayList<>();
        List<ModelTypeDto> modelTypeDtoList = new ArrayList<>();
        try {
            modelTypeList = modelTypeService.getModelTypeList();
            modelTypeDtoList.addAll(modelTypeList);
            for (ModelTypeDto modelType :modelTypeDtoList) {
                modelType.setFilePath( modelType.getFilePath());
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取模型类型列表失败");
            return returnErrorInfo(jo);
        }
        jo.put("modelTypeList",modelTypeList);
        jo.put("data",modelTypeDtoList);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/addModelType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addModelType(@RequestParam(value = "name", required = false) String name,
                                @RequestBody Map<String,Object> map,
                                HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        String modelName = (String) map.get("name");
        String iconName = (String) map.get("photoName");
        try {
            List<Attachment> iconList = attachmentService.getInsertIcon();
            ModelType modelType = new ModelType();
            for (Attachment attachment : iconList) {
                if (attachment.getName().equals(iconName)) {
                    modelType.setIcon(attachment.getId());
                    modelType.setName(modelName);
                    modelTypeService.add(modelType);
                    attachment.setModelId(-2);
                    attachmentService.update(attachment);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("模型类型添加失败");
            return  returnErrorInfo(jo);
        }
        return  returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/checkName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject checkName(@RequestParam(value = "modelTypeName", required = false) String modelTypeName,
                             HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<ModelTypeDto> modelTypeList = new ArrayList<>();
        try {
            modelTypeList = modelTypeService.getModelTypeList();
            for (ModelTypeDto modelTypeDto : modelTypeList) {
                if(modelTypeDto.getName().equals(modelTypeName)){
                    return  returnErrorInfo(jo);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("获取模型类型列表失败");
            return returnErrorInfo(jo);
        }
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/updateModelType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject updateModelType(@RequestParam(value = "name", required = false) String name,
                                   @RequestBody Map<String,Object> map,
                                   HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        String modelName = (String) map.get("name");
        String iconName = (String) map.get("photoName");
        try {
            List<Attachment> iconList = attachmentService.getInsertIcon();
            ModelType modelType = modelTypeService.getByType(modelName);
            for (Attachment attachment : iconList) {
                if (attachment.getName().equals(iconName)) {
                    modelType.setIcon(attachment.getId());
                    modelTypeService.update(modelType);
                    attachment.setModelId(-2);
                    attachmentService.update(attachment);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("模型类型修改失败");
            return  returnErrorInfo(jo);
        }
        return  returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/deleted", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject deleted(@RequestParam(value = "modelTypeName", required = false) String modelTypeName,
                                      HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        try {
            modelTypeService.delete(modelTypeName);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("模型类型添加失败");
            return  returnErrorInfo(jo);
        }
        return  returnSuccessInfo(jo);
    }

}
