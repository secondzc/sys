package com.tongyuan.client;

import com.tongyuan.model.domain.Directory;
import com.tongyuan.model.service.DirectoryService;
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

    public void DefalutDo(){
        List<Directory> defaultModel = directoryService.selectDefaultDirectory();
        if(defaultModel.size() == 0){
            Directory publicDir = new Directory();
            publicDir.setName("公有模型");
            publicDir.setParentId(0);
            publicDir.setDeleted(false);
            directoryService.add(publicDir);
        }
    }
}
