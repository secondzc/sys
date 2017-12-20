package com.tongyuan.model.domain;

import java.sql.Timestamp;
import javax.persistence.*;

/**
 * OperationlogService entity.
 * @author MyEclipse Persistence Tools
 * 对应于操作日志表，
 * 该表用于保存用户在各个系统中的操作日志。
 */
@Entity
public class Operationlog  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
    //ID
	private long id;

	//用户id
	@Column
	private long userId;
	//用户名
	@Column
	private String userName;

	@Column
	private String realName;
	//用户ip
	@Column
	private String userIp;
	//标题
	@Column
	private String title;
	//内容
	@Column
	private String content;
	//创建日期
	@Column
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public Operationlog() {
	}

	public Operationlog(long userId, String userName, String realName, String userIp, String title, String content, Timestamp createDate) {
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
		this.userIp = userIp;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}