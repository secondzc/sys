package com.tongyuan.model.dao;

import com.tongyuan.model.DTO.FileTypeDto;
import com.tongyuan.model.DTO.ModelTypeDto;
import com.tongyuan.model.domain.FileType;
import com.tongyuan.model.domain.ModelType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xyx on 2017-12-22.
 */
@Mapper
public interface FileTypeMapper {
    public List<FileTypeDto> getFileTypeList();
    public FileType getByExt(String extName);
    public Long add(FileType fileType);
    public void update(FileType fileType);
    public void delete(String fileType);
}
