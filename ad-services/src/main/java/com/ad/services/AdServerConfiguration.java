package com.ad.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "adserverEntityManager", transactionManagerRef = "adserverTransactionManager", basePackages = { "com.ad.server" })
public class AdServerConfiguration {

  @Value("${spring.datasource.adserver.username}")
  private String username;

  @Value("${spring.datasource.adserver.password}")
  private String password;

  @Value("${spring.datasource.adserver.url}")
  private String url;

  @Value("${spring.datasource.adserver.driver-class-name}")
  private String driver;

  @Primary
  @Bean
  public DataSource adserverDataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  @Primary
  @Bean(name = "adserverEntityManager")
  public LocalContainerEntityManagerFactoryBean adserverEntityManagerFactory(final EntityManagerFactoryBuilder builder) {
    return builder.dataSource(adserverDataSource()).packages("com.ad.server").persistenceUnit("adserver").build();
  }
}
