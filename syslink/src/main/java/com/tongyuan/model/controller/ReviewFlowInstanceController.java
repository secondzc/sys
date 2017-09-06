package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tongyuan.model.domain.*;
import com.tongyuan.model.service.*;
import com.tongyuan.tools.CurdUtil;
import com.tongyuan.tools.ErrorMsg;
import com.tongyuan.model.domain.enums.ExceptionMsg;
import com.tongyuan.model.domain.result.Response;
import com.tongyuan.model.domain.result.ResponseData;
import com.tongyuan.tools.DateUtil;
import com.tongyuan.tools.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/6/24.
 */
@Controller
@RequestMapping("/api/reviewFlowInstance")
public class ReviewFlowInstanceController extends BaseController{
    @Autowired
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private ReviewFlowTemplateService reviewFlowTemplateService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private NodeInstanceService nodeInstanceService;
    @Autowired
    private StatusChangeService statusChangeService;
    @Autowired
    private ReviewModelService reviewModelService;

    @RequestMapping("")
    public String reviewFlowInstance(){
        return "review-flow-instance";
    }
    /**
     * 新增一个流程实例
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/addReviewFlowInstance",method = RequestMethod.POST)
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //第一步：填充reviewFlowInstance表
        Long instanceId = addReviewFlowInstance(request,response);

        //第二步：若添加流程实例成功，则用它来填充nodeInstance表
        //方法：已知templateId，templateId查reviewNode表得到所有的nodeId，
        //将nodeId和instanceId填入nodeInstance表即可。
        CompleteNodeInstance(request,response,instanceId);

        //第三步：将nodeInstance表中的第一个节点标志位从1变为2
        statusChangeService.updateNextStatus(instanceId,"1");

    }

    /**
     * 按照实例名字和实例状态查询，返回实例对象list 的json数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/queryByNameAndStatus",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public void queryByNameAndStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
        String name = request.getParameter("flowInstanceName");
        String tempStaus = request.getParameter("flowInstanceStatus");
        Byte status = null;
        if(tempStaus!=null && !(tempStaus.equals(""))){
            status = Byte.valueOf(tempStaus);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("instanceName", name);
        map.put("status", status);

        List<ReviewFlowInstance> reviewFlowInstances = reviewFlowInstanceService.queryByNameAndStatus(map);
        PageInfo<ReviewFlowInstance> pageInfo = new PageInfo<ReviewFlowInstance>(reviewFlowInstances);
        JSONObject jo = new JSONObject();
        jo.put("rows", reviewFlowInstances);
        jo.put("total", pageInfo.getPages());
        jo.put("records", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    /**
     * 根据id删除流程实例
     * @param request
     * @param response
     */
    @RequestMapping(value="/deleteByInstanceId",method= RequestMethod.POST)
    @ResponseBody
    public void deleteReviewFlowInstance(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String instanceIds = request.getParameter("instanceIds");
        int index = reviewFlowInstanceService.deleteByInstanceIds(instanceIds.split(","));

        Map<String, Object> map = new HashMap<String, Object>();
        map = CurdUtil.curd(index);
        ServletUtil.createSuccessResponse(200, map, response);
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
        List<ReviewModel> reviewModels = reviewModelService.queryByModelName(map);
        PageInfo<ReviewModel> pageInfo = new PageInfo<ReviewModel>(reviewModels);
        JSONObject jo = new JSONObject();
        jo.put("rows", reviewModels);
        jo.put("total", pageInfo.getPages());
        jo.put("records", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);
    }

    //-----------------------------------------------------------------------------
    /**
     * 1.填充流程实例表
     * @param request
     * @param response
     */
    public Long addReviewFlowInstance(HttpServletRequest request, HttpServletResponse response){
        String instanceName = request.getParameter("flowInstanceName");
        String description = request.getParameter("flowInstanceDescription");
        Long templateId = Long.valueOf(request.getParameter("templateId"));
        Long modelId = Long.valueOf(request.getParameter("modelId"));
        Timestamp timestamp = DateUtil.getCurrentTime();

        ReviewFlowInstance reviewFlowInstance = new ReviewFlowInstance();
        reviewFlowInstance.setInstanceName(instanceName);
        reviewFlowInstance.setDescription(description);
        reviewFlowInstance.setModelId(modelId);
        reviewFlowInstance.setTemplateId(templateId);
        reviewFlowInstance.setCreateTime(timestamp);
        reviewFlowInstance.setLastUpdateTime(timestamp);
        reviewFlowInstance.setStatus(new Byte("1"));

        int index =  reviewFlowInstanceService.add(reviewFlowInstance);
        Long instanceId = reviewFlowInstance.getInstanceId();
        Map<String, Object> map = new HashMap<String, Object>();
        map = CurdUtil.curd(index);
        ServletUtil.createSuccessResponse(200, map, response);
        return instanceId;
    }

    /**
     * 2.填充nodeInstance节点实例表
     */
    public void CompleteNodeInstance(HttpServletRequest request, HttpServletResponse response,Long iinstanceId){
        Long instanceId = iinstanceId;
        Long templateId = Long.valueOf(request.getParameter("templateId"));
        Map<String,Object> map = new HashMap<>();
        map.put("templateId",templateId);
        List<ReviewNode> reviewNodes = nodeService.queryByTemplateId(map);
        //遍历list，并填充
        Iterator<ReviewNode> iterator = reviewNodes.iterator();
        while(iterator.hasNext()){
            ReviewNodeInstance reviewNodeInstance = new ReviewNodeInstance();
            reviewNodeInstance.setNodeId(iterator.next().getNodeId());
            //填充第一步传过来的instanceId
            reviewNodeInstance.setInstanceId(instanceId);

            Timestamp timestamp = DateUtil.getCurrentTime();
            reviewNodeInstance.setCreateTime(timestamp);
            reviewNodeInstance.setLastUpdateTime(timestamp);
            reviewNodeInstance.setStatus(new Byte("1"));
            //向数据库中添加一条reviwNodeInstance
            nodeInstanceService.add(reviewNodeInstance);
        }
    }
}
