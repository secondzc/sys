package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by xyx on 2017-9-18.
 */

@Entity
public class GUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    @Column(nullable = false, unique = true)
    private String LowerName;
    @Column(nullable = false, unique = true)
    private String Name;
    @Column
    private String FullName;
    @Column
    private String Email;
    @Column
    private String Passwd;
    @Column
    private Integer LoginType;
    @Column
    private Long LoginSource;
    @Column
    private String LoginName;
    @Column
    private Integer Type;
    @Column
    private String Location;
    @Column
    private String Website;
    @Column
    private String Rands;
    @Column
    private String Salt;
    @Column
    private Long CreatedUnix;
    @Column
    private Long UpdatedUnix;
    @Column
    private Boolean LastRepoVisibility;
    @Column
    private Boolean MaxRepoCreation;
    @Column
    private Boolean IsActive;
    @Column
    private Boolean IsAdmin;
    @Column
    private Boolean AllowGitHook;
    @Column
    private Boolean AllowImportLocal;
    @Column
    private Boolean ProhibitLogin;
    @Column
    private String Avatar;
    @Column
    private String AvatarEmail;
    @Column
    private Boolean UseCustomAvatar;
    @Column
    private Integer NumFollowers;
    @Column
    private Integer NumFollowing;
    @Column
    private Integer NumStars;
    @Column
    private Integer NumRepos;
    @Column
    private String Description;
    @Column
    private Integer NumTeams;
    @Column
    private Integer NumMembers;


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

    public Boolean getMaxRepoCreation() {
        return MaxRepoCreation;
    }

    public void setMaxRepoCreation(Boolean maxRepoCreation) {
        MaxRepoCreation = maxRepoCreation;
    }

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
