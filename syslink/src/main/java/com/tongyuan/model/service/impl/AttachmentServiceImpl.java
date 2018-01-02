package com.tongyuan.model.service.impl;

import com.tongyuan.model.DTO.AttachmentDto;
import com.tongyuan.model.DTO.FileJsonArrayDto;
import com.tongyuan.model.dao.AttachmentMapper;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.service.AttachmentService;
import com.tongyuan.pageModel.VariableTreeObj;
import com.tongyuan.util.DateUtil;
import com.tongyuan.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public void addIconOfModel(String fileName, String iconUrl, Long size) {
        Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setCreateTime(DateUtil.getTimestamp());
        attachment.setExt(".png");
        attachment.setFloder(false);
        attachment.setIconUrl(iconUrl);
        attachment.setParentId(0);
        this.attachmentMapper.add(attachment);
    }

    @Override
    public List<Attachment> getModelFiles(Long modelId) {
        return this.attachmentMapper.getModelFiles(modelId);
    }

    /**
     * 获取模型目录
     * @param modelCatalogList
     * @param modelFiles
     * @return
     */
    @Override
    public List<VariableTreeObj> getModelCatalog(List<VariableTreeObj> modelCatalogList, List<Attachment> modelFiles) {
        VariableTreeObj model = new VariableTreeObj();
        //子文件列表
        List<VariableTreeObj> childList = new ArrayList<>();
        for (Attachment modelRoot:modelFiles) {
            if(modelRoot.getParentId() == 0){
                model.setId(modelRoot.getId());
                model.setName(modelRoot.getName());
                List<VariableTreeObj> rootChild = new ArrayList<>();
                model.setChildren(rootChild);
                modelCatalogList.add(model);
            }
        }
        this.getModelChild(modelFiles,model.getId(),childList);
        model.setChildren(childList);
        return modelCatalogList;
    }

    @Override
    public List<Attachment> getModelDetail(Long modelId) {
        return this.attachmentMapper.getModelDetail(modelId);
    }

    /**
     * 获取模型详细列表
     * @param modelFiles
     * @param modelId
     * @param modelDetail
     * @return
     */
    @Override
    public List<Attachment> getModelDetailList(List<Attachment> modelFiles, Long modelId, List<Attachment> modelDetail) {

        //判断选中的文件是否为文件夹
        boolean floder = false;
        Attachment root = new Attachment();
        for (Attachment attchCatelog: modelFiles) {
            if(attchCatelog.getParentId() == 0) {
                floder = attchCatelog.getFloder();
                root = attchCatelog;
            }
        }
        if(floder){
            for (Attachment attachment : modelFiles) {
                if(attachment.getId() != root.getId() && attachment.getFloder() == false){
                    modelDetail.add(attachment);
                }
            }
        }else{
            modelDetail.addAll(modelFiles);
        }
        return modelDetail;
    }

    /**
     * 查找该目录下的所有文件
     * @param modelFiles
     * @param modelDetail
     * @return
     */
    @Override
    public List<Attachment> getDetailListByAttachId(List<Attachment> modelFiles, List<Attachment> modelDetail,Long catalog) {
        boolean floder = false;
        for (Attachment attachment : modelFiles) {
            if( attachment.getFloder() == false && attachment.getId() != catalog){
                modelDetail.add(attachment);
            }
            if(attachment.getId() == catalog){
                floder = attachment.getFloder();
            }
        }
        if(modelDetail.size() == 0 && !floder){
            modelDetail.addAll(modelFiles);
        }
        return  modelDetail;
    }

    @Override
    public List<Attachment> getAttachByParentId(Long attachId) {
        return this.attachmentMapper.getAttachByParentId(attachId);
    }

    @Override
    public AttachmentDto transformToDto(Attachment attachment) {
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setId(attachment.getId());
        attachmentDto.setName(attachment.getName());
        attachmentDto.setExt(attachment.getExt());
        attachmentDto.setIconUrl(attachment.getIconUrl());
        attachmentDto.setSize(attachment.getSize());
        attachmentDto.setFilePath(attachment.getFilePath());
        attachmentDto.setHttpPath(attachment.getHttpPath());
        attachmentDto.setFloder(attachment.getFloder());
        attachmentDto.setParentId(attachment.getParentId());
        attachmentDto.setModelId(attachment.getModelId());
        attachmentDto.setCreateTime(DateUtil.format(attachment.getCreateTime(),"yyyy-MM-dd"));
        return attachmentDto;
    }

    @Override
    public List<AttachmentDto> transformDtoList(List<Attachment> attachmentList) {
        List<AttachmentDto>  attachmentDtoList = new ArrayList<>();
        for (Attachment attachment :attachmentList) {
            attachmentDtoList.add(this.transformToDto(attachment));
        }
        return attachmentDtoList;
    }

    @Override
    public Attachment getParentAttach(Long attachmentId) {
        return this.attachmentMapper.getParentAttach(attachmentId);
    }

    @Override
    public List<Attachment> getAllFiles() {
        return this.attachmentMapper.getAllFiles();
    }

    @Override
    public void addFileOfModel(String fileName, String filePath, Long fileSize, String tempRelativePath) {
        Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setExt(ModelUtil.splitName(fileName));
        attachment.setFloder(false);
        attachment.setCreateTime(DateUtil.getTimestamp());
        attachment.setSize(fileSize);
        attachment.setTempRelativePath(tempRelativePath);
        attachment.setModelId(-1);
        this.attachmentMapper.add(attachment);
    }

    @Override
    public List<Attachment> queryNullModelId(Long modelId) {
        return this.attachmentMapper.queryNullModelId(modelId);
    }

    @Override
    public void addFileJsonDto(FileJsonArrayDto fileJsonArrayDto,Long modelId) {
        Attachment attachment = new Attachment();
        attachment.setModelId(modelId);
        attachment.setCreateTime(DateUtil.getTimestamp());
        attachment.setFloder(true);
        attachment.setName(fileJsonArrayDto.getName());
        attachment.setSize(0);
        attachment.setTempRelativePath(fileJsonArrayDto.getPath());
        this.attachmentMapper.add(attachment);
    }

    public void getModelChild(List<Attachment> modelFiles,Long modelId,List<VariableTreeObj> childList){
        for(int i=0; i<modelFiles.size(); i++){
            if(modelFiles.get(i).getParentId() == modelId) {
                VariableTreeObj treeObj = new VariableTreeObj();
                treeObj.setId(modelFiles.get(i).getId());
                treeObj.setName(modelFiles.get(i).getName());
                List<VariableTreeObj> childVar = new ArrayList<>();
                treeObj.setChildren(childVar);
                childList.add(treeObj);
            }
        }
        if( childList != null){
            for (VariableTreeObj treechild: childList) {
                getModelChild(modelFiles,treechild.getId(),treechild.getChildren());
            }
        }
    }

}
