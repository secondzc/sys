package com.tongyuan.model.domainmodel;

import com.tongyuan.model.domain.Loginstate;
import com.tongyuan.gogs.domain.GUser1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 已登录用户信息
 * 
 * @author liuq
 *
 */
public class LoginedUserModel  {

    // 用户信息
 //   private User profile;

	private GUser1 profile;

    // 用户角色
    private List<RoleModel> roles=new ArrayList<RoleModel>();

    // 用户权限
    private Map<String, PermissionValueModel> permissions = new HashMap<String, PermissionValueModel>();

    // 登录情况
    private Loginstate loginState;

    // 票据
    private String token ;
    
    /**
     * WebService使用复杂对象，必须有一个不带参数的构造方法。
     */
    public LoginedUserModel()
    {
    	
    }

    /**
     * 获取用户信息
     * 
     * @return

	public User getProfile() {
		return profile;
	}
	 */
	/**
	 * 设置用户信息
	 * 
	 * @param profile

	public void setProfile(User profile) {
		this.profile = profile;
	}
	 */


	public GUser1 getProfile() {
		return profile;
	}

	public void setProfile(GUser1 profile) {
		this.profile = profile;
	}

	/**
	 * 获取用户用于角色
	 * 
	 * @return
	 */
	public List<RoleModel> getRoles() {
		return roles;
	}

	/**
	 * 设置用户的角色
	 * 
	 * @param roles
	 */
	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}

	/**
	 * 获取权限
	 * 
	 * @return
	 */
	public Map<String, PermissionValueModel> getPermissions() {
		return permissions;
	}

	/**
	 * 设置用户权限
	 * 
	 * @param permissions
	 */
	public void setPermissions(Map<String, PermissionValueModel> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 获取登录状态
	 * 
	 * @return
	 */
	public Loginstate getLoginState() {
		return loginState;
	}

	/**
	 * 设置登录状态
	 * 
	 * @param loginState
	 */
	public void setLoginState(Loginstate loginState) {
		this.loginState = loginState;
	}

	/**
	 * 获取令牌
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置令牌
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
