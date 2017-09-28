package com.tongyuan.model.controller;


import com.tongyuan.model.domain.User;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.model.gogsmodel.GUser;
import com.tongyuan.model.service.OperationlogService;
import com.tongyuan.model.service.UserService;
<<<<<<< HEAD
=======
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.EncodePasswd;
import com.tongyuan.util.IpUtil;
>>>>>>> e664b522a06118c9c4afab9a1e51300a0bdf3581
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.security.Guard;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 登录
 * Created by tengj on 2017/4/10.
 */
@Controller
public class LoginController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    OperationlogService operationlogService;



    private Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        if(!userName.equals("") && password!=""){
            User user =new User(userName,password);
            request.getSession().setAttribute("user",user);
            map.put("result","1");
        }else{
            map.put("result","0");
        }
        return map;
    }*/

    //使用gogs用户登录
    @RequestMapping(value = "api/loginUser", method = RequestMethod.GET )
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        Map<String,Object> map =new HashMap<String,Object>();
        Map<String,Object> params = new HashMap<String,Object>();
     //   params.put("userName",username);
        params.put("name",username);
        User user = userService.querUserByName(params);

        List<Map<String,Object>> list =  userService.queryUser(params);
        if(list.size()>0)
        {
          Map<String,Object> gUserMap = list.iterator().next();
          GUser gUser = new GUser(gUserMap);
          String passwdCheck = EncodePasswd.getEncryptedPassword(password,gUser.getSalt(),10000,50);
          if(passwdCheck.equalsIgnoreCase(gUser.getPasswd()))
          {

              if(rememberMe != null && "1".equals(rememberMe)){
                  Cookie userCookie = new Cookie("syslinkUser",username + "==" + password);
                  int seconds=60*60;
                  userCookie.setMaxAge(seconds);
                  response.addCookie(userCookie);
              }else{
                  Cookie[] cookies = request.getCookies();
                  if(cookies != null&&cookies.length>0){
                      for(Cookie cookie : cookies){
                          String cookieName = cookie.getName();
                          if("syslinkUser".equals(cookieName)){
                              Cookie new_cookie = new Cookie(cookieName, null);
                              new_cookie.setMaxAge(0);
                              response.addCookie(new_cookie);
                          }
                      }
                  }
              }
              HttpSession session = request.getSession();
            //  session.setAttribute("user", user);
              session.setAttribute("user", gUser);
              session.setAttribute("base_path", request.getContextPath());
              String lginIp = IpUtil.getIpAddr(request);
              Date loginDate =DateUtil.getTimestamp();
              userService.updateLoginstate(gUser.getId(),lginIp,loginDate);


              LoginedUserModel loginedUser =userService.CreateLoginedUser(gUser);
              setSessionUser(request,loginedUser);

              operationlogService.addLog("登录","登录系统",request);



              Cookie c = new Cookie("gogs_awesome",username);
              c.setDomain(".modelica-china.com");
              c.setMaxAge(60);
              c.setPath("/");
              response.addCookie(c);
              //             return "redirect:/model/getMyIndex";
              map.put("result","1");
              map.put("errormsg","登陆成功！");
          }else{
              request.setAttribute("loginFlag",1);
              //             return "login";
              map.put("result","0");
              map.put("errormsg","用户名/密码错误！");
          }
        }else{
            request.setAttribute("loginFlag", -1);
            //  return "login";
            map.put("result","0");
            map.put("errormsg","登陆失败，请输入正确的用户！");
        }








    /**
        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        currentUser.login(token);
        ShiroUser shiroUser = ShiroKit.getUser();
    **/
        /**
        if(user != null){
            if(user != null && password.equals(user.getPassWord())){
                if(rememberMe != null && "1".equals(rememberMe)){
                    Cookie userCookie = new Cookie("syslinkUser",username + "==" + password);
                    int seconds=60*60;
                    userCookie.setMaxAge(seconds);
                    response.addCookie(userCookie);
                }else{
                    Cookie[] cookies = request.getCookies();
                    if(cookies != null&&cookies.length>0){
                        for(Cookie cookie : cookies){
                            String cookieName = cookie.getName();
                            if("syslinkUser".equals(cookieName)){
                                Cookie new_cookie = new Cookie(cookieName, null);
                                new_cookie.setMaxAge(0);
                                response.addCookie(new_cookie);
                            }
                        }
                    }
                }
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("base_path", request.getContextPath());
                String lginIp = IpUtil.getIpAddr(request);
                Date loginDate =DateUtil.getTimestamp();
                userService.updateLoginstate(user.getId(),lginIp,loginDate);

                LoginedUserModel loginedUser =userService.CreateLoginedUser(user);
                setSessionUser(request,loginedUser);

                operationlogService.addLog("登录","登录系统",request);



                Cookie c = new Cookie("gogs_awesome",username);
                c.setDomain(".modelica-china.com");
                c.setMaxAge(60);
                c.setPath("/");
                response.addCookie(c);
   //             return "redirect:/model/getMyIndex";
                map.put("result","1");
                map.put("errormsg","登陆成功！");
            }else{
                request.setAttribute("loginFlag",1);
   //             return "login";
                map.put("result","1");
                map.put("errormsg","登陆成功！");
            }
        }else{
            request.setAttribute("loginFlag", -1);
          //  return "login";
            map.put("result","0");
            map.put("errormsg","登陆失败，请输入正确的用户！");
        }
        **/

        return map;
    }
}
