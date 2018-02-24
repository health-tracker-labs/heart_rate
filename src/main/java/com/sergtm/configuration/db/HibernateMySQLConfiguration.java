package com.sergtm.configuration.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Sergey on 17.07.2017.
 */

@Configuration
@Profile("MySQL")
@PropertySource({"classpath:mySql.properties"})
public class HibernateMySQLConfiguration extends BaseConfiguration {

}