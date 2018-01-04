package com.tongyuan.Helper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by zhangcy on 2018/1/4
 * 创建JSONObject对象的辅助类
 */
public class JsonObjectHelper {
    /**
     * 默认成功
     * @param object
     * @return
     */
    public static JSONObject create(Object object){
        return create(true,object);
    }

    /**
     *
     * @param flag 是否成功
     * @param object 返回的结果
     * @return
     */
    public static JSONObject create(boolean flag,Object object){
        JSONObject jo = new JSONObject();
        jo.put("flag",flag);
        jo.put("msg",object);
        return dateformat(jo);
    }

    /**
     * 分页返回的结果
     * @param records  结果集
     * @param pages  总页数
     * @param total  总结果数
     * @return
     */
    public static JSONObject create(Object records,int pages,Long total){
        JSONObject jo = new JSONObject();
        jo.put("records",records);
        jo.put("pages",pages);
        jo.put("total",total);
        return dateformat(jo);
    }

    /**
     * 返回要求的jsonObject的日期格式
     * @param jo
     * @return
     */
    public static JSONObject dateformat(JSONObject jo){
        String jsonString = JSONObject.toJSONStringWithDateFormat(jo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
        return JSONObject.parseObject(jsonString);
    }
}
