package com.tongyuan.model.service;

import com.tongyuan.model.DTO.FileTypeDto;
import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.FileType;
import com.tongyuan.model.domain.ModelType;

import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
public interface FileTypeService {
    //获取刚刚插入的iconFiletype
    public List<FileTypeDto> getFileTypeList();
    //根据类型名获取modeltype对象
    public FileType getByExt(String extName);
    //添加fileType
    public Long add(FileType fileType);
    //修改fileType
    public void update(FileType fileType);
    //删除fileType
    public void delete(String fileType);
    //获取默认的icon
    public FileTypeDto getDefaultIcon();
}
