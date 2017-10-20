package com.tongyuan.webservice;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.tongyuan.gogs.dao", sqlSessionTemplateRef  = "test2SqlSessionTemplate")
public class DataSource2Config {

//    public static final String SIGN = "gogs"; // 唯一标识

//    @Value("${" + SIGN + ".pagehelper.offsetAsPageNum}")
//    private String offsetAsPageNum;
//
//    @Value("${" + SIGN + ".pagehelper.helperDialect}")
//    private String helperDialect;


    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 分页拦截器-begin
        PageInterceptor interceptor = new PageInterceptor();
        Properties p = new Properties();
      //  properties.setProperty("helperDialect", "com.github.pagehelper.dialect.helper.SqlServerDialect");
       // properties.setProperty("offsetAsPageNum", "true");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("returnPageInfo", "check");
        p.setProperty("params", "count=countSql");
        interceptor.setProperties(p);
      //  bean.getObject().getConfiguration().addInterceptor(interceptor);
        Interceptor[] plugins = new Interceptor[]{interceptor};
        bean.setPlugins(plugins);
        //        properties.setProperty("helperDialect", helperDialect);
        //        properties.setProperty("offsetAsPageNum", offsetAsPageNum);
        // 分页拦截器-end
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/DataSource2/*.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/DataSource2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "test2TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test2SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
