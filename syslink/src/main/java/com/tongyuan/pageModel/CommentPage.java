package com.tongyuan.pageModel;


import java.sql.Timestamp;

public class CommentPage {
    private Byte status;
    private Timestamp lastUpdateTime;
    private String comment;
    private String checkorName;
    private String showStatus;

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCheckorName() {
        return checkorName;
    }

    public void setCheckorName(String checkorName) {
        this.checkorName = checkorName;
    }
}
