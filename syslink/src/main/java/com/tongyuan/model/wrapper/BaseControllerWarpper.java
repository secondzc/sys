package com.tongyuan.model.wrapper;

import com.tongyuan.model.dao.AuthMapper;
import com.tongyuan.util.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器查询结果的包装类基类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:49:36
 */
public abstract class BaseControllerWarpper {

    public Object obj = null;


  ;
    private AuthMapper authMapper =SpringContextHolder.getBean(AuthMapper.class);





    public List<Map<String,Object>> getPermissionItem(Map<String,Object>map)
    {
   //    List<Map<String,Object>> list = permissionItemMapper.query(map);
        List<Map<String,Object>> list = authMapper.getChildren(map);
       for (Map<String,Object> objectMap : list)
       {
           objectMap.put("createDate",objectMap.get("createDate").toString());
       }
       return list;
    }


    public BaseControllerWarpper(Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings("unchecked")
    public Object warp() {
        if (this.obj instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) this.obj;
            for (Map<String, Object> map : list) {
                warpTheMap(map);
            }
            return list;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            warpTheMap(map);
            return map;
        } else {
            return this.obj;
        }
    }

    protected abstract void warpTheMap(Map<String, Object> map);




}
