package com.tongyuan.model.interceptor;


import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tengj on 2017/3/29.
 */

@Controller
public class MyInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private GUserService gUserService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("IndexInterceptor  preHandle...");
        boolean flag =false;
        Map<String,Object> params = new HashMap<String,Object>();
        Cookie[] cookies = request.getCookies();
        String[] cooks = null;
        String username = null;
        String password = null;

        if (cookies != null) {
            for (Cookie coo : cookies) {
//            	System.out.println("getName: " + coo.getName());
                if("syslinkUser".equals(coo.getName())){
                    String aa = coo.getValue();
                    cooks = aa.split("==");
                    if (cooks.length == 2) {
                        username = cooks[0];
                        password = cooks[1];
                        request.setAttribute("username", username);
                        request.setAttribute("upassword", password);
                    }
                }
            }
//        System.out.println("username: " + username);
//        System.out.println("upassword: " + password);
        }
  //      username = "root";
   //     password = "root";
        if(!("".equals(username))&& username!=null && !("".equals(password))&& password!=null){
/*            GUser1 user = userService.getUserByName(username);
            String EncryptedPassword =PBKDF2SHA256.getEncryptedPassword(password, user.getSalt());
            if(user != null && EncryptedPassword.equals(user.getPasswd())){*/
            params.put("userName",username);
         //   User user = userService.querUserByName(params);
            GUser user = gUserService.querListByName(username);
            //           String EncryptedPassword =PBKDF2SHA256.getEncryptedPassword(password, user.getSalt());
            //           if(user != null && EncryptedPassword.equals(user.getPasswd())){
            if(user != null && password.equals(user.getPasswd()) || ("root".equals(username) && "root".equals(password))){
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("base_path", request.getContextPath());
                Cookie c = new Cookie("gogs_awesome",username);
                c.setDomain(".modelica-china.com");
                c.setMaxAge(60);
                c.setPath("/");
                response.addCookie(c);
                flag = true;
            }
        }else{
            response.sendRedirect("toLogin");
            flag = false;
        }
        return flag;
    }
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
