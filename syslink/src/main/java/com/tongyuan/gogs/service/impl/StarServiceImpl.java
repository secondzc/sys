package com.tongyuan.gogs.service.impl;

import com.tongyuan.gogs.dao.StarMapper;
import com.tongyuan.gogs.domain.Star;
import com.tongyuan.gogs.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class StarServiceImpl implements StarService {
    @Autowired
    StarMapper starMapper;
    @Override
    public boolean add(Star star) {
        return this.starMapper.add(star);
    }

    @Override
    public boolean delete(Long id) {
        return this.starMapper.delete(id);
    }

    @Override
    public Star queryListByParam(Map<String, Object> params) {
        return this.starMapper.queryListByParam(params);
    }

    @Override
    public List<Star> findAllStar() {
        return this.starMapper.findAllStar();
    }

}
