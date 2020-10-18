package com.anuj.elasticsearch;

import static springfox.documentation.builders.PathSelectors.any;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ESConfiguration {

	
//    @Bean
//    TransportClient transportClient() throws UnknownHostException  {
//        return new PreBuiltTransportClient(
//            Settings.builder()
//                .put(ClusterName.CLUSTER_NAME_SETTING.getKey(), "docker-cluster")
//                .build()
//            )
//            .addTransportAddress(new TransportAddress(
//                InetAddress.getByName("localhost"), 9200)).addTransportAddress(new TransportAddress(
//                        InetAddress.getByName("localhost"), 9300));
//    }

    
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("ElasticSearchProject").select()
                .apis(RequestHandlerSelectors.basePackage("com.anuj.elasticsearch"))
                .paths(any()).build().apiInfo(new ApiInfo("Elastic Search",
                        "A set of services to provide data access from ElasticSearch", "1.0.0", null,
                        new Contact("Anuj Parmar", "https://github.com/anujparmar545/elasticsearch", null),null, null));
    }
	
	@Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
