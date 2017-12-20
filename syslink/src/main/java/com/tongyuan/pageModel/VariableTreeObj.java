package com.tongyuan.pageModel;

import java.util.List;

/**
 * Created by xieyx on 2017-9-27.
 */
public class VariableTreeObj {
    //树节点id
    private Long id;
    //树节点名称
    private String name;
    //变量类型
    private String type;
    //默认值
    private String defaultValue;
    //变量单位
    private String units;
    //变量下界
    private String lowerBound;
    //变量上界
    private String upperBound;
    //针对Modelica模型，是参数还是变量，参数为1，变量为0
    private String IsParam;
    //针对其他模型，是否为输入变量，是为1，不是为0
    private String IsInput;
    private List<VariableTreeObj> children;

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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public String getIsParam() {
        return IsParam;
    }

    public void setIsParam(String isParam) {
        IsParam = isParam;
    }

    public String getIsInput() {
        return IsInput;
    }

    public void setIsInput(String isInput) {
        IsInput = isInput;
    }

    public List<VariableTreeObj> getChildren() {
        return children;
    }

    public void setChildren(List<VariableTreeObj> children) {
        this.children = children;
    }
}
