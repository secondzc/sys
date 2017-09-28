package com.tongyuan.model.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tongyuan.model.dao.OperationlogMapper;
import com.tongyuan.model.domain.Operationlog;
import com.tongyuan.model.domainmodel.Log;
import com.tongyuan.model.service.OperationlogService;
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
public class OperationlogServiceImpl implements OperationlogService{

    @Autowired
    OperationlogMapper operationlogMapper;
    @Override
    public boolean add(Operationlog operationlog){
        return  this.operationlogMapper.add(operationlog);
    }

    @Override
    public boolean addLog(String title,String content,HttpServletRequest request){
        Operationlog log = new Operationlog();
        {

            Log log2 =new Log(request);
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
        return  this.operationlogMapper.add(log);
    }

    @Override
    public List<Operationlog> findAllLog()
    {

        return this.operationlogMapper.findAllLog();
    }


    @Override
    public List<Operationlog> query(Map<String,Object> map)
    {
        PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));

        return this.operationlogMapper.queryLog(map);
    }

    @Override
    public Page<Operationlog>find(Map<String,Object>map)
    {
        Page<Operationlog>page = PageHelper.startPage(Integer.parseInt(map.get("pageIndex").toString()), Integer.parseInt(map.get("pageSize").toString()));
        operationlogMapper.queryLog(map);
        return page;
    }



}
