package com.tongyuan.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-5.
 */
@Component
public class FileX {
    //对压缩文件进行读取
    public Map<String,Object> readZip(String fileDir) throws IOException {
        Map<String , Object> dataMap = new HashMap<>();
        Integer g[] = new Integer [60];

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileInputStream fin  = null;
        try {
            fin = new FileInputStream(fileDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int read;
        byte[] bytes=new byte[1024];
        try {
            while((read = fin.read(bytes)) >0){
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fin.close();

        bytes = out.toByteArray(); // 这就是全部的字节数组了。
        out.close();
        long byteslength = bytes.length;
        System.out.println(byteslength);
        dataMap.put("byteslength",byteslength);
        dataMap.put("bytes",bytes);
        return dataMap;
    }


    /**
     * 主要是输入流的使用，最常用的写法
     * @param filePath 文件地址
     * @return  返回的String
     */
    public static String read(String filePath)
    {
        // 读取txt内容为字符串
        StringBuffer txtContent = new StringBuffer();
        // 每次读取的byte数
        byte[] b = new byte[8 * 1024];
        InputStream in = null;
        try
        {
            // 文件输入流
            in = new FileInputStream(filePath);
            while (in.read(b) != -1)
            {
                // 字符串拼接
                txtContent.append(new String(b));
            }
            // 关闭流
            in.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return txtContent.toString();
    }
}
