package com.tongyuan.model.domainmodel;

import com.tongyuan.model.domain.User;
import com.tongyuan.util.DateUtil;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;


/**
 * 用户信息
 * 
 * @author liuq
 *
 */

public class UserModel {


	private long id;

	//    用户名

	private String userName;
	//    密码

	private String passWord;
	//    部门

	private long departmentId;

//    所在部门的数组

	private  String depts;

	//性别
	
	private Short gender;
	//真实姓名

	private String realName;
	//电子邮箱

	private String email;


	//手机号码

	private String mobile;
	//用户状态

	private Short status;
	//创建人

	private String createUser;
	//创建日期

	private Date createDate;
	//修改人

	private String updateUser;
	//修改时间

	private Date updateDate;
	//是否允许删除

	private Short deletable;
	//是否删除

	private Short deleted;



	private User user;

	public UserModel()
	{

	}


}
