package com.tongyuan.model.controller;


import com.alibaba.fastjson.JSONObject;
import com.tongyuan.gogs.controller.InputStreamRunnable;
import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.domain.FileModel;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.User;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.service.*;
import com.tongyuan.pageModel.DirectoryModel;
import com.tongyuan.tools.ServletUtil;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.FileX;
import com.tongyuan.util.ResourceUtil;
import com.tongyuan.webservice.CommonServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/** 教程页面
 * Created by tengj on 2017/3/13.
 */
@Controller
@RequestMapping("/api/directory")
public class DirectoryController {
    @Autowired
    private LearnService learnService;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private ResourceUtil resourceUtil;
    @Autowired
    private FileModelService fileModelService;
    @Autowired
    private FileX fileX;
    @Autowired
    private CommonServiceImp CommonServiceImp;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelController modelController;
    @Autowired
    private UserService userService;


    private Date nowDate = new Date();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public String directory(){
        return "directory";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public void test(HttpServletRequest request , HttpServletResponse response) throws IOException {
//        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
//        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
//        String author = request.getParameter("author");
//        String title = request.getParameter("title");
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("page", page);
//        params.put("rows", rows);
//        params.put("author", author);
//        params.put("title", title);
//        List<LearnResouce> learnList=learnService.queryLearnResouceList(params);
//        PageInfo<LearnResouce> pageInfo =new PageInfo<LearnResouce>(learnList);
//
//        JSONObject jo=new JSONObject();
//        jo.put("rows", learnList);
//        jo.put("total", pageInfo.getPages());//总页数
//        jo.put("records",pageInfo.getTotal());//查询出的总记录数
//        ServletUtil.createSuccessResponse(200, jo, response);

     //   CommonServiceImp CommonServiceImp = new CommonServiceImp();

   //     ResourceUtil resourceUtil = new ResourceUtil();

/*        Integer g[] = new Integer [60];

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileInputStream fin  = null;
        try {
            fin = new FileInputStream("D:\\syslink.zip");
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
        System.out.println(byteslength);*/
        JSONObject result=new JSONObject();
        //文件解析的数据Map
        Map<String , Object> dataMap = new HashMap<>();
        dataMap = fileX.readZip("D:\\syslink.zip");
        boolean  uploadResult = CommonServiceImp.UploadFile("syslink", 0,(Long)dataMap.get("byteslength"),
                (byte[])dataMap.get("bytes"));
        if(!uploadResult){
            result.put("message","文件上传失败!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
            return;
        }
        // 模型相对路径xieyx/20170620.../
        String modelDir = resourceUtil.unzipFile("syslink", "xieyx");
        //输出文件的目录（modelDir是解压缩到的目录）
        System.out.println("modelDir==========" + modelDir + "*************");
        //获取到model解压缩的路径
        String modelPath =  resourceUtil.getModelPath(modelDir, "syslink");
        //遍历文件，对model库进行插入
        //	ResourceUtil.insertModelData(modelDir,"syslink",modelPath,"这是syslink项目");
       // String parentPath = ResourceUtil.getFileDriectory() + modelDir;
        String parentPath = modelPath;
        try {
            //遍历项目的文件
            resourceUtil.getSubFile(parentPath.substring(0,
                    parentPath.length()), parentPath.substring(0,
                    parentPath.length()), "这是syslink项目");
        }
        catch (Exception e){
            e.printStackTrace();
            result.put("message","模型上传失败!");
            result.put("flag",false);
            ServletUtil.createSuccessResponse(200, result, response);
            return;
        }
        result.put("message","模型上传成功!");
        result.put("flag",true);
        ServletUtil.createSuccessResponse(200, result, response);
        return;
    }

    //把项目在数据库中仿真，描述整个模型的层次结构
    public boolean createModel(File parentF, String filePath, String rootPath, String description){

        boolean result = false;
        try{
            //获取当前的路径
          //  String currentPath=parentF.getAbsolutePath().replace('\\', '/');
            String relativePath=parentF.getAbsolutePath().replace('\\', '/');
            String unzipPath= ResourceUtil.getFileDriectory();
            //获取实际路径
         //   String relativePath=currentPath.substring(unzipPath.length(), currentPath.length());
            String currentPath=relativePath.substring(unzipPath.length(), relativePath.length());
            FileModel directory  = new FileModel();
            //       directory.setId(UUID.randomUUID().toString());
            //       directory.setParentId(UUID.randomUUID().toString());
            directory.setName(parentF.getName());
            directory.setAbsoluteAddress(currentPath);
            directory.setRelativeAddress(relativePath);
            //获取当前的时间
            directory.setCreateTime(nowDate);
            directory.setDescription(description);
            directory.setDeleted(false);
            //如果当前的路径就是根目录
            if(currentPath.equals(rootPath)){
                // directory.setParentId();
            }else{
                //吧父目录id插入到子目录表当中
                //获取父目录地址
                String parentPath=parentF.getParent().replace('\\', '/');
                //创建一个父类目录对象
                FileModel parentDirectory = new FileModel();
                List<FileModel> directoryList = new ArrayList<FileModel>();
                directoryList = fileModelService.queryListByPath(parentPath);
                if(!directoryList.isEmpty()){
                    parentDirectory = directoryList.get(0);
                    directory.setParentId(parentDirectory.getId());
                }
            }

            fileModelService.add(directory);
            result = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

//    @RequestMapping(value = "/getTreeAsync",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public List<EasyuiTreeNode> getTreeAsync(HttpServletRequest request , HttpServletResponse response){
//        String name = request.getParameter("name");
//        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
//        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("page", page);
//        params.put("rows", rows);
//        List<Directory> allDirectoryList = directoryService.findAllDirectory();
//        List<Directory> rootDirectoryList = directoryService.findRootDirectory();
//        //公有库的树
//        List<EasyuiTreeNode> publicTree = new ArrayList<EasyuiTreeNode>();
//        for (Directory directory : rootDirectoryList) {
//            publicTree.add(tree(directory,allDirectoryList,true));
//        }
//        return publicTree;
//    }
//
//    /**
//     * 递归
//     * @param directory
//     * @param recursive
//     * @return
//     */
//    private EasyuiTreeNode tree(Directory directory,List<Directory> allDirectoryList, boolean recursive) {
//        EasyuiTreeNode node = new EasyuiTreeNode();
//        Map<String, Object> attributes = new HashMap<String, Object>();
//    //    attributes.put("taskId", directory.getTask().getTaskId());
//        if(directory != null){
//            node.setId(directory.getRelativeAddress());
//            node.setText(directory.getName());
//        }
//    //    node.setAttributes(attributes);
//        if (directory != null
//                && allDirectoryList.size() > 0) {
//            if (recursive) {
//                List<Directory> directoryList = new ArrayList<Directory>();
//                for (Directory  fileDir: allDirectoryList) {
//                    if(fileDir.getParentId() == directory.getId()){
//                        directoryList.add(fileDir);
//                    }
//                }
//                //Collections.sort(directoryList, new FilelibraryComparator());
//                List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
//                for (Directory m : directoryList) {
//                        node.setState("closed");
//                        EasyuiTreeNode t = tree(m,allDirectoryList, true);
//                        children.add(t);
//
//                }
//                node.setChildren(children);
//            }
//        }
//        return node;
//    }
//
//    @RequestMapping(value = "/getDirectoryList",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
//    @ResponseBody
//    public void  getDirectoryList(HttpServletRequest request , HttpServletResponse response){
//        String page = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数
//        String rows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("page", page);
//        params.put("rows", rows);
//        List<FileModel> rootDirectoryList = fileModelService.findRootDirectoryList(params);
//        PageInfo<FileModel> pageInfo =new PageInfo<FileModel>(rootDirectoryList);
//
//        JSONObject jo=new JSONObject();
//        jo.put("rows", rootDirectoryList);
//        jo.put("total", pageInfo.getPages());//总页数
//        jo.put("records",pageInfo.getTotal());//查询出的总记录数
//        ServletUtil.createSuccessResponse(200, jo, response);
////        return rootDirectoryList;
//    }

    //web端上传模型
    @RequestMapping(value = "/uploadDirectory",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    @CrossOrigin(origins = "http://gogs.modelica-china.com:80", maxAge = 3600)
    public void uploadDirectory(@RequestParam(value = "name",required = false)String name,
                                @RequestParam(value = "directoryId",required = false)Long directoryId,
                                HttpServletRequest request , HttpServletResponse response){

        StandardMultipartHttpServletRequest multiRequest = (StandardMultipartHttpServletRequest)request;
        MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();
         Long fileSize = map.get("file").get(0).getSize();
         String fileNames2[] = map.get("file").get(0).getOriginalFilename().split("\\.");
         String fileName="" ;
        byte[] bytes = new byte[0];
         if(fileNames2.length >=1){
             fileName = fileNames2[0];
         }
        try {
             bytes =  map.get("file").get(0).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("starting upload the file...");
        boolean result = false;
        //获取压缩包 C:/Temp/zip/文件名
        String filePath = resourceUtil.getzipPath() + fileName;
        System.out.println("filePath==" + filePath);
        try {
            System.out.println("starting writing file...");
            resourceUtil.writeFile(filePath, 0, fileSize, bytes);

            // 模型相对路径xieyx/20170620.../
            String modelDir = resourceUtil.unzipFile(fileName, "xieyx");
            //输出文件的目录（modelDir是解压缩到的目录）
            System.out.println("modelDir==========" + modelDir + "*************");
            //获取到model解压缩的路径
            String modelPath =  resourceUtil.getModelPath(modelDir, fileName);
            //遍历文件，对model库进行插入
            //	ResourceUtil.insertModelData(modelDir,"syslink",modelPath,"这是syslink项目");
            // String parentPath = ResourceUtil.getFileDriectory() + modelDir;
            String parentPath = modelPath;
            resourceUtil.getSubFile(parentPath.substring(0,
                    parentPath.length()), parentPath.substring(0,
                    parentPath.length()), "");
            Map<String, Object> xmlMap = new HashMap<String, Object>();
            //存放解析的所有xmlMap
            Map<String,Map> xmlAnalysisMap = new HashMap<>();
            //存放解析svg，info文件所在位置的Map
            Map<String,String> svgPath = new HashMap<>();
//            String name = fileName;
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("name",fileName);
            //查找到项目所在的位置
            List<FileModel> directoryList = fileModelService.queryListByName(params);
            //选取最近push的一个directory对象
            FileModel directory = new FileModel();
            if(!directoryList.isEmpty()){
                directory = directoryList.get(0);
            }else {
                result = false;
//                return result;
            }
            //获取文件所在位置，寻找xml文件所在的路径，解析xml吧所需的数据插入到数据库中
            //文件所在位置
            String fileXmlPath = directory.getRelativeAddress();
            //获取到xml所在的文件位置
            String xmlPath = "";
            xmlPath= resourceUtil.getXmlPath(fileXmlPath,xmlPath);
            //对xml进行解析,遍历xml文件下所有文件
            if(StringUtil.isNull(xmlPath)){
                result = false;
//                return result;
            }
            File xmlFilePath = new File(xmlPath);
            String[] subFiles = xmlFilePath.list();
            User user = userService.getUserByName(name);
            Model model = new Model();
            model.setName(subFiles[0].split("\\.")[0]);
            model.setFileId(directory.getId());
            model.setDirectoryId(directoryId);
            model.setClasses(ModelClasses.Package.getKey());
            model.setModelFilePath(filePath);
            model.setScope(false);
            model.setUserId(user.getId());
           // model.setUserId(1);
            model.setDeleted(false);
            if(modelService.queryModelByName(subFiles[0].split("\\.")[0]) == null){
                modelService.add(model);
            }
            //查找最外层空的model
            Model nullModel = modelService.queryModelByName(subFiles[0].split("\\.")[0]);
            for (int i = 0; i < subFiles.length; i++) {
                //查看文件的格式
                String [] fileNames = subFiles[i].split("\\.");
                //文件的类型
                String filePreType = fileNames[fileNames.length-2];
                String fileType = fileNames[fileNames.length-1];
                if(("xml").equals(fileType)){
                    xmlMap =  resourceUtil.analysisXmlPath(xmlFilePath +"/" +subFiles[i]);
                    xmlAnalysisMap.put(subFiles[i],xmlMap);
                    svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
                }else if("svg".equals(fileType)){
                    if("icon".equals(filePreType)){
                        svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
                    }else if("diagram".equals(filePreType)){
                        svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
                    }
                }else if("html".equals(fileType)){
                    svgPath.put(subFiles[i],xmlFilePath +"/" +subFiles[i]);
                }
            }
            //遍历xmlMap进行数据的插入
            for(Map.Entry<String,Map> entry : xmlAnalysisMap.entrySet()){
                //解析xmlmap 把数据存放到数据库
                modelController.insertData(entry,svgPath,nullModel,directory,directoryId);
            }

            //更新模型的层次结构
            //获取package下面的所有model
            List<Model> modelList = modelService.queryModelByParId(nullModel.getId());
            for (Model modelParent: modelList) {
                for (Model modelChild: modelList) {
                    int modelChildLen = modelChild.getName().split("\\.").length;
                    //匹配model名称是否有父子关系
                    int modelNameLen = modelChild.getName().split("\\.")[modelChildLen-1].length();
                    if( modelChildLen> 1){
                        if(modelParent.getName().equals(modelChild.getName().substring(0,modelChild.getName().length()- modelNameLen-1))){
                            modelParent.setParentId(modelChild.getId());
                            modelService.update(modelParent);
                        }
                    }
                }
            }
            this.doCmd(name,fileXmlPath,fileName);
            result = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        System.out.println("上传完毕！！！");
//        return result;

    }

    //web端增加模型目录
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public  JSONObject  add(@RequestParam(value = "id",required = false)Long id,
                      @RequestParam(value = "name",required = false)String name,
                      @RequestParam(value = "parent_id",required = false)Long parent_id,
                      HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            DirectoryModel directoryModel = new DirectoryModel();
                Directory directory = new Directory();
                directory.setName(name);
                directory.setParentId(parent_id);
                directory.setDeleted(false);
                directoryService.add(directory);
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("name",name);
                params.put("parent_id",parent_id);
                List<Directory> rootDirectoryList = directoryService.queryByParentName(params);
                directoryModel.setName(name);
                directoryModel.setId(rootDirectoryList.get(0).getId());
                directoryModel.setParentId(rootDirectoryList.get(0).getParentId()+"");
                jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",jsonObject);
        return (JSONObject) JSONObject.toJSON(jo);

    }

    //web端树状目录修改模型名称
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@RequestParam(value = "id",required = false)Long id,
                             @RequestParam(value = "name",required = false)String name,
                             HttpServletRequest request , HttpServletResponse response){

        JSONObject jo=new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            List<Directory> rootDirectoryList = directoryService.queryListById(id);
            if(rootDirectoryList.size() >0){
                Directory directory = rootDirectoryList.get(0);
                directory.setName(name);
                boolean res = directoryService.update(directory);
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setName(name);
                directoryModel.setId(id);
                directoryModel.setParentId(directory.getParentId()+"");
                jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
            }
        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",jsonObject);
        return (JSONObject) JSONObject.toJSON(jo);

    }

    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "id",required = false)Long id,
                             @RequestParam(value = "name",required = false)String name,
                             @RequestParam(value = "parent_id",required = false)Long parent_id,
                             HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        try{
            List<Directory> rootDirectoryList = directoryService.queryListById(id);
            rootDirectoryList.get(0).setDeleted(true);
            boolean res = directoryService.update(rootDirectoryList.get(0));

        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",id);
        return (JSONObject) JSONObject.toJSON(jo);

    }

    @RequestMapping(value="/list",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(@RequestParam(value = "parent_id",required = false)Long parent_id,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        List<JSONObject> directoryModelList = new ArrayList<>();
        try {
            List<Directory> rootDirectoryList = directoryService.queryListByParentId(parent_id);
            for (int i = 0; i <= rootDirectoryList.size() -1; i++) {
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setId(rootDirectoryList.get(i).getId());
                directoryModel.setName(rootDirectoryList.get(i).getName());
                directoryModel.setParentId(rootDirectoryList.get(i).getParentId()+"");
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                directoryModelList.add(jsonObject );

            }
        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",directoryModelList);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    @RequestMapping(value="/directoryList",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject directoryList(
            HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        List<JSONObject> directoryModelList = new ArrayList<>();
        try {
            List<Directory> allDirectoryList = directoryService.findAllDirectory();
            for (int i = 0; i <= allDirectoryList.size() -1; i++) {
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setId(allDirectoryList.get(i).getId());
                directoryModel.setName(allDirectoryList.get(i).getName());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                directoryModelList.add(jsonObject );

            }
        }catch(Exception e){
            e.printStackTrace();
            jo.put("status","1");
            jo.put("code",0);
            jo.put("msg","ok");
            return jo;
        }
        jo.put("status",1);
        jo.put("code",0);
        jo.put("msg","ok");
        jo.put("data",directoryModelList);
        return (JSONObject) JSONObject.toJSON(jo);
    }


    /*
  * name 是上传者
  * fileXmlPath 是文件所在位置
  * filName 为文件名称
  * */
    public void doCmd(String name, String fileXmlPath,String fileName){
        JSONObject jo = new JSONObject();
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        User userWeb = userService.querUserByName(params);
        String userName = userWeb.getUserName();
        String password = userWeb.getPassWord();
        String addr = "";
        try {
            addr = InetAddress.getLocalHost().getHostAddress();//获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String[] cmd = {"cmd", "/k", "cd /d C: & cd / & cd %HOMEPATH% & echo http://"+userName+":"+password+"@"+addr+"%3a3000>> .git-credentials" +
                "& cd / & cd "+fileXmlPath+   " & \"C:/Program Files (x86)/Git/bin/git.exe\" init " +
                "& \"C:/Program Files (x86)/Git/bin/git.exe\" remote add origin http://"+addr+"/"+userName+"/"+fileName+".git " +
                "& \"C:/Program Files (x86)/Git/bin/git.exe\" add . & \"C:/Program Files (x86)/Git/bin/git.exe\" commit -m \"model\" " +
                " & \"C:/Program Files (x86)/Git/bin/git.exe\" config --global credential.helper store & \"C:/Program Files (x86)/Git/bin/git.exe\" push origin master"};
        excuteCmd_multiThread(cmd,"GBK");
    }




    public static String excuteCmd_multiThread(String[] cmd, String encoding)
    {
        BufferedReader bReader=null;
        InputStreamReader sReader=null;
        try
        {
            Process p = Runtime.getRuntime().exec(cmd);

               /*为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞*/
            Thread t=new Thread(new InputStreamRunnable(p.getErrorStream(),"ErrorStream"));
            t.start();

               /*"标准输出流"就在当前方法中读取*/
            BufferedInputStream bis = new BufferedInputStream(p.getInputStream());

            if(encoding!=null && encoding.length()!=0)
            {
                sReader = new InputStreamReader(bis,encoding);//设置编码方式
            }
            else
            {
                sReader = new InputStreamReader(bis,"GBK");
            }
            bReader=new BufferedReader(sReader);

            StringBuilder sb=new StringBuilder();
            String line;

            while((line=bReader.readLine())!=null)
            {
                sb.append(line);
                sb.append("/n");
            }

            bReader.close();
            p.destroy();
            return sb.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //         return ErrorString;
            return "";
        }
        finally
        {
        }
    }

}

