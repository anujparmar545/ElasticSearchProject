package com.anuj.elasticsearch;


import static springfox.documentation.builders.PathSelectors.any;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@ComponentScans(value = {@ComponentScan(value="com.anuj.elasticsearch")})
public class ElasticSearchProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchProjectApplication.class, args);
	}

	  
	  
	  

	
}
