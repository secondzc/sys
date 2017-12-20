package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.ComponentMapper;
import com.tongyuan.model.domain.Component;
import com.tongyuan.model.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件数据操作
 * Created by xieyx on 2017-6-21.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentMapper componentMapper;


    @Override
    public int add(Component component) {
        return this.componentMapper.add(component);
    }

    @Override
    public boolean update(Component component) {
        return this.componentMapper.update(component);
    }

    @Override
    public boolean deleteByIds(String[] ids) {
        return false;
    }

    @Override
    public long selectId() {
        return this.componentMapper.selectId();
    }

    @Override
    public List<Component> queryListNullComp() {
        return this.componentMapper.queryListNullComp();
    }

    @Override
    public List<Component> findAllComp() {
        return this.componentMapper.findAllComp();
    }
}
