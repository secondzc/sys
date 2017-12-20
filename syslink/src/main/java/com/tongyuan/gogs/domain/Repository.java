package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by xyx on 2017-9-18.
 */
public class Repository {
    private long ID;
    private long OwnerID;
    private String LowerName;
    private String Name;
    private String Description;
    private String Website;
    private String DefaultBranch;
    private long Size;
    private Integer NumWatches;
    private Integer NumStars;
    private Integer NumForks;
    private Integer NumIssues;
    private Integer NumClosedIssues;
    private Integer NumPulls;
    private Integer NumClosedPulls;
    private Integer NumMilestones;
    private Integer NumClosedMilestones;
    private Boolean IsPrivate;
    private Boolean IsBare;
    private Boolean IsMirror;
    private Boolean EnableWiki;
    private Boolean AllowPublicWiki;
    private Boolean EnableExternalWiki;
    private String ExternalWikiURL;
    private Boolean EnableIssues;
    private Boolean AllowPublicIssues;
    private Boolean EnableExternalTracker;
    private String ExternalTrackerURL;
    private String ExternalTrackerFormat;
    private String ExternalTrackerStyle;
    private Boolean EnablePulls;
    private Boolean IsFork;
    private Long ForkID;
    private Long CreatedUnix;
    private Long UpdatedUnix;
    public Repository(){}


