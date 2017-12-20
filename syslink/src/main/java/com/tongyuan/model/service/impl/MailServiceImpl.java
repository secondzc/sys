//package com.tongyuan.model.service.impl;
//
//import com.tongyuan.gogs.domain.GUser;
//import com.tongyuan.gogs.service.GUserService;
//import com.tongyuan.model.domain.ReviewMsg;
//import com.tongyuan.model.service.MailService;
//import com.tongyuan.model.service.ReviewMsgService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MailServiceImpl implements MailService{
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private JavaMailSender mailSender;
//    @Autowired
//    private GUserService gUserService;
//    @Autowired
//    private ReviewMsgService reviewMsgService;
//
//    @Value("${mail.fromMail.addr}")
//    private String from;
//    @Override
//    public void sendSimpleMail(Long toUserId,Long fromUserId) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        GUser toUser = gUserService.queryById(toUserId);
//        String toUserEmail = toUser.getEmail();
//        String subject = "审签流程提醒";
//        String content = "您好，您有一个待查看的审签流程任务需要您确认，请尽快登录办理。";
//
//        message.setFrom(from);
//        message.setTo(toUserEmail);
//        message.setSubject(subject);
//        message.setText(content);
//
//        try{
//            //发送邮件并以消息的形式进行提示
//            ReviewMsg reviewMsg = new ReviewMsg(fromUserId,toUserId,content,subject);
//            reviewMsgService.add(reviewMsg);
//            mailSender.send(message);
//            logger.info("简单邮件已经发送。");
//        }catch(Exception e) {
//            logger.error("发送简单邮件时发送异常！", e);
//        }
//    }
//}
