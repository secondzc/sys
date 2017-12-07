package com.tongyuan.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.tongyuan.support.CollectionKit.join;

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
            return parentName.substring(0,parentName.length()-1);
        }
        return parentName;
    }


    /***
     * 解压Zip
     *已经废弃
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
                ByteArrayOutputStream fileByte = new ByteArrayOutputStream();
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

    /**
     * 根据客户端传过来的数据流直接生成文件，不需要写成文件再解压缩
     * @param datas
     * @param targetFileDir
     * @throws Exception
     */
    public void unZipByte(byte[] datas,String targetFileDir) throws Exception {

        // System.setProperty("sun.zip.encoding",
        // System.getProperty("sun.jnu.encoding"));
        File outFile = new File(targetFileDir);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(datas);
        ZipInputStream zis = new ZipInputStream(bis);
        ZipEntry entry = null;

        while ((entry = zis.getNextEntry()) != null) {

            // System.out.printf("条目信息： 名称%1$b, 大小%2$d, 压缩时间%3$d \n",
            // entry.getName(), entry.getSize(), entry.getTime());

            if (entry.isDirectory()) { // is dir
                // System.out.println(entry.getName() + "是一个目录");
                File f = new File(targetFileDir + File.separator + entry.getName());
                if (!f.exists())
                    f.mkdirs();
            } else { //

                byte[] data = getByte(zis); // 获取当前条目的字节数组

                InputStream is = new ByteArrayInputStream(data); // 把当前条目的字节数据转换成Inputstream流
                String[] names = entry.getName().split("/");

                String path = targetFileDir + File.separator;

                path += join(names, File.separator);

                //System.out.println(path);
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                    toWrite(is, file);
                }

            }
        }
    }
    /**
     * 向file文件写入字节
     * @param ins
     * @param file
     */
    public static void toWrite(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取条目byte[]字节
     * @param zis
     * @return
     */
    public byte[] getByte(InflaterInputStream zis) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            byte[] buf = null;
            int length = 0;

            while ((length = zis.read(temp, 0, 1024)) != -1) {
                bout.write(temp, 0, length);
            }

            buf = bout.toByteArray();
            bout.close();
            return buf;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String join(Object[] o, String flag) {
        StringBuffer str_buff = new StringBuffer();

        for (int i = 0, len = o.length; i < len; i++) {
            str_buff.append(String.valueOf(o[i]));
            if (i < len - 1)
                str_buff.append(flag);
        }
        return str_buff.toString();
    }

}
