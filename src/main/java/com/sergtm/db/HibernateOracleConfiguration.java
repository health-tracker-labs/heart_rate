package com.sergtm.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("oracle")
@PropertySource({"classpath:oracle.properties"})
public class HibernateOracleConfiguration extends BaseConfiguration{

}
