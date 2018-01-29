package com.tongyuan.model.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tongyuan.model.DTO.AttachmentDto;
import com.tongyuan.model.DTO.FileJsonArrayDto;
import com.tongyuan.model.dao.AttachmentMapper;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Model;
import com.tongyuan.model.service.AttachmentService;
import com.tongyuan.pageModel.VariableTreeObj;
import com.tongyuan.tools.StringUtil;
import com.tongyuan.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xyx on 2017-12-20.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    AttachmentMapper attachmentMapper;
    @Autowired
    ResourceUtil resourceUtil;

    @Override
    public Long add(Attachment attachment) {
        return this.attachmentMapper.add(attachment);
    }

    @Override
    public int update(Attachment attachment) {
        return this.attachmentMapper.update(attachment);
    }

    @Override
    public int batchUpdate(List<Attachment> attachmentList) {
        return this.attachmentMapper.batchUpdate(attachmentList);
    }

    @Override
    public void delete(Long attachmentId) {
        this.attachmentMapper.delete(attachmentId);
    }

    @Override
    public Attachment queryListByPath(String parentPath) {
        return this.attachmentMapper.queryListByPath(parentPath);
    }

    /**
     * 创建一个模型的图标文件
     * @param fileName
     * @param iconUrl
     * @param size
     * @return
     */
    @Override
    public Long addIconOfModel(String fileName, String iconUrl, Long size) {
        Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setCreateTime(DateUtil.getTimestamp());
        attachment.setExt("png");
        attachment.setFloder(false);
        attachment.setIconUrl(iconUrl);
        attachment.setFilePath(iconUrl);
        attachment.setParentId(0);
        attachment.setSize(size);
        attachment.setModelId(-1);
        this.attachmentMapper.add(attachment);
        Long attachmentId = attachment.getId();
        return  attachmentId;
    }

    /**
     * 根据模型id获取所有文件（包含文件夹）
     * @param modelId
     * @return
     */
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
        for (Attachment modelRoot:modelFiles) {
            if(modelRoot.getParentId() == 0){
                VariableTreeObj model = new VariableTreeObj();
                model.setId(modelRoot.getId());
                model.setName(modelRoot.getName());
                model.setParentId(modelRoot.getParentId());
                List<VariableTreeObj> rootChild = new ArrayList<>();
                model.setChildren(rootChild);
                modelCatalogList.add(model);
            }
        }
        for (VariableTreeObj variableTreeObj : modelCatalogList) {
            //子文件列表
            List<VariableTreeObj> childList = new ArrayList<>();
            this.getModelChild(modelFiles,variableTreeObj.getId(),childList);
            variableTreeObj.setChildren(childList);
        }
        return modelCatalogList;
    }

    /**
     * 根据模型id获取所有文件（不包含文件夹）
     * @param modelId
     * @return
     */
    @Override
    public List<AttachmentDto> getModelDetail(Long modelId) {
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
    public List<AttachmentDto> getModelDetailList(List<AttachmentDto> modelFiles, Long modelId, List<AttachmentDto> modelDetail) {

        //判断选中的文件是否为文件夹
//        boolean floder = false;
        AttachmentDto root = new AttachmentDto();
        for (AttachmentDto attchCatelog: modelFiles) {
            if(attchCatelog.getParentId() == 0) {
                root = attchCatelog;
                break;
            }
        }
        if(root != null){
            for (AttachmentDto attchCatelog: modelFiles) {
                if(attchCatelog.getParentId() == root.getId()){
                    modelDetail.add(attchCatelog);
                }
            }
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
    public List<AttachmentDto> getDetailListByAttachId(List<AttachmentDto> modelFiles, List<AttachmentDto> modelDetail,Long catalog) {
        boolean floder = false;
        for (AttachmentDto attachment : modelFiles) {
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

    /**
     * 根据文件id获取子文件和自身
     * @param attachId
     * @return
     */
    @Override
    public List<AttachmentDto> getAttachByParentId(Long attachId) {
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

    /**
     * 获取所有的文件
     * @return
     */
    @Override
    public List<AttachmentDto> getAllFiles(Boolean scope) {
        return this.attachmentMapper.getAllFiles(scope);
    }

    /**
     * 添加模型中的一个文件
     * @param fileName
     * @param filePath
     * @param fileSize
     * @param tempRelativePath
     */
    @Override
    public void addFileOfModel(String fileName, String filePath, Long fileSize, String tempRelativePath,String uniqueIdentifier) {
        Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setExt(ModelUtil.getFileExt(fileName));
        attachment.setFloder(false);
        attachment.setFilePath(filePath);
        attachment.setCreateTime(DateUtil.getTimestamp());
        attachment.setSize(fileSize);
        attachment.setTempRelativePath(tempRelativePath);
        attachment.setModelId(-1);
        attachment.setIdentifier(uniqueIdentifier);
        this.attachmentMapper.add(attachment);
    }

    /**
     * 获取刚插入的文件
     * @param modelId
     * @return
     */
    @Override
    public List<Attachment> queryNullModelId(Long modelId) {
        return this.attachmentMapper.queryNullModelId(modelId);
    }

    /**
     * 添加一个文件的Dto
     * @param fileJsonArrayDto
     * @param modelId
     */
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

    @Override
    public Attachment queryById(Long attachmentId) {
        return this.attachmentMapper.queryById(attachmentId);
    }

    @Override
    public List<Attachment> getAttachmentsByModelId(Long modelId) {
        return this.attachmentMapper.getAttachmentsByModelId(modelId);
    }

    /**
     * 获取压缩文件的Url
     * @param attachmentList
     * @param modelName
     * @return
     */
    @Override
    public String getZipUrl(List<Attachment> attachmentList, String modelName, Boolean folder) {
        for (Attachment attachment:attachmentList) {
            if(!attachment.getFloder()){
                //下载的相对路径
                String downloadAbsolutePath = "";
                if(folder){
                    downloadAbsolutePath = ResourceUtil.getXiaZai() +attachment.getTempRelativePath().substring(attachment.getTempRelativePath().indexOf(modelName));
                }else{
                    downloadAbsolutePath = ResourceUtil.getXiaZai() +modelName +"/" + attachment.getTempRelativePath();
                }
                FileUtils.copyFile(resourceUtil.getunzipPath()+attachment.getFilePath(),resourceUtil.getunzipPath()+downloadAbsolutePath);
            }
        }
        String downloadDirPath = resourceUtil.getunzipPath()+ ResourceUtil.getXiaZai() + modelName;
        ZipDir.createZip(downloadDirPath,downloadDirPath+".zip");
        return downloadDirPath + ".zip";
    }

    @Override
    public List<Attachment> getInsertIcon() {
        return this.attachmentMapper.getInsertIcon();
    }

    /**
     * 更改模型的目录层次结构
     * @param attachmentFileList
     * @param modelId
     */
    @Override
    public void UpdateModelFrame(List<Attachment> attachmentFileList,Long modelId,List<Attachment> floderList) {
//        List<Attachment> batchUpdateList = new ArrayList<>();
        for (Attachment attachmentChild : attachmentFileList) {
            for (Attachment attachmentParent : floderList) {
                if(!StringUtil.isNull(attachmentChild.getTempRelativePath()) && !StringUtil.isNull(attachmentParent.getTempRelativePath())) {
                    if (attachmentParent.getTempRelativePath().equals(ModelUtil.getParentNameByPara(attachmentChild.getTempRelativePath(), "/"))) {
                        attachmentChild.setParentId(attachmentParent.getId());
                        attachmentChild.setModelId(modelId);
                        this.attachmentMapper.update(attachmentChild);
//                        batchUpdateList.add(attachmentChild);
                    }
                }
            }
        }
        for (Attachment attachment : attachmentFileList){
            if(attachment.getModelId() != modelId){
                attachment.setModelId(modelId);
                this.attachmentMapper.update(attachment);
//                batchUpdateList.add(attachment);
            }
        }
        System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) + "结果");
//        this.attachmentMapper.batchUpdate(batchUpdateList);
        System.out.println(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) + "跟新");
    }

    @Override
    public List<Attachment> getDeleteAttach() {
        return this.attachmentMapper.getDeleteAttach();
    }

    @Override
    public List<Attachment> getRealFileList(List<Attachment> attachmentFileList, List<FileJsonArrayDto> fileJsonArrayDtoList,List<Attachment> floderList) {
        List<Attachment> fileList = new ArrayList<>();
        for (FileJsonArrayDto fileJsonDto: fileJsonArrayDtoList) {
            for (Attachment attachment: attachmentFileList) {
                if(!StringUtil.isNull(attachment.getTempRelativePath())) {
                    if (fileJsonDto.getFolder() && attachment.getTempRelativePath().equals(fileJsonDto.getPath())) {
                        fileList.add(attachment);
                        break;
                    }
                }
                if(!StringUtil.isNull(fileJsonDto.getUniqueIdentifier())){
                    if(fileJsonDto.getUniqueIdentifier().equals(attachment.getIdentifier())){
                        fileList.add(attachment);
                        break;
                    }
                }
            }
        }
        for (Attachment attachment: fileList) {
           if (attachment.getFloder()){
               floderList.add(attachment);
           }
        }
        return fileList;
    }


    @Override
    public List<Attachment> getFloderAttach(List<Attachment> modelAttachmentList,Attachment attachment,List<Attachment> attachmentList) {
        List<Attachment> childAttchList = new ArrayList<>();
        for (Attachment attach :modelAttachmentList) {
            if (attach.getParentId() == attachment.getId()){
                attachmentList.add(attach);
                childAttchList.add(attach);
            }
        }
        if(childAttchList.size() >0){
            for (Attachment childAttch :childAttchList) {
                getFloderAttach(modelAttachmentList,childAttch,attachmentList);
            }
        }
        return  attachmentList;
    }

    @Override
    public void sortAttachDto(List<AttachmentDto> attachmentDtos) {
        Collections.sort(attachmentDtos, new Comparator<AttachmentDto>() {
            @Override
            public int compare(AttachmentDto o1, AttachmentDto o2) {
                if(o1.getSize() > o2.getSize()){
                    return 1;
                }else if(o1.getSize() < o2.getSize()){
                    return -1;
                }else{
                    return  0;
                }
            }
        });
    }

    /**
     * 获取模型的子
     * @param modelFiles
     * @param modelId
     * @param childList
     */
    public void getModelChild(List<Attachment> modelFiles,Long modelId,List<VariableTreeObj> childList){
        for(int i=0; i<modelFiles.size(); i++){
            if(modelFiles.get(i).getParentId() == modelId) {
                VariableTreeObj treeObj = new VariableTreeObj();
                treeObj.setId(modelFiles.get(i).getId());
                treeObj.setName(modelFiles.get(i).getName());
                treeObj.setParentId(modelFiles.get(i).getParentId());
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
