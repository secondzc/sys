package com.tongyuan.model.wrapper;

import com.tongyuan.util.SpringContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/11.
 */
public class UserRoleWarpper extends BaseControllerWarpper {




    public UserRoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        String createDate = map.get("createDate").toString();
        map.put("createDate",createDate);
        if(map.get("roles")!=null&&(!map.get("roles").equals("[]")))
        {
            String a = map.get("roles").toString();
            String b = a.replaceAll("[\\[\\]]", "");
            String []c = b.split(",");
            Integer []d = new Integer[c.length];
            for(int i = 0;i<c.length;i++)
            {
                d[i] = Integer.parseInt(c[i]);
            }
            Map<String ,Object> map1 = new HashMap<>();
            map.put("roles",d);


        }


    }
}
