package com.tongyuan.model.domainmodel;


import com.tongyuan.model.enums.PermissionItemDisplayStyle;

/**
 * 权限值信息
 * 
 * @author liuq
 *
 */
public class PermissionValueModel {
    
	// 权限代码（唯一）
    private String code ;

    // 显示名称
    private String displayName;

    // 显示样式
    private PermissionItemDisplayStyle displayStyle;

    // 权限值
    private String value;
      
    public PermissionValueModel()
    {
    	
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public PermissionItemDisplayStyle getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(PermissionItemDisplayStyle displayStyle) {
		this.displayStyle = displayStyle;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
