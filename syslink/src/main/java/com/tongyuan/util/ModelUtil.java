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

    //获取模型的父类名
    /**
     * paramater 子模型名称（.split 长度大于二）
     */
    public String getParentName(String childName){
        String [] childModelArr = childName.split("\\.");
        String parentName = "";
        if(childModelArr.length >1){
            for (int i= 0; i<childModelArr.length -1; i++){
                    parentName += childModelArr[i] + ".";
            }
        }
        return parentName.substring(0,parentName.length()-1);
    }
}
