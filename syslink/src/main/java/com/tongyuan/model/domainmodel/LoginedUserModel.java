package com.tongyuan.model.domainmodel;


import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.domain.OrgUser;
import com.tongyuan.model.domain.Loginstate;
import com.tongyuan.model.domain.ModelAuth;

import java.util.*;

/**
 * 已登录用户信息
 * 
 * @author liuq
 *
 */
public class LoginedUserModel {


	private GUser profile;

    // 用户角色
    private List<Long> roles=new ArrayList<>();

    //用户权限
    private Set<String> auths  = new HashSet<>();

    private List<Map<String,Object>> user_team = new ArrayList<>();

    private List<OrgUser> user_org = new ArrayList<OrgUser>();

    private List<String>modelAuths = new ArrayList<>();


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


	public GUser getProfile() {
		return profile;
	}

	public void setProfile(GUser profile) {
		this.profile = profile;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

	public Set<String> getAuths() {
		return auths;
	}

	public void setAuths(Set<String> auths) {
		this.auths = auths;
	}

	public List<Map<String, Object>> getUser_team() {
		return user_team;
	}

	public void setUser_team(List<Map<String, Object>> user_team) {
		this.user_team = user_team;
	}

	public List<OrgUser> getUser_org() {
		return user_org;
	}

	public void setUser_org(List<OrgUser> user_org) {
		this.user_org = user_org;
	}

	public Loginstate getLoginState() {
		return loginState;
	}

	public void setLoginState(Loginstate loginState) {
		this.loginState = loginState;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getModelAuths() {
		return modelAuths;
	}

	public void setModelAuths(List<String> modelAuths) {
		this.modelAuths = modelAuths;
	}
}
