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
    public List<Attachment> getModelFiles(Long modelId);
    public List<Attachment> getModelDetail(Long modelId);
    public List<Attachment> getAttachByParentId(Long attachId);
    public Attachment getParentAttach(Long attachmentId);
    public List<Attachment> getAllFiles();
}
