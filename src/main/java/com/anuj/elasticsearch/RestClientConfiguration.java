package com.anuj.elasticsearch;


import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class RestClientConfiguration {

	 	
	/*
	 * @Bean public RestHighLevelClient elasticsearchClient() {
	 * 
	 * // final ClientConfiguration clientConfiguration =
	 * ClientConfiguration.builder().connectedTo("localhost:9200","localhost:9300")
	 * .build(); // // return RestClients.create(clientConfiguration).rest(); //
	 * RestHighLevelClient client = new RestHighLevelClient( RestClient.builder(new
	 * HttpHost("localhost",9200,"http"),new HttpHost("localhost",9300,"http")));
	 * 
	 * return client;
	 * 
	 * }
	 */
	

    private static RestHighLevelClient restHighLevelClient=null;

    
    public  static RestHighLevelClient getRestHighLevelClientInstance()  {
    	if(restHighLevelClient==null) {
    	try {
    		restHighLevelClient = new RestHighLevelClient(RestClient.builder( new HttpHost("localhost", 9200, "http"),
                    																												 new HttpHost("localhost", 9300, "http")));       
            
    		return restHighLevelClient;
        	} 
        catch (Exception e) {
            e.printStackTrace();
        }
    	
    	} 
        return restHighLevelClient;
    }
	
	
	
}
