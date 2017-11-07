package com.tongyuan.gogs.dao;

import com.tongyuan.gogs.domain.Collaboration;

import java.util.List;

public interface CollaborationMapper {


    List<Collaboration> queryByUid(long uid);
}
