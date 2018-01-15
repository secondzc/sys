package com.tongyuan.client;

import com.tongyuan.model.DTO.FileTypeDto;
import com.tongyuan.model.domain.Attachment;
import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.domain.FileType;
import com.tongyuan.model.service.AttachmentService;
import com.tongyuan.model.service.DirectoryService;
import com.tongyuan.model.service.FileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by xyx on 2017-11-23.
 */
@Controller
public class DefalutAction {
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private FileTypeService fileTypeService;
    @Autowired
    private AttachmentService attachmentService;

    public void DefalutDo(){
        List<Directory> defaultModel = directoryService.selectDefaultDirectory();
        //默认图标
        FileTypeDto fileTypeDto = fileTypeService.getDefaultIcon();
        if(defaultModel.size() == 0){
            Directory publicDir = new Directory();
            publicDir.setName("公有模型");
            publicDir.setParentId(0);
            publicDir.setDeleted(false);
            directoryService.add(publicDir);
        }
        if(fileTypeDto == null){
          Long attachmentId = attachmentService.addIconOfModel("default","/img/default.jpg", (long) 8931);
          Attachment attachment = attachmentService.queryById(attachmentId);
          attachment.setModelId(-2);
          FileType fileType = new FileType();
          fileType.setAttachmentId(attachmentId);
          fileType.setExt("default");
          fileTypeService.add(fileType);
        }
    }
}
