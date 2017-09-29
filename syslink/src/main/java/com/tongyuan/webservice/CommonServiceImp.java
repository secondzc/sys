package com.tongyuan.webservice;

import com.tongyuan.model.controller.ModelController;
import com.tongyuan.model.domain.FileModel;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.enums.ModelClasses;
import com.tongyuan.model.service.DirectoryService;
import com.tongyuan.model.service.FileModelService;
import com.tongyuan.model.service.ModelService;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     private ResourceUtil resourceUtil ;

	@Autowired
	private ModelService modelService;
	@Autowired
	private ModelController modelController;
	@Autowired
	private DirectoryService directoryService;
	@Autowired
	private FileModelService fileModelService;

	@Override
	public String sayHello(String name) {

		return "Hello ," + name;
	}

	@Override
	public boolean validateUser(String userName, String passWord) {
		Boolean result =true;
        if(StringUtil.isNull(userName)){
          return false;
		}
		if(StringUtil.isNull(passWord)){
			result = false;
		}
		try{

		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/* 上传任务压缩文件,fileName为**.zip
    */
	public boolean UploadFile(String fileName, long beginPos, long length,
								  byte[] data) {
		System.out.println("starting upload the file...");
		boolean result = false;
		//获取压缩包 C:/Temp/zip/文件名
		String filePath = resourceUtil.getzipPath() + fileName;
		System.out.println("filePath==" + filePath);
		try {
			System.out.println("starting writing file...");
			resourceUtil.writeFile(filePath, beginPos, length, data);

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
			String name = fileName;
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("name",name);
			//查找到项目所在的位置
			List<FileModel> directoryList = fileModelService.queryListByName(params);
			//选取最近push的一个directory对象
			FileModel directory = new FileModel();
			if(!directoryList.isEmpty()){
				directory = directoryList.get(0);
			}else {
				result = false;
				return result;
			}
			//获取文件所在位置，寻找xml文件所在的路径，解析xml吧所需的数据插入到数据库中
			//文件所在位置
			String fileXmlPath = directory.getAbsoluteAddress();
			//获取到xml所在的文件位置
			String xmlPath = "";
			xmlPath= resourceUtil.getXmlPath(fileXmlPath,xmlPath);
			//对xml进行解析,遍历xml文件下所有文件
			if(StringUtil.isNull(xmlPath)){
				result = false;
				return result;
			}
			File xmlFilePath = new File(xmlPath);
			String[] subFiles = xmlFilePath.list();
			Model model = new Model();
			model.setName(subFiles[0].split("\\.")[0]);
			model.setDirectoryId(directory.getId());
			model.setClasses(ModelClasses.Package.getKey());
			model.setModelFilePath(filePath);
			model.setScope(false);
			model.setUserId(1);
			model.setDeleted(false);
			Long id = -1L;
			if(modelService.queryModelByName(subFiles[0].split("\\.")[0]) == null){
				id = modelService.add(model);
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
//				modelController.insertData(entry,svgPath,nullModel,directory);
			}
			result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		System.out.println("上传完毕！！！");
		return result;
	}

	public  void uploadFileDate(String filename ,String username,String modelName,String description){

		// 模型相对路径xieyx/20170620.../
		String modelDir = resourceUtil.unzipFile(filename, username);
		//输出文件的目录（modelDir是解压缩到的目录）
		System.out.println("modelDir==========" + modelDir + "*************");
		//获取到model解压缩的路径
		String modelPath =  resourceUtil.getModelPath(modelDir, modelName);
		//遍历文件，对model库进行插入
	//	 modelService.insertModelData(modelDir,modelName,modelPath,description);
		//获取根目录
		//String parentPath = ResourceUtil.getFileDriectory() + modelDir;
        String parentPath = modelPath;
        resourceUtil.getSubFile(parentPath.substring(0,
				parentPath.length() ),parentPath.substring(0,
				parentPath.length() ),description);
		return ;
	}
}


