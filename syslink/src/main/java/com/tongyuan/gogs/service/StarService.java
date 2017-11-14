package com.tongyuan.gogs.service;

import com.tongyuan.gogs.domain.Star;

import java.util.List;
import java.util.Map;

/**
 * Created by xyx on 2017-9-18.
 */
public interface StarService {
    boolean add(Star star);
    boolean delete(Long id);
    public Star queryListByParam(Map<String, Object> params);
    public List<Star> findAllStar();
}
