package com.tongyuan.model.wrapper;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created by yh on 2017/11/15.
 */
public class test  {


    public List<T> test(List<T> list,int page ,int size)
    {
        if(list.size()==0)
        {
            return null;
        }
        int total = list.size();
        int pageSize = total/size;
        int lastPageSize = total%size;
        if(page<pageSize)
        {
            return list.subList((page-1)*size,page*size);
        }
        else
        {
            return list.subList((page-1)*size,(page-1)*size+lastPageSize);
        }
    }

}
