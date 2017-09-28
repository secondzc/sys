package com.tongyuan.gogs.service;

import com.tongyuan.gogs.domain.GUser;

/**
 * Created by Administrator on 2017-9-18.
 */
public interface GUserService {
    boolean add(GUser user);
    GUser querListByName(String name);
    boolean update(GUser user);
}
