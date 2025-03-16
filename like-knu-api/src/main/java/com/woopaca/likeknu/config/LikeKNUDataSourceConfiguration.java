package com.woopaca.likeknu.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile("!test")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.woopaca.likeknu",
        entityManagerFactoryRef = "likeKnuEntityManagerFactory",
        transactionManagerRef = "likeKnuTransactionManager"
)
public class LikeKNUDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.like-knu")
    public DataSource likeKnuDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean likeKnuEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("likeKnuDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.woopaca.likeknu")
                .persistenceUnit("like-knu")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager likeKnuTransactionManager(
            @Qualifier("likeKnuEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
