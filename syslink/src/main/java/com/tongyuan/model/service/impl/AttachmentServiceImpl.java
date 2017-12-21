package com.tongyuan.model.service.impl;

import com.tongyuan.model.dao.AttachmentMapper;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xyx on 2017-12-20.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    AttachmentMapper attachmentMapper;
    @Override
    public Long add(Attachment attachment) {
        return this.attachmentMapper.add(attachment);
    }

    @Override
    public int update(Attachment attachment) {
        return this.attachmentMapper.update(attachment);
    }

    @Override
    public Attachment queryListByPath(String parentPath) {
        return this.attachmentMapper.queryListByPath(parentPath);
    }
}
