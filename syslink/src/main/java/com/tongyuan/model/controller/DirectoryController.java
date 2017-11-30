package com.tongyuan.model.controller;


import com.alibaba.fastjson.JSONObject;
import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.gogs.controller.InputStreamRunnable;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.domain.FileModel;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.service.*;
import com.tongyuan.pageModel.DirectoryModel;
import com.tongyuan.tools.ServletUtil;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.FileX;
import com.tongyuan.util.ModelUtil;
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
    private GUserService gUserService;
    @Autowired
    private ModelUtil modelUtil;
    @Autowired
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private StatusChangeService statusChangeService;




    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public String directory(){
        return "directory";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public void test(HttpServletRequest request , HttpServletResponse response) throws IOException {
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
         Date nowDate = new Date();
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


    //web端上传模型
    @RequestMapping(value = "/uploadDirectory",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
    public void uploadDirectory(@RequestParam(value = "name",required = false)String name,
                                @RequestParam(value = "directoryId",required = false)Long directoryId,
                                @RequestParam(value = "scope",required = false)Boolean scope,
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
        System.out.println("starting writing file...");
//            resourceUtil.writeFile(filePath, 0, fileSize, bytes);
//            // 模型相对路径xieyx/20170620.../
//            String modelDir = resourceUtil.unzipFile(fileName, "xieyx");
        String modelDir = "";
        try {
            modelDir = resourceUtil.unzipByte(fileName, name,bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //输出文件的目录（modelDir是解压缩到的目录）
        System.out.println("modelDir==========" + modelDir + "*************");
        //获取到model解压缩的路径
        String modelPath =  resourceUtil.getModelPath(modelDir, fileName);
        //遍历文件，对model库进行插入
        //	ResourceUtil.insertModelData(modelDir,"syslink",modelPath,"这是syslink项目");
        String parentPath = modelPath;
        resourceUtil.getSubFile(parentPath.substring(0,
                parentPath.length()), parentPath.substring(0,
                parentPath.length()), "");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        //存放解析的所有xmlMap
        Map<String,Map> xmlAnalysisMap = new HashMap<>();
        //存放解析svg，info文件所在位置的Map
        Map<String,String> svgPath = new HashMap<>();
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
        }
        File xmlFilePath = new File(xmlPath);
        String[] subFiles = xmlFilePath.list();
        Model model = this.setPackageParam(name,subFiles,directory,directoryId,scope,filePath);
        Map<String, Object> param = this.isAddModelAndReview(subFiles,directoryId,model);
        //查找最外层空的model
        //修改成根据插入的分类id找到对应的package包
        Model nullModel = modelService.queryByNameAndDir(param);
       this.insertSvgPath(subFiles,xmlFilePath,xmlMap,svgPath,xmlAnalysisMap);
        //遍历xmlMap进行数据的插入
        for(Map.Entry<String,Map> entry : xmlAnalysisMap.entrySet()){
            //解析xmlmap 把数据存放到数据库
            modelController.insertData(entry,svgPath,nullModel,directory,directoryId);
        }
        //更新模型的层次结构
        //获取package下面的所有model
       this.updateModelFramwork(nullModel);
        //        this.doCmd(name,fileXmlPath,fileName);
        result = true;
        System.out.println("上传完毕！！！");
    }

    //web端增加模型目录
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public  JSONObject  add(@RequestParam(value = "id",required = false)Long id,
                      @RequestParam(value = "name",required = false)String name,
                      @RequestParam(value = "parent_id",required = false)Long parent_id,
                            @RequestParam(value = "userName",required = false)String userName,
                      HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            DirectoryModel directoryModel = new DirectoryModel();
                Directory directory = new Directory();
                directory.setName(name);
                directory.setParentId(parent_id);
                directory.setUserName(userName);
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

    @RequestMapping(value="/list",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject list(@RequestParam(value = "parent_id",required = false)Long parent_id,
                           @RequestParam(value = "scope",required = false)Boolean scope,
                           @RequestParam(value = "userName",required = false)String userName,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        List<JSONObject> directoryModelList = new ArrayList<>();
        List<Directory> rootDirectoryList = new ArrayList<>();
//        //公有模型目录
//        List<JSONObject> publicDirModelList = new ArrayList<>();
//        //私有模型目录
//        List<JSONObject> privateDirModelList = new ArrayList<>();
        try {
            if(scope != null && parent_id == 0){
                 rootDirectoryList = directoryService.getPrivateDir();
            }else if (parent_id == 0){
                rootDirectoryList = directoryService.getPublicDir();
            }else{
                rootDirectoryList = directoryService.queryListByParentId(parent_id);
            }

            for (int i = 0; i <= rootDirectoryList.size() -1; i++) {
                if(scope != null && userName.equals(rootDirectoryList.get(i).getUserName())){
                    DirectoryModel directoryModel = new DirectoryModel();
                    directoryModel.setId(rootDirectoryList.get(i).getId());
                    directoryModel.setName(rootDirectoryList.get(i).getName());
                    directoryModel.setParentId(rootDirectoryList.get(i).getParentId()+"");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                    directoryModelList.add(jsonObject );
                }else{
                    DirectoryModel directoryModel = new DirectoryModel();
                    directoryModel.setId(rootDirectoryList.get(i).getId());
                    directoryModel.setName(rootDirectoryList.get(i).getName());
                    directoryModel.setParentId(rootDirectoryList.get(i).getParentId()+"");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                    directoryModelList.add(jsonObject );
                }

            }
//            if(scope != null){
//                DirectoryModel privateDirModel = new DirectoryModel();
//                privateDirModel.setId(-1);
//                privateDirModel.setName("公有模型库");
//            }else{
//
//            }

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
//        Map<String,Object> params = new HashMap<>();
//        params.put("name",name);
        GUser userWeb = gUserService.querListByName(name);
        String userName = userWeb.getLowerName();
        String password = userWeb.getPasswd();
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
    @RequestMapping(value = "/getTreeItem",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getTreeItem(@RequestParam(value = "parent_id",required = false)Long parent_id,
                           HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //点击树节点父类列表
        List<Directory> treeItemList = new ArrayList<>();
        try {
            //所有的目录列表
            List<Directory> allDirectory = directoryService.findAllDirectory();
            //添加节点父类列表
            addTreeItem(parent_id, treeItemList, allDirectory);
            //所需的列表需要倒序
            Collections.reverse(treeItemList);
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
        jo.put("treeItem",treeItemList);
        return jo;
    }
    @RequestMapping(value = "/getDirectoryTree",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getDirectoryTree(HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        //点击树节点父类列表
        List<Map<String,Object>> directoryTree = new ArrayList<>();
        try {
            directoryTree = directoryService.queryMapListByParentId(Long.valueOf(0));
            if(directoryTree.size()>0)
            {
                for(Map<String,Object>map:directoryTree)
                {
                    setChidren(map);
                }
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
        jo.put("directoryTree",directoryTree);
        return jo;
    }
    public void addTreeItem(Long parentId,List<Directory> treeItemList,List<Directory> allDirectory){
        for (Directory directory: allDirectory) {
            if(directory.getId() == parentId){
                treeItemList.add(directory);
                addTreeItem(directory.getParentId(),treeItemList,allDirectory);
            }
        }
    }

    public void setChidren(Map<String ,Object>map)
    {
        List<Map<String,Object>>chidlren = directoryService.queryMapListByParentId(Long.parseLong(map.get("id").toString()));
        if(chidlren.size()>0)
        {
            map.put("children",chidlren);
            Iterator<Map<String,Object>> iterator = chidlren.iterator();
            while (iterator.hasNext())
            {
                Map<String,Object> child =iterator.next();
                setChidren(child);
            }
        }

    }


    @RequestMapping(value = "/checkModel",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject checkModel(@RequestParam(value = "fileName",required = false)String fileName,
                                 @RequestParam(value = "directoryId",required = false)Long directoryId,
                                 HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("fileName",fileName);
            params.put("directoryId",directoryId);
            Model model = modelService.queryByNameAndDirId(params);
            if(model == null){
                jo.put("status",1);
                jo.put("code",0);
                jo.put("msg","ok");
                return jo;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        //status 为0 ，存在该模型
        jo.put("status",0);
        jo.put("code",0);
        jo.put("msg","ok");
        return jo;

    }

    @RequestMapping(value = "/getPrivateDirId",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPrivateDirId(HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        Directory privateDir = new Directory();
        try{
            List<Directory> directoryList = directoryService.getPrivateDir();

            if(directoryList.size() != 1){
                return jo;
            }else {
                privateDir = directoryList.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jo.put("data",privateDir);
        return jo;
    }

    @RequestMapping(value = "/getPublicDirId",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPublicDirId(HttpServletRequest request , HttpServletResponse response){
        JSONObject jo=new JSONObject();
        Directory publicDir = new Directory();
        try{
            List<Directory> directoryList = directoryService.getPublicDir();

            if(directoryList.size() != 1){
                return jo;
            }else {
                publicDir = directoryList.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        jo.put("data",publicDir);
        return jo;
    }

    /**
     * 输入一个模型目录，判断是否是公有模型
     * true 公有模型 false 私有模型
     * @param directoryId
     * @return
     */
    public Boolean isScope(Long directoryId){
        Boolean result = false;
        Directory publicDir = new Directory();
        List<Directory> treeItemList = new ArrayList<>();
        try{
            List<Directory> directoryList = directoryService.getPublicDir();
            publicDir = directoryList.get(0);
            List<Directory> allDirectory = directoryService.findAllDirectory();
            //添加节点父类列表
            addTreeItem(directoryId, treeItemList, allDirectory);
            for (Directory directory : treeItemList) {
                if(directory.getId() == publicDir.getId()){
                    result = true;
                    return result;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }


    /**
     * 給不同的模型存入對應的模型svg地址
     * @param subFiles
     * @param xmlFilePath
     * @param xmlMap
     * @param svgPath
     * @param xmlAnalysisMap
     */
    public void insertSvgPath(String[] subFiles,File xmlFilePath,Map<String, Object> xmlMap,Map<String,String> svgPath,Map<String,Map> xmlAnalysisMap){
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
            else if("mo".equals(fileType)){
                //mo文件信息
                String textAllInfo = fileX.read(xmlFilePath +"/" +subFiles[i]);
                svgPath.put(subFiles[i],textAllInfo);
            }
        }
    }

    /**
     * 把對應的模型庫的目录进行更改
     * @param nullModel(包)
     */
    public void updateModelFramwork(Model nullModel){
        List<Model> modelList = modelService.queryModelByParId(nullModel.getId());
        for (Model modelParent: modelList) {
            for (Model modelChild: modelList) {
                String childParentName = modelUtil.getParentName(modelChild.getName());
                if(childParentName != null && !childParentName.equals("")){
                    if(childParentName.equals(modelParent.getName())){
                        modelChild.setParentId(modelParent.getId());
                        modelService.update(modelChild);
                    }
                }
            }
        }
    }

    public Model setPackageParam(String name,String[] subFiles,FileModel directory,Long directoryId,Boolean scope,String filePath){
        GUser user =  gUserService.querListByName(name);
        Model model = new Model();
        model.setName(subFiles[0].split("\\.")[0]);
        model.setFileId(directory.getId());
        model.setDirectoryId(directoryId);
        model.setClasses(ModelClasses.Package.getKey());
        model.setModelFilePath(filePath);
        model.setScope(scope);
        model.setUserId(user.getID());
        model.setCreateTime(new Date());
        // model.setUserId(1);
        model.setDeleted(false);
        return  model;
    }

    /**
     * 是否增加模型并申签
     * @param subFiles
     * @param directoryId
     * @param model
     * @return
     */
    public Map<String, Object> isAddModelAndReview(String[] subFiles,Long directoryId,Model model){
        Map<String, Object> param = new HashMap<>();
        param.put("fileName",subFiles[0].split("\\.")[0]);
        param.put("directoryId",directoryId);
        if(modelService.queryByNameAndDir(param) == null){
            //by:zhangcy  在这里加入了审签的代码
            modelService.add(model);
            Long modelId = model.getId();
            Long instanceId = reviewFlowInstanceService.startInstance(modelId);
            try{
                statusChangeService.updateNextStatus(instanceId,"1");
            }catch(SqlNumberException e){
                e.printStackTrace();
            }
        }
        return param;
    }
}


