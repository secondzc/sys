package com.tongyuan.model.wrapper;

import com.tongyuan.model.dao.DepartMapper;
import com.tongyuan.util.SpringContextHolder;

import java.util.*;

/**
 * Created by yh on 2017/10/25.
 */
public class DepartWarpper extends BaseControllerWarpper {

    public DepartWarpper(List<Map<String, Object>> list) {
        super(list);
    }
    public DepartWarpper (Map<String,Object>map){super(map);}
    private DepartMapper departMapper = SpringContextHolder.getBean(DepartMapper.class);

    @Override
    public void warpTheMap(Map<String, Object> map) {

       setChildren(map);


    }


    public void setChildren (Map<String,Object>map)
    {

        Map<String,Object> parent = departMapper.queryById(Integer.parseInt(map.get("parentId").toString()));
        if(parent!=null)
        {
            map.put("parentName",parent.get("name").toString());
        }


        if(map.get("createDate")!=null)
        {
            String createDate = map.get("createDate").toString();
            map.put("createDate",createDate);

        }

        List<Integer> departPath = getDepartPath(map);
        Collections.reverse(departPath);
        map.put("parentId",departPath);

        List<Map<String,Object>>children = departMapper.queryByPid(Integer.parseInt(map.get("id").toString()));
       map.put("children",children);
       if(children!=null)
       {
           Iterator<Map<String,Object>>it  =  children.iterator();
           while (it.hasNext())
           {
               Map<String,Object> child = it.next();
               setChildren(child);
           }
       }

    }

    public List<Integer> getDepartPath(Map<String,Object>map)
    {
        List <Integer> path = new ArrayList<>();
        path.add(Integer.parseInt(map.get("parentId").toString()));
        Map<String,Object> parent =  departMapper.queryById(Integer.parseInt(map.get("parentId").toString()));
        if(parent!=null)
        {

            path.addAll(getDepartPath(parent));
        }


        return  path;
    }

}
