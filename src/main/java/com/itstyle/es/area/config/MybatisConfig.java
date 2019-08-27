package com.itstyle.es.area.config;



import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.itstyle.es.area.entity.DatabaseType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;



@Configuration
@MapperScan(basePackages="com.itstyle.es.area.mapper", sqlSessionFactoryRef="sessionFactory")
public class MybatisConfig {

    @Autowired
    Environment environment;

    @Value("${datasource.jdbc.driverClassName}")
    private String dbDriver;

    @Value("${datasource.jdbc.url}")
    private String dbUrl;

    @Value("${datasource.jdbc.username}")
    private String dbUsername;

    @Value("${datasource.jdbc.password}")
    private String dbPassword;

    @Value("${datasource.jdbc.url_stg}")
    private String dbUrl_stg;

    @Value("${datasource.jdbc.username_stg}")
    private String dbUsername_stg;

    @Value("${datasource.jdbc.password_stg}")
    private String dbPassword_stg;


    /**
     * 创建 local环境 dataSource
     * @throws Exception
     */
    @Bean(name="dataSourceFunctional")
    public DataSource dataSourceLocal() throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    /**
     * 创建 回归环境 dataSource
     * @throws Exception
     */
    @Bean(name="dataSourceRegression")
    public DataSource dataSourceStaging() throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl_stg);
        dataSource.setUsername(dbUsername_stg);
        dataSource.setPassword(dbPassword_stg);

        return dataSource;
    }

    /**
     * 1、创建动态数据源
     */
    @Bean(name="dynamicDataSource")
    @Primary
    public DynamicDataSource DataSource(@Qualifier("dataSourceFunctional") DataSource dataSourceFunctional,
                                        @Qualifier("dataSourceRegression") DataSource dataSourceRegression){
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DatabaseType.function, dataSourceFunctional);
        targetDataSource.put(DatabaseType.regression, dataSourceRegression);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(dataSourceFunctional);

        return dataSource;
    }

    /**
     * 2、根据数据源创建SqlSessionFactory
     */
//    @Bean(name="sessionFactory")
//    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource")DynamicDataSource dataSource) throws Exception{
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sessionFactoryBean.setMapperLocations(resolver.getResources(environment.getProperty("mybatis.mapperLocations")));    //*mapper.xml位置
//        return sessionFactoryBean.getObject();
//    }
}