package com.tongyuan.model.service;

import com.github.pagehelper.Page;
import com.tongyuan.model.domain.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/8/30.
 */
public interface LogService {
    public boolean add(Log log);
    public boolean addLog(String title,String content);
    public boolean delete(long id);
    public List<Log> findAllLog();

   public List<Log> query(Map<String,Object> map) ;

   public Page<Log>find (Map<String,Object>map);


}
