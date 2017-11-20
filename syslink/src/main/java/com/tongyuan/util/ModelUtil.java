package com.tongyuan.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipInputStream;

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


    /***
     * 解压Zip
     *
     * @param data
     * @return
     */
    public  byte[] unZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 根据byte数组，生成文件
     * @param bfile 数据流
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public  void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
