package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.CaeVariableMapper;
import com.tongyuan.model.domain.CAE.CaeVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaeVariableService implements com.tongyuan.model.service.CaeVariableService {
    @Autowired
    private CaeVariableMapper caeVariableMapper;

    @Override
    public int add(CaeVariable caeVariable) {
        return caeVariableMapper.add(caeVariable);
    }
}
