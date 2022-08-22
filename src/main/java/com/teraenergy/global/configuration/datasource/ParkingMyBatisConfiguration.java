package com.teraenergy.global.configuration.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.yml")
public class ParkingMyBatisConfiguration {

	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;

	@Value("${mybatis.config-location}")
	private String configPath;

	@Primary
	@Bean(name = "parkingSqlSessionFactory")
	public SqlSessionFactory parkingSqlSessionFactory(
			@Qualifier("parkingDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//MyBatis config파일 위치
		Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource(configPath);
		sqlSessionFactoryBean.setConfigLocation(myBatisConfig);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));

		return sqlSessionFactoryBean.getObject();
	}

	@Primary
	@Bean(name = "parkingSqlSessionTemplate")
	public SqlSessionTemplate parkingSqlSessionTemplate(
			@Qualifier("parkingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Primary
	@Bean(name = "parkingDataSourceTransactionManager")
	public DataSourceTransactionManager parkingTransactionManager (@Qualifier("parkingDataSource") DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
}
