package com.tongyuan.tools;



/**
 *整个应用通用的常量 
 *
 *@author liuq
 *
 */
public class CommonConstant
{
   /**
    * 用户对象放到Session中的键名称
    */
   public static final String USER_CONTEXT = "USER_CONTEXT";
   
 
   
   /**
    * 将登录前的URL放到Session中的键名称
    */
   public static final String LOGIN_TO_URL = "toUrl";
   
   public static final String BasePath = "http://localhost:80";
   
   public static final String WEBROOT ="C://Temp//FileLibrary//";
   
   public static final String Admin ="A8D8FD0C-677D-4D3D-BF38-DB16B0B4E90B";
   

 
   /**
    * 是否是来自WebService的请求
    */
   public static boolean ISWSREQUEST = false;

   /**
    * 记录是否被删除
    * 0-未被删除
    * 1-已被删除
    */
   public static final short NO_DELETED = 0;
   public static final short DELETED = 1;
   
   /**
    * 记录是否可删除
    * 0-不可删除
    * 1-可删除
    */
   public static final short NO_DELETABLE=0;
   public static final short DELETABLE = 1;
   
}
