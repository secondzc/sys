package com.tongyuan.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.Repository;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.model.dao.DirectoryMapper;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.ReviewFlowInstance;
import com.tongyuan.model.service.AttachmentService;
import com.tongyuan.model.service.DirectoryService;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.pageModel.DirectoryModel;
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.ResourceUtil;
import com.tongyuan.util.analysisXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 目录数据操作
 * Created by xieyx on 2017-6-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class DirectoryServiceImpl implements DirectoryService{
    @Autowired
    private DirectoryMapper directoryMapper;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private GUserService gUserService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private AttachmentService attachmentService;

    @Override
    public List<Directory> queryListByPath(String absoluteAddress) {
        return this.directoryMapper.queryListByPath(absoluteAddress);
    }

    @Override
    public Long add(Directory directory) {
        return this.directoryMapper.add(directory);
    }


    @Override
    public boolean update(Directory directory) {
        return this.directoryMapper.update(directory);
    }

    @Override
    public boolean deleteByIds(String[] ids) {
        return this.directoryMapper.deleteByIds(ids);
    }

    @Override
    public List<Directory> queryListByName(Map<String, Object> params) {
        return this.directoryMapper.queryListByName(params);
    }

    @Override
    public List<Directory> findAllDirectory() {
        return this.directoryMapper.findAllDirectory();
    }

    @Override
    public List<Directory> findRootDirectory() {
        return this.directoryMapper.findRootDirectory();
    }

    @Override
    public List<Directory> findRootDirectoryList(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("rows").toString()));
        return this.directoryMapper.findRootDirectoryList(params);
    }

    @Override
    public List<Directory> queryListByParentId(Long id) {
        return this.directoryMapper.queryListByParentId(id);
    }

    @Override
    public List<Directory> queryListById(Long id) {
        return this.directoryMapper.queryListById(id);
    }

    @Override
    public List<Directory> queryByParentName(Map<String, Object> params) {
        return this.directoryMapper.queryByParentName(params);
    }

    @Override
    public List<Map<String,Object>> queryMapListByParentId(Long id)
    {
        return this.directoryMapper.queryMapListByParentId(id);
    }

    @Override
    public Map<String,Object> queryPublicRoot()
    {
        return this.directoryMapper.queryPublicRoot();
    }

    @Override
    public Map<String,Object> queryMapById(Long id)
    {
        return this.directoryMapper.queryMapById(id);
    }

    @Override
    public List<Directory> selectDefaultDirectory() {
        return this.directoryMapper.selectDefaultDirectory();
    }

    @Override
    public Directory getPublicDir() {
        return this.directoryMapper.getPublicDir();
    }

    @Override
    public Directory getPrivateDir(String userName) {
        return this.directoryMapper.getPrivateDir(userName);
    }

    @Override
    public void createPersonalModelRoot(Map<String, Object> map) {
        Date date = new Date();
        Directory directory = new Directory();
        directory.setName((String)map.get("name")+"的模型");
        directory.setUserName((String) map.get("name"));
        directory.setDeleted(false);
        directory.setCreateTime(date);
        directory.setLastUpdateTime(date);
        directory.setParentId(0);
        this.directoryMapper.add(directory);
    }

    @Override
    public void getDirExpChild(List<JSONObject> directoryModelList, Long parent_id, Boolean scope, String userName) {
        //顶层目录的时候
        Directory rooDir = new Directory();
        List<Directory> rootDirectoryList = new ArrayList<>();
        if (scope != null && parent_id == 0) {
            rooDir = this.directoryMapper.getPrivateDir(userName);
            rootDirectoryList.add(rooDir);
            } else if (parent_id == 0) {
            rooDir = this.directoryMapper.getPublicDir();
            rootDirectoryList.add(rooDir);
            } else {
                rootDirectoryList = this.directoryMapper.queryListByParentId(parent_id);
            }

            for (int i = 0; i <= rootDirectoryList.size() - 1; i++) {
                if (scope != null && userName.equals(rootDirectoryList.get(i).getUserName())) {
                    DirectoryModel directoryModel = new DirectoryModel();
                    directoryModel.setId(rootDirectoryList.get(i).getId());
                    directoryModel.setName(rootDirectoryList.get(i).getName());
                    directoryModel.setParentId(rootDirectoryList.get(i).getParentId() + "");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                    directoryModelList.add(jsonObject);
                } else {
                    DirectoryModel directoryModel = new DirectoryModel();
                    directoryModel.setId(rootDirectoryList.get(i).getId());
                    directoryModel.setName(rootDirectoryList.get(i).getName());
                    directoryModel.setParentId(rootDirectoryList.get(i).getParentId() + "");
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(directoryModel);
                    directoryModelList.add(jsonObject);
                }
            }
    }

    @Override
    public Directory addOneDir(String name, Long parent_id, String userName) {
        Date date = new Date();
        Directory directory = new Directory();
        directory.setName(name);
        directory.setParentId(parent_id);
        directory.setUserName(userName);
        directory.setDeleted(false);
        directory.setCreateTime(date);
        directory.setLastUpdateTime(date);
        this.directoryMapper.add(directory);
        return directory;
    }

    /**
     * 如果是公共库且是覆盖的方式，则撤回之前的审签流程，并新开始一个审签流程
     * @param fileName 文件名称
     * @param directoryId 文件所选目录
     */
    @Override
    public void isAddNewReviewFlowInstance(String fileName,Long directoryId) {
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

    /**
     *
     * @param user 用户
     * @param fileName 文件名称
     * @param name 上传者
     */
    @Override
    public void uploadToPublicPository(GUser user,String fileName,String name) {
        GUser admin = gUserService.querListByName("admin");
        Map<String, Object> param = new HashMap<>();
        param.put("userId", admin.getID());
        param.put("repositoryName", user.getLowerName() + fileName.toLowerCase());
        Repository repository = repositoryService.queryByNameAndUserId(param);
        if (repository == null) {
            repositoryService.forkAndCollaboration(name, fileName);
        }
    }

    /**
     * 把模型插入到数据库中
     * @param filePath 文件路径
     * @param rootPath 文件根路径
     * @param description 文件描述
     */
    @Override
    public void getSubFile(String filePath, String rootPath, String description) {
        File parentF = new File(filePath);
        if (!parentF.exists()) {
            System.out.println("文件或文件夹不存在");
            return;
        }
        if(parentF.isFile()){
            this.createDirectory(parentF,filePath,rootPath,description);
            return;
        }else{
            this.createDirectory(parentF,filePath,rootPath,description);
            String[] subFiles = parentF.list();
            for (int i = 0; i < subFiles.length; i++) {
                getSubFile(filePath + "/" + subFiles[i],rootPath ,description);
            }
        }
    }

    /**
     * 创建目录信息（attachment）
      * @param parentF 父目录文件
     * @param filePath 文件地址
     * @param rootPath 根目录地址
     * @param description 文件描述信息
     * @return 是否创建成功
     */
    @Override
    public boolean createDirectory(File parentF, String filePath, String rootPath, String description) {
        Date nowDate = new Date();
        boolean result = false;
        try {
            //获取当前的路径
            //  String currentPath=parentF.getAbsolutePath().replace('\\', '/');
            String AbsolutePath = parentF.getAbsolutePath().replace('\\', '/')+"/";
            String unzipPath = ResourceUtil.getFileDriectory();
            //获取实际路径
            String currentPath = AbsolutePath.substring(unzipPath.length(), AbsolutePath.length()-1);
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
                String parentAbsolutePath = parentPath.substring(unzipPath.length(), parentPath.length())+"/";
                //创建一个父类目录对象
                //TODO 文件删除
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

    @Override
    public void getXMlJson(String[] subFiles, File xmlFilePath, Map<String, JSONObject> xmlAnalysisMap) {
        for (int i = 0; i < subFiles.length; i++) {
            //查看文件的格式
            String[] fileNames = subFiles[i].split("\\.");
            //文件的类型
            String filePreType = fileNames[fileNames.length - 2];
            String fileType = fileNames[fileNames.length - 1];
            //读取文件字符串
            String xmlStr ="";
            if (("xml").equals(fileType)) {
                try {
                   xmlStr= analysisXml.readFile(xmlFilePath + "/" + subFiles[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONObject xmlJson = analysisXml.analysisXmlFile(xmlStr);
                xmlAnalysisMap.put(subFiles[i], xmlJson);
            }
        }
    }


}
