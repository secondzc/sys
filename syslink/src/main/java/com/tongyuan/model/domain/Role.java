package com.tongyuan.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Role entity. 
 * @author MyEclipse Persistence Tools
 * 对应于角色表，
 * 该表为用户角色基本信息。
 */
@Entity

public class Role {

	// Fields
    //角色id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private int id;

	//角色名称
	@Column
	private String name;
	//描述
	@Column
	private String description;


	//创建日期
	@Column
	private Date createDate;


	// Constructors

	/** default constructor */
	public Role() {
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}