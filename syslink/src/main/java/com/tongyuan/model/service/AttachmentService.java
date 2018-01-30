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
    int batchUpdate(List<Attachment> attachmentList);
    public List<AttachmentDto> queryListById(Long attachmentId);
    public void delete(Long attachmentId);
    public Attachment queryListByPath(String parentPath);
    public Long addIconOfModel(String fileName,String iconUrl,Long size);
    public List<Attachment> getModelFiles(Long modelId);
    public List<VariableTreeObj> getModelCatalog(List<VariableTreeObj> modelCatalogList,List<Attachment> modelFiles);
    //获取该目录下的所有文件
    public List<AttachmentDto> getModelDetail(Long modelId);
    public List<AttachmentDto> getModelDetailList(List<AttachmentDto> modelFiles,Long modelId,List<AttachmentDto> modelDetail);
    public List<AttachmentDto> getDetailListByAttachId(List<AttachmentDto> modelFiles,List<AttachmentDto> modelDetail,Long catalogId);
    //获取模型文件目录下的所有文件
    public List<AttachmentDto> getAttachByParentId(Long attachId);
    public AttachmentDto transformToDto (Attachment attachment);
    public List<AttachmentDto> transformDtoList(List<Attachment> attachmentList);
    public Attachment getParentAttach(Long attachmentId);
    public List<AttachmentDto> getAllFiles(Boolean scope);
    public void addFileOfModel(String fileName,String filePath,Long fileSize,String tempRelativePath,String uniqueIdentifier);
    //查找刚插入的数据和刚插入的文件夹数据
    public List<Attachment> queryNullModelId(Long modelId);
    public void addFileJsonDto(FileJsonArrayDto fileJsonArrayDto,Long modelId);
    //获取查询id（Attachment）
    public Attachment queryById(Long attachmentId);
    //查找model的所有文件
    public List<Attachment> getAttachmentsByModelId(Long modelId);
    //复制attachList到用户下载文件夹下
    public String getZipUrl(List<Attachment> attachmentList,String  modelName,Boolean folder);
    //查找刚上传的icon图片List
    public List<Attachment> getInsertIcon();
    //更新上传文件的modelId和parentId
    public void UpdateModelFrame(List<Attachment> attachmentFileList,Long modelId,List<Attachment> floderList);
    //获取要删除的数据
    public List<Attachment> getDeleteAttach();
    //过滤掉不存在表单的文件
    public List<Attachment> getRealFileList(List<Attachment> attachmentFileList,List<FileJsonArrayDto> fileJsonArrayDtoList,List<Attachment> floderList);
    //根据文件夹找到文件夹下面的所有文件
    public List<Attachment> getFloderAttach(List<Attachment> modelAttachmentList,Attachment attachment,List<Attachment> attachmentList);
    //对attachmentDto 进行排序
    public void sortAttachDto(List<AttachmentDto> attachmentDtos);
    //查询Modelica模型关联文件
    public List<Attachment> getModelicaRelatedAttach();
    //更新modelica上传的文件modelid
    public void updateModelicaAttach();
}
