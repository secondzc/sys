package com.tongyuan.gogs.service;

import com.tongyuan.gogs.domain.Collaboration;

/**
 * Created by xyx on 2017-9-18.
 */
public interface CollaborationService {
    boolean add(Collaboration collaboration);
    boolean delete(Long id);
    public void addDefault(Long userId,Long repositoryId);
}
