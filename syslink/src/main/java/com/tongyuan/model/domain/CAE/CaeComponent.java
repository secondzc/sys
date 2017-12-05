package com.tongyuan.model.domain.CAE;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CaeComponent {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String author;
    @Column
    private String description;
    @Column
    private String requirements;
    @Column
    private String runOS;
    @Column
    private String iconFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getRunOS() {
        return runOS;
    }

    public void setRunOS(String runOS) {
        this.runOS = runOS;
    }

    public String getIconFile() {
        return iconFile;
    }

    public void setIconFile(String iconFile) {
        this.iconFile = iconFile;
    }
}
