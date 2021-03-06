package com.tongyuan.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 接口
 * 
 * @author leftso
 *
 */
@WebService(name = "CommonService", // 暴露服务名称
		targetNamespace = "http://webservice.tongyuan.com/"// 命名空间,一般是接口的包名倒序
)
public interface CommonService {
	@WebMethod
	@WebResult(name = "String", targetNamespace = "")
	public String sayHello(@WebParam(name = "userName") String name);

	@WebMethod
	@WebResult(name = "String",targetNamespace = "")
	public boolean validateUser(@WebParam(name = "userName") String userName,@WebParam(name = "passWord") String passWord);

	@WebMethod
	@WebResult(name = "String",targetNamespace = "")
	public String UploadFile(@WebParam(name = "userName") String userName,@WebParam(name = "fileName") String fileName,@WebParam(name = "remoteModelPath") String remoteModelPath,@WebParam(name = "beginPos") long beginPos,@WebParam(name = "length") long length,@WebParam(name = "data") byte[] data);

	@WebMethod
	@WebResult(name = "String",targetNamespace = "")
	public boolean UploadMetaFile(@WebParam(name = "userName") String userName,@WebParam(name = "dirID") Long dirID,@WebParam(name = "modelFilePath") String modelFilePath,@WebParam(name = "metaFileContent") String metaFileContent);

	@WebMethod
	@WebResult(name = "String",targetNamespace = "")
	public String uploadModel(@WebParam(name = "userName") String userName,@WebParam(name = "classID") Long classID,@WebParam(name = "fileName") String fileName,@WebParam(name = "beginPos") long beginPos,@WebParam(name = "length") long length,@WebParam(name = "data") byte[] data);

	@WebMethod
	@WebResult(name = "String",targetNamespace = "")
	public String getClassTree(@WebParam(name = "userName") String userName);

	@WebMethod
	@WebResult(name = "String",targetNamespace = "")
	public String getModelListByDirId(@WebParam(name = "dirId") Long dirId);
}
