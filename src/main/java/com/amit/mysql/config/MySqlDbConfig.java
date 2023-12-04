package com.amit.mysql.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactoryBean",
		transactionManagerRef = "transactionManager", 
		basePackages = {
		"com.amit.mysql.repo" 
				}

)
public class MySqlDbConfig {

	@Autowired
	private Environment environment;

	// DataSource

	@Bean
	@Primary
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getProperty("spring.datasource.Url"));
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));

		return dataSource;
	}

	// EntityManagerFactory

	@Bean(name= "entityManagerFactoryBean")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

		bean.setDataSource(dataSource());

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

		bean.setJpaVendorAdapter(adapter);

		HashMap<String, String> props = new HashMap<>();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "update");
		bean.setJpaPropertyMap(props);
		bean.setPackagesToScan("com.amit.mysql.entity");

		return bean;
	}

	// tranctionManazer

	@Bean(name= "transactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return manager;
	}

}
