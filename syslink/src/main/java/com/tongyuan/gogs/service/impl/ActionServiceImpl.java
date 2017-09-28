package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.ActionMapper;
import com.tongyuan.gogs.domain.Action;
import com.tongyuan.gogs.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    ActionMapper actionMapper;
    @Override
    public boolean add(Action action) {
        return this.actionMapper.add(action);
    }

}
