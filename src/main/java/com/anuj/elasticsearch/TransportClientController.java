package com.anuj.elasticsearch;


import java.io.IOException;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
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
@RequestMapping(value="/test")
@Validated
@Api(value="TransportClientController", description = "Crud Ops using Transport Client", tags=("TransportClientController"))
public class TransportClientController {

	
	@Autowired 
	private  TransportClient client;
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/createIndex")
	@ApiOperation(value="createIndex", notes="create Index:company, type:employees in elasticserach", nickname="createIndex")
    public String createIndex(@RequestParam String index,@RequestParam String type,@RequestParam JSONObject obj ){
			
			IndexRequest req = new IndexRequest(index, type, "1"/*optional*/);
			req.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
			req.source(obj.toString(), XContentType.JSON);
			ActionFuture<IndexResponse> af=client.index(req);
		
			if(af.isCancelled())
				return "Some error occured";
			else
				return "Index created successfully";
    }
	
	@RequestMapping(method = RequestMethod.POST,value = "/insert")
	@ApiOperation(value="insertData", notes="insert data into company index", nickname="insertData")
    public String insertData(@RequestParam String id,@RequestParam String name,@RequestParam String age) throws JSONException{
        
			JSONObject obj=new JSONObject();
			obj.put("name",	name);
			obj.put("age",	age);

			IndexRequest req = new IndexRequest("company", "employees", id);
			req.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
			req.source(obj.toString(), XContentType.JSON);
			ActionFuture<IndexResponse> af=client.index(req);
		
			if(af.isCancelled())
				return "Some error occured";
			else
				return "Data inserted successfully";
    }
	
	@RequestMapping(method = RequestMethod.POST,value = "/update")
	@ApiOperation(value="updateData", notes="update data into company index and employee type", nickname="updateData")
    public String updateData(@RequestParam String id,@RequestParam String name,@RequestParam String age) throws Exception{
        
		 
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
		            .endObject())
		        .upsert(indexRequest);              
		ActionFuture<UpdateResponse> response= client.update(updateRequest);

		
			if(response.isCancelled())
				return "Some error occured";
			else
				return "Data upserted successfully";
    }
	
	

	@RequestMapping(method = RequestMethod.DELETE,value = "/delete")
	@ApiOperation(value="deleteData", notes="delete data into company index and employee type", nickname="updateData")
    public String deleteData(@RequestParam String index,@RequestParam String type,@RequestParam String id) throws Exception{
        
		
		DeleteRequest request = new DeleteRequest(index, type, id) ;
		ActionFuture<DeleteResponse> response= client.delete(request);

		
			if(response.isCancelled())
				return "Some error occured";
			else
				return "Data deleted successfully";
    }
	
}
