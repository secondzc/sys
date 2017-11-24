package com.tongyuan.initializers;

import com.tongyuan.client.DefalutAction;
import com.tongyuan.model.domain.SimpleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Format on 2017/5/1.
 */
public class SimpleApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if(applicationContext instanceof AnnotationConfigEmbeddedWebApplicationContext) {
            ((AnnotationConfigEmbeddedWebApplicationContext)applicationContext).getBeanFactory().registerSingleton("testBean", new SimpleBean("id-001", "created by initializer"));

        }
    }
}
