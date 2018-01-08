package com.tongyuan.model.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.LogMapper;
import com.tongyuan.model.domain.Log;
import com.tongyuan.model.service.LogService;
import com.tongyuan.tools.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/8/30.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;
    @Override
    public boolean add(Log log){
        return  this.logMapper.add(log);
    }
    @Override
    public boolean delete(long id)
    {
        return this.logMapper.delete(id);
    }


    @Override
    public boolean addLog(String title,String content){
        Log log = new Log();
        {

            com.tongyuan.model.domainmodel.Log log2 =new com.tongyuan.model.domainmodel.Log();
            log.setUserId(log2.getUserId1());
            log.setUserIp(log2.getUserIp());
            log.setUserName(log2.getUserName1());
            log.setRealName(log2.getRealName());
            log.setCreateDate(DateUtil.getCurrentTime());
            //设置标题
            log.setTitle(title);
            //设置日志内容
            log.setContent(content);
        }
        return  this.logMapper.add(log);
    }



    @Override
    public Page<Log> query(Map<String,Object>map)
    {
        Page<Log>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        logMapper.queryLog(map);
        return page;
    }



}
