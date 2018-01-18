//package com.tongyuan.model.wrapper;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by yh on 2017/9/7.
// */
//public class AuthWarpper extends BaseControllerWarpper {
//
//    public AuthWarpper(List<Map<String, Object>> list) {
//        super(list);
//    }
//
//    @Override
//    public void warpTheMap(Map<String, Object> map) {
//
////        if(map.get("createDate")!=null)
////        {
////            String createDate = map.get("createDate").toString();
////            map.put("createDate",createDate);
////
////        }
//
//        map.put("children",getPermissionItem(map));
//    }
//}
