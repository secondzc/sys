package com.tongyuan.Helper;

import org.dom4j.Attribute;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 这是一个xml的辅助类  zhangcy
 */
public class XmlHelper {
    /**
     * 自动将一个Element下的Attribute映射到某个类的属性中，只映射公共匹配的部分
     * 这里采用拼接set方法
     * 这个方法要求attribute采用驼峰法命名，第一个字母的大小写不加以限制
     * 元素的参数类型为String
     * @return
     */
    public static Object xml2Object(Object object, Element element){
        List<Attribute> attributes = element.attributes();
        Class clz = object.getClass();
        for(Attribute attribute:attributes) {
            String attributeName = attribute.getName();
            String attributeValue = attribute.getValue();
            StringBuilder sb= new StringBuilder("set");
            String first = String.valueOf(attributeName.charAt(0)).toUpperCase();
            sb.append(first).append(attributeName.substring(1));
            Method method=null;
            try {
                method = clz.getDeclaredMethod(sb.toString(),String.class);
            } catch (NoSuchMethodException e) {
                //没有匹配的set方法，则直接跳过
                continue;
            }
            try {
                method.invoke(object,attributeValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
