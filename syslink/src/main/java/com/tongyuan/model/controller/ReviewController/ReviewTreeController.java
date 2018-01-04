package com.tongyuan.model.controller.ReviewController;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.ReviewUserNode;
import com.tongyuan.model.service.ReviewService.ReviewUserNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/userTree")
public class ReviewTreeController {
    @Autowired
    private ReviewUserNodeService reviewUserNodeService;

    @PostMapping("/query")
    @ResponseBody
    public JSONObject query(){
        JSONObject jo = new JSONObject();
        ReviewUserNode root = reviewUserNodeService.query();

        jo.put("flag",true);
        jo.put("users",root);
        return jo;
    }
}
