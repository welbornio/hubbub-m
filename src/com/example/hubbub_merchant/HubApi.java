//package com.example.hubbub_merchant;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.protocol.HTTP;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import android.os.Looper;
//
//public class HubApi {
//	
//	public static HttpResponse theResponse;
//	
//	public HubApi() {
//		
//	}
//	
//	public static HttpResponse write(final String userName, final String userId, final String merchantName, final String mechantId, final String metric) {
//		
//		new Thread(new Runnable() {
//			public void run() {
//				
//				HttpClient client = new DefaultHttpClient();
//		        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
//		        HttpResponse response;
//		        Looper.prepare();
//		        
//		        HttpPost post = new HttpPost("https://hubbubapp.herokuapp.com/api/v1/interactions");
//		        JSONObject data = new JSONObject();
//		        
//		        try {
//		        	data.put("merchantId", "53158607a71a5902008f6528");
//		        	data.put("clientId", userId);
//		        	data.put("metric", metric);
//		            
//		        	StringEntity se = new StringEntity(data.toString());
//		        	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//		            post.setEntity(se);
//		            response = client.execute(post);
//		            
//		            /*Checking response */
//		            if(response != null){
//		            	HubApi.theResponse = response;
//		                InputStream in = response.getEntity().getContent(); //Get the data in the entity
//		            }
//		            
//		        }
//		        catch(Exception e) {
//		        	e.printStackTrace();
//		        }
//		        finally {
//		        	
//		        }
//		        
//		        Looper.loop();
//				
//			}
//		}).start();
//		
//		Thread.yield();
//		return theResponse;
//	}
//	
//}
