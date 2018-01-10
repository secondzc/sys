package com.tongyuan.model.service.impl;

import com.tongyuan.model.DTO.FileTypeDto;
import com.tongyuan.model.dao.FileTypeMapper;
import com.tongyuan.model.domain.FileType;
import com.tongyuan.model.service.FileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyx on 2018-1-9.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FileTypeServiceImpl implements FileTypeService {
    @Autowired
    FileTypeMapper fileTypeMapper;

    @Override
    public List<FileTypeDto> getFileTypeList() {
        return this.fileTypeMapper.getFileTypeList();
    }

    @Override
    public FileType getByExt(String extName) {
        return this.fileTypeMapper.getByExt(extName);
    }

    @Override
    public Long add(FileType fileType) {
        return this.fileTypeMapper.add(fileType);
    }

    @Override
    public void update(FileType fileType) {
            this.fileTypeMapper.update(fileType);
    }

    @Override
    public void delete(String fileType) {
        this.fileTypeMapper.delete(fileType);
    }

    @Override
    public FileTypeDto getDefaultIcon() {
        return this.fileTypeMapper.getDefaultIcon();
    }
}
