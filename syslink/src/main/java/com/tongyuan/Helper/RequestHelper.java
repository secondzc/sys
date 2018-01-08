package com.tongyuan.Helper;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcy on 2018/1/4
 */
public class RequestHelper {
    /**
     * 将请求包装成map
     * @param request 请求
     * @param args  参数
     * @return
     */
    public static Map<String,Object> createRequestMap(HttpServletRequest request, String ...args){
        assert args.length!=0;
        Map<String,Object> map = new HashMap<>();
        for(String attribute:args){
            Object object = request.getParameter(attribute);
            map.put(attribute,object);
        }
        return map;
    }

}
