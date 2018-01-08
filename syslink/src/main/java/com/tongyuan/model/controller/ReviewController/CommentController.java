package com.tongyuan.model.controller.ReviewController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.service.ReviewService.NodeInstanceService;
import com.tongyuan.pageModel.CommentPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/comment")
public class CommentController extends BaseController {
    @Autowired
    private NodeInstanceService nodeInstanceService;

    @PostMapping("/queryCommentPages")
    @ResponseBody
    public JSONObject queryCommentPages(HttpServletRequest request) throws NumberFormatException{
            Long instanceId = Long.valueOf(request.getParameter("instanceId"));
            List<CommentPage> commentPages = nodeInstanceService.queryCommentPages(instanceId);
            JSONObject jo = new JSONObject();
            jo.put("flag",true);
            jo.put("commentPages",commentPages);
            String joString = JSONObject.toJSONStringWithDateFormat(jo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
            return JSONObject.parseObject(joString);
    }
}
