package com.tongyuan.webservice;

import com.alibaba.fastjson.JSONArray;
import com.tongyuan.gogs.controller.RepositoryController;
import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.gogs.service.RepositoryService;
import com.tongyuan.gogs.service.StarService;
import com.tongyuan.gogs.service.WatchService;
//import com.tongyuan.model.controller.DirectoryController;
//import com.tongyuan.model.controller.ModelController;
import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.service.DirectoryService;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.pageModel.ComponentTreeObj;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.*;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//xml解析相关
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 接口实现
 *
 * @author leftso
 *
 */
@WebService(serviceName = "CommonService", // 与接口中指定的name一致
		targetNamespace = "http://webservice.tongyuan.com/", // 与接口中的命名空间一致,一般是接口的包名倒
		endpointInterface = "com.tongyuan.webservice.CommonService"// 接口地址
)
@Component
public class CommonServiceImp implements CommonService {
	@Autowired
	private ResourceUtil resourceUtil;
	@Autowired
	private GUserService gUserService;

	@Autowired
	private ModelService modelService;
//	@Autowired
//	private ModelController modelController;
	@Autowired
	private DirectoryService directoryService;
//	@Autowired
//	private FileModelService fileModelService;
	@Autowired
	private ReviewFlowInstanceService reviewFlowInstanceService;
	@Autowired
	private GUserService userService;
	@Autowired
	private ModelUtil modelUtil;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private WatchService watchService;
	@Autowired
	private StarService starService;
	@Autowired
	private FileX fileX;
//	@Autowired
//	private DirectoryController directoryController;
	@Autowired
	private RepositoryController repositoryController;
	private String userName;
	//判断分批上传文件是否上传完整
	private  static  byte[] isAllByte;

	private WebServiceContext wsContext;

	@Override
	public String sayHello(String name) {

		return "Hello ," + name;
	}

