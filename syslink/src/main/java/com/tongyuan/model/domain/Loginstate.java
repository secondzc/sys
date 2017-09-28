package com.tongyuan.model.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Loginstate entity. 
 * @author MyEclipse Persistence Tools
 * 对应于登录记录表，
 * 用于保存用户的登录状况。
 */
@Entity
public class Loginstate {

	// Fields
    //id
	@Id
	@GeneratedValue
	private long id;
	//登录总数
	@Column
	private long loginCount;
	//现在登录IP
	@Column
	private String nowLoginIp;
	//上次登录IP
	@Column
	private String lastLoginIp;
	//现在登录日期
	@Column
	private Date nowLoginDate;
	//上次登录日期
	@Column
	private Date lastLoginDate;
	//创建日期
	@Column
	private Date createDate;

	// Constructors

	/** default constructor */
	public Loginstate() {
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(long loginCount) {
		this.loginCount = loginCount;
	}

	public String getNowLoginIp() {
		return nowLoginIp;
	}

	public void setNowLoginIp(String nowLoginIp) {
		this.nowLoginIp = nowLoginIp;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getNowLoginDate() {
		return nowLoginDate;
	}

	public void setNowLoginDate(Date nowLoginDate) {
		this.nowLoginDate = nowLoginDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}