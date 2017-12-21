package com.tongyuan.model.dao;

import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/9/19.
 */
@Mapper
public interface AttachmentMapper {

    Long add(Attachment attachment);
    int update(Attachment attachment);
    public Attachment queryListByPath(String parentPath);
}
