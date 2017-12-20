package com.tongyuan.gogs.domain;

/**
 * Created by yh on 2017/9/25.
 */
public class OrgUser {
    private long id;

    private long uid;

    private long orgId;

    private short isPublic;

    private short isOwner;

    private int numTeams;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public short getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(short isPublic) {
        this.isPublic = isPublic;
    }

    public short getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(short isOwner) {
        this.isOwner = isOwner;
    }

    public int getNumTeams() {
        return numTeams;
    }

    public void setNumTeams(int numTeams) {
        this.numTeams = numTeams;
    }
}
