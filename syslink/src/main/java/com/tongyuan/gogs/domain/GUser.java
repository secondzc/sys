package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by xyx on 2017-9-18.
 */

public class GUser {
    private long ID;
    private String LowerName;
    private String Name;
    private String FullName;
    private String Email;
    private String Passwd;
    private Integer LoginType;
    private Long LoginSource;
    private String LoginName;
    private Integer Type;
    private String Location;
    private String Website;
    private String Rands;
    private String Salt;
    private Long CreatedUnix;
    private Long UpdatedUnix;
    private Boolean LastRepoVisibility;
    private  Integer MaxRepoCreation;
    private Boolean IsActive;
    private Boolean IsAdmin;
    private Boolean AllowGitHook;
    private Boolean AllowImportLocal;
    private Boolean ProhibitLogin;
    private String Avatar;
    private String AvatarEmail;
    private Boolean UseCustomAvatar;
    private Integer NumFollowers;
    private Integer NumFollowing;
    private Integer NumStars;
    private Integer NumRepos;
    private String Description;
    private Integer NumTeams;
    private Integer NumMembers;

    public GUser(){}

    public GUser(long ID, String lowerName, String name, String fullName, String email, String passwd, Integer loginType, Long loginSource, String loginName, Integer type, String location, String website, String rands, String salt, Long createdUnix, Long updatedUnix, Boolean lastRepoVisibility, Integer maxRepoCreation, Boolean isActive, Boolean isAdmin, Boolean allowGitHook, Boolean allowImportLocal, Boolean prohibitLogin, String avatar, String avatarEmail, Boolean useCustomAvatar, Integer numFollowers, Integer numFollowing, Integer numStars, Integer numRepos, String description, Integer numTeams, Integer numMembers) {
        this.ID = ID;
        LowerName = lowerName;
        Name = name;
        FullName = fullName;
        Email = email;
        Passwd = passwd;
        LoginType = loginType;
        LoginSource = loginSource;
        LoginName = loginName;
        Type = type;
        Location = location;
        Website = website;
        Rands = rands;
        Salt = salt;
        CreatedUnix = createdUnix;
        UpdatedUnix = updatedUnix;
        LastRepoVisibility = lastRepoVisibility;
        MaxRepoCreation = maxRepoCreation;
        IsActive = isActive;
        IsAdmin = isAdmin;
        AllowGitHook = allowGitHook;
        AllowImportLocal = allowImportLocal;
        ProhibitLogin = prohibitLogin;
        Avatar = avatar;
        AvatarEmail = avatarEmail;
        UseCustomAvatar = useCustomAvatar;
        NumFollowers = numFollowers;
        NumFollowing = numFollowing;
        NumStars = numStars;
        NumRepos = numRepos;
        Description = description;
        NumTeams = numTeams;
        NumMembers = numMembers;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLowerName() {
        return LowerName;
    }

    public void setLowerName(String lowerName) {
        LowerName = lowerName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String passwd) {
        Passwd = passwd;
    }

    public Integer getLoginType() {
        return LoginType;
    }

    public void setLoginType(Integer loginType) {
        LoginType = loginType;
    }

    public Long getLoginSource() {
        return LoginSource;
    }

    public void setLoginSource(Long loginSource) {
        LoginSource = loginSource;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getRands() {
        return Rands;
    }

    public void setRands(String rands) {
        Rands = rands;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public Long getCreatedUnix() {
        return CreatedUnix;
    }

    public void setCreatedUnix(Long createdUnix) {
        CreatedUnix = createdUnix;
    }

    public Long getUpdatedUnix() {
        return UpdatedUnix;
    }

    public void setUpdatedUnix(Long updatedUnix) {
        UpdatedUnix = updatedUnix;
    }

    public Boolean getLastRepoVisibility() {
        return LastRepoVisibility;
    }

    public void setLastRepoVisibility(Boolean lastRepoVisibility) {
        LastRepoVisibility = lastRepoVisibility;
    }

    public Integer getMaxRepoCreation() {
        return MaxRepoCreation;
    }

    public void setMaxRepoCreation(Integer maxRepoCreation) {
        MaxRepoCreation = maxRepoCreation;
    }


    //    public Boolean getMaxRepoCreation() {
//        return MaxRepoCreation;
//    }
//
//    public void setMaxRepoCreation(Boolean maxRepoCreation) {
//        MaxRepoCreation = maxRepoCreation;
//    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Boolean getAdmin() {
        return IsAdmin;
    }

    public void setAdmin(Boolean admin) {
        IsAdmin = admin;
    }

    public Boolean getAllowGitHook() {
        return AllowGitHook;
    }

    public void setAllowGitHook(Boolean allowGitHook) {
        AllowGitHook = allowGitHook;
    }

    public Boolean getAllowImportLocal() {
        return AllowImportLocal;
    }

    public void setAllowImportLocal(Boolean allowImportLocal) {
        AllowImportLocal = allowImportLocal;
    }

    public Boolean getProhibitLogin() {
        return ProhibitLogin;
    }

    public void setProhibitLogin(Boolean prohibitLogin) {
        ProhibitLogin = prohibitLogin;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAvatarEmail() {
        return AvatarEmail;
    }

    public void setAvatarEmail(String avatarEmail) {
        AvatarEmail = avatarEmail;
    }

    public Boolean getUseCustomAvatar() {
        return UseCustomAvatar;
    }

    public void setUseCustomAvatar(Boolean useCustomAvatar) {
        UseCustomAvatar = useCustomAvatar;
    }

    public Integer getNumFollowers() {
        return NumFollowers;
    }

    public void setNumFollowers(Integer numFollowers) {
        NumFollowers = numFollowers;
    }

    public Integer getNumFollowing() {
        return NumFollowing;
    }

    public void setNumFollowing(Integer numFollowing) {
        NumFollowing = numFollowing;
    }

    public Integer getNumStars() {
        return NumStars;
    }

    public void setNumStars(Integer numStars) {
        NumStars = numStars;
    }

    public Integer getNumRepos() {
        return NumRepos;
    }

    public void setNumRepos(Integer numRepos) {
        NumRepos = numRepos;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getNumTeams() {
        return NumTeams;
    }

    public void setNumTeams(Integer numTeams) {
        NumTeams = numTeams;
    }

    public Integer getNumMembers() {
        return NumMembers;
    }

    public void setNumMembers(Integer numMembers) {
        NumMembers = numMembers;
    }
}
