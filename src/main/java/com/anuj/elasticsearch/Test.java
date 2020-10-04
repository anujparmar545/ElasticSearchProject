package com.anuj.elasticsearch;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class Test {

	@Value("classpath:school-index.json") 
	private Resource index;
	
	@Autowired 
	private static TransportClient client;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final ClusterHealthResponse response = client
			    .admin()
			    .cluster()
			    .prepareHealth()
			    .setWaitForGreenStatus()
			    .setTimeout(TimeValue.timeValueSeconds(5))
			    .execute()
			    .actionGet();
			 
			if(response.isTimedOut()) {
				System.out.println("The cluster is unhealthy: %s"+response.getStatus());
			}else {System.out.println("Cluster Healthy");}
			  
			final IndicesExistsResponse response1 = client
				    .admin()
				    .indices()
				    .prepareExists("school")
				    .get(TimeValue.timeValueMillis(100));
				         
				if (!response1.isExists()) {
				    System.out.println("Index Present");
				}
	}

}
