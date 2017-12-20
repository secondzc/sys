package com.tongyuan.gogs.domain;

/**
 * Created by yh on 2017/9/25.
 */
public class Team {

    private long id;

    private long orgId;

    private String lowerName;

    private String name;

    private String description;

    private int authorize;

    private int numRepos;

    private int numMembers;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorize() {
        return authorize;
    }

    public void setAuthorize(int authorize) {
        this.authorize = authorize;
    }

    public int getNumRepos() {
        return numRepos;
    }

    public void setNumRepos(int numRepos) {
        this.numRepos = numRepos;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }
}
