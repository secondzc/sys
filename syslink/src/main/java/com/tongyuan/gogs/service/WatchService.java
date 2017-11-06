package com.tongyuan.gogs.service;

import com.tongyuan.gogs.domain.Watch;

import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-9-18.
 */
public interface WatchService {
    boolean add(Watch watch);
    boolean delete(Long id);
    public Watch queryListByParam(Map<String, Object> params);
    public List<Watch> findAllWatch();
}
