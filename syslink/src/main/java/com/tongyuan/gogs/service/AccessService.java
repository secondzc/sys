package com.tongyuan.gogs.service;

import com.tongyuan.gogs.domain.Access;
import com.tongyuan.gogs.domain.Star;

import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-9-18.
 */
public interface AccessService {
    boolean add(Access access);
    boolean delete(Long id);
    public void addDefault(Long userId,Long repositoryId);
}
