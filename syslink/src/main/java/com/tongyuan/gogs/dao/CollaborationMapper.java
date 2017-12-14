package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Collaboration;

import java.util.List;
import java.util.Map;

public interface CollaborationMapper {

    boolean add(Collaboration collaboration);
    boolean delete(Long id);
   // List<Map<String,Object>> queryByUid(Map<String,Object>map);
    boolean queryByRepoId(Long id);
}
