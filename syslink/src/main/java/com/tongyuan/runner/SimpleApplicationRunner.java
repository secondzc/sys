package com.tongyuan.runner;

import com.tongyuan.client.DefalutAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Created by Format on 2017/5/2.
 */
@Component
@Controller
public class SimpleApplicationRunner implements ApplicationRunner {
    @Autowired
    private  DefalutAction defalutAction;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===== SimpleApplicationRunner");;
       //默认增加公有模型，私有模型的目录
        defalutAction.DefalutDo();
    }
}
