package com.tongyuan.model.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/8.
 */
public class RoleWarpper extends BaseControllerWarpper {
    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        String createDate = map.get("createDate").toString();
        map.put("createDate",createDate);
        if(map.get("permissions")!=null&&(!map.get("permissions").equals("[]")&&(!map.get("permissions").equals(""))))
        {
            String a = map.get("permissions").toString();
            String b = a.replaceAll("[\\[\\]]", "");
            String []c = b.split(",");
            Integer []d = new Integer[c.length];
            for(int i = 0;i<c.length;i++)
            {
                d[i] = Integer.parseInt(c[i]);
            }
            Map<String ,Object> map1 = new HashMap<>();
            map.put("permissions",d);


        }
    }
}
