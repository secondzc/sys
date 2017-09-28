package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.WatchMapper;
import com.tongyuan.gogs.domain.Watch;
import com.tongyuan.gogs.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class WatchServiceImpl implements WatchService {
    @Autowired
    WatchMapper watchMapper;
    @Override
    public boolean add(Watch watch) {
        return this.watchMapper.add(watch);
    }
}