	@Override
	public boolean validateUser(String userName, String passWord) {
		Boolean result = true;
		if (StringUtil.isNull(userName)) {
			return false;
		}
		if (StringUtil.isNull(passWord)) {
			result = false;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", userName);
			GUser user = userService.querListByName(userName);
			if (user != null) {
				String passwdCheck = EncodePasswd.getEncryptedPassword(passWord, user.getSalt(), 10000, 50);
				if (!passwdCheck.equalsIgnoreCase(user.getPasswd())) {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/* 上传模型压缩文件,并返回文件相对路径,fileName为**.zip
	*  磁盘路径liuq/time/fileName
    */
	public String UploadFile(String userName,String fileName, String remoteModelPath,
                             long beginPos, long length, byte[] data) {
        boolean result = false;
        String relativePath = "";
        String fullPath = "";
        //第一次上传时指定文件保存位置
        if (remoteModelPath==null||remoteModelPath.equals("")) {
            String relativeDir = userName+"/"+resourceUtil.getNowTime();
            File file = new File(resourceUtil.getunzipPath() + relativeDir);
            file.mkdirs();
            relativePath = relativeDir+"/"+fileName;
            fullPath = resourceUtil.getunzipPath() + relativePath;
        }
        else {
            relativePath = remoteModelPath;
            fullPath = resourceUtil.getunzipPath()+ relativePath;
        }

		try {
			resourceUtil.writeFile(fullPath,beginPos,length,data);
			result = true;
		}catch (IOException e){
			e.printStackTrace();
			result =false;
		}
		if (result){
			return  relativePath;
		}
		else {
			return  "";
		}
	}

	//上传模型元文件到指定目录
	public boolean UploadMetaFile(String userName,Long dirID,String modelFilePath,String metaFileContent)
    {
        boolean result = false;
        //解析xml字符串
        try {
            Document doc = DocumentHelper.parseText(metaFileContent);
            Element root = doc.getRootElement();
            Map<String,Object> xmlMap = (Map<String, Object>)resourceUtil.xml2map(root);
            //TODO:存入数据库
			GUser user =  gUserService.querListByName(userName);
//			Boolean isScopeDir = directoryController.isScope(dirID);
//			modelController.toolInsertCAE(xmlMap,isScopeDir,user,dirID,modelFilePath);
			result =true;
        }catch (DocumentException e){
            e.printStackTrace();
        }

        return  result;
    }

	/**
	 * 模型分类树
	 * @param userName
	 * @return
	 */
	public String getClassTree(String userName){
		List<ComponentTreeObj> classTree = new ArrayList<>();
		String jsonStr = null;
		JSONArray json = new JSONArray();
		try {
			//查找所有的树
			List<Directory> directoryList = directoryService.findAllDirectory();
			for (Directory directory : directoryList) {
				//找出顶层模型分类
				if (directory.getParentId() == 0) {
					ComponentTreeObj componentTreeObj = new ComponentTreeObj();
					componentTreeObj.setId(directory.getId());
					componentTreeObj.setName(directory.getName());
					componentTreeObj.setUserName(directory.getUserName());
					//新增一个子对象
					List<ComponentTreeObj> componentTreeObjChild = new ArrayList<>();
					componentTreeObj.setChildren(componentTreeObjChild);
					classTree.add(componentTreeObj);
				}
			}
			//迭代插入子模型对象
			for (ComponentTreeObj  classTreeRoot:classTree) {
				insertChild(classTreeRoot,  directoryList,userName);
			}
			;json.add(classTree);
			jsonStr = json.toString();
		}catch (Exception e){
			e.printStackTrace();
		}
		return jsonStr;
	}


	/**
	 * 上传模型并返回模型仓库路径
	 * @param userName
	 * @param fileName
	 * @param beginPos
	 * @param length
	 * @param data
	 * @return
	 */
	public String uploadModel(String userName,Long classID,String fileName, long beginPos, long length,
		byte[] data){
//		fileName = fileName.split("\\.")[0];
//		GUser user =  gUserService.querListByName(userName);
//		System.out.println("starting create the repository...");
//		Boolean isScopeDir = directoryController.isScope(classID);
///*		if(isScopeDir){
//			repositoryController.addRepository("admin",fileName,isScopeDir);
//		}else{
//			repositoryController.addRepository(userName,fileName,null);
//		}*/
//		//当用户上传模型到公有库（fork到admin用户下面）
//		if(isScopeDir){
//			GUser admin = gUserService.querListByName("admin");
//			Map<String,Object> param = new HashMap<>();
//			param.put("userId",admin.getID());
//			param.put("repositoryName",user.getLowerName()+fileName.toLowerCase());
//			Repository repository = repositoryService.queryByNameAndUserId(param);
//			if(repository == null) {
//				repositoryController.forkAndCollaboration(userName, fileName);
//			}
//		}
//
//		System.out.println("End create the repository...");
//		System.out.println("starting upload the file...");
//
//		String modelReposityUrl = "";
//		boolean result = false;
//		//获取压缩包 C:/Temp/zip/文件名
//		String filePath = resourceUtil.getzipPath() + fileName;
//		System.out.println("filePath==" + filePath);
//		System.out.println("starting writing file...");
//		//TODO:上传到内存中，并在内存中完成解压
//		String modelDir = "";
//
///*		if(data.length < length){
//			isAllByte = modelUtil.unitByteArray(isAllByte,data);
//			if(isAllByte.length <length){
//				return "";
//			}
//		}*/
//
//
//		try {
//            modelDir = resourceUtil.unzipByte(fileName, userName,data);
///*			byte[] realByte = new byte[isAllByte.length];
//			System.arraycopy(isAllByte, (int)beginPos, realByte, 0, (int)length);
//			modelDir = resourceUtil.unzipByte(fileName, userName,realByte);
//			isAllByte = new byte[1024];*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//		//输出文件的目录（modelDir是解压缩到的目录）
//		System.out.println("modelDir==========" + modelDir + "*************");
//		//获取到model解压缩的路径
//		String modelPath = resourceUtil.getModelPath(modelDir, fileName);
//		//遍历文件，对model库进行插入
//		String parentPath = modelPath;
//		resourceUtil.getSubFile(parentPath.substring(0,
//                parentPath.length()), parentPath.substring(0,
//                parentPath.length()), "");
//		Map<String, Object> xmlMap = new HashMap<String, Object>();
//		//存放解析的所有xmlMap
//		Map<String,Map> xmlAnalysisMap = new HashMap<>();
//		//存放解析的CAExmlMap
//		Map<String,Map> caeXmlAnalysisMap = new HashMap<>();
//		//存放解析svg，info文件所在位置的Map
//		Map<String,String> svgPath = new HashMap<>();
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("name",fileName);
//		//查找到项目所在的位置
//		List<FileModel> directoryList = fileModelService.queryListByName(params);
//		//选取最近push的一个directory对象
//		FileModel directory = new FileModel();
//		if(!directoryList.isEmpty()){
//            directory = directoryList.get(0);
//        }else {
//            result = false;
//        }
//		//获取文件所在位置，寻找xml文件所在的路径，解析xml吧所需的数据插入到数据库中
//		//文件所在位置
//		String fileXmlPath = directory.getRelativeAddress();
//		//获取到xml所在的文件位置
//		String xmlPath = "";
//		//获取cae模型xml所在职位
//		String caePath = "";
//		xmlPath= resourceUtil.getXmlPath(fileXmlPath,xmlPath);
//		//对xml进行解析,遍历xml文件下所有文件
//		if(StringUtil.isNull(xmlPath)){
//			//       result = false;
//			caePath = resourceUtil.getCAEXmlPath(fileXmlPath,caePath);
//			File caeXmlFilePath = new File(caePath);
//			String[] caeSubFiles = caeXmlFilePath.list();
//			directoryController.insertCaeXmlInfo(caeSubFiles,caeXmlFilePath,xmlMap,caeXmlAnalysisMap);
//			for(Map.Entry<String,Map> entry : caeXmlAnalysisMap.entrySet()){
//				modelController.insertCAEData(entry,svgPath,isScopeDir,user,directory,classID,"");
//				modelReposityUrl = "http://"+resourceUtil.getGogsPath()+"/" + userName.toLowerCase() + "/"+ entry.getKey().split("\\.")[0] + ".git";
//			}
//
//		}else{
//			File xmlFilePath = new File(xmlPath);
//			String[] subFiles = xmlFilePath.list();
///*		Model model = directoryController.setPackageParam(userName,subFiles,directory,classID,isScopeDir,filePath);
//		Map<String, Object> param = directoryController.isAddModelAndReview(subFiles,classID,model);
//		//查找最外层空的model
//		//修改成根据插入的分类id找到对应的package包
//		Model nullModel = modelService.queryByNameAndDir(param);*/
//
//			String repository = subFiles[0].split("\\.")[0];
//			modelReposityUrl = "http://"+resourceUtil.getGogsPath()+"/" + userName.toLowerCase() + "/"+ repository + ".git";
//			directoryController.insertSvgPath(subFiles,xmlFilePath,xmlMap,svgPath,xmlAnalysisMap);
//			//遍历xmlMap进行数据的插入
//			for(Map.Entry<String,Map> entry : xmlAnalysisMap.entrySet()){
//				//解析xmlmap 把数据存放到数据
//				modelController.insertData(entry,svgPath,isScopeDir,user,directory,classID);
//			}
//			//更新模型的层次结构
//			//获取package下面的所有model
//			directoryController.updateModelFramwork(userName,fileName,true);
//			System.out.println("上传完毕！！！");
//		}
//		return modelReposityUrl;
		return null;
	}

	public  void uploadFileDate(String filename ,String username,String modelName,String description){

//		// 模型相对路径xieyx/20170620.../
//		String modelDir = resourceUtil.unzipFile(filename, username);
//		//输出文件的目录（modelDir是解压缩到的目录）
//		System.out.println("modelDir==========" + modelDir + "*************");
//		//获取到model解压缩的路径
//		String modelPath =  resourceUtil.getModelPath(modelDir, modelName);
//		//遍历文件，对model库进行插入
//	//	 modelService.insertModelData(modelDir,modelName,modelPath,description);
//		//获取根目录
//		//String parentPath = ResourceUtil.getFileDriectory() + modelDir;
//        String parentPath = modelPath;
//        resourceUtil.getSubFile(parentPath.substring(0,
//				parentPath.length() ),parentPath.substring(0,
//				parentPath.length() ),description);
//		return ;
	}
	private void insertChild(ComponentTreeObj classTreeRoot,List<Directory> directoryList,String userName){
		List<ComponentTreeObj> componentTreeObjChild = new ArrayList<>();
		for (Directory directory: directoryList) {
			if("我的模型".equals(classTreeRoot.getName())){
				if(directory.getUserName() != null) {
					if (directory.getParentId() == classTreeRoot.getId() && directory.getUserName().equals(userName)) {
						ComponentTreeObj componentTreeObj = new ComponentTreeObj();
						componentTreeObj.setId(directory.getId());
						componentTreeObj.setName(directory.getName());
						//新增一个子对象
						componentTreeObj.setChildren(componentTreeObjChild);
						componentTreeObjChild.add(componentTreeObj);
					}
				}
			}else{
				if(directory.getParentId() == classTreeRoot.getId()){
					ComponentTreeObj componentTreeObj = new ComponentTreeObj();
					componentTreeObj.setId(directory.getId());
					componentTreeObj.setName(directory.getName());
					//新增一个子对象
					componentTreeObj.setChildren(componentTreeObjChild);
					componentTreeObjChild.add(componentTreeObj);
				}
			}
		}
		classTreeRoot.setChildren(componentTreeObjChild);
		if(componentTreeObjChild.size() >0){
			for (ComponentTreeObj componentTreeObj: componentTreeObjChild) {
				insertChild(componentTreeObj,directoryList,userName);
			}
		}
	}

	//根据目录id获取当前的模型列表
	public String getModelListByDirId(Long dirId){
//		List<ModelWeb>  repositoryModelList = new ArrayList<>();
//		//过滤后的modelList
//		List<Model> searchModel = new ArrayList<>();
//		//查询过滤后模型库内的一个组件
//		List<Model> oneOfModel = new ArrayList<>();
//		//查询所有direactory
//		List<Directory> allDirectory = directoryService.findAllDirectory();
//		//查询所有的repository
//		List<Repository> allRepository = repositoryService.findAllRepository();
//		//查询所有的watch
//		List<Watch> allWatch = watchService.findAllWatch();
//		//查询所有的star
//		List<Star> allStar = starService.findAllStar();
//		//存放directory的id
//		List<Long> directoryIdList  = new ArrayList<>();
//		String jsonStr = "";
//		JSONArray json = new JSONArray();
//		if(dirId == null){
//			return  jsonStr;
//		}
//		try {
//			List<Directory> rootDirectoryList = directoryService.queryListById(dirId);
//			List<Model> allModelList = modelService.findAllModel();
//			if(dirId != null && dirId != 0 && rootDirectoryList.size() >0){
//				//仅有一个directory
//				if(rootDirectoryList.size() >0){
//					Directory oneDirectory = rootDirectoryList.get(0);
//					modelController.getModelTree(oneDirectory.getId(),allDirectory,directoryIdList);
//					directoryIdList.add(oneDirectory.getId());
//				}
//				for (Long id : directoryIdList) {
//					for (Model model: allModelList) {
//						if(model.getDirectoryId() == id){
//								if(model.getParentId() == 0){
//									searchModel.add(model);
//							}
//						}
//					}
//				}
//				for(int  j= 0; j<= searchModel.size() -1; j++){
//					for (Model model: allModelList) {
//						if(model.getParentId() == searchModel.get(j).getId()){
//							oneOfModel.add(model);
//							break;
//						}
//					}
//				}
//			}
//			for (int i = 0; i <= oneOfModel.size() -1; i++) {
//				ModelWeb modelWeb = new ModelWeb();
//				GUser user = gUserService.queryById(oneOfModel.get(i).getUserId());
//				modelWeb.setIndex(oneOfModel.get(i).getId());
//				modelWeb.setTotal(oneOfModel.size());
//				modelWeb.setName(modelUtil.splitName(oneOfModel.get(i).getName()));
//				modelWeb.setRepositoryName(oneOfModel.get(i).getName().split("\\.")[0]);
//				modelWeb.setParentId(oneOfModel.get(i).getParentId());
//				modelWeb.setUserName(user.getLowerName());
//				modelWeb.setUserId(user.getID());
//				modelWeb.setClasses(oneOfModel.get(i).getClasses());
//				modelWeb.setTextInfo(oneOfModel.get(i).getTextInfo());
//				modelWeb.setType(searchModel.get(i).getType());
////				if(oneOfModel.get(i).getDiagramSvgPath() != null && oneOfModel.get(i).getDiagramSvgPath() != ""){
//				if(oneOfModel.get(i).getIconSvgPath() != null && oneOfModel.get(i).getIconSvgPath() != ""){
//					modelWeb.setImageUrl("http://"+resourceUtil.getLocalPath()+"/FileLibrarys"+oneOfModel.get(i).getIconSvgPath().substring(7));
//				}
//				modelWeb.setUploadTime(oneOfModel.get(i).getCreateTime().getTime());
//				modelWeb.setCreateTime(DateUtil.format(oneOfModel.get(i).getCreateTime(),"yyyy-MM-dd"));
//				if(oneOfModel.get(i).getLastUpdateTime() != null){
//					modelWeb.setUpdateTime(DateUtil.format(oneOfModel.get(i).getLastUpdateTime(),"yyyy-MM-dd"));
//				}
//				modelWeb.setDiscription(oneOfModel.get(i).getDiscription());
//				modelWeb.setType(oneOfModel.get(i).getType());
//				modelWeb.setNumberStar(0);
//				modelWeb.setNumberWatch(0);
//				modelWeb.setAlreadyStar(false);
//				modelWeb.setAlreadyWatch(false);
//				repositoryModelList.add(modelWeb );
//			}
//			for (ModelWeb modelWeb : repositoryModelList) {
//				for (Repository repository: allRepository) {
//					if(modelWeb.getRepositoryName().equals(repository.getName())){
//						//关注列表
//						List<Watch> watches = new ArrayList<>();
//						for (Watch watch : allWatch){
//							if(repository.getID() == watch.getRepoID()){
//								watches.add(watch);
//							}
//							if(repository.getID() == watch.getRepoID() && modelWeb.getUserId() == watch.getUserID()){
//								modelWeb.setAlreadyWatch(true);
//							}
//						}
//						modelWeb.setNumberWatch(watches.size());
//						//收藏列表
//						List<Star> stars = new ArrayList<>();
//						for (Star star : allStar) {
//							if (repository.getID() == star.getRepoId()){
//								stars.add(star);
//							}
//							if(repository.getID() == star.getRepoId() && modelWeb.getUserId() == star.getUid()){
//								modelWeb.setAlreadyStar(true);
//							}
//						}
//						modelWeb.setNumberStar(stars.size());
//					}
//				}
//			}
//
//		}catch(Exception e){
//			e.printStackTrace();
//			return jsonStr;
//		}
//		json.add(repositoryModelList);
//		jsonStr = json.toString();
//		return  jsonStr;
		return null;
	}

	}



