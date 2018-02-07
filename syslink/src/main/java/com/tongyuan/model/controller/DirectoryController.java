package com.tongyuan.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.gogs.controller.InputStreamRunnable;
import com.tongyuan.gogs.controller.RepositoryController;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.model.domain.*;
import com.tongyuan.model.domain.enums.ConstNodeInstanceStatus;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.service.*;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.model.service.ReviewService.StatusChangeService;
import com.tongyuan.pageModel.DirectoryModel;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.DateUtil;
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
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


@Controller
@RequestMapping("/api/directory")
public class DirectoryController extends BaseController {
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private ResourceUtil resourceUtil;
    //TODO 文件删除
    @Autowired
    private AttachmentService attachmentService;
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
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private GUserService userService;
    @Autowired
    private ModelUnionService modelUnionService;
    @Autowired
    private LogService logService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public String directory() {
        return "directory";
    }

    //把项目在数据库中仿真，描述整个模型的层次结构
    public boolean createModel(File parentF, String filePath, String rootPath, String description) {
        Date nowDate = new Date();
        boolean result = false;
        try {
            //获取当前的路径
            //  String currentPath=parentF.getAbsolutePath().replace('\\', '/');
            String AbsolutePath = parentF.getAbsolutePath().replace('\\', '/') + "/";
            String unzipPath = ResourceUtil.getFileDriectory();
            //获取实际路径
            String currentPath = AbsolutePath.substring(unzipPath.length(), AbsolutePath.length());
            //TODO 文件删除
            Attachment directory = new Attachment();
            directory.setName(parentF.getName());
            //获取当前的时间
            directory.setCreateTime(DateUtil.getTimestamp());

            //如果当前的路径就是根目录
            if (AbsolutePath.equals(rootPath)) {
                directory.setParentId(0);
                directory.setFilePath(currentPath);
            } else {
                //吧父目录id插入到子目录表当中
                //获取父目录地址
                String parentPath = parentF.getParent().replace('\\', '/');
                String parentAbsolutePath = parentPath.substring(unzipPath.length(), parentPath.length()) + "/";
                //创建一个父类目录对象
                //TODO 文件删除
                Attachment parentDirectory = new Attachment();
                Attachment directoryParent = attachmentService.queryListByPath(parentAbsolutePath);
                directory.setParentId(directoryParent.getId());
                directory.setFilePath(currentPath);
            }
            directory.setModelId(-2);
            attachmentService.add(directory);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //web端上传模型
    @RequestMapping(value = "/uploadDirectory", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
//    @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
    public void uploadDirectory(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "directoryId", required = false) Long directoryId,
                                @RequestParam(value = "scope", required = false) Boolean scope,
                                HttpServletRequest request, HttpServletResponse response) {

        StandardMultipartHttpServletRequest multiRequest = (StandardMultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();
        Long fileSize = map.get("file").get(0).getSize();
        String fileNames2[] = map.get("file").get(0).getOriginalFilename().split("\\.");
        String fileName = "";
        byte[] bytes = new byte[0];
        if (fileNames2.length >= 1) {
            fileName = fileNames2[0];
        }

        //如果是公共库且是覆盖的方式，则撤回之前的审签流程，并新开始一个审签流程
        if (scope) {
            //根据directoryid和name找到相对应的model，再找到对应的reviewFlowInstance
            Map<String, Object> map1 = new HashMap<>();
            map1.put("fileName", fileName);
            map1.put("directoryId", directoryId);
            Model model = modelService.queryByNameAndDir(map1);
            if (model != null) {
                //如果不为空，则说明是覆盖的方式，进行撤销
                ReviewFlowInstance reviewFlowInstance = reviewFlowInstanceService.queryByModelId(model.getId());
                reviewFlowInstanceService.cancel(reviewFlowInstance.getInstanceId());
            }
        }

        try {
            bytes = map.get("file").get(0).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GUser user = gUserService.querListByName(name);

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
            modelDir = resourceUtil.unzipByte(fileName, name, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //输出文件的目录（modelDir是解压缩到的目录）
        System.out.println("modelDir==========" + modelDir + "*************");
        //获取到model解压缩的路径
        String modelPath = resourceUtil.getModelPath(modelDir, fileName);
        //遍历文件，对model库进行插入
        //	ResourceUtil.insertModelData(modelDir,"syslink",modelPath,"这是syslink项目");
        String parentPath = modelPath;
        resourceUtil.getSubFile(parentPath.substring(0,
                parentPath.length()), parentPath.substring(0,
                parentPath.length()), "");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        //存放解析的所有xmlMap
        Map<String, Map> xmlAnalysisMap = new HashMap<>();
        //存放解析的CAExmlMap
        Map<String, Map> caeXmlAnalysisMap = new HashMap<>();
        //存放解析svg，info文件所在位置的Map
        Map<String, String> svgPath = new HashMap<>();
        //把上传的文件zip包存在映射路径
        String caeZipAbsoluteUrl = "";
        try {
            caeZipAbsoluteUrl = modelPath + "/" + fileName + ".zip";
            resourceUtil.writeFile(caeZipAbsoluteUrl, 0, fileSize, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查找到项目所在的位置
        Attachment directory = attachmentService.queryListByPath(modelDir + fileName + "/");
        //获取文件所在位置，寻找xml文件所在的路径，解析xml吧所需的数据插入到数据库中
        //文件所在位置
        String fileXmlPath = directory.getFilePath();
        //获取到xml所在的文件位置
        String xmlPath = "";
        //获取cae模型xml所在职位
        String caePath = "";
        xmlPath = resourceUtil.getXmlPath(resourceUtil.getunzipPath() + fileXmlPath, xmlPath);
        //对xml进行解析,遍历xml文件下所有文件
        if (StringUtil.isNull(xmlPath)) {
            //       result = false;
            caePath = resourceUtil.getCAEXmlPath(fileXmlPath, caePath);
            File caeXmlFilePath = new File(caePath);
            String[] caeSubFiles = caeXmlFilePath.list();
            this.insertCaeXmlInfo(caeSubFiles, caeXmlFilePath, xmlMap, caeXmlAnalysisMap);
            for (Map.Entry<String, Map> entry : caeXmlAnalysisMap.entrySet()) {
                modelController.insertCAEData(entry, svgPath, scope, user, directory, directoryId, caeZipAbsoluteUrl);
            }

        } else {
            if (scope) {
                GUser admin = gUserService.querListByName("admin");
                Map<String, Object> param = new HashMap<>();
                param.put("userId", admin.getID());
                param.put("repositoryName", user.getLowerName() + fileName.toLowerCase());
                Repository repository = repositoryService.queryByNameAndUserId(param);
                if (repository == null) {
                    repositoryService.forkAndCollaboration(name, fileName);
                }
            }
            File xmlFilePath = new File(xmlPath);
            String[] subFiles = xmlFilePath.list();
            this.insertSvgPath(subFiles, xmlFilePath, xmlMap, svgPath, xmlAnalysisMap);
            //遍历xmlMap进行数据的插入
            for (Map.Entry<String, Map> entry : xmlAnalysisMap.entrySet()) {
                //解析xmlmap 把数据存放到数据
                modelController.insertData(entry, svgPath, scope, user, directory, directoryId);
            }
            //更新模型的层次结构
            //获取package下面的所有model
            this.updateModelFramwork(name, fileName, scope);
            //        this.doCmd(name,fileXmlPath,fileName);
            result = true;
            System.out.println("上传完毕！！！");

        }

    }

    //web端增加模型目录
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject add(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "parent_id", required = false) Long parent_id,
                          @RequestParam(value = "userName", required = false) String userName,
                          @RequestParam(value = "scope", required = false) Boolean scope,
                          HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            DirectoryModel directoryModel = new DirectoryModel();
            Directory directory = directoryService.addOneDir(name, parent_id, userName);
            directoryModel.setName(name);
            directoryModel.setId(directory.getId());
            directoryModel.setParentId(directory.getParentId() + "");
            jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
            if(scope)
            {
                Map<String,Object> parent = directoryService.queryMapById(directory.getParentId());
                String title = "新建目录";
                String content ="用户\t"+getUserName()+"\t添加目录\t"+directory.getName()+"\t到目录\t"+parent.get("name");
                logService.addLog(title,content);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加目录失败!");
            return returnErrorInfo(jo);
        }
        jo.put("data", jsonObject);
        return returnSuccessInfo(jo);

    }

    //web端树状目录修改模型名称
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject update(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "scope", required = false) Boolean scope,
                             HttpServletRequest request, HttpServletResponse response) {

        JSONObject jo = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            List<Directory> rootDirectoryList = directoryService.queryListById(id);
            if (rootDirectoryList.size() > 0) {
                Directory directory = rootDirectoryList.get(0);
                String beforeUpdateName = directory.getName();
                directory.setName(name);
                boolean res = directoryService.update(directory);
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setName(name);
                directoryModel.setId(id);
                directoryModel.setParentId(directory.getParentId() + "");
                jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                if(scope)
                {
                    String title = "目录";
                    String content ="用户\t"+getUserName()+"\t把目录"+ beforeUpdateName +"修改为\t"+directory.getName();
                    logService.addLog(title,content);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改目录失败!");
            return returnErrorInfo(jo);
        }
        jo.put("data", jsonObject);
        return returnSuccessInfo(jo);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "parent_id", required = false) Long parent_id,
                             @RequestParam(value = "scope", required = false) Boolean scope,
                             HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        try {
            List<Directory> rootDirectoryList = directoryService.queryListById(id);
            rootDirectoryList.get(0).setDeleted(true);
            boolean res = directoryService.update(rootDirectoryList.get(0));
            if(scope)
            {
                String title = "目录";
                String content ="用户\t"+getUserName()+"\t删除目录\t"+rootDirectoryList.get(0).getName();
                logService.addLog(title,content);
            }

        } catch (Exception e) {
            e.printStackTrace();
            jo.put("status", "1");
            jo.put("code", 0);
            jo.put("msg", "ok");
            return jo;
        }
        jo.put("status", 1);
        jo.put("code", 0);
        jo.put("msg", "ok");
        jo.put("data", id);
        return (JSONObject) JSONObject.toJSON(jo);

    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject list(@RequestParam(value = "parent_id", required = false) Long parent_id,
                           @RequestParam(value = "scope", required = false) Boolean scope,
                           @RequestParam(value = "userName", required = false) String userName,
                           HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<JSONObject> directoryModelList = new ArrayList<>();
        try {
            if(parent_id == null){
                parent_id = (long)0;
            }
            directoryService.getDirExpChild(directoryModelList, parent_id
                    , scope, userName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("目录获取失败");
            return returnErrorInfo(jo);
        }
        jo.put("data", directoryModelList);
        return returnSuccessInfo(jo);
    }

    @RequestMapping(value = "/directoryList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject directoryList(
            HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<JSONObject> directoryModelList = new ArrayList<>();
        try {
            List<Directory> allDirectoryList = directoryService.findAllDirectory();
            for (int i = 0; i <= allDirectoryList.size() - 1; i++) {
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setId(allDirectoryList.get(i).getId());
                directoryModel.setName(allDirectoryList.get(i).getName());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                directoryModelList.add(jsonObject);

            }
        } catch (Exception e) {
            e.printStackTrace();
            jo.put("status", "1");
            jo.put("code", 0);
            jo.put("msg", "ok");
            return jo;
        }
        jo.put("status", 1);
        jo.put("code", 0);
        jo.put("msg", "ok");
        jo.put("data", directoryModelList);
        return (JSONObject) JSONObject.toJSON(jo);
    }

    /**
     * @param name        是上传者
     * @param fileXmlPath 是文件所在位置
     * @param fileName    为文件名称
     */
    public void doCmd(String name, String fileXmlPath, String fileName) {
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
        String[] cmd = {"cmd", "/k", "cd /d C: & cd / & cd %HOMEPATH% & echo http://" + userName + ":" + password + "@" + addr + "%3a3000>> .git-credentials" +
                "& cd / & cd " + fileXmlPath + " & \"C:/Program Files (x86)/Git/bin/git.exe\" init " +
                "& \"C:/Program Files (x86)/Git/bin/git.exe\" remote add origin http://" + addr + "/" + userName + "/" + fileName + ".git " +
                "& \"C:/Program Files (x86)/Git/bin/git.exe\" add . & \"C:/Program Files (x86)/Git/bin/git.exe\" commit -m \"model\" " +
                " & \"C:/Program Files (x86)/Git/bin/git.exe\" config --global credential.helper store & \"C:/Program Files (x86)/Git/bin/git.exe\" push origin master"};
        excuteCmd_multiThread(cmd, "GBK");
    }


    public static String excuteCmd_multiThread(String[] cmd, String encoding) {
        BufferedReader bReader = null;
        InputStreamReader sReader = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);

            //为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞

            Thread t = new Thread(new InputStreamRunnable(p.getErrorStream(), "ErrorStream"));
            t.start();

            //"标准输出流"就在当前方法中读取

            BufferedInputStream bis = new BufferedInputStream(p.getInputStream());

            if (encoding != null && encoding.length() != 0) {
                sReader = new InputStreamReader(bis, encoding);//设置编码方式
            } else {
                sReader = new InputStreamReader(bis, "GBK");
            }
            bReader = new BufferedReader(sReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bReader.readLine()) != null) {
                sb.append(line);
                sb.append("/n");
            }

            bReader.close();
            p.destroy();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //         return ErrorString;
            return "";
        } finally {
        }
    }

    @RequestMapping(value = "/getTreeItem", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getTreeItem(@RequestParam(value = "parent_id", required = false) Long parent_id,
                                  HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        //点击树节点父类列表
        List<Directory> treeItemList = new ArrayList<>();
        try {
            //所有的目录列表
            List<Directory> allDirectory = directoryService.findAllDirectory();
            //添加节点父类列表
            addTreeItem(parent_id, treeItemList, allDirectory);
            //所需的列表需要倒序
            Collections.reverse(treeItemList);
        } catch (Exception e) {
            e.printStackTrace();
            jo.put("status", "1");
            jo.put("code", 0);
            jo.put("msg", "ok");
            return jo;
        }
        jo.put("status", 1);
        jo.put("code", 0);
        jo.put("msg", "ok");
        jo.put("treeItem", treeItemList);
        return jo;
    }

    @RequestMapping(value = "/getDirectoryTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getDirectoryTree(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        //点击树节点父类列表
        List<Map<String, Object>> directoryTree = new ArrayList<>();
        Map<String, Object> publicDirectory = new HashMap<>();
        try {
            publicDirectory = directoryService.queryPublicRoot();
            if (publicDirectory != null) {
                setChidren(publicDirectory);
                directoryTree.add(publicDirectory);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jo.put("status", "1");
            jo.put("code", 0);
            jo.put("msg", "ok");
            return jo;
        }
        jo.put("status", 1);
        jo.put("code", 0);
        jo.put("msg", "ok");
        jo.put("directoryTree", directoryTree);
        return jo;
    }

    public void addTreeItem(Long parentId, List<Directory> treeItemList, List<Directory> allDirectory) {
        for (Directory directory : allDirectory) {
            if (directory.getId() == parentId) {
                treeItemList.add(directory);
                addTreeItem(directory.getParentId(), treeItemList, allDirectory);
            }
        }
    }

    public void setChidren(Map<String, Object> map) {
        if (map.get("icon") == null) {
            map.put("icon", "iconfont icon-wenjianjia");
        }

        map.put("mode", 0);
        List<Map<String, Object>> chidlren = directoryService.queryMapListByParentId(Long.parseLong(map.get("id").toString()));
        if (chidlren.size() > 0) {
            map.put("children", chidlren);
            Iterator<Map<String, Object>> iterator = chidlren.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> child = iterator.next();
                setChidren(child);
            }
        }

    }


    @RequestMapping(value = "/checkModel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject checkModel(@RequestParam(value = "fileName", required = false) String fileName,
                                 @RequestParam(value = "directoryId", required = false) Long directoryId,
                                 HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("fileName", fileName);
            params.put("directoryId", directoryId);
            Model model = modelService.queryByNameAndDirId(params);
            if (model == null) {
                jo.put("status", 1);
                jo.put("code", 0);
                jo.put("msg", "ok");
                return jo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //status 为0 ，存在该模型
        jo.put("status", 0);
        jo.put("code", 0);
        jo.put("msg", "ok");
        return jo;

    }

    @RequestMapping(value = "/getPrivateDirId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPrivateDirId(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        Directory privateDir = new Directory();
        try {
            GUser user = userService.queryById(getUserId());
            String userName = user.getLowerName();
            privateDir = directoryService.getPrivateDir(userName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        jo.put("data", privateDir);
        return jo;
    }

    @RequestMapping(value = "/getPublicDirId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPublicDirId(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        Directory publicDir = new Directory();
        try {
            publicDir = directoryService.getPublicDir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jo.put("data", publicDir);
        return jo;
    }

    /**
     * 输入一个模型目录，判断是否是公有模型
     * true 公有模型 false 私有模型
     *
     * @param directoryId
     * @return
     */
    public Boolean isScope(Long directoryId) {
        Boolean result = false;
        Directory publicDir = new Directory();
        List<Directory> treeItemList = new ArrayList<>();
        try {
            publicDir = directoryService.getPublicDir();
            List<Directory> allDirectory = directoryService.findAllDirectory();
            //添加节点父类列表
            addTreeItem(directoryId, treeItemList, allDirectory);
            for (Directory directory : treeItemList) {
                if (directory.getId() == publicDir.getId()) {
                    result = true;
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 給不同的模型存入對應的模型svg地址
     *
     * @param subFiles
     * @param xmlFilePath
     * @param xmlMap
     * @param svgPath
     * @param xmlAnalysisMap
     */

    public void insertSvgPath(String[] subFiles, File xmlFilePath, Map<String, Object> xmlMap, Map<String, String> svgPath, Map<String, Map> xmlAnalysisMap) {
        for (int i = 0; i < subFiles.length; i++) {
            //查看文件的格式
            String[] fileNames = subFiles[i].split("\\.");
            //文件的类型
            String filePreType = fileNames[fileNames.length - 2];
            String fileType = fileNames[fileNames.length - 1];
            if (("xml").equals(fileType)) {
                xmlMap = resourceUtil.analysisXmlPath(xmlFilePath + "/" + subFiles[i]);
                xmlAnalysisMap.put(subFiles[i], xmlMap);
                svgPath.put(subFiles[i], xmlFilePath + "/" + subFiles[i]);
            } else if ("svg".equals(fileType)) {
                if ("icon".equals(filePreType)) {
                    svgPath.put(subFiles[i], xmlFilePath + "/" + subFiles[i]);
                } else if ("diagram".equals(filePreType)) {
                    svgPath.put(subFiles[i], xmlFilePath + "/" + subFiles[i]);
                }
            } else if ("html".equals(fileType)) {
                svgPath.put(subFiles[i], xmlFilePath + "/" + subFiles[i]);
            } else if ("mo".equals(fileType)) {
                //mo文件信息
                String textAllInfo = fileX.read(xmlFilePath + "/" + subFiles[i]);
                svgPath.put(subFiles[i], textAllInfo);
            }
        }
    }

    public void insertCaeXmlInfo(String[] subFiles, File xmlFilePath, Map<String, Object> xmlMap, Map<String, Map> xmlAnalysisMap) {
        for (int i = 0; i < subFiles.length; i++) {
            String[] fileNames = subFiles[i].split("\\.");
            //文件的类型
            String filePreType = fileNames[fileNames.length - 2];
            String fileType = fileNames[fileNames.length - 1];
            if (("xmlwrapper").equals(fileType)) {
                xmlMap = resourceUtil.analysisXmlPath(xmlFilePath + "/" + subFiles[i]);
                xmlAnalysisMap.put(subFiles[i], xmlMap);
            }
        }
    }


    /**
     * 把对应的模型库的目录进行更改
     */
    public void updateModelFramwork(String userName, String fileName, boolean scope) {
        //查询刚插入的model
        List<Model> packageList = modelService.getNullParId();
        if (packageList.size() == 1) {
            packageList.get(0).setParentId(0);
            modelService.update(packageList.get(0));
            Long modelId = packageList.get(0).getId();
            addModelUnion(userName, fileName, modelId);

            //下面两行都有异常要抛出
            //scope为1表示公共库，要参加审签
            if (scope) {
                try {
                    Long instanceId = reviewFlowInstanceService.startInstance(modelId);
                    statusChangeService.updateStatus(instanceId, "1", ConstNodeInstanceStatus.ACTIVE);
                } catch (SqlNumberException e) {
                    e.printStackTrace();
                }
            }
        } else if (packageList.size() <= 0) {
            return;
        } else {
            for (Model model : packageList) {
                if (model.getName().split("\\.").length == 1) {
                    model.setParentId(0);
                    modelService.update(model);
                    Long modelId = model.getId();
                    addModelUnion(userName, fileName, modelId);

                    //下面两行都有异常要抛出
                    if (scope) {
                        try {
                            Long instanceId = reviewFlowInstanceService.startInstance(modelId);
                            statusChangeService.updateStatus(instanceId, "1", ConstNodeInstanceStatus.ACTIVE);
                        } catch (SqlNumberException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            for (Model modelParent : packageList) {
                for (Model modelChild : packageList) {
                    String childParentName = modelUtil.getParentName(modelChild.getName());
                    if (childParentName != null && !childParentName.equals("")) {
                        if (childParentName.equals(modelParent.getName())) {
                            modelChild.setParentId(modelParent.getId());
                            modelService.update(modelChild);
                        }
                    }
                }
            }
        }
    }

    public Model setPackageParam(String name, String[] subFiles, Attachment directory, Long directoryId, Boolean scope, String filePath) {
        GUser user = gUserService.querListByName(name);
        Model model = new Model();
        model.setName(subFiles[0].split("\\.")[0]);
        model.setDirectoryId(directoryId);
        model.setClasses(ModelClasses.Package.getKey());
        model.setScope(scope);
        model.setUserId(user.getID());
        model.setCreateTime(DateUtil.getTimestamp());
        // model.setUserId(1);
        model.setDeleted(false);
        return model;
    }

    /**
     * 是否增加模型并申签
     *
     * @param subFiles
     * @param directoryId
     * @param model
     * @return
     */
    public Map<String, Object> isAddModelAndReview(String[] subFiles, Long directoryId, Model model) {
        Map<String, Object> param = new HashMap<>();
        param.put("fileName", subFiles[0].split("\\.")[0]);
        param.put("directoryId", directoryId);
//        if(modelService.queryByNameAndDir(param) == null){
        //by:zhangcy  在这里加入了审签的代码
        modelService.add(model);
        Long modelId = model.getId();
        //下面两行都有异常要抛出
        try {
            Long instanceId = reviewFlowInstanceService.startInstance(modelId);
            statusChangeService.updateStatus(instanceId, "1", ConstNodeInstanceStatus.ACTIVE);
        } catch (SqlNumberException e) {
            e.printStackTrace();
        }
//        }
        return param;
    }

    @RequestMapping(value = "/checkDirName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject checkDirName(@RequestParam(value = "parentId", required = false) String parentId,
                                   @RequestParam(value = "dirName", required = false) String dirName,
                                   @RequestParam(value = "dirId", required = false) Long dirId,
                                   @RequestParam(value = "dirParentId", required = false) Long dirParentId,
                                   @RequestParam(value = "userName", required = false) String userName,
                                   HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<Directory> allDirectory = directoryService.findAllDirectory();
        Directory privateDir = directoryService.getPrivateDir(userName);
        //同级分类目录列表
        List<Directory> levelDirList = new ArrayList<>();
        try {
            if ("".equals(dirId) || dirId == null) {
                //找出同级分类目录
                for (Directory levelDir : allDirectory) {
                    if (dirParentId == privateDir.getId()) {
                        if (levelDir.getParentId() == dirParentId && userName.equals(levelDir.getUserName())) {
                            levelDirList.add(levelDir);
                        }
                    } else {
                        if (levelDir.getParentId() == dirParentId) {
                            levelDirList.add(levelDir);
                        }
                    }

                }
            } else {
                for (Directory levelDir : allDirectory) {
                    if (Long.parseLong(parentId) == privateDir.getId()) {
                        if (levelDir.getParentId() == Long.parseLong(parentId) && userName.equals(levelDir.getUserName())) {
                            levelDirList.add(levelDir);
                        }
                    } else {
                        if (levelDir.getParentId() == Long.parseLong(parentId)) {
                            levelDirList.add(levelDir);
                        }
                    }

                }
            }
            if (levelDirList.size() > 0) {
                for (Directory directory :
                        levelDirList) {
                    if (directory.getName().equals(dirName)) {
                        jo.put("state", 0);
                        return jo;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            jo.put("state", 0);
            return jo;
        }
        jo.put("state", 1);
        return jo;
    }


    @RequestMapping(value = "/checkRootDir", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject checkRootDir(@RequestParam(value = "dirName", required = false) String dirName,
                                   @RequestParam(value = "dirParentId", required = false) Long dirParentId,
                                   @RequestParam(value = "userName", required = false) String userName,
                                   HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        List<Directory> allDirectory = directoryService.findAllDirectory();
        Directory privateDir = directoryService.getPrivateDir(userName);
        //同级分类目录列表
        List<Directory> levelDirList = new ArrayList<>();
        try {
            if (dirParentId == privateDir.getId()) {
                for (Directory levelDir : allDirectory) {
                    if (levelDir.getParentId() == dirParentId ) {
                        levelDirList.add(levelDir);
                    }
                }
            } else {
                for (Directory levelDir : allDirectory) {
                    if (levelDir.getParentId() == dirParentId) {
                        levelDirList.add(levelDir);
                    }
                }
            }
            if (levelDirList.size() > 0) {
                for (Directory directory :
                        levelDirList) {
                    if (directory.getName().equals(dirName)) {
                        jo.put("state", 0);
                        return jo;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            jo.put("state", 0);
            return jo;
        }
        jo.put("state", 1);
        return jo;
    }

    public void addModelUnion(String userName, String fileName, Long modelId) {
        //获取mworks传来的gogsUrl
        String gogsUrl = "http://localhost:3000/xyx/MyPkg.git";
        //获取仓库的名字
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> unionParam = new HashMap<>();
        GUser gUser = gUserService.querListByName(userName);
        param.put("userId", gUser.getID());
        param.put("repositoryName", fileName.toLowerCase());
        Repository repository = repositoryService.queryByNameAndUserId(param);
        unionParam.put("model_id", modelId);

        if (repository != null) {
            unionParam.put("repository_id", repository.getID());
        }

        ModelRepo modelUnion = modelUnionService.queryUnion(unionParam);
        if (modelUnion == null) {
            ModelRepo Union = new ModelRepo();
            Union.setModelId(modelId);
            Union.setRepositoryId(repository.getID());
            modelUnionService.add(Union);
        }
    }

    //-------------------------------------------------------上传模型代码重构--------------------------------------------------------------------
    //web端上传模型
    @RequestMapping(value = "/uploadDirectoryTest", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
//    @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
    public void uploadDirectoryTest(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "directoryId", required = false) Long directoryId,
                                    @RequestParam(value = "scope", required = false) Boolean scope,
                                    HttpServletRequest request, HttpServletResponse response) {

        StandardMultipartHttpServletRequest multiRequest = (StandardMultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> map = multiRequest.getMultiFileMap();
        long fileSize = 0;
        String fileName = "";
        byte[] bytes = new byte[0];
        try {
            fileSize = map.get("file").get(0).getSize();
            String fileNames2[] = map.get("file").get(0).getOriginalFilename().split("\\.");
            if (fileNames2.length >= 1) {
                fileName = fileNames2[0];
            }
            bytes = map.get("file").get(0).getBytes();
            //如果是公共库且是覆盖的方式，则撤回之前的审签流程，并新开始一个审签流程
            if (scope) {
                directoryService.isAddNewReviewFlowInstance(fileName, directoryId);
            }
            GUser user = gUserService.querListByName(name);
            System.out.println("starting upload the file...");
            boolean result = false;
            //获取压缩包 C:/Temp/zip/文件名
            String filePath = resourceUtil.getzipPath() + fileName;
            System.out.println("filePath==" + filePath);
            System.out.println("starting writing file...");
            String modelDir = "";
            modelDir = resourceUtil.unzipByte(fileName, name, bytes);
            //输出文件的目录（modelDir是解压缩到的目录）
            System.out.println("modelDir==========" + modelDir + "*************");
            //获取到model解压缩的路径
            String modelPath = resourceUtil.getModelPath(modelDir, fileName);
            //遍历文件，对model库进行插入
            String parentPath = modelPath;
            directoryService.getSubFile(parentPath.substring(0, parentPath.length()), parentPath.substring(0, parentPath.length()), "");
            Map<String, Object> xmlMap = new HashMap<String, Object>();
            //存放解析的所有xmlMap
            Map<String, JSONObject> xmlAnalysisMap = new HashMap<>();
            //存放解析svg，info文件所在位置的Map
            Map<String, String> svgPath = new HashMap<>();
            //把上传的文件zip包存在映射路径
            String caeZipAbsoluteUrl = "";
            caeZipAbsoluteUrl = modelPath + fileName + ".zip";
            resourceUtil.writeFile(caeZipAbsoluteUrl, 0, fileSize, bytes);
            //查找到项目所在的位置
            Attachment directory = attachmentService.queryListByPath(modelDir + fileName);
            //获取文件所在位置，寻找xml文件所在的路径，解析xml吧所需的数据插入到数据库中
            //文件所在位置
            String fileXmlPath = directory.getFilePath();
            //获取到xml所在的文件位置
            String xmlPath = "";
            xmlPath = resourceUtil.getXmlPath(resourceUtil.getunzipPath() + fileXmlPath, xmlPath);
            //对xml进行解析,遍历xml文件下所有文件
            if (StringUtil.isNull(xmlPath)) {
            } else {
                if (scope) {
                    directoryService.uploadToPublicPository(user, fileName, name);
                }
                File xmlFilePath = new File(xmlPath);
                String[] subFiles = xmlFilePath.list();
                directoryService.getXMlJson(subFiles, xmlFilePath, xmlAnalysisMap);
                //遍历xmlMap进行数据的插入
                for (Map.Entry<String, JSONObject> entry : xmlAnalysisMap.entrySet()) {
                    //解析xmlmap 把数据存放到数据
                    modelService.insertModelicaData(entry, scope, user, directory, directoryId);
                }
                //更新模型的层次结构
                modelService.updateModelFramwork(user, fileName, scope);
                result = true;
                System.out.println("上传完毕！！！");

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传失败！");
        }

    }


}


