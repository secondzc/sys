package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.DTO.FileTypeDto;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.FileType;
import com.tongyuan.model.service.AttachmentService;
import com.tongyuan.model.service.FileTypeService;
import com.tongyuan.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-12-22.
 */
@Controller
@RequestMapping("/api/filetype")
public class FileTypeController extends  BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileTypeService fileTypeService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private ResourceUtil resourceUtil;

    @RequestMapping(value = "/getFileTypeList",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getFileTypeList(HttpServletRequest request , HttpServletResponse response){
        JSONObject jo = new JSONObject();
        List<FileTypeDto> fileTypeList = new ArrayList<>();
        try {
            fileTypeList = fileTypeService.getFileTypeList();
            for (FileTypeDto fileType : fileTypeList) {
                fileType.setIconPath("http://"+resourceUtil.getLocalPath()+ resourceUtil.getMapped()+ resourceUtil.getunzipPath().substring(7) + fileType.getIconPath());
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取模型类型列表失败");
            return returnErrorInfo(jo);
        }
        jo.put("fileTypeList",fileTypeList);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/addFileType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject addFileType(@RequestParam(value = "name", required = false) String name,
                                @RequestBody Map<String,Object> map,
                                HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        String fileName = (String) map.get("name");
        String iconName = (String) map.get("photoName");
        try {
            List<Attachment> iconList = attachmentService.getInsertIcon();
            FileType fileType = new FileType();
            for (Attachment attachment : iconList) {
                if (attachment.getName().equals(iconName)) {
                    fileType.setAttachmentId(attachment.getId());
                    fileType.setExt(fileName.toLowerCase().trim());
                    fileTypeService.add(fileType);
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
    public JSONObject checkName(@RequestParam(value = "fileTypeName", required = false) String fileTypeName,
                             HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<FileTypeDto> fileTypeList = new ArrayList<>();
        try {
            fileTypeList = fileTypeService.getFileTypeList();
            for (FileTypeDto fileTypeDto : fileTypeList) {
                if(fileTypeDto.getExt().equals(fileTypeName)){
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

    @RequestMapping(value = "/updateFileType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject updateFileType(@RequestParam(value = "name", required = false) String name,
                                   @RequestBody Map<String,Object> map,
                                   HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        String fileName = (String) map.get("name");
        String iconName = (String) map.get("photoName");
        try {
            List<Attachment> iconList = attachmentService.getInsertIcon();
            FileType fileType = fileTypeService.getByExt(fileName);
            for (Attachment attachment : iconList) {
                if (attachment.getName().equals(iconName)) {
                    fileType.setAttachmentId(attachment.getId());
                    fileTypeService.update(fileType);
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
    public JSONObject deleted(@RequestParam(value = "fileTypeName", required = false) String fileTypeName,
                                      HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        try {
            fileTypeService.delete(fileTypeName);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("模型类型添加失败");
            return  returnErrorInfo(jo);
        }
        return  returnSuccessInfo(jo);
    }

}
