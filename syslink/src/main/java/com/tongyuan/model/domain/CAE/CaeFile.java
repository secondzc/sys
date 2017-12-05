package com.tongyuan.model.domain.CAE;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CaeFile {
    @Id
    @GeneratedValue
    private  Long id;
    @Column
    private String templateFileName;
    @Column
    private String fileName;
    @Column
    private String format;
    @Column
    private String delimitersType;
    @Column
    private String type;
    @Column
    private Long caeComponentId;

    public Long getCaeComponentId() {
        return caeComponentId;
    }

    public void setCaeComponentId(Long caeComponentId) {
        this.caeComponentId = caeComponentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDelimitersType() {
        return delimitersType;
    }

    public void setDelimitersType(String delimitersType) {
        this.delimitersType = delimitersType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
