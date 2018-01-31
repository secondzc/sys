package com.tongyuan.model.dao;

import com.tongyuan.model.DTO.AttachmentDto;
import com.tongyuan.model.domain.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yh on 2017/9/19.
 */
@Mapper
public interface AttachmentMapper {

    Long add(Attachment attachment);
    int update(Attachment attachment);
    public void delete(Long attachmentId);
    public Attachment queryListByPath(String parentPath);
    public List<Attachment> getModelFiles(Long modelId);
    public List<AttachmentDto> getModelDetail(Long modelId);
    public List<AttachmentDto> getAttachByParentId(Long attachId);
    public Attachment getParentAttach(Long attachmentId);
    public List<AttachmentDto> getAllFiles(Boolean scope);
    public List<Attachment> queryNullModelId(Long modelId);
    public Attachment queryById(Long attachmentId);
    public List<Attachment> getAttachmentsByModelId(Long modelId);
    public List<Attachment> getInsertIcon();
    public List<Attachment> getDeleteAttach();
    //批量更新
    public int batchUpdate(@Param("list") List<Attachment> attachmentList);
    //查询Modelica模型关联文件
    public List<Attachment> getModelicaRelatedAttach();
    public List<AttachmentDto> queryListById(Long attachmentId);
    public List<AttachmentDto> getFilesOfModel(Long modelId);
}
