package com.woopaca.likeknu.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.woopaca.univclub",
        entityManagerFactoryRef = "univClubEntityManagerFactory",
        transactionManagerRef = "univClubTransactionManager"
)
public class UnivClubDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.univ-club")
    public DataSource univClubDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean univClubEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("univClubDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.woopaca.univclub")
                .persistenceUnit("univ-club")
                .build();
    }

    @Bean
    public PlatformTransactionManager univClubTransactionManager(
            @Qualifier("univClubEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
