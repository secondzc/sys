package com.tongyuan.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.tongyuan.support.CollectionKit.join;

/**
 * Created by xyx on 2017-11-1.
 */
@Component
public class ModelUtil {
    //获取组件名称（点后面的名字）
    public static String splitName(String name){
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

    public static String getParentNameByPara(String childName,String parameter){
        String [] childModelArr = childName.split(parameter);
        String parentName = "";
        if(childModelArr.length >1){
            for (int i= 0; i<childModelArr.length -1; i++){
                parentName += childModelArr[i] + parameter;
            }
            return parentName.substring(0,parentName.length());
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


    /**
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1,byte[] byte2){
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }


    public void copyDirectory(String srcDirName, String descDirName){
        File srcDir = new File(srcDirName);
        File descDir = new File(descDirName);
        findFiles(srcDir,descDir);
    }


    //复制文件夹获取两个文件夹对象
    public  void findFiles(File file, File file2)
    {
        if (file.exists())
        {
            if (file.isFile())
            {// 是文件的话直接复制到目标文件夹内,并打印文件路径
                copyFiles(file, file2);
                System.out.println(file.getAbsolutePath() + "已创建");
            }
            else if (file.isDirectory())
            {// 是文件夹的话创建指向新目录的文件实例,调用mkdirs创建多级文件夹
                File file5 = new File(file2.getAbsolutePath() + "\\"
                        + file.getName());
                file5.mkdirs();

                // 接收此文件夹下所有文件目录 实例
                File[] file3 = file.listFiles();

                // 递归出口
                if (file3 == null)
                {
                    return;
                }
                // 遍历文件,递归,循环判断
                for (File file4 : file3)
                {
                    findFiles(file4, file5);
                    System.out.println(file5.getAbsolutePath() + "已创建");
                }
            }
        }
        else
        {
            return;
        }
    }

    /**
     * 把file的内容复制到file2的方法
     * @param file
     * @param file2
     */
    public  void copyFiles(File file, File file2)
    {
        FileInputStream fis;
        FileOutputStream fos;

        // 包装流 带缓冲区
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try
        {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(file2.getAbsolutePath() + "\\"
                    + file.getName());
            bis = new BufferedInputStream(fis);
            // 构造10M的缓冲区
            bos = new BufferedOutputStream(fos, 10 * 1024 * 1024);

            // 文件格式多样,用字节流逐块接收
            byte[] bytes = new byte[1024];
            int len = 0;

            // 记录复制时间 逐块读取到内存并写出到新文本
            long start = System.currentTimeMillis();

            while ((len = bis.read(bytes, 0, 1024)) != -1)
            {
                bos.write(bytes, 0, len);
            }

            long end = System.currentTimeMillis();

            System.out.println("复制" + file.getName() + "花费时间" + (end - start)
                    + "ms");

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bis != null)
            {
                try
                {//关闭流
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFileContent(FileInputStream fileInputStream){
        String  relativePath =new String();
        try {
            byte[] b=new byte[fileInputStream.available()];//新建一个字节数组
            fileInputStream.read(b);//将文件中的内容读取到字节数组中
            fileInputStream.close();
            relativePath = new String(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //再将字节数组中的内容转化成字符串形式输出
        System.out.println(relativePath);
        return  relativePath;
    }




}
