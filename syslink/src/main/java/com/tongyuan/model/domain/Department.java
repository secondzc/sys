package com.tongyuan.model.domain;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Department entity. 
 *@author MyEclipse Persistence Tools
 * 对应于部门表
 * 存储部门信息
 */
@Entity
public class Department {

	// Fields
    //部门id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Column
	//部门的上级部门
	private Long pid;

	@Column
    private String depts;

    @Column
	//排序
	private Integer sort;

	@Column( nullable = false, length = 128)
	//部门名称
	private String name;
	@Column( length = 255)
	//描述
	private String description;
	@Column
	//创建人
	private long creator;

	@Column
	//创建日期
	private Date createDate;
	@Column
	//更新人
	private String updatePerson;
	@Column
	//更新日期
	private Date updateDate;

	public Department()
    {

    }


    public Department(Long pid, String depts, Integer sort, String name, String description, long creator, Date createDate, String updatePerson, Date updateDate) {
        this.pid = pid;
        this.depts = depts;
        this.sort = sort;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.createDate = createDate;
        this.updatePerson = updatePerson;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}