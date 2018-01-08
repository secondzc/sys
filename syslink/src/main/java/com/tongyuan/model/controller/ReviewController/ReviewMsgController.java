package com.tongyuan.model.controller.ReviewController;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.controller.BaseController;
import com.tongyuan.model.domain.ReviewMsg;
import com.tongyuan.model.service.ReviewService.ReviewMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/msg")
public class ReviewMsgController extends BaseController {
    @Autowired
    private ReviewMsgService reviewMsgService;

    @PostMapping("/getMsgs")
    @ResponseBody
    public JSONObject getMsg(){
        JSONObject jo = new JSONObject();
        Long userId = getUserId();
        List<ReviewMsg> reviewMsgList = reviewMsgService.queryByToUserId(userId);
        jo.put("flag",true);
        jo.put("msgs",reviewMsgList);
        return jo;
    }

    @PostMapping("/getMsgNum")
    @ResponseBody
    public JSONObject getMsgNum(){
        JSONObject jo = new JSONObject();
        Long userId = getUserId();
        int msgNum = reviewMsgService.queryCount(userId);
        jo.put("flag",true);
        jo.put("msgNum",msgNum);
        return jo;
    }

    @PostMapping("/delete")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request){
        JSONObject jo = new JSONObject();
        String[] ids = request.getParameter("ids").split(",");
        reviewMsgService.delete(ids);
        jo.put("flag",true);
        return jo;
    }
}
