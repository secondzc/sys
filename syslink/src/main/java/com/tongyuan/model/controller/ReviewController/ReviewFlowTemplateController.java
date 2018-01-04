package com.tongyuan.model.controller.ReviewController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.domain.ReviewFlowTemplate;
import com.tongyuan.model.service.ReviewService.ReviewFlowTemplateService;
import com.tongyuan.tools.CurdUtil;
import com.tongyuan.tools.DateUtil;
import com.tongyuan.tools.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/6/26.
 */
@Controller
@RequestMapping("/api/reviewFlowTemplate")
public class ReviewFlowTemplateController extends BaseController {
    @Autowired
    private ReviewFlowTemplateService reviewFlowTemplateService;

    @RequestMapping("")
    public String reviewFlowTemplate(){
        return "review-flow-template";
    }

    /**
     * 新增审签模板
     *
     * 1.此模板不是默认，则直接添加
     * 2.此模板是默认，且之前没有默认的，则直接添加
     * 以上两种情况前端都不会弹出对话框
     * 3.此模板是默认，且之前有默认，且客户没有确认，则前端弹出对话框让客户确定
     * 4. 若客户确定，则添加并在前端显示成功信息
     *
     * @param request
     * @param response
     */
    @PostMapping("/addReviewFlowTemplate")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, HttpServletResponse response) throws Exception{
        JSONObject jo = new JSONObject();

        String templateName = request.getParameter("templateName");
        String description = request.getParameter("description");
        String assure = request.getParameter("assure");
        Long userId = getUserId();
        Boolean defaultTemplate = Boolean.valueOf(request.getParameter("defaultTemplate"));
        Timestamp timestamp = DateUtil.getCurrentTime();
        //存在的默认模板的id，若之前不存在则为null
        Long existDefault = -1L;
        if(defaultTemplate && "no".equals(assure)){
            existDefault = reviewFlowTemplateService.checkDefault();
            if(existDefault!=null){
                jo.put("changeDefault",true);
                jo.put("msg","ok");
                jo.put("code",200);
                return jo;
            }
        }
        ReviewFlowTemplate reviewFlowTemplate = new ReviewFlowTemplate();
        reviewFlowTemplate.setTemplateName(templateName);
        reviewFlowTemplate.setDescription(description);
        reviewFlowTemplate.setUserId(userId);
        reviewFlowTemplate.setDefaultTemplate(defaultTemplate);
        reviewFlowTemplate.setCreateTime(timestamp);
        reviewFlowTemplate.setLastUpdateTime(timestamp);
        reviewFlowTemplate.setAlreadyConfig(false);

        //若为第4种情况，即assure=yes，还需要将之前的默认模板改为false,其templateId为existDefault
        if("yes".equals(assure)){
            existDefault = reviewFlowTemplateService.checkDefault();
            if(existDefault!=null){
                reviewFlowTemplateService.setDefaultFalse(existDefault);
            }
        }

        int index = reviewFlowTemplateService.add(reviewFlowTemplate);
        if(index>0){
            jo.put("msg","ok");
            jo.put("code",200);
            jo.put("changeDefault",false);
        }
        return (JSONObject) JSONObject.toJSON(jo);
    }

    /**
     * 根据模板名字查询
     * @param request
     * @param response
     * @return
     */
    @PostMapping ("/queryReviewFlowTemplateByName")
    public void queryReviewFlowTemplateByName(
            HttpServletRequest request, HttpServletResponse response) throws  Exception{
        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
        String name = request.getParameter("templateName");
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("templateName", name);

        List<ReviewFlowTemplate> reviewFlowTemplates = reviewFlowTemplateService.queryByName(map);
        PageInfo<ReviewFlowTemplate> pageInfo = new PageInfo<ReviewFlowTemplate>(reviewFlowTemplates);
        JSONObject jo = new JSONObject();
        //records 结果 pages总页数  total总个数
        jo.put("records", reviewFlowTemplates);
        jo.put("pages", pageInfo.getPages());
        jo.put("total", pageInfo.getTotal());
        ServletUtil.createSuccessResponse(200, jo, response);

    }

    /**
     * 根据模板id删除模板
     * @param request
     * @param response
     */
    @PostMapping("/deleteByTemplateId")
    public void  delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String templateIds = request.getParameter("templateIds");
        int index = reviewFlowTemplateService.delete(templateIds.split(","));
        Map<String, Object> map = new HashMap<String, Object>();
        map = CurdUtil.curd(index);
        ServletUtil.createSuccessResponse(200, map, response);
    }

    @PostMapping("/setDefault")
    @ResponseBody
    public JSONObject setDefault(HttpServletRequest request, HttpServletResponse response) throws Exception{
        JSONObject jo = new JSONObject();
        Long templateId = Long.valueOf(request.getParameter("templateId"));
        String assure = request.getParameter("assure");

        Long existDefault = -1L;
        existDefault = reviewFlowTemplateService.checkDefault();
        //若之前不存在默认模板，或之前的默认模板id就是当前模板，或已经得到客户端确认，则正常设置，
        //否则要对客户端发送提醒 （并且如果要修改之前的默认，则之前的默认要置为false）
        if(existDefault==null || existDefault.equals(templateId) ||"yes".equals(assure)){
            reviewFlowTemplateService.setDefaultTrue(templateId);
            if(existDefault!=null && !existDefault.equals(templateId)){
                reviewFlowTemplateService.setDefaultFalse(existDefault);
            }
            jo.put("code",200);
            jo.put("reminder",false);
        }else{
            jo.put("code",200);
            jo.put("reminder",true);
        }
        return jo;
    }
}
