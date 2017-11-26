package com.tongyuan;

import com.tongyuan.client.CxfClient;
import com.tongyuan.client.DefalutAction;
import com.tongyuan.model.domain.SimpleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
public class SyslinkApplication extends SpringBootServletInitializer {
    @Autowired
    private static CxfClient cxfClient;
/*    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }*/
    /**
     * 实现SpringBootServletInitializer可以让spring-boot项目在web容器中运行
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }

    public static void main(String[] args) {
//        SpringApplication.run(SyslinkApplication.class, args);

        ApplicationContext applicationContext = SpringApplication.run(SyslinkApplication.class, args);
        SimpleBean sb = applicationContext.getBean("testBean", SimpleBean.class);
        System.out.println("id: " + sb.getId() + ", name: " + sb.getName());
        cxfClient.cl1();

    }


    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }

    /**
     * 文件上传配置
     * @return
     */
    /*@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }*/

    //增加一个SpringMVC的DispatcherServlet，接收前台/api开头的请求
    /*@Bean
    public ServletRegistrationBean apiV1ServletBean(WebApplicationContext wac) {
        DispatcherServlet servlet = new DispatcherServlet(wac);
        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/api");
        bean.setName("ApiServlet");
        return bean;
    }*/
}