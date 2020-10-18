package com.anuj.elasticsearch;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@ComponentScans(value = {@ComponentScan(value="com.anuj.elasticsearch")})
public class ElasticSearchProjectApplication {


	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchProjectApplication.class, args);
	}

	  
	  
	  

	
}
