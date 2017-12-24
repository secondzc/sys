package com.tongyuan.model.service;

import com.tongyuan.model.domain.Attachment;

/**
 * Created by xyx on 2017-12-20.
 */
public interface AttachmentService {
    Long add(Attachment attachment);
    int update(Attachment attachment);
    public Attachment queryListByPath(String parentPath);
    public void addIconOfModel(String fileName,String iconUrl,Long size);
}
