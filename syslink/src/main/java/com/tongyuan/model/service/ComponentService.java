package com.tongyuan.model.service;

import com.tongyuan.model.domain.Component;

import java.util.List;

/**
 * Created by xieyx on 2017-6-21.
 */
public interface ComponentService {
    public boolean add(Component component);
    public boolean update(Component component);
    public boolean deleteByIds(String[] ids);
    public long selectId();
    public List<Component> queryListNullComp();
    public List<Component> findAllComp();
}
