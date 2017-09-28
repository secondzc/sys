package com.tongyuan.model.wrapper;



import com.tongyuan.model.dao.DepartmentMapper;
import com.tongyuan.model.domain.Department;
import com.tongyuan.model.domain.User;
import com.tongyuan.util.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class UserWarpper extends BaseControllerWarpper {








    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }



    @Override
    public void warpTheMap(Map<String, Object> map) {
      long deptId = Integer.parseInt(map.get("departmentId").toString());
        Map<String,Object> department = getDept(deptId);
  //    Object  b = map.get("depts");
//      String a = map.get("depts").toString();
   //   map.put("departmentName",department.getName());
  //    List <String> deptIds =getDepts(department);
//      Collections.reverse(deptIds);
//      List<Integer> deptIds1 = new ArrayList<>();
//      for(String a : deptIds)
//      {
//          Integer b = Integer.parseInt(a);
//          deptIds1.add(b);
//      }
//      map.put("departmentId",deptIds1);

      if(map.get("gender")!=null)
      {
          Integer gender = Integer.parseInt(map.get("gender").toString());
          if(gender==1)
          {
              map.put("gender","男");
          }
          if(gender==0)
          {
              map.put("gender","女");
          }
      }

      String createDate = map.get("createDate").toString();
      map.put("createDate",createDate);
      if(map.get("updateDate")!=null)
      {
          map.put("updateDate",map.get("updateDate").toString());
      }

//      String []bcd = (String[]) abc.toArray();

    }

}
