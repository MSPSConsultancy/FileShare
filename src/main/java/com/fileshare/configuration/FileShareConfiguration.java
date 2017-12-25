package com.fileshare.configuration;


import com.fileshare.repository.ItemRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
public class FileShareConfiguration {

    public static final String DATASOURCE_USERNAME = "datasource.username";
    public static final String DATASOURCE_PASSWORD = "datasource.password";
    public static final String DATASOURCE_URL = "datasource.url";
    public static final String PACKAGES_SCAN_MODEL = "com.fileshare.model";

    @Autowired
    private Environment environment;


    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(environment.getProperty(DATASOURCE_USERNAME));
        dataSource.setPassword(environment.getProperty(DATASOURCE_PASSWORD));
        dataSource.setUrl(environment.getProperty(DATASOURCE_URL));

        return  dataSource;
    }
    @Bean (name = "sessionFactory")
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder
                .scanPackages("com.fileshare.model")
                .addProperties(getHibernateProperties());
        return builder.buildSessionFactory();
    }


    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return prop;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST","GET", "DELETE","OPTIONS", "HEAD")
                        .allowedHeaders("*");
            }
        };
    }


}
