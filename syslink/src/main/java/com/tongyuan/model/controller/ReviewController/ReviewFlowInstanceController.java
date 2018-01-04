package com.tongyuan.model.controller.ReviewController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.ReviewFlowInstance;
import com.tongyuan.model.service.ReviewService.NodeInstanceService;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.model.service.ReviewService.ReviewModelService;
import com.tongyuan.model.service.ReviewService.StatusChangeService;
import com.tongyuan.tools.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/6/24.
 */
@Controller
@RequestMapping("/api/reviewFlowInstance")
public class ReviewFlowInstanceController extends BaseController {
    @Autowired
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private StatusChangeService statusChangeService;
    @Autowired
    private ReviewModelService reviewModelService;
    @Autowired
    private NodeInstanceService nodeInstanceService;


    @RequestMapping("")
    public String reviewFlowInstance(){
        return "review-flow-instance";
    }

    @PostMapping("/cancel")
    @ResponseBody
    public JSONObject cancel(HttpServletRequest request){
        JSONObject jo = new JSONObject();
        Long instanceId = Long.valueOf(request.getParameter("instanceId"));
        reviewFlowInstanceService.cancel(instanceId);
        jo.put("flag",true);
        return jo;
    }


    /**
     * 新增一个流程实例
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/addReviewFlowInstance",method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Long modelId = Long.valueOf(request.getParameter("modelId"));
        Long instanceId = reviewFlowInstanceService.startInstance(modelId);
        //将nodeInstance表中的第一个节点标志位从1变为2
        statusChangeService.updateNextStatus(instanceId,"1");
        JSONObject jo = new JSONObject();
        jo.put("message","新增审签流程成功！");
        jo.put("flag",true);
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    /**
     * 按照实例名字和实例状态查询，返回实例对象list 的json数据
     * 通过名字和状态查询 提交的流程（只能查询自己提交的）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/queryByNameAndStatus",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public void queryByNameAndStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
        String name = request.getParameter("flowInstanceName");
        String status = request.getParameter("flowInstanceStatus");
        String[] statusArray = status.split(",");
        Long userId = getUserId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("instanceName", name);
        map.put("status", statusArray);
        map.put("userId",userId);

        List<ReviewFlowInstance> reviewFlowInstances = reviewFlowInstanceService.queryByNameAndStatus(map);
        PageInfo<ReviewFlowInstance> pageInfo = new PageInfo<ReviewFlowInstance>(reviewFlowInstances);
        JSONObject jo = new JSONObject();
        jo.put("records", reviewFlowInstances);
        jo.put("pages", pageInfo.getPages());
        jo.put("total", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    /**
     * 根据id删除流程实例(并不是删一行，而相当于是设一个隐藏的标志位）
     * @param request
     * @param response
     */
    @RequestMapping(value="/deleteByInstanceId",method= RequestMethod.POST)
    @ResponseBody
    public void deleteReviewFlowInstance(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String instanceIds = request.getParameter("instanceIds");
        int index = reviewFlowInstanceService.deleteByInstanceIds(instanceIds.split(","));

        JSONObject jo = new JSONObject();
        jo.put("flag",true);
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    /**
     * 按模型名字查询模型
     */
    @RequestMapping(value="/queryByModelName",method = RequestMethod.POST)
    @ResponseBody
    public void queryByModelName(HttpServletRequest request, HttpServletResponse response){
        String modelName = request.getParameter("modelName");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String,Object> map =new  HashMap<String,Object>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("modelName",modelName);
        List<Model> reviewModels = reviewModelService.queryByModelName(map);
        PageInfo<Model> pageInfo = new PageInfo<Model>(reviewModels);
        JSONObject jo = new JSONObject();
        jo.put("rows", reviewModels);
        jo.put("total", pageInfo.getPages());
        jo.put("records", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    @RequestMapping(value="/getModelIdByInstanceId",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getModelIdByInstanceId(HttpServletRequest request){
        JSONObject jo = new JSONObject();
        Long instanceId = Long.valueOf(request.getParameter("instanceId"));
        ReviewFlowInstance reviewFlowInstance = reviewFlowInstanceService.queryByInstanceId(instanceId);
        Long modelId = reviewFlowInstance.getModelId();
        jo.put("flag",true);
        jo.put("modelId",modelId);
        return jo;
    }
}
