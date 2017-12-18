package com.tongyuan.pageModel;

import com.tongyuan.model.domain.Model;
import com.tongyuan.model.domain.ReviewFlowTemplate;
import com.tongyuan.model.domain.ReviewNodeInstance;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by Y470 on 2017/8/24.
 */
@Component
public class CheckorPage {
    //表示reviewNodeInstance的id
    private Long id;
    private String instanceName;
    private String modelName;
    private String templateName;
    private String description;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Model model;
    private ReviewFlowTemplate template;
    private ReviewNodeInstance reviewNodeInstance;

    public ReviewNodeInstance getReviewNodeInstance() {
        return reviewNodeInstance;
    }

    public void setReviewNodeInstance(ReviewNodeInstance reviewNodeInstance) {
        this.reviewNodeInstance = reviewNodeInstance;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ReviewFlowTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ReviewFlowTemplate template) {
        this.template = template;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