    public Repository(long ID, long ownerID, String lowerName, String name, String description, String website, String defaultBranch, long size, Integer numWatches, Integer numStars, Integer numForks, Integer numIssues, Integer numClosedIssues, Integer numPulls, Integer numClosedPulls, Integer numMilestones, Integer numClosedMilestones, Boolean isPrivate, Boolean isBare, Boolean isMirror, Boolean enableWiki, Boolean allowPublicWiki, Boolean enableExternalWiki, String externalWikiURL, Boolean enableIssues, Boolean allowPublicIssues, Boolean enableExternalTracker, String externalTrackerURL, String externalTrackerFormat, String externalTrackerStyle, Boolean enablePulls, Boolean isFork, Long forkID, Long createdUnix, Long updatedUnix) {
        this.ID = ID;
        OwnerID = ownerID;
        LowerName = lowerName;
        Name = name;
        Description = description;
        Website = website;
        DefaultBranch = defaultBranch;
        Size = size;
        NumWatches = numWatches;
        NumStars = numStars;
        NumForks = numForks;
        NumIssues = numIssues;
        NumClosedIssues = numClosedIssues;
        NumPulls = numPulls;
        NumClosedPulls = numClosedPulls;
        NumMilestones = numMilestones;
        NumClosedMilestones = numClosedMilestones;
        IsPrivate = isPrivate;
        IsBare = isBare;
        IsMirror = isMirror;
        EnableWiki = enableWiki;
        AllowPublicWiki = allowPublicWiki;
        EnableExternalWiki = enableExternalWiki;
        ExternalWikiURL = externalWikiURL;
        EnableIssues = enableIssues;
        AllowPublicIssues = allowPublicIssues;
        EnableExternalTracker = enableExternalTracker;
        ExternalTrackerURL = externalTrackerURL;
        ExternalTrackerFormat = externalTrackerFormat;
        ExternalTrackerStyle = externalTrackerStyle;
        EnablePulls = enablePulls;
        IsFork = isFork;
        ForkID = forkID;
        CreatedUnix = createdUnix;
        UpdatedUnix = updatedUnix;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(long ownerID) {
        OwnerID = ownerID;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getDefaultBranch() {
        return DefaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        DefaultBranch = defaultBranch;
    }

    public long getSize() {
        return Size;
    }

    public void setSize(long size) {
        Size = size;
    }

    public Integer getNumWatches() {
        return NumWatches;
    }

    public void setNumWatches(Integer numWatches) {
        NumWatches = numWatches;
    }

    public Integer getNumStars() {
        return NumStars;
    }

    public void setNumStars(Integer numStars) {
        NumStars = numStars;
    }

    public Integer getNumForks() {
        return NumForks;
    }

    public void setNumForks(Integer numForks) {
        NumForks = numForks;
    }

    public Integer getNumIssues() {
        return NumIssues;
    }

    public void setNumIssues(Integer numIssues) {
        NumIssues = numIssues;
    }

    public Integer getNumClosedIssues() {
        return NumClosedIssues;
    }

    public void setNumClosedIssues(Integer numClosedIssues) {
        NumClosedIssues = numClosedIssues;
    }

    public Integer getNumPulls() {
        return NumPulls;
    }

    public void setNumPulls(Integer numPulls) {
        NumPulls = numPulls;
    }

    public Integer getNumClosedPulls() {
        return NumClosedPulls;
    }

    public void setNumClosedPulls(Integer numClosedPulls) {
        NumClosedPulls = numClosedPulls;
    }

    public Integer getNumMilestones() {
        return NumMilestones;
    }

    public void setNumMilestones(Integer numMilestones) {
        NumMilestones = numMilestones;
    }

    public Integer getNumClosedMilestones() {
        return NumClosedMilestones;
    }

    public void setNumClosedMilestones(Integer numClosedMilestones) {
        NumClosedMilestones = numClosedMilestones;
    }

    public Boolean getIsPrivate() {
        return IsPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        IsPrivate = isPrivate;
    }

    public Boolean getIsBare() {
        return IsBare;
    }

    public void setIsBare(Boolean isBare) {
        IsBare = isBare;
    }

    public Boolean getIsMirror() {
        return IsMirror;
    }

    public void setIsMirror(Boolean isMirror) {
        IsMirror = isMirror;
    }

    public Boolean getEnableWiki() {
        return EnableWiki;
    }

    public void setEnableWiki(Boolean enableWiki) {
        EnableWiki = enableWiki;
    }

    public Boolean getAllowPublicWiki() {
        return AllowPublicWiki;
    }

    public void setAllowPublicWiki(Boolean allowPublicWiki) {
        AllowPublicWiki = allowPublicWiki;
    }

    public Boolean getEnableExternalWiki() {
        return EnableExternalWiki;
    }

    public void setEnableExternalWiki(Boolean enableExternalWiki) {
        EnableExternalWiki = enableExternalWiki;
    }

    public String getExternalWikiURL() {
        return ExternalWikiURL;
    }

    public void setExternalWikiURL(String externalWikiURL) {
        ExternalWikiURL = externalWikiURL;
    }

    public Boolean getEnableIssues() {
        return EnableIssues;
    }

    public void setEnableIssues(Boolean enableIssues) {
        EnableIssues = enableIssues;
    }

    public Boolean getAllowPublicIssues() {
        return AllowPublicIssues;
    }

    public void setAllowPublicIssues(Boolean allowPublicIssues) {
        AllowPublicIssues = allowPublicIssues;
    }

    public Boolean getEnableExternalTracker() {
        return EnableExternalTracker;
    }

    public void setEnableExternalTracker(Boolean enableExternalTracker) {
        EnableExternalTracker = enableExternalTracker;
    }

    public String getExternalTrackerURL() {
        return ExternalTrackerURL;
    }

    public void setExternalTrackerURL(String externalTrackerURL) {
        ExternalTrackerURL = externalTrackerURL;
    }

    public String getExternalTrackerFormat() {
        return ExternalTrackerFormat;
    }

    public void setExternalTrackerFormat(String externalTrackerFormat) {
        ExternalTrackerFormat = externalTrackerFormat;
    }

    public String getExternalTrackerStyle() {
        return ExternalTrackerStyle;
    }

    public void setExternalTrackerStyle(String externalTrackerStyle) {
        ExternalTrackerStyle = externalTrackerStyle;
    }

    public Boolean getEnablePulls() {
        return EnablePulls;
    }

    public void setEnablePulls(Boolean enablePulls) {
        EnablePulls = enablePulls;
    }

    public Boolean getIsFork() {
        return IsFork;
    }

    public void setIsFork(Boolean isFork) {
        IsFork = isFork;
    }

    public Long getForkID() {
        return ForkID;
    }

    public void setForkID(Long forkID) {
        ForkID = forkID;
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
}
