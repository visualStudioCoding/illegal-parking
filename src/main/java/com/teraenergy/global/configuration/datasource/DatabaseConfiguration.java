package com.teraenergy.global.configuration.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@PropertySource("classpath:/application.yml")
public class DatabaseConfiguration {
	@Primary
	@Bean(name = "parkingDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.parking")
	public DataSource parkingDataSource() {
		return DataSourceBuilder.create().build();
	}

}