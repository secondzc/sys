package com.tongyuan.model.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Permissiongroup entity. 
 * @author MyEclipse Persistence Tools
 * 对应于权限组表，
 * 该表用于保存各系统的权限组信息。
 */
@Entity

public class PermissionGroup  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private long id;

	@Column
	//权限组名称
	private String name;
    @Column
	private String url;
	@Column
	//创建日期
	private Timestamp createDate;
	@Column
	//描述
	private String description;

	public PermissionGroup() {

	}

	public PermissionGroup(String name, String url, Timestamp createDate, String description) {
		this.name = name;
		this.url = url;
		this.createDate = createDate;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}