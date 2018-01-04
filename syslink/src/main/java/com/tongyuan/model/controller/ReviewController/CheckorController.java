package com.tongyuan.model.controller.ReviewController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.tongyuan.Helper.RequestHelper;
import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.service.*;
import com.tongyuan.model.service.ReviewService.NodeInstanceService;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.model.service.ReviewService.ReviewModelService;
import com.tongyuan.model.service.ReviewService.StatusChangeService;
import com.tongyuan.pageModel.CheckorPage;
import com.tongyuan.model.domain.ReviewFlowInstance;
import com.tongyuan.tools.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Y470 on 2017/7/7.
 */
@Controller
@RequestMapping("/api/checkor")
public class CheckorController extends BaseController {
    @Autowired
    private StatusChangeService statusChangeService;
    @Autowired
    private CheckorService checkorService;
    @Autowired
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private ReviewModelService reviewModelService;
    @Autowired
    private NodeInstanceService nodeInstanceService;
    @Autowired
    private GUserService gUserService;

    @RequestMapping("")
    public String checkor(){
        return "checkor";
    }

    @PostMapping(value="/agree")
    public void agree(HttpServletRequest request, HttpServletResponse response) throws SqlNumberException{
        Long id = Long.valueOf(request.getParameter("id"));
        statusChangeService.agree(id);

        JSONObject result = new JSONObject();
        result.put("message","操作成功!");
        result.put("flag",true);
        ServletUtil.createSuccessResponse(200,result,response);
    }

    @PostMapping(value="/disagree")
    public void disagree(HttpServletRequest request, HttpServletResponse response){
        Long id = Long.valueOf(request.getParameter("id"));
        statusChangeService.disagree(id);

        JSONObject result = new JSONObject();
        result.put("message","操作成功!");
        result.put("flag",true);
        ServletUtil.createSuccessResponse(200,result,response);
    }

    //展示给审签者的review_node_instance列表
    @PostMapping(value="/queryByReviewer")
    public void queryByReviewer(HttpServletRequest request, HttpServletResponse response){
        Long userId = getUserId();
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String modelName = request.getParameter("modelName");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("modelName",modelName);
        //测试用
        //Long userId = 4L;
        map.put("userId",userId);

        List<CheckorPage> chekorPages = checkorService.queryByReviewer(map);
        PageInfo<CheckorPage> pageInfo = new PageInfo<>(chekorPages);
        JSONObject jo = new JSONObject();
        jo.put("records", chekorPages);
        jo.put("pages", pageInfo.getPages());
        jo.put("total", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    //点击详细信息，可查看审签实例对应的模型信息
    @PostMapping(value="/showModelDetails")
    @ResponseBody
    public JSONObject showModelDetails(HttpServletRequest request, HttpServletResponse response){
        Long instanceId = Long.valueOf(request.getParameter("instanceId"));
        ReviewFlowInstance reviewFlowInstance=reviewFlowInstanceService.queryByInstanceId(instanceId);
        Long modelId = reviewFlowInstance.getModelId();
        //ReviewModel reviewModel = reviewModelService.queryByModelId(modelId);
        Model reviewModel = reviewModelService.queryByModelId(modelId);
        Long userId = reviewModel.getUserId();
        GUser guser = gUserService.queryById(userId);
        String userName = guser.getName();
        JSONObject js = new JSONObject();
        js.put("reviewModel",reviewModel);
        js.put("userName",userName);
        js.put("flag",true);
        //ServletUtil.createSuccessResponse(200,js,response);
        String joString = JSONObject.toJSONStringWithDateFormat(js,"yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
        return JSONObject.parseObject(joString);
    }

    //于上面queryByReviewer方法的区别是，这个方法是查看所有的记录，包括历史记录
    @PostMapping(value="/queryAllByReviewer")
    public void queryAllByReviewer(HttpServletRequest request, HttpServletResponse response){
        Long userId = getUserId();
//        String page = request.getParameter("page");
//        String rows = request.getParameter("rows");
//        String modelName = request.getParameter("modelName");
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("page",page);
//        map.put("rows",rows);
//        map.put("modelName",modelName);
        Map<String,Object> map = RequestHelper.createRequestMap(request,"page","rows","modelName");
        map.put("userId",userId);

        List<CheckorPage> chekorPages = checkorService.queryAllByReviewer(map);
        PageInfo<CheckorPage> pageInfo = new PageInfo<>(chekorPages);
        JSONObject jo = new JSONObject();
        jo.put("records", chekorPages);
        jo.put("pages", pageInfo.getPages());
        jo.put("total", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    @PostMapping("/disagreeWithComment")
    @ResponseBody
    public JSONObject disagreeWithComment(HttpServletRequest request)throws NumberFormatException{
        Long id = Long.valueOf(request.getParameter("id"));
        String comment = request.getParameter("comment");
        statusChangeService.disagree(id);
        Map<String,Object> commentMap = new HashMap<>();
        commentMap.put("id",id);
        commentMap.put("comment",comment);
        nodeInstanceService.updateComment(commentMap);

        JSONObject result = new JSONObject();
        result.put("message","操作成功!");
        result.put("flag",true);
        return result;
    }

    @PostMapping("/agreeWithComment")
    @ResponseBody
    public JSONObject agreeWithComment(HttpServletRequest request) throws NumberFormatException,SqlNumberException{
        Long id = Long.valueOf(request.getParameter("id"));
        String comment = request.getParameter("comment");
        statusChangeService.agree(id);
        Map<String,Object> commentMap = new HashMap<>();
        commentMap.put("id",id);
        commentMap.put("comment",comment);
        nodeInstanceService.updateComment(commentMap);

        JSONObject result = new JSONObject();
        result.put("message","操作成功!");
        result.put("flag",true);
        return result;
    }
}
