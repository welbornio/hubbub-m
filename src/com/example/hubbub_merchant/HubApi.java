package com.example.hubbub_merchant;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Looper;

public class HubApi {
	
	public static void write(JSONObject params) {
		
		HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        Looper.prepare();
        
        HttpPost post = new HttpPost("http://hubbubapp.herokuapp.com/api/v1/interactions");
        
        try {
        	StringEntity se = new StringEntity( params.toString() );
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
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
	
}
