package com.tongyuan.model.wrapper;

import com.tongyuan.gogs.dao.GUserMapper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.util.SpringContextHolder;
import com.tongyuan.util.UnixToDate;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/11/6.
 */
public class RepositoryWarpper extends BaseControllerWarpper {

    private GUserMapper userMapper = SpringContextHolder.getBean(GUserMapper.class);

    public RepositoryWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        String createDate = map.get("createdUnix").toString();
        String  date = UnixToDate.TimeStamp2Date(createDate);
        map.put("createdUnix",date);
        if(map.get("updatedUnix")!=null)
        {
            String updateDate = map.get("updatedUnix").toString();
            String  date1 = UnixToDate.TimeStamp2Date(updateDate);
            map.put("updatedUnix",date);
        }



        GUser user = userMapper.queryById(Long.parseLong(map.get("ownerId").toString()));
        map.put("userName",user.getName());


    }
}
