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
            Directory privateModel = new Directory();
            privateModel.setName("我的模型");
            privateModel.setParentId(0);
            privateModel.setDeleted(false);
            directoryService.add(privateModel);
        }
        if(defaultModel.size() == 1){
            defaultModel.get(0).setName("公有模型");
            defaultModel.get(0).setDeleted(false);
            directoryService.update(defaultModel.get(0));
            Directory privateModel = new Directory();
            privateModel.setName("我的模型");
            privateModel.setParentId(0);
            privateModel.setDeleted(false);
            directoryService.add(privateModel);
        }
    }
}
