package com.tongyuan.util;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017-11-1.
 */
@Component
public class ModelUtil {
    //获取组件名称（点后面的名字）
    public String splitName(String name){
        String splitName[] = name.split("\\.");
        if(splitName.length >1) {
            return splitName[splitName.length-1];
        }else{
            return name;
        }

    }
}
