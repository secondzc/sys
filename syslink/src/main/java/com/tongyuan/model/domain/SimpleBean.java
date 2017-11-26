package com.tongyuan.model.domain;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * Created by Format on 2017/5/1.
 */
@Getter
@Setter
@ToString
@Component
//@AllArgsConstructor
@NoArgsConstructor
public class SimpleBean {

    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleBean(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
