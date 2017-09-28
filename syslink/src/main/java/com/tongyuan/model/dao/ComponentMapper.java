package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Component;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 目录dao方法
 * Created by xieyx on 2017-6-21.
 */
@Mapper
public interface ComponentMapper {
    boolean add(Component component);
    boolean update(Component component);
    boolean deleteByIds(String[] ids);
    public long selectId();
    public List<Component> queryListNullComp();
    public List<Component> findAllComp();
}
