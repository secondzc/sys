package com.tongyuan.model.controller;

import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.model.domain.enums.ExceptionMsg;
import com.tongyuan.model.domain.result.Response;
import com.tongyuan.model.domain.result.ResponseData;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.tools.CommonConstant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Y470 on 2017/6/27.
 */
@Controller
public class BaseController {
    protected Logger logger = Logger.getLogger(this.getClass());

    protected HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpSession getSession(){
        return getRequest().getSession();
    }

    protected GUser getUser(){
        return (GUser) getSession().getAttribute("user");
    }



    protected Long getUserId(){
        Long id = 0L;
        GUser user = getUser();
        if(user!=null){
            id = user.getID();
        }
        return id;
    }

    protected String getUserName(){
        String userName = "";
        GUser user = getUser();
        if(user!=null){
            userName = user.getName();
        }
        return userName;
    }

    protected Response result(){
        return new Response();
    }

    protected Response result(ExceptionMsg exceptionMsg){
        return new Response(exceptionMsg);
    }

    protected ResponseData resultData(Object data){
        return new ResponseData(data);
    }



    /**
     * 获取保存在Session中的用户对象
     *
     * @param request 请求对象
     * @return
     */
    protected LoginedUserModel getSessionUser(HttpServletRequest request) {
        LoginedUserModel loginedUserModel = null;
        Object obj = request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
        if (obj!=null) {
            loginedUserModel = (LoginedUserModel)obj;
        }
        return loginedUserModel ;
    }



    /**
     * 获取当前用户用户名
     *
     * @param request 请求对象
     * @return
     */
    protected String getCurrentUserName(HttpServletRequest request)
    {
        //开发时使用
        if (getSessionUser(request)!=null&&getSessionUser(request).getProfile()!=null) {
            return getSessionUser(request).getProfile().getName();
        }
        else {
            return "获取失败";
        }
    }

    /**
     * 获取当前用户真名
     * @param request
     * @return
     */
    protected String getCurrentRealName(HttpServletRequest  request)
    {
        if (getSessionUser(request)!=null&&getSessionUser(request).getProfile()!=null) {
            return getSessionUser(request).getProfile().getFullName();
        }
        else{
            return "获取失败";
        }
    }

    /**
     * 获取当前用户id
     * @param request
     * @return
     */
    protected long getCurrentUserId(HttpServletRequest request)
    {
        if (getSessionUser(request)!=null&&getSessionUser(request).getProfile()!=null) {
            return getSessionUser(request).getProfile().getID();
        }
        else {
            return 0;
        }
    }


    /**
     * 获取当前用户权限
     * @param request
     * @return

    protected Map<String, PermissionValueModel> getUserPermission(HttpServletRequest request){

        if(getSessionUser(request)!=null&&getSessionUser(request).getPermissions()!=null){
            return getSessionUser(request).getPermissions();
        }
        else{
            return null ;
        }
    }
     */
    /**
     * 获取当前用户角色
     * @param request
     * @return

    protected List<RoleModel> getUserRoles(HttpServletRequest request){
        if(getSessionUser(request)!=null&&getSessionUser(request).getRoles()!=null){
            return getSessionUser(request).getRoles();
        }
        else{
            return null;
        }
    }
     */

    /**
     * 保存用户对象到Session中
     *
     * @param request 请求对象
     * @param user 用户
     */
    protected void setSessionUser(HttpServletRequest request,LoginedUserModel user) {
        request.getSession().setAttribute(CommonConstant.USER_CONTEXT,
                user);
    }

    /**
     * 判断当前使用户是否拥有字段名为string的权限
     * @param string
     * @param request2
     * @return

    protected boolean perJudge(String string,HttpServletRequest request2)
    {
        Map<String, PermissionValueModel> map = getUserPermission(request2);
        PermissionValueModel o1 = map.get(string);
        if(o1.getValue().equalsIgnoreCase("false"))
        {
            return false;
        }
        else{
            return true;
        }
    }
     */

    /**
     * 获取基于应用程序的url绝对路径
     *
     * @param request
     * @param url  以"/"打头的URL地址
     * @return 基于应用程序的url绝对路径

    public final String getAppbaseUrl(HttpServletRequest request, String url) {
        return request.getContextPath() + url;
    }
     */
}
