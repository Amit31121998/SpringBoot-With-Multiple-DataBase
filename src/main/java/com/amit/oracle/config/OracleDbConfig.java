package com.amit.oracle.config;

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
		entityManagerFactoryRef = "secondEntityManagerFactoryBean",
		transactionManagerRef = "secondTransactionManager",
		basePackages = { "com.amit.oracle.repo" }

)
public class OracleDbConfig {
	
	@Autowired
	private Environment environment;
	
	
	// DataSource
	
	@Bean(name= "secondDataSource")
	@Primary
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getProperty("second.datasource.Url"));
		dataSource.setDriverClassName(environment.getProperty("second.datasource.driver-class-name"));
		dataSource.setPassword(environment.getProperty("second.datasource.password"));
		dataSource.setUsername(environment.getProperty("second.datasource.username"));
		
		return dataSource;
	}
	
	//EntityManagerFactory
	
	@Bean(name= "secondEntityManagerFactoryBean")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		
		bean.setDataSource(dataSource());
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		
		bean.setJpaVendorAdapter(adapter);
		
		
		HashMap<String, String> props = new HashMap<>();
		props.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "update");
		bean.setJpaPropertyMap(props);
		bean.setPackagesToScan("com.amit.oracle.entity");
		
		return bean;
	}
	
	//tranctionManazer
	
	@Primary
	@Bean(name= "secondTransactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		
		return manager;
	}
}
