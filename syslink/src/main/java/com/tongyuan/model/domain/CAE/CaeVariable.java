package com.tongyuan.model.domain.CAE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CaeVariable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private  String type;
    @Column
    private String startBookmark;  //表示相对于
    @Column
    private String valueType;
    @Column
    private String value;
    @Column
    private String units;
    @Column
    private String description;
    @Column
    private String max;
    @Column
    private String min;
    @Column
    private String enumValues;
    @Column
    private String enumAliases;
    @Column
    private String format;
    @Column
    private String fortranFormat;
    @Column
    private String delimiters;   //范围
    @Column
    private Long caeFileId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartBookmark() {
        return startBookmark;
    }

    public void setStartBookmark(String startBookmark) {
        this.startBookmark = startBookmark;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(String enumValues) {
        this.enumValues = enumValues;
    }

    public String getEnumAliases() {
        return enumAliases;
    }

    public void setEnumAliases(String enumAliases) {
        this.enumAliases = enumAliases;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFortranFormat() {
        return fortranFormat;
    }

    public void setFortranFormat(String fortranFormat) {
        this.fortranFormat = fortranFormat;
    }

    public String getDelimiters() {
        return delimiters;
    }

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

    public Long getCaeFileId() {
        return caeFileId;
    }

    public void setCaeFileId(Long caeFileId) {
        this.caeFileId = caeFileId;
    }
}
