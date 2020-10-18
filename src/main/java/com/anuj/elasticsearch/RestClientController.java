package com.anuj.elasticsearch;


import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.tasks.ElasticsearchException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/rest")
@Validated
@Api(value="RestClientController", description = "Crud Ops using Rest Client", tags=("RestClientController"))
public class RestClientController {

	 RestHighLevelClient client=RestClientConfiguration.getRestHighLevelClientInstance();
	
	 RestClient lowLevelClient = client.getLowLevelClient(); 
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/createIndex")
	@ApiOperation(value="createIndex", notes="create Index:company, type:employees in elasticserach", nickname="createIndex")
    public String createIndex(@RequestParam String index,@RequestParam String type,@RequestParam JSONObject obj ){
			try {
			IndexRequest req = new IndexRequest(index, type, "1"/*optional*/);
			req.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
			req.source(obj.toString(), XContentType.JSON);
			
			IndexResponse resp=client.index(req,RequestOptions.DEFAULT);
		
			if(resp.getResult()==null)
				return "Some error occured";
			else
				return "Index created successfully";
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
    }
	
	@RequestMapping(method = RequestMethod.DELETE,value = "/deleteIndex")
	@ApiOperation(value="insertData", notes="insert data into company index", nickname="deleteIndex")
    public String insertData(@RequestParam String indexName) {
        
		try {
		    DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		    AcknowledgedResponse resp=client.indices().delete(request, RequestOptions.DEFAULT);
		
			if(!resp.isAcknowledged())
				return "Some error occured";
			else
				return "Index deleted successfully";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
    }
	
	@RequestMapping(method = RequestMethod.POST,value = "/insert")
	@ApiOperation(value="insertData", notes="insert data into company index", nickname="insertData")
    public String insertData(@RequestParam String id,@RequestParam String name,@RequestParam String age) {
        
		try {
			JSONObject obj=new JSONObject();
			obj.put("name",	name);
			obj.put("age",	age);

			IndexRequest req = new IndexRequest("company", "employees", id);
			req.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
			req.source(obj.toString(), XContentType.JSON);
			IndexResponse resp=client.index(req,RequestOptions.DEFAULT);
		
			if(resp.getResult()==null)
				return "Some error occured";
			else
				return "Data inserted successfully";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
    }
	
	@RequestMapping(method = RequestMethod.POST,value = "/upsert")
	@ApiOperation(value="upsertData", notes="update data into company index and employee type", nickname="upsertData")
    public UpdateResponse upsertData(@RequestParam String id,@RequestParam String name,@RequestParam String age) {
        
		try { 
		IndexRequest indexRequest = new IndexRequest("company", "employees", id)
		        .source(XContentFactory.jsonBuilder()
		            .startObject()
		                .field("name",name)
		                .field("age", age)
		            .endObject());
		UpdateRequest updateRequest = new UpdateRequest("company", "employees", id)
		        .doc(XContentFactory.jsonBuilder()
		            .startObject()
		                .field("name", name)
		                .field("age", age)
		            .endObject())
		        .upsert(indexRequest);              
		UpdateResponse resp=client.update(updateRequest,RequestOptions.DEFAULT);
		return resp;
		
//		if(resp.getResult()==null)
//				return "Some error occured";
//			else
//				return "Data upserted successfully";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
    }
	
	

	@RequestMapping(method = RequestMethod.DELETE,value = "/delete")
	@ApiOperation(value="deleteData", notes="delete data into company index and employee type", nickname="deleteData")
    public String deleteData(@RequestParam String index,@RequestParam String type,@RequestParam String id) {
        
		try {
		DeleteRequest request = new DeleteRequest(index, type, id) ;
		DeleteResponse response= client.delete(request,RequestOptions.DEFAULT);

		if(response.getResult()==null)
				return "Some error occured";
			else
				return "Data deleted successfully";
    
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
	}
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/search")
	@ApiOperation(value="search", notes="update data into company index and employee type", nickname="searchData")
    public GetResponse searchData(@RequestParam String index,@RequestParam String docType) {
        
		try { 
			GetRequest request =new GetRequest();
			request.index(index);
			GetResponse resp=client.get(request, RequestOptions.DEFAULT);
//			SearchRequest searchRequest = new SearchRequest(index);
//			//searchRequest.preference(docType); 
//			QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", docType);
//			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//			sourceBuilder.query(matchQueryBuilder);
//			searchRequest.source(sourceBuilder);
//		SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
		return resp;
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
    }
}
