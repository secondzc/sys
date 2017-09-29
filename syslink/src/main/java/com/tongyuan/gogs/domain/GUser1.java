package com.tongyuan.gogs.domain;

import java.util.Map;

/**
 * Created by yh on 2017/9/25.
 */
public class GUser1 {

    private long id;

    private String lowerName;

    private String name;
    //    自定义名称
    private String fullName;

    private String email;

    private String passwd;
    //  登陆类型默认为0,用户名密码登录
    private int loginType;

    private long loginSource;

    private String loginName;
    //  类型，0为用户，1为组织
    private int type;
    //  所在地
    private String location;
    //  个人网站
    private String website;

    private String rands;

    private String salt;

    private long createdUnix;

    private long updatedUnix;

    private boolean lastRepoVisibility;
    //  最大仓库创建数量-1为默认
    private int maxRepoCreation;
    //  是否激活
    private boolean isActive;
    //  是否为管理员
    private boolean isAdmin;

    private boolean allowGitHook;

    private boolean allowImportLocal;
    //  是否禁止登陆
    private boolean prohibitLogin;
    //  头像
    private String avatar;
    //  获取头像的邮箱
    private String avatarEmail;
    //  是否使用自定义头像
    private boolean useCustomAvatar;

    private int numFollowers;

    private int numFollowing;

    private int numStars;

    private int numRepos;

    private String description;
    //  团队数量,类型为组织才有
    private int numTeams;
    //  成员数量,类型为组织才有
    private int numMembers;


    public GUser1(Map<String, Object> map) {
        this.id = Integer.parseInt(map.get("id").toString());
        this.lowerName = map.get("lowerName").toString();
        this.name = map.get("name").toString();
        this.fullName = map.get("fullName").toString();
        this.email = map.get("email").toString();
        this.passwd = map.get("passwd").toString();
        this.loginType = Integer.parseInt(map.get("loginType").toString());
        this.loginSource = Integer.parseInt(map.get("loginSource").toString());
        this.loginName = map.get("loginName").toString();
        this.type = Integer.parseInt(map.get("type").toString());
        this.location = map.get("location").toString();
        this.website = map.get("website").toString();
        this.rands = map.get("rands").toString();
        this.salt = map.get("salt").toString();
        this.createdUnix = Long.parseLong(map.get("createdUnix").toString());
        this.updatedUnix = Long.parseLong(map.get("updatedUnix").toString());
        this.lastRepoVisibility = (boolean) map.get("lastRepoVisibility");
        this.maxRepoCreation = Integer.parseInt(map.get("maxRepoCreation").toString());
        this.isActive = (boolean) map.get("isActive");
        this.isAdmin = (boolean) map.get("isAdmin");
        this.allowGitHook = (boolean) map.get("allowGitHook");
        this.allowImportLocal = (boolean) map.get("allowImportLocal");
        this.prohibitLogin = (boolean) map.get("prohibitLogin");
        this.avatar = map.get("avatar").toString();
        this.avatarEmail = map.get("avatarEmail").toString();
        this.useCustomAvatar = (boolean) map.get("useCustomAvatar");
        this.numFollowers = Integer.parseInt(map.get("numFollowers").toString());
        this.numFollowing = Integer.parseInt(map.get("numFollowing").toString());
        this.numStars = Integer.parseInt(map.get("numStars").toString());
        this.numRepos = Integer.parseInt(map.get("numRepos").toString());
        this.description = map.get("description").toString();
        this.numTeams = Integer.parseInt(map.get("numTeams").toString());
        this.numMembers = Integer.parseInt(map.get("numMembers").toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLowerName() {
        return lowerName;
    }

    public void setLowerName(String lowerName) {
        this.lowerName = lowerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public long getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(long loginSource) {
        this.loginSource = loginSource;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRands() {
        return rands;
    }

    public void setRands(String rands) {
        this.rands = rands;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getCreatedUnix() {
        return createdUnix;
    }

    public void setCreatedUnix(long createdUnix) {
        this.createdUnix = createdUnix;
    }

    public long getUpdatedUnix() {
        return updatedUnix;
    }

    public void setUpdatedUnix(long updatedUnix) {
        this.updatedUnix = updatedUnix;
    }

    public boolean isLastRepoVisibility() {
        return lastRepoVisibility;
    }

    public void setLastRepoVisibility(boolean lastRepoVisibility) {
        this.lastRepoVisibility = lastRepoVisibility;
    }

    public int getMaxRepoCreation() {
        return maxRepoCreation;
    }

    public void setMaxRepoCreation(int maxRepoCreation) {
        this.maxRepoCreation = maxRepoCreation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAllowGitHook() {
        return allowGitHook;
    }

    public void setAllowGitHook(boolean allowGitHook) {
        this.allowGitHook = allowGitHook;
    }

    public boolean isAllowImportLocal() {
        return allowImportLocal;
    }

    public void setAllowImportLocal(boolean allowImportLocal) {
        this.allowImportLocal = allowImportLocal;
    }

    public boolean isProhibitLogin() {
        return prohibitLogin;
    }

    public void setProhibitLogin(boolean prohibitLogin) {
        this.prohibitLogin = prohibitLogin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarEmail() {
        return avatarEmail;
    }

    public void setAvatarEmail(String avatarEmail) {
        this.avatarEmail = avatarEmail;
    }

    public boolean isUseCustomAvatar() {
        return useCustomAvatar;
    }

    public void setUseCustomAvatar(boolean useCustomAvatar) {
        this.useCustomAvatar = useCustomAvatar;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public int getNumRepos() {
        return numRepos;
    }

    public void setNumRepos(int numRepos) {
        this.numRepos = numRepos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumTeams() {
        return numTeams;
    }

    public void setNumTeams(int numTeams) {
        this.numTeams = numTeams;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }
}