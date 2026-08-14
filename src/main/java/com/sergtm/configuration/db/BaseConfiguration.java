package com.sergtm.configuration.db;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static com.sergtm.configuration.db.BaseConfiguration.JPA_REPOSITORIES_PACKAGE;
import static org.hibernate.cfg.JdbcSettings.DIALECT;
import static org.hibernate.cfg.SchemaToolingSettings.HBM2DDL_AUTO;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {JPA_REPOSITORIES_PACKAGE})
public class BaseConfiguration {
    private static final String[] MODELS_PACKAGES = {
            "com.sergtm.entities",
            "com.sergtm.health.tracker.persistence.entity"
    };
    public static final String JPA_REPOSITORIES_PACKAGE =
            "com.sergtm.health.tracker.persistence.repository";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            Properties hibernateProperties
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan(MODELS_PACKAGES);

        em.setJpaProperties(hibernateProperties);

        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    Properties hibernateProperties(Environment env) {
        Properties properties = new Properties();
        properties.put(DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }
}
