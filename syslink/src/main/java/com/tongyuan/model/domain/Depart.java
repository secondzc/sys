package com.tongyuan.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yh on 2017/10/25.
 */
@Entity
public class Depart {


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        private Integer id;
        @Column
        private Integer parentId;
        @Column(nullable = false)
        private String name;
        @Column
        private String description;
        @Column
        private Date createDate;


      public Depart(Integer parentId, String name, String description, Date createDate) {
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
