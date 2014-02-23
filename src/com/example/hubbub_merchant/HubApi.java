package com.example.hubbub_merchant;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.os.Looper;

public class HubApi {
	
	final String publicKeyString = "MCIwDQYJKoZIhvcNAQEBBQADEQAwDgIJAMVbnWIwX5q1AgED";
	static PublicKey publicKey = null;
	
	public HubApi() {
		KeyFactory fact;
		X509EncodedKeySpec spec;
		try {
			byte[] data = publicKeyString.getBytes();
		    spec = new X509EncodedKeySpec(data);	
		    fact = KeyFactory.getInstance("RSA");
		    publicKey = fact.generatePublic(spec);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void write(String userIdVal, String metricVal) {
		
		final String thisMerchantId = "34321ssrtl";
		
		final String userId = userIdVal;
		final String metric = metricVal;
		
		new Thread(new Runnable() {
			public void run() {
				
				HttpClient client = new DefaultHttpClient();
		        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
		        HttpResponse response;
		        Looper.prepare();
		        
		        HttpPost post = new HttpPost("http://hubbubapp.herokuapp.com/api/v1/interactions");
		        JSONObject parentData = new JSONObject();
		        JSONObject childData = new JSONObject();
		        
		        try {
		        	parentData.put("merchantId", thisMerchantId);
		        	
		        	Cipher cipher = Cipher.getInstance("RSA");
		        	cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		            childData.put("clientId", userId);
		            childData.put("metric", metric);
		            parentData.put("data", cipher.doFinal(childData.toString().getBytes()).toString());
		            
		            post.setEntity( new ByteArrayEntity(parentData.toString().getBytes("UTF8")) );
		            response = client.execute(post);
		            
		            /*Checking response */
		            if(response!=null){
		                InputStream in = response.getEntity().getContent(); //Get the data in the entity
		            }
		        }
		        catch(Exception e) {
		        	e.printStackTrace();
		        }
		        
		        Looper.loop();
				
			}
		}).start();
		
	}
	
}
