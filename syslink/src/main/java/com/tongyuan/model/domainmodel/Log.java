package com.tongyuan.model.domainmodel;

import com.tongyuan.model.controller.BaseController;
import com.tongyuan.util.IpUtil;

import javax.servlet.http.HttpServletRequest;

public class Log extends BaseController {
	

	private  long userId;
	private String userName;
	private  String realName;
	private  String userIp;



	public Log(HttpServletRequest request) {
		userId=getCurrentUserId(request);
		realName=getCurrentRealName(request);
		 userIp = IpUtil.getIpAddr(request);
		 userName=getCurrentUserName(request);

	}

	public long getUserId1() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}



	public String getUserName1() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
