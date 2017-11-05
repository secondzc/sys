package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.model.domain.ReviewNode;
import com.tongyuan.model.domain.ReviewNodeInstance;
import com.tongyuan.model.service.NodeService;
import com.tongyuan.pageModel.DetailPage;
import com.tongyuan.model.service.NodeInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Y470 on 2017/9/6.
 */
@Controller
@RequestMapping("/api/nodeInstance")
public class ReviewNodeInstanceController {
    @Autowired
    private NodeInstanceService nodeInstanceService;
    @Autowired
    private NodeService nodeService;

    @PostMapping("/details")
    @ResponseBody
    public JSONObject details(@RequestParam("instanceId")Long instanceId){
        //本方法是为了审签流程条准备的，返回节点实例，并返回当前激活的节点的sequence
        List<DetailPage> reviewNodeInstanceList = nodeInstanceService.details(instanceId);
        String sequence = "";
        for(DetailPage detail:reviewNodeInstanceList){
            if(new Byte((byte)2).equals(detail.getStatus())){
                sequence = detail.getNode().getSequence();
            }
        }

        JSONObject jo = new JSONObject();
        jo.put("records",reviewNodeInstanceList);
        jo.put("sequence",sequence);
        return jo;
    }
}
