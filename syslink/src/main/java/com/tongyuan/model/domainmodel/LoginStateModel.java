package com.tongyuan.model.domainmodel;


import com.tongyuan.model.domain.Loginstate;

/**
 * 登录状况
 * 
 * @author liuq
 *
 */
public class LoginStateModel {
    
	// 用户ID
    private String userId;

    // 系统ID
    private String systemId;

    // 登录总数
    private int loginCount;

    // 现在登录IP
    private String nowLoginIp;

    // 上次登录IP
    private String lastLoginIp;

    // 现在登录日期
    private String nowLoginDate;
    
    // 上次登录日期
    private String lastLoginDate;

    // 创建日期
    private String createDate;
    
    /**
     * 无参构造函数
     */
    public LoginStateModel()
    {
    	
    }
     
    /**
     * 将Loginstate构造为LoginStateModel
     * 
     * @param loginState
     */
    public LoginStateModel(Loginstate loginState)
    {
    	this.userId = String.valueOf(loginState.getId());

    //	this.loginCount = loginState.getLoginCount();
    	this.nowLoginIp =loginState.getNowLoginIp();
    	this.nowLoginDate = loginState.getNowLoginDate().toString();
    	this.lastLoginIp=loginState.getLastLoginIp();
    	if (loginState.getLastLoginDate()!=null) {
        	this.lastLoginDate =loginState.getLastLoginDate().toString();
		}
    	if (loginState.getCreateDate()!=null) {
        	this.createDate = loginState.getCreateDate().toString();	
		}
    }
       
    /**
     * 获取用户id
     * 
     * @return
     */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户id
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * 获取登录次数
	 * 
	 * @return
	 */
	public int getLoginCount() {
		return loginCount;
	}

	/**
	 * 设置登录次数
	 * 
	 * @param loginCount
	 */
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

    /**
     * 获取当前登录ip
     * 
     * @return
     */
	public String getNowLoginIp() {
		return nowLoginIp;
	}

	/**
	 * 设置当前登录ip
	 * 
	 * @param nowLoginIp
	 */
	public void setNowLoginIp(String nowLoginIp) {
		this.nowLoginIp = nowLoginIp;
	}

	/**
	 * 获取上次登录ip
	 * 
	 * @return
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * 设置上次登录ip
	 * 
	 * @param lastLoginIp
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * 获取当前登录时间
	 * 
	 * @return
	 */
	public String getNowLoginDate() {
		return nowLoginDate;
	}

	/**
	 * 设置当前登录时间
	 * 
	 * @param nowLoginDate
	 */
	public void setNowLoginDate(String nowLoginDate) {
		this.nowLoginDate = nowLoginDate;
	}

	/**
	 * 获取上次登录时间
	 * 
	 * @return
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * 设置上次登录时间
	 * 
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
      
}
