package com.teraenergy;

import com.teraenergy.global.configuration.CustomBeanNameGenerator;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEncryptableProperties
public class IllegalParkingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		final SpringApplicationBuilder builder = new SpringApplicationBuilder(IllegalParkingApplication.class);
		builder.beanNameGenerator(new CustomBeanNameGenerator());
		builder.run(args);
	}

}
