package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.CaeComponentMapper;
import com.tongyuan.model.domain.CAE.CaeComponent;
import com.tongyuan.model.service.CaeComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaeComponentServiceImpl implements CaeComponentService{
    @Autowired
    private CaeComponentMapper caeComponentMapper;

    @Override
    public int add(CaeComponent caeComponent) {
        return caeComponentMapper.add(caeComponent);
    }
}
