package com.tongyuan.util;

import com.tongyuan.model.controller.DirectoryController;
import com.tongyuan.model.service.DirectoryService;
import com.tongyuan.tools.StringUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xieyx on 2017-6-19.
 */
@Component
public class ResourceUtil {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

    private static final int BUFFEREDSIZE = 1024;

    @Autowired
    private DirectoryController directoryController;
    @Autowired
    private DirectoryService directoryService;
//    @Autowired
//    private FileModelService filemodelService;
    @Autowired
    private ResourceUtil resourceUtil;;
    @Autowired
    private ModelUtil modelUtil;

    public static final String getString(String key) {
        if (key == null || key.equals("") || key.equals("null")) {
            return "";
        }
        String result = "";
        try {
            result = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取临时文件目录
     * @return
     */
    public static final String getTempFileDriectory()
    {
        return getString("zipPath");
    }

    public static final String getGogsHttpPort(){
        return  getString("gogsHostPath");
    }

    public static final String getHttpPort(){
        return  getString("HostPath");
    }
    public static final String getMapped(){
        return getString("MappedPackage");
    }

    public static final String getExportFileUrl(){
        return getString("exportFileUrl");
    }

    public static final String getXiaZai(){
        return getString("xiazai");
    }


    /**
     * 获取文件仓目录
     * @return
     */
    public static final String getFileDriectory()
    {
        return getString("unzipPath");
    }

    /**
     * 获取zip文件临时根路径，若不存在，则创建
     * @return
     */
    public String getzipPath() {
        String zipPath = ResourceUtil.getTempFileDriectory();
        File file = new File(zipPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return ResourceUtil.getTempFileDriectory();
    }


    public String getGogsPath(){
        String path = ResourceUtil.getGogsHttpPort();
        if(StringUtil.isNull(path)){
            path = "";
        }
        return  path;
    }

    public String getLocalPath(){
        String path = ResourceUtil.getHttpPort();
        if(StringUtil.isNull(path)){
            path = "";
        }
        return  path;
    }
    public String getMappedPackage(){
        String path = ResourceUtil.getMapped();
        if(StringUtil.isNull(path)){
            path = "";
        }
        return  path;
    }

    public String getXiaZaiPath(){
        String path = ResourceUtil.getXiaZai();
        if(StringUtil.isNull(path)){
            path = "";
        }
        return  path;
    }

    public String getRelativeUrl(){
        String path = ResourceUtil.getExportFileUrl();
        if(StringUtil.isNull(path)){
            path = "";
        }
        return  path;
    }

    /**
     * 接收文件，将其写入zip文件临时地址
     * @param filePath
     * @param beginPos
     * @param length
     * @param data
     * @returnF
     */
    public boolean writeFile(String filePath, long beginPos, long length,
                             byte[] data) throws IOException {
        boolean result = false;
        File file = new File(filePath);
        RandomAccessFile ra = new RandomAccessFile(filePath, "rw");
        try {
            ra.seek(beginPos);
            ra.write(data, 0, (int) length);
            result = true;
        } catch (IOException ioe) {
            result = false;
            ioe.printStackTrace();
        } finally {
            ra.close();
        }
        return result;
    }


    /**
     * 获取当前时间
     * @return
     */
    public String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-MM-SS");
        return sdf.format(new Date()).toString();
    }

    /**
     * 获取文件仓地址
     * @return
     */
    public String getunzipPath() {
        return ResourceUtil.getFileDriectory();
    }



    /**
     * 解压算法:将压缩文件解压到指定目录
     * @param zipFilename
     * @param outputDirectory
     */
    public synchronized void unzip(String zipFilename, String outputDirectory)
            throws IOException {
        File outFile = new File(outputDirectory);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        ZipFile zipFile = new ZipFile(zipFilename);
        Enumeration en = zipFile.getEntries();
        ZipEntry zipEntry = null;
        while (en.hasMoreElements()) {
            zipEntry = (ZipEntry) en.nextElement();
            if (zipEntry.isDirectory()) {
                // mkdir directory
                String dirName = zipEntry.getName();
                dirName = dirName.substring(0, dirName.length() - 1);
                File f = new File(outFile.getPath() + File.separator + dirName);
                f.mkdirs();
            } else {
                // unzip file
                String strFilePath = outFile.getPath() + File.separator
                        + zipEntry.getName();
                File f = new File(strFilePath);
                if (!f.exists()) {
                    String[] arrFolderName = zipEntry.getName().split("/");
                    String strRealFolder = "";
                    for (int i = 0; i < (arrFolderName.length - 1); i++) {
                        strRealFolder += arrFolderName[i] + File.separator;
                    }
                    strRealFolder = outFile.getPath() + File.separator
                            + strRealFolder;
                    File tempDir = new File(strRealFolder);
                    tempDir.mkdirs();
                }
                f.createNewFile();
                InputStream in = zipFile.getInputStream(zipEntry);
                FileOutputStream out = new FileOutputStream(f);
                try {
                    int c;
                    byte[] by = new byte[BUFFEREDSIZE];
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    // out.flush();
                } catch (IOException e) {
                    throw e;
                } finally {
                    out.close();
                    in.close();
                }
            }
        }
        zipFile.close();
    }

    /**
     * 解压文件
     * @param fileName
     * @param userName
     * @return
     */
    public String unzipFile(String fileName, String userName) {
        //压缩文件的路径
        String filePath = getzipPath() + fileName;
        String renametaskName = getNowTime();
        //输出文件的路径
        String outputDirectory = getunzipPath() + userName + "/"
                + renametaskName + "/" + fileName;
        try {
            unzip(filePath, outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userName + "/" + renametaskName + "/";
    }

    public String unzipByte(String fileName,String userName ,byte[] data) throws Exception {
        //压缩文件的路径
        String filePath = getzipPath() + fileName;
        String renametaskName = getNowTime();
        //输出文件的路径
//        String outputDirectory = getunzipPath() + userName + "/"
//                + renametaskName + "/";
        String outputDirectory = getunzipPath() + userName + "/"
                + renametaskName ;
        try {
//            unzip(filePath, outputDirectory);
            modelUtil.unZipByte(data,outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userName + "/" + renametaskName + "/";
    }


    /**
     * xyx
     * 解压文件
     * @param filePath
     * @param outPutPath
     * @return
     */
    public void UnZip(String filePath,String outPutPath){
        try {
            unzip(filePath,outPutPath);
        } catch (IOException e){
            e.printStackTrace();
        }
    }





    /**
     * 获取模型路径
     * @param modelDir
     * @param modelName
     */
    public String getModelPath(String modelDir, String modelName) {
        String modelPath = "";
        String modelRealDir = getunzipPath() + modelDir;
        modelPath = modelRealDir;
//            modelPath = modelRealDir + modelName;
        return modelPath;
    }

    public void getSubFile(String filePath, String rootPath, String description){
        File parentF = new File(filePath);
        if (!parentF.exists()) {
            System.out.println("文件或文件夹不存在");
            return;
        }
        if(parentF.isFile()){
            directoryController.createModel(parentF,filePath,rootPath,description);
            return;
        }else{
            directoryController.createModel(parentF,filePath,rootPath,description);
            String[] subFiles = parentF.list();
            for (int i = 0; i < subFiles.length; i++) {
                getSubFile(filePath + "/" + subFiles[i],rootPath ,description);
            }
        }
    }

    /*
    * 获取xml文件所在文件的位置
    * */
    public String getXmlPath(String filePath, String xmlPath){
        String path= "";
        //查看这个根目录文件啊是否存在
        File rootPath = new File(filePath);
        if (!rootPath.exists()) {
            System.out.println("文件或文件夹不存在");
            return null;
        }
        String fileNameToLowerCase = rootPath.getName().trim().toLowerCase();
        //找到xml所在的文件夹位置并输出
        if(rootPath.isFile()){
            if(fileNameToLowerCase.endsWith(".xml")){
                path =  rootPath.getParent();
            }
            else if(fileNameToLowerCase.endsWith(".svg")){
                path =  rootPath.getParent();
            }
        }
        else{
            String[] subFiles = rootPath.list();
            for (int i = 0; i < subFiles.length; i++) {
                path = getXmlPath(rootPath +"/" +subFiles[i],path);
                if(!StringUtil.isNull(path)){
                    break;
                }
            }
        }
        return path;
    }

    public String getCAEXmlPath(String filePath, String caePath){
        String path= "";
        //查看这个根目录文件啊是否存在
        File rootPath = new File(filePath);
        if (!rootPath.exists()) {
            System.out.println("文件或文件夹不存在");
            return null;
        }
        String fileNameToLowerCase = rootPath.getName().trim().toLowerCase();
        //找到xml所在的文件夹位置并输出
        if(rootPath.isFile()){
            if(fileNameToLowerCase.endsWith(".xmlwrapper")){
                path =  rootPath.getParent();
            }
            else if(fileNameToLowerCase.endsWith(".lgw")){
                path =  rootPath.getParent();
            }
        }
        else{
            String[] subFiles = rootPath.list();
            for (int i = 0; i < subFiles.length; i++) {
                path = getCAEXmlPath(rootPath +"/" +subFiles[i],path);
                if(!StringUtil.isNull(path)){
                    break;
                }
            }
        }
        return path;
    }

    //解析xml
    public static Map<String,Object> analysisXmlPath(String xmlFilePath) {
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        //查看这个根目录文件啊是否存在
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            System.out.println("文件或文件夹不存在");
        }
        //把xml文件写解析成String
        if (xmlFile.isFile()) {
            Document doc = Dom4jUtil.load(xmlFilePath);
            if (doc == null) {
                return xmlMap;
            }
            Element root = doc.getRootElement();
            xmlMap = (Map<String, Object>) xml2map(root);
        }
        return xmlMap;
    }

    //吧xml解析成map
    public static Object xml2map(Element element) {
        System.out.println(element);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String,String> componentMap = new HashMap<String,String>();
        List<Element> elements = element.elements();
        if("Component".equals(element.getName())){
            map.put("ModelType",element.attribute(0).getValue());
        }
        if(element.getName().endsWith("_Model")){
            map.put(element.getName(),element.getText());
        }
        if (elements.size() == 0) {
            //这边是非固定格式内容的获取
            if(element.attributeCount() > 0) {
                for (int i = 0; i < element.attributeCount(); i++) {
                    componentMap.put(element.attribute(i).getName(), element.attribute(i).getValue());
                }
                return componentMap;
            }else{
                map.put(element.getName(), element.getText());
            }

            if (!element.isRootElement()) {
                return element.getText();
            }
        } else if (elements.size() == 1) {
            List<Object> list = new ArrayList<Object>();
            Map<String ,String > templateMap = new HashMap<>();
            if(elements.get(0).attributeCount() >0){
                for (int i = 0; i < elements.get(0).attributeCount(); i++) {
                    templateMap.put(elements.get(0).attribute(i).getName(), elements.get(0).attribute(i).getValue());
                }
                String parentName = "";
                if(elements.get(0).attribute(0) != null){
                    parentName = elements.get(0).attribute(0).getValue();
                }
                parentName = getParentName(elements.get(0),parentName);
                templateMap.put("parentName",parentName);
            }
            list.add(templateMap);
            map.put("componentVar", list);
            map.put(elements.get(0).getName(), xml2map(elements.get(0)));

        } else if (elements.size() > 1) {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Element> tempMap = new HashMap<String, Element>();
            for (Element ele : elements) {
                tempMap.put(ele.getName(), ele);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = tempMap.get(string).getNamespace();
                List<Element> elements2 = element.elements(new QName(string, namespace));
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size() > 1) {
                    List<Object> list = new ArrayList<Object>();
                    for(int j=0 ; j< elements2.size();j++){
                        //多层次的Component参数展示
                        //存储组件参数信息的map
                        Map<String ,String > templateMap = new HashMap<>();
                        if(elements2.get(j).attributeCount() >0){
                            for (int i = 0; i < elements2.get(j).attributeCount(); i++) {
                                templateMap.put(elements2.get(j).attribute(i).getName(), elements2.get(j).attribute(i).getValue());
                            }
                            String parentName = "";
                            parentName = getParentName(elements2.get(j),parentName);
                            templateMap.put("parentName",parentName);
                        }
                        list.add(templateMap);
//                        list.add(xml2map(ele));
                        list.add(xml2map(elements2.get(j)));

                    }
                    map.put(string, list);
                } else {
                    // 同名的数量不大于1则直接递归去
                    map.put(string, xml2map(elements2.get(0)));
                }
            }
        }

        return map;
    }

    /**
     *
     * 删除单个文件
     *
     * @param fileName 被删除的文件各1�7
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.print("删除单个文件" + fileName + "成功!");
                return true;
            } else {
                System.out.print("删除单个文件" + fileName + "失败!");
                return false;
            }
        } else {
            return true;
        }
    }


    //求element的父类名称
    public static String getParentName(Element element,String parentName){
        if(element.getParent().attributeCount() >0 ){
                parentName = element.getParent().attribute(0).getValue()+";" + parentName;
               parentName = getParentName(element.getParent(),parentName);
        }
        return parentName;
    }

    //吧web端传过来的文件存在映射文件目录下
    public String getStorePath(String userName,String fileName){
        //相对地址
        String relativePath = "";
        String relativeDir = userName+"/"+resourceUtil.getNowTime();
        File file = new File(resourceUtil.getunzipPath() + relativeDir);
        file.mkdirs();
        relativePath = relativeDir+"/"+fileName;
        return relativePath;
    }


}



