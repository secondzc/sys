package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.CaeFileMapper;
import com.tongyuan.model.domain.CAE.CaeFile;
import com.tongyuan.model.service.CaeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaeFileServiceImpl implements CaeFileService{
    @Autowired
    private CaeFileMapper caeFileMapper;

    @Override
    public int add(CaeFile caeFile) {
        return caeFileMapper.add(caeFile);
    }
}
