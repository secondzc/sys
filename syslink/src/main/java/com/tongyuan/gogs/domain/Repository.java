package com.tongyuan.gogs.domain;

import javax.persistence.*;

/**
 * Created by xyx on 2017-9-18.
 */
@Entity
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    @Column( unique = true)
    private long OwnerID;
    @Column(nullable = false, unique = true)
    private String LowerName;
    @Column(nullable = false)
    private String Name;
    @Column
    private String Description;
    @Column
    private String Website;
    @Column
    private String DefaultBranch;
    @Column
    private long Size;
    @Column
    private Integer NumWatches;
    @Column
    private Integer NumStars;
    @Column
    private Integer NumForks;
    @Column
    private Integer NumIssues;
    @Column
    private Integer NumClosedIssues;
    @Column
    private Integer NumPulls;
    @Column
    private Integer NumClosedPulls;
    @Column
    private Integer NumMilestones;
    @Column
    private Integer NumClosedMilestones;
    @Column
    private Boolean IsPrivate;
    @Column
    private Boolean IsBare;
    @Column
    private Boolean IsMirror;
    @Column
    private Boolean EnableWiki;
    @Column
    private Boolean AllowPublicWiki;
    @Column
    private Boolean EnableExternalWiki;
    @Column
    private String ExternalWikiURL;
    @Column
    private Boolean EnableIssues;
    @Column
    private Boolean AllowPublicIssues;
    @Column
    private Boolean EnableExternalTracker;
    @Column
    private String ExternalTrackerURL;
    @Column
    private String ExternalTrackerFormat;
    @Column
    private String ExternalTrackerStyle;
    @Column
    private Boolean EnablePulls;
    @Column
    private Boolean IsFork;
    @Column
    private Long ForkID;
    @Column
    private Long CreatedUnix;
    @Column
    private Long UpdatedUnix;

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
