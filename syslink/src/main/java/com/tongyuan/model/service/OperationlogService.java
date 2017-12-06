package com.tongyuan.model.service;

import com.github.pagehelper.Page;
import com.tongyuan.model.domain.Operationlog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/8/30.
 */
public interface OperationlogService {
    public boolean add(Operationlog operationlog);
    public boolean addLog(String title,String content,HttpServletRequest request);
    public boolean delete(long id);
    public List<Operationlog> findAllLog();

   public List<Operationlog> query(Map<String,Object> map) ;

   public Page<Operationlog>find (Map<String,Object>map);


}
