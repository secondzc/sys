package com.tongyuan.model.domainmodel;


import com.tongyuan.model.domain.Role;
import com.tongyuan.util.DateUtil;

/**
 * 角色基本信息
 * 
 * @author liuq
 *
 */
public class RoleModel {
	
    public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	// 角色ID
    private String id ;



    // 角色名
    private String name;

    // 角色描述
    private String description;

    // 权限Json数据
    private String permissionJsonData = "{}";

    // 排序值
    private int sort;

    // 创建日期
    private String createDate ;
    
    public RoleModel()
    {
    	
    }
    
    public RoleModel(Role role)
    {
    	 this.id = String.valueOf(role.getId());

    	 this.name = role.getName();
    	 this.description = role.getDescription();
    	 this.permissionJsonData = role.getPermissions();
    	 this.sort = role.getSort();
    	 this.createDate = role.getCreateDate().toString();
    }
    
    /**
     * 转为Role对象
     * 
     * @return
     */
    public Role toRole()
    {
    	Role role=new Role();
    	role.setId(Integer.parseInt(this.id));

    	role.setName(this.name);
    	role.setDescription(this.description);
    	role.setPermissions(this.permissionJsonData);
    	role.setSort(this.sort);
    	role.setCreateDate(DateUtil.toTimestamp(this.createDate, null));
    	return role;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getPermissionJsonData() {
		return permissionJsonData;
	}

	public void setPermissionJsonData(String permissionJsonData) {
		this.permissionJsonData = permissionJsonData;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
