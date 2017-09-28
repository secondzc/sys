package com.tongyuan.model.wrapper;

import com.tongyuan.tools.DateUtil;
import com.tongyuan.util.UnixToDate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/26.
 */
public class GUserWarpper extends BaseControllerWarpper{

    public GUserWarpper(List<Map<String, Object>> list) {
        super(list);
    }



    @Override
    public void warpTheMap(Map<String, Object> map) {

      String createDate = map.get("createdUnix").toString();
      String  date = UnixToDate.TimeStamp2Date(createDate);
      map.put("createdUnix",date);
      map.remove("passwd");
      map.remove("rands");
      map.remove("salt");
//      map.remove("");
//      map.remove("");
//      map.remove("");
//      map.remove("");
//      map.remove("");


    }

}
