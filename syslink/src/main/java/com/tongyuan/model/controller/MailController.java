//package com.tongyuan.model.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.tongyuan.model.service.MailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//@RequestMapping("/api/mail")
//public class MailController extends BaseController{
//    @Autowired
//    private MailService mailService;
//
//    @PostMapping("/send")
//    @ResponseBody
//    public JSONObject send(HttpServletRequest request, HttpServletResponse response){
//        JSONObject jo = new JSONObject();
//        Long toUserId = Long.valueOf(request.getParameter("toUserId"));
//        Long fromUserId = getUserId();
//       mailService.sendSimpleMail(toUserId,fromUserId);
//        jo.put("flag",true);
//        jo.put("msg","发送简单邮件成功！");
//        return jo;
//    }
//}
