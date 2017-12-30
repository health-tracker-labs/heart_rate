package com.sergtm.db;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * Created by Sergey on 17.07.2017.
 */

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Profile("MySQL")
@PropertySource({"classpath:mySql.properties"})
public class HibernateMySQLConfiguration extends BaseConfiguration{

}