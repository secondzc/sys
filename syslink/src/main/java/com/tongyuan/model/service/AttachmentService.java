package com.tongyuan.model.service;

import com.tongyuan.model.DTO.AttachmentDto;
import com.tongyuan.model.DTO.FileJsonArrayDto;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Model;
import com.tongyuan.pageModel.VariableTreeObj;

import java.util.List;

/**
 * Created by xyx on 2017-12-20.
 */
public interface AttachmentService {
    Long add(Attachment attachment);
    int update(Attachment attachment);
    public void delete(Long attachmentId);
    public Attachment queryListByPath(String parentPath);
    public void addIconOfModel(String fileName,String iconUrl,Long size);
    public List<Attachment> getModelFiles(Long modelId);
    public List<VariableTreeObj> getModelCatalog(List<VariableTreeObj> modelCatalogList,List<Attachment> modelFiles);
    public List<Attachment> getModelDetail(Long modelId);
    public List<Attachment> getModelDetailList(List<Attachment> modelFiles,Long modelId,List<Attachment> modelDetail);
    public List<Attachment> getDetailListByAttachId(List<Attachment> modelFiles,List<Attachment> modelDetail,Long catalogId);
    public List<Attachment> getAttachByParentId(Long attachId);
    public AttachmentDto transformToDto (Attachment attachment);
    public List<AttachmentDto> transformDtoList(List<Attachment> attachmentList);
    public Attachment getParentAttach(Long attachmentId);
    public List<Attachment> getAllFiles();
    public void addFileOfModel(String fileName,String filePath,Long fileSize,String tempRelativePath);
    //查找刚插入的数据和刚插入的文件夹数据
    public List<Attachment> queryNullModelId(Long modelId);
    public void addFileJsonDto(FileJsonArrayDto fileJsonArrayDto,Long modelId);
    //获取查询id（Attachment）
    public Attachment queryById(Long attachmentId);
    //查找model的所有文件
    public List<Attachment> getAttachmentsByModelId(Long modelId);
    //复制attachList到用户下载文件夹下
    public String getZipUrl(List<Attachment> attachmentList,Model model);
    //查找刚上传的icon图片List
    public List<Attachment> getInsertIcon();
    //更新上传文件的modelId和parentId
    public void UpdateModelFrame(List<Attachment> attachmentFileList,Long modelId);
    //获取要删除的数据
    public List<Attachment> getDeleteAttach();
}
