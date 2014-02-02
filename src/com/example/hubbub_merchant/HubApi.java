package com.example.hubbub_merchant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class HubApi {
	
	public static HttpResponse write(JSONObject params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;

	    try {
	        HttpPost request = new HttpPost("http://yoururl");
	        request.addHeader("content-type", "application/x-www-form-urlencoded");
	        response = httpClient.execute(request);
	        
	        return response;
	    }catch (Exception ex) {
	    	System.out.println(ex.getMessage().toString());
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	    }
	    
	    return null;
		
	}
	
	public static void read() {
		
	}
}
